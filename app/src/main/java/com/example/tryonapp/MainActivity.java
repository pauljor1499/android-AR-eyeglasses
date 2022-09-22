package com.example.tryonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Model> mData = new ArrayList<>();

        mData.add(new Model("Ray-ban", "1299kr", "Clubmaster", R.drawable.clubmaster_brun));
        mData.add(new Model("Ray-ban", "999kr", "Wayfarer", R.drawable.glasogon_fyrkantiga));
        mData.add(new Model("Ray-ban", "1499kr", "Club round", R.drawable.runda_glasogon));
        mData.add(new Model("Oakley", "799kr", "Sport", R.drawable.sport_solglasogon));

        RecyclerView rv = findViewById(R.id.recyclerview_id);
        MyAdapter myAdapter = new MyAdapter(this, mData);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        rv.setAdapter(myAdapter);
    }
}
