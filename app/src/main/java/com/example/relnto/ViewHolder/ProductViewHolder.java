package com.example.relnto.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relnto.Interface.ItemClassListener;
import com.example.relnto.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName , txtProductDescription ,txtProductPrice;
    public ImageView imageView;
    public ItemClassListener listener;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);

    }

    public void setItemClickListener(ItemClassListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {

        listener.onClick(view , getAdapterPosition(), false);
    }
}
