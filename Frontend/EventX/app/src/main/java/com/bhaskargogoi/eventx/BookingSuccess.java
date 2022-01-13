package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingSuccess extends AppCompatActivity {

    TextView tv_booking_ref_id, tv_payment_ref_id;
    Button btn_continue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_success);
        getSupportActionBar().setTitle("Booking Successful");

        tv_booking_ref_id = findViewById(R.id.tv_booking_ref_id);
        tv_payment_ref_id = findViewById(R.id.tv_payment_ref_id);
        btn_continue = findViewById(R.id.btn_continue);

        Intent intent = getIntent();
        String booking_ref_id = intent.getStringExtra("booking_ref_id");
        String payment_ref_id = intent.getStringExtra("payment_ref_id");

        tv_booking_ref_id.setText("Booking Ref Id: " + booking_ref_id);
        tv_payment_ref_id.setText("Payment Ref Id:" + payment_ref_id);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookingSuccess.this, MainActivity.class);
                intent.putExtra("booking_confirmed", "true");
                startActivity(intent);
                finish();
            }
        });
    }
}