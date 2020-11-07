package com.example.relnto.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.relnto.Interface.ItemClassListener;
import com.example.relnto.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView orderId,days,status;
    private ItemClassListener itemClassListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderId = itemView.findViewById(R.id.order_view_id);
        days = itemView.findViewById(R.id.order_view_days);
        status = itemView.findViewById(R.id.order_view_status);
    }

    @Override
    public void onClick(View view) {

        itemClassListener.onClick(view,getAdapterPosition(),false);

    }

    public void setItemClassListener(ItemClassListener itemClassListener)
    {
        this.itemClassListener = itemClassListener;
    }
}
