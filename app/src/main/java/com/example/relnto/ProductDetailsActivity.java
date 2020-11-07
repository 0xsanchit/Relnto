package com.example.relnto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.relnto.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private Button addToCartBtn;
    private ImageView productImage,pImage;
    private ElegantNumberButton numberButton;
    private TextView productPrice,productDescription,productName,totalPrice;
    private String productID = "" , state = "Normal";
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pId");

        firebaseAuth = FirebaseAuth.getInstance();

        addToCartBtn = (Button) findViewById(R.id.pd_add_to_cart_button);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        //pImage = (ImageView) findViewById(R.id.product_image_details);
        numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        productName = (TextView) findViewById(R.id.product_name_details);
        productDescription = (TextView) findViewById(R.id.product_description_details);
        productPrice = (TextView) findViewById(R.id.product_price_details);
        //totalPrice = (TextView) findViewById(R.id.textView2);

        getProductDetails(productID);




        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetailsActivity.this,ConfirmFinalOrderActivity.class);
                intent.putExtra("pId",productID);
                intent.putExtra("numberOfDays",numberButton.getNumber());
                intent.putExtra("price",productPrice.getText());
                startActivity(intent);
               // if(state.equals("Order Placed") || state.equals("Order Shipped"))
                //{
                  //  Toast.makeText(ProductDetailsActivity.this,"You can add more products only once your previous order is delivered",Toast.LENGTH_SHORT).show();

                //}
                //else
                //{
                    // addingToCartList();
                //}
                //Toast.makeText(ProductDetailsActivity.this,"Added product",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        //CheckOrderState();
    }



    private void addingToCartList() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart_List");
        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",productID);
        cartMap.put("pName",productName.getText().toString());
        cartMap.put("price",productPrice.getText().toString());
        cartMap.put("date",saveCurrentDate);
        cartMap.put("time",saveCurrentTime);
        cartMap.put("quantity",numberButton.getNumber());
        cartMap.put("discount", "");


        cartListRef.child("User_View").child("User").child("Products").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    cartListRef.child("Admin View").child(firebaseAuth.getCurrentUser().getUid()).child("items").child(productID).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ProductDetailsActivity.this,"Added to Cart List",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ProductDetailsActivity.this,RentHomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("items");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText("Product Name : " + products.getpName());
                    productDescription.setText("Product Description : " + products.getDescription());
                    productPrice.setText("Product Price per Day : " + products.getPrice());


                    Glide.with(getApplicationContext()).load(products.getUrl()).override(200,0).into(productImage);

                    //totalPrice.setText("Amount = " + String.valueOf(Integer.parseInt(productPrice.getText().toString())*(Integer.parseInt(numberButton.getNumber()))));






                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState()
    {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("orders").child(firebaseAuth.getCurrentUser().getUid());
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String shippingState = dataSnapshot.child("state").getValue().toString();

                    if(shippingState.equals("shipped"))
                    {
                       state = "Order Shipped" ;
                    }

                    else if(shippingState.equals("not shipped"))
                    {
                       state = "Order Placed" ;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}
