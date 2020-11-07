package com.example.relnto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Final_Activity extends AppCompatActivity {


    private Button home1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_);

        home1 = (Button) findViewById(R.id.home);

        home1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Final_Activity.this,HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
