package com.example.tryonapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private List<Model> models;

    MyAdapter(Context mContext, List<Model> models){
        this.mContext = mContext;
        this.models = models;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mLayoutInflater = LayoutInflater.from(mContext);
        view = mLayoutInflater.inflate(R.layout.cardview_item_glasses, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.product_image.setImageResource(models.get(position).getImg());
        holder.product_brand.setText(models.get(position).getBrand());
        holder.product_model.setText(models.get(position).getModel());
        holder.product_price.setText(models.get(position).getPrice());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ProductActivity.class);
                intent.putExtra("brand", models.get(position).getBrand());
                intent.putExtra("model", models.get(position).getModel());
                intent.putExtra("price", models.get(position).getPrice());
                intent.putExtra("img", models.get(position).getImg());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }
}
