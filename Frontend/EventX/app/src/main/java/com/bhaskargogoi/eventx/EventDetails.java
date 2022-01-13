package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventDetails extends AppCompatActivity {
    String urlPostFix = "api/event-details.php";
    String url;
    TextView event_name_detailed, price, tv_event_type, duration, tv_ticketsLeft, date, time, hosted_by, about, event_mode, address;
    ImageView coverImage;
    Button btn_book;
    int  total_tickets_left;
    String event_name, event_type, host, ticket_price;
    LinearLayout linearLayoutEventDetails;
    ShimmerFrameLayout shimmerLayoutEventDetails;
    String token;
    ImageView btn_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        linearLayoutEventDetails = findViewById(R.id.linearLayoutEventDetails);
        shimmerLayoutEventDetails = findViewById(R.id.shimmerLayoutEventDetails);
        coverImage = findViewById(R.id.coverImage);
        event_name_detailed = findViewById(R.id.event_name_detailed);
        price = findViewById(R.id.ticket_price);
        tv_event_type = findViewById(R.id.event_type);
        duration = findViewById(R.id.duration);
        tv_ticketsLeft = findViewById(R.id.ticketsLeft);
        date = findViewById(R.id.tv_date);
        time = findViewById(R.id.time);
        hosted_by = findViewById(R.id.hosted_by);
        about = findViewById(R.id.about);
        event_mode = findViewById(R.id.event_mode);
        address = findViewById(R.id.address);
        btn_book = findViewById(R.id.btn_book);
        btn_share = findViewById(R.id.btn_share);

        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;

        shimmerLayoutEventDetails.startShimmerAnimation();

        Bundle bundle = getIntent().getExtras();
        String event_id = bundle.getString("event_id");
        String event_name = bundle.getString("event_name");
        String imageUrl = bundle.getString("imageUrl");

        Glide.with(EventDetails.this).load(imageUrl).into(coverImage);
        event_name_detailed.setText(event_name);
        getEventDetails(event_id, imageUrl);
        getSupportActionBar().setTitle(event_name);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out this event, I am planning to buy tickets for this event : http://65.1.52.77/");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Share Event"));
            }
        });
    }

    void getEventDetails(final String event_id, final String imageUrl) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shimmerLayoutEventDetails.stopShimmerAnimation();
                shimmerLayoutEventDetails.setVisibility(View.GONE);
                linearLayoutEventDetails.setVisibility(View.VISIBLE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject event = data.getJSONObject(i);
                            event_name = event.getString("event_name");
                            event_type = event.getString("event_type");
                            host = event.getString("hosted_by");
                            ticket_price = event.getString("price");
                            total_tickets_left = respObj.getInt("ticketsLeft");
                            price.setText(EventDetails.this.getResources().getString(R.string.Rs) + " " + event.getString("price"));
                            tv_event_type.setText(event.getString("event_type"));
                            duration.setText(event.getString("duration") + " Hours");
                            tv_ticketsLeft.setText(respObj.getInt("ticketsLeft") + " Ticket(s) Left");
                            total_tickets_left = respObj.getInt("ticketsLeft"); // to set the button clickable or not
                            if(total_tickets_left == 0){
                                btn_book.setEnabled(false);
                                btn_book.setText("Tickets Sold Out");
                            }else{
                                btn_book.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkIfLoggedIn() == true) {
                                            Intent intent = new Intent(EventDetails.this, BookingConfirm.class);
                                            Bundle bundle = new Bundle();
                                            bundle.putString("event_id" , event_id);
                                            bundle.putString("event_name" , event_name);
                                            bundle.putString("event_type" , event_type);
                                            bundle.putString("hosted_by" , host);
                                            bundle.putString("price" , ticket_price);
                                            bundle.putString("tickets_left" , String.valueOf(total_tickets_left));
                                            bundle.putString("imageUrl" , imageUrl);
                                            intent.putExtras(bundle);
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }

                            //date
                            String input_date=event.getString("date");
                            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                            Date dt1= null;
                            try {
                                dt1 = format1.parse(input_date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            //day
                            DateFormat day=new SimpleDateFormat("dd");
                            String finalDay=day.format(dt1);
                            //day string
                            DateFormat day_full =new SimpleDateFormat("EEEE");
                            String finalDayFull=day_full.format(dt1);
                            //month
                            DateFormat month=new SimpleDateFormat("MMM");
                            String finalMonth =month.format(dt1);
                            date.setText(finalMonth + " " + finalDay + " | " + finalDayFull );

                            //time 24 hour to 12 hour
                            String inputTime = event.getString("time");
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                Date dateObj = sdf.parse(inputTime);
                                String finalTime = new SimpleDateFormat("K:mm a").format(dateObj);
                                time.setText(finalTime);
                            } catch (final ParseException e) {
                                e.printStackTrace();
                            }

                            hosted_by.setText(event.getString("hosted_by"));
                            about.setText(event.getString("about"));

                            if(event.getString("event_mode").equals("Online")){
                                event_mode.setText("Online Event");
                                address.setText("Link will be shared after booking on your registered email.");
                            } else{
                                event_mode.setText("Address");
                                String address_area = event.getString("address_area");
                                String address_locality = event.getString("address_locality");
                                String address_city = event.getString("address_city");
                                String address_state = event.getString("address_state");
                                String address_pin = event.getString("address_pin");
                                address.setText(address_area +", "+ address_locality + "\n" + address_city + ", " + address_state + "\n" + address_pin);
                            }
                        }
                    } else {
                        Toast.makeText(EventDetails.this, "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventDetails.this, "Error loading the data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_id", event_id);
                return params;
            }
        };
        MySingleton.getInstance(EventDetails.this).addToRequestQueue(request);
    }


    boolean checkIfLoggedIn() {
        //Check if logged in
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (sp.contains("token")) {
            token = sp.getString("token", null);
            return true;
        } else {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }
        return false;
    }
}