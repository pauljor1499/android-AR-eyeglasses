package com.example.tryonapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView product_image;
    TextView product_brand;
    TextView product_model;
    TextView product_price;
    CardView cardView;

    MyViewHolder(@NonNull View itemView) {
        super(itemView);

        product_image = itemView.findViewById(R.id.card_img);
        product_brand = itemView.findViewById(R.id.Brand);
        product_model = itemView.findViewById(R.id.Model);
        product_price = itemView.findViewById(R.id.Price);
        cardView = itemView.findViewById(R.id.card_view_id);
    }
}
