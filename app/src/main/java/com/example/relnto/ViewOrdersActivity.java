package com.example.relnto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.relnto.Model.Cart;
import com.example.relnto.Model.Orders;
import com.example.relnto.ViewHolder.CartViewHolder;
import com.example.relnto.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        recyclerView = findViewById(R.id.view_order);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firebaseAuth= FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        //CheckOrderState();

        //firebaseAuth = FirebaseAuth.getInstance();

        final DatabaseReference orderListRef = FirebaseDatabase.getInstance().getReference().child("orders");
        FirebaseRecyclerOptions<Orders> options =
                new FirebaseRecyclerOptions.Builder<Orders>()
                        .setQuery(orderListRef, Orders.class).build();

        FirebaseRecyclerAdapter<Orders, OrderViewHolder> adapter
                = new FirebaseRecyclerAdapter<Orders, OrderViewHolder>(options) {
            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.order_items_layout, parent, false);
                OrderViewHolder holder = new OrderViewHolder(view);
                return holder;
            }

            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder orderViewHolder, int i, @NonNull Orders orders) {
               if(orders.getOrderPlacedBy().contentEquals(firebaseAuth.getUid())) {
                   orderViewHolder.itemView.setVisibility(View.VISIBLE);
                   orderViewHolder.orderId.setText("Order Id = " + orders.getOrderId());
                   orderViewHolder.days.setText("Days = " + orders.getDays());
                   orderViewHolder.status.setText("Status = " + orders.getStatus());
                   orderViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 220));

                   //orderViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

               }
               else
              {
                  orderViewHolder.itemView.setVisibility(View.GONE);
                   orderViewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

               }
            }

            // @Override
            //protected void onBindViewHolder(@NonNull CartViewHolder holder, int i, @NonNull final Cart cart) {

            //  holder.txtProductQuantity.setText("Quantity = "+ cart.getQuantity());
            //holder.txtProductPrice.setText("Price = "+ cart.getPrice());
            //holder.txtProductName.setText("Name = "+ cart.getpName());

            //int oneTypeProductprice = ((Integer.valueOf(cart.getPrice()))) * ((Integer.valueOf(cart.getQuantity())));
            //overTotalPrice = overTotalPrice + oneTypeProductprice ;

                /*holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit",
                                        "Remove"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                        builder.setTitle("Cart Options : ");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(i==0)
                                {
                                    Intent intent = new Intent(CartActivity.this,ProductDetailsActivity.class);
                                    intent.putExtra("pid",cart.getPid());
                                    startActivity(intent);
                                }
                                if(i==1)
                                {
                                    cartListRef.child("Admin View").child(firebaseAuth.getCurrentUser().getUid()).child("Products").child(cart.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(CartActivity.this,"Item Removed Successfully",Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(CartActivity.this,RentHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        });
                        builder.show();
                    }
                }); */

            // txtTotalAmount.setText("Total Price = Rs. " + String.valueOf(overTotalPrice));


        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();


    }
}

