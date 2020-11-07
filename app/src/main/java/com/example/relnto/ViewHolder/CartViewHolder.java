package com.example.relnto.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relnto.Interface.ItemClassListener;
import com.example.relnto.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice , txtProductQuantity ;
    private ItemClassListener itemClassListener;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);

//        txtProductPrice = itemView.findViewById(R.id.cart_product_price);
       // txtProductQuantity = itemView.findViewById(R.id.cart_product_quantity);
    }

    @Override
    public void onClick(View view) {

        itemClassListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClassListener(ItemClassListener itemClassListener) {

        this.itemClassListener = itemClassListener;
    }
}
