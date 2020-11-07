package com.example.relnto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

public class LendHomeActivity extends AppCompatActivity {

    private ImageView books,sports,mobiles,laptops,bags,watches,tshirts,camera,shoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend_home);

        books = (ImageView) findViewById(R.id.books);
        sports = (ImageView) findViewById(R.id.sports);
        mobiles = (ImageView) findViewById(R.id.mobiles);
        laptops = (ImageView) findViewById(R.id.laptops);
        bags = (ImageView) findViewById(R.id.bags);
        watches = (ImageView) findViewById(R.id.watches);
        tshirts = (ImageView) findViewById(R.id.tshirts);
        camera = (ImageView) findViewById(R.id.camera);
        shoes = (ImageView) findViewById(R.id.shoes);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                        intent.putExtra("category","books");
                startActivity(intent);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","sports");
                startActivity(intent);
            }
        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","mobiles");
                startActivity(intent);
            }
        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","laptops");
                startActivity(intent);
            }
        });

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","bags");
                startActivity(intent);
            }
        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","watches");
                startActivity(intent);
            }
        });

        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","tshirts");
                startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","camera");
                startActivity(intent);
            }
        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendHomeActivity.this,AddProductForLending.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });







    }
}
