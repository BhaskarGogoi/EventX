package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class About extends AppCompatActivity {
    LinearLayout sendEmail;
    TextView web_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setTitle("About");
        sendEmail = findViewById(R.id.sendEmail);
        web_link = findViewById(R.id.web_link);

        web_link.setMovementMethod(LinkMovementMethod.getInstance());

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "thebhaskargogoi@gmail.com", null));
                startActivity(Intent.createChooser(emailIntent, null));
            }
        });

    }
}