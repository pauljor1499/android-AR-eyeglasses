package com.example.tryonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        ImageView product_img = findViewById(R.id.product_img_id);
        TextView product_brand = findViewById(R.id.product_brand_id);
        TextView product_model = findViewById(R.id.product_model_id);
        TextView product_price = findViewById(R.id.product_price_id);
        Button tryOnButton = findViewById(R.id.try_on_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String brand = bundle.getString("brand");
            String model = bundle.getString("model");
            String price = bundle.getString("price");
            int img = bundle.getInt("img");

            product_img.setImageResource(img);
            product_brand.setText(brand);
            product_model.setText(model);
            product_price.setText(price);
        }

        tryOnButton.setOnClickListener(buttonListener);
    }

    private View.OnClickListener buttonListener = view -> {

        //start ar fragment
        Intent intent = new Intent(this, GlassesActivity.class);
        startActivity(intent);

    };

}
