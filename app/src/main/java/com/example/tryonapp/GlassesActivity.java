package com.example.tryonapp;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.ArCoreApk;
import com.google.ar.core.AugmentedFace;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ArSceneView;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.AugmentedFaceNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GlassesActivity extends AppCompatActivity {

    private static final String TAG = GlassesActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ModelRenderable faceRegionsRenderable;
    List<ModelRenderable> glasses = new ArrayList<>();
    private final Map<AugmentedFace, AugmentedFaceNode> faceNodeMap = new HashMap<>();
    private boolean changeModel = false;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glasses);

        if(!checkIsSupportedDeviceOrFinish(this)){
            return;
        }

        Button next_button = findViewById(R.id.glasses_next);
        Button prev_button = findViewById(R.id.glasses_previous);

        next_button.setOnClickListener(v -> {

            changeModel = true;
            index++;
            if(index > glasses.size() - 1){
                index = glasses.size() - 1;
            }

            faceRegionsRenderable = glasses.get(index);
        });

        prev_button.setOnClickListener(v -> {

            changeModel = true;
            index--;
            if(index < 0){
                index = 0;

            }

            faceRegionsRenderable = glasses.get(index);
        });

        CustomArFragment arFragment =
                (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.ar_fragment);

        ModelRenderable.builder()
                .setSource(this, Uri.parse("wayfarer.sfb"))
                .build()
                .thenAccept(
                        modelRenderable -> {
                            glasses.add(modelRenderable);
                            faceRegionsRenderable = modelRenderable;
                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                        });

        ModelRenderable.builder()
                .setSource(this, Uri.parse("square_glasses2.sfb"))
                .build()
                .thenAccept(
                        modelRenderable -> {
                            glasses.add(modelRenderable);
                            faceRegionsRenderable = modelRenderable;
                            modelRenderable.setShadowCaster(false);
                            modelRenderable.setShadowReceiver(false);
                        });

        ArSceneView sceneView = Objects.requireNonNull(arFragment).getArSceneView();
        sceneView.setCameraStreamRenderPriority(Renderable.RENDER_PRIORITY_FIRST);
        Scene scene = sceneView.getScene();

        setSceneListener(scene, sceneView);

    }

    private void setSceneListener(Scene scene, ArSceneView sceneView) {

        scene.addOnUpdateListener(
                (FrameTime frameTime) -> {
                    if (faceRegionsRenderable == null) {
                        return;
                    }

                    Collection<AugmentedFace> faceList =
                            Objects.requireNonNull(sceneView.getSession()).getAllTrackables(AugmentedFace.class);

                    // Make new AugmentedFaceNodes for any new faces.
                    for (AugmentedFace face : faceList) {
                        if (!faceNodeMap.containsKey(face)) {
                            AugmentedFaceNode faceNode = new AugmentedFaceNode(face);
                            faceNode.setParent(scene);
                            faceNode.setFaceRegionsRenderable(faceRegionsRenderable);
                            faceNodeMap.put(face, faceNode);
                        }
                        //change the 3d-model
                        else if(changeModel){
                            Objects.requireNonNull(faceNodeMap.get(face)).setFaceRegionsRenderable(faceRegionsRenderable);
                        }
                    }
                    changeModel = false;
                    // Remove any AugmentedFaceNodes associated with an AugmentedFace that stopped tracking.
                    Iterator<Map.Entry<AugmentedFace, AugmentedFaceNode>> iter =
                            faceNodeMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<AugmentedFace, AugmentedFaceNode> entry = iter.next();
                        AugmentedFace face = entry.getKey();
                        if (face.getTrackingState() == TrackingState.STOPPED) {
                            AugmentedFaceNode faceNode = entry.getValue();
                            faceNode.setParent(null);
                            iter.remove();
                        }
                    }
                });

    }

    private boolean checkIsSupportedDeviceOrFinish(final AppCompatActivity activity) {

        if (ArCoreApk.getInstance().checkAvailability(activity)
                == ArCoreApk.Availability.UNSUPPORTED_DEVICE_NOT_CAPABLE) {
            Log.e(TAG, "Augmented Faces requires ARCore.");
            Toast.makeText(activity, "Augmented Faces requires ARCore", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE)))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
