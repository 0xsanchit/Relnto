package com.example.relnto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ConfirmFinalOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nameEditText,phoneEditText,roomEditText;
    private Button confirmOrderBtn;
    private CheckBox checkBox;
    private TextView amount;

    private String totalAmount = "",price,numberOfDays,productId,Hostel,orderRandomKey;

    private Spinner spinner;

    private long millis;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_order);

        price = getIntent().getStringExtra("price");
        numberOfDays = getIntent().getStringExtra("numberOfDays");
        productId = getIntent().getStringExtra("pId");

        amount = (TextView) findViewById(R.id.Amount);
        checkBox = (CheckBox)findViewById(R.id.checkBox2);

        //totalAmount=String.valueOf(Integer.valueOf(price)*Integer.valueOf(numberOfDays));

        //amount.setText(totalAmount);


        //totalAmount = getIntent().getStringExtra("Total Price");
        //Toast.makeText(this,"Total Price = " + totalAmount , Toast.LENGTH_SHORT).show();

        confirmOrderBtn = (Button) findViewById(R.id.confirm_final_order_btn);
        nameEditText = (EditText) findViewById(R.id.shipment_name);
        phoneEditText = (EditText) findViewById(R.id.shipment_phone_number);
        roomEditText = (EditText) findViewById(R.id.shipment_room);

        final Spinner dropdown = (Spinner) findViewById(R.id.spinner);

        String[] items = new String[]{"Tunga", "Bhatra", "Sabarmati", "Tapti", "Tamraparani", "Sindhu", "Sharavati", "Sarayu", "Saraswathi", "Pampa", "Narmada", "Mahanadhi", "Krishna", "Jamuna", "Godavari", "Ganga", "Cauvery", "Brahmaputra", "Alaknanda"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        //spinner.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Hostel = dropdown.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your full Name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your phone number",Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(roomEditText.getText().toString()))
        {
            Toast.makeText(this,"Please provide your address",Toast.LENGTH_SHORT).show();
        }
        else if (!checkBox.isChecked())
        {
            Toast.makeText(this,"Please agree to the terms and conditions",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentDate,saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        millis = System.currentTimeMillis();
        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("orders");

        orderRandomKey = ordersRef.push().getKey();

        HashMap<String,Object> ordersMap = new HashMap<>();
        ordersMap.put("days",numberOfDays);
        ordersMap.put("name",nameEditText.getText().toString());
        ordersMap.put("phone",phoneEditText.getText().toString());
        ordersMap.put("room",roomEditText.getText().toString());
        //ordersMap.put("City",cityEditText.getText().toString());
        ordersMap.put("hostel",Hostel);
        ordersMap.put("itemId",productId);
        ordersMap.put("timeStamp", millis);
        ordersMap.put("status","Not Shipped");
        ordersMap.put("orderPlacedBy",firebaseAuth.getCurrentUser().getUid());
        ordersMap.put("orderId",orderRandomKey);

        ordersRef.child(orderRandomKey).updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    //FirebaseDatabase.getInstance().getReference().child("Cart_List").child("Admin View").child(firebaseAuth.getCurrentUser().getUid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                      //  @Override
                       //public void onComplete(@NonNull Task<Void> task) {
                         //   if(task.isSuccessful())
                           // {
                                Toast.makeText(ConfirmFinalOrderActivity.this,"Your final Order has been placed successfully",Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(ConfirmFinalOrderActivity.this,Final_Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            //}
                        //}
                    //});
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }
}
