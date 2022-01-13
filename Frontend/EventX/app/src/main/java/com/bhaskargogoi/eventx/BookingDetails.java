package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Adapters.BookingsAdapter;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bhaskargogoi.eventx.Models.Booking;
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

public class BookingDetails extends AppCompatActivity {
    TextView tv_title, tv_event_type, tv_hosted_by, tv_date, tv_time, tv_price, tv_total_price, tv_qty, event_mode, address, tv_duration;
    String booking_id, token = "";
    UrlPrefix urlPrefix;
    String urlPostFix = "api/booking-details.php";
    String url;
    ImageView image_booking_details;
    ShimmerFrameLayout shimmer_layout_booking_details;
    ConstraintLayout constraint_layout_booking_details, error_occurred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Booking Details");
        setContentView(R.layout.activity_booking_details);
        shimmer_layout_booking_details = findViewById(R.id.shimmer_layout_booking_details);
        constraint_layout_booking_details = findViewById(R.id.constraint_layout_booking_details);
        tv_title = findViewById(R.id.tv_title_order_details);
        tv_event_type = findViewById(R.id.tv_event_type_order_details);
        tv_hosted_by = findViewById(R.id.tv_hosted_by_order_details);
        tv_date = findViewById(R.id.tv_date_order_details);
        tv_time = findViewById(R.id.tv_time_order_details);
        tv_duration = findViewById(R.id.tv_duration_booking_details);
        tv_price = findViewById(R.id.ticket_price_booking_details);
        tv_total_price = findViewById(R.id.totalPrice_booking_details);
        tv_qty = findViewById(R.id.ticket_qty_booking_details);
        event_mode = findViewById(R.id.tv_event_mode_booking_details);
        address = findViewById(R.id.tv_address_booking_details);
        image_booking_details = findViewById(R.id.image_booking_details);
        error_occurred = findViewById(R.id.error_occurred);

        //getting complete url
        urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;

        Intent intent = getIntent();
        booking_id = intent.getStringExtra("booking_id");

        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.contains("token")) {
            token = sp.getString("token", null);
        }
        error_occurred.setVisibility(View.GONE);
        shimmer_layout_booking_details.startShimmerAnimation();
        getBookingDetails();
    }

    void getBookingDetails(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shimmer_layout_booking_details.stopShimmerAnimation();
                shimmer_layout_booking_details.setVisibility(View.GONE);
                constraint_layout_booking_details.setVisibility(View.VISIBLE);
                String imageUrl = "";
                String event_id = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray event_details = respObj.getJSONArray("event_details");
                        imageUrl = respObj.getString("image_url");
                        for (int i = 0; i < event_details.length(); i++) {
                            JSONObject event = event_details.getJSONObject(i);
                            event_id = event.getString("event_id");
                            tv_title.setText(event.getString("event_name"));
                            tv_event_type.setText(event.getString("event_type"));
                            tv_hosted_by.setText(event.getString("hosted_by"));

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
                            tv_date.setText(finalMonth + " " + finalDay + " | " + finalDayFull );

                            //time 24 hour to 12 hour
                            String inputTime = event.getString("time");
                            try {
                                SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
                                Date dateObj = sdf.parse(inputTime);
                                String finalTime = new SimpleDateFormat("K:mm a").format(dateObj);
                                tv_time.setText(finalTime);
                            } catch (final ParseException e) {
                                e.printStackTrace();
                            }

                            tv_duration.setText(event.getString("duration") + " " + "Hours");
                            tv_price.setText(BookingDetails.this.getResources().getString(R.string.Rs) + " " + event.getString("price"));

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
                        Glide.with(BookingDetails.this).load(urlPrefix.getUrlPrefix() + imageUrl + event_id + ".jpg").into(image_booking_details);
                        JSONArray booking_details = respObj.getJSONArray("booking_details");
                        for (int i = 0; i < booking_details.length(); i++) {
                            JSONObject booking = booking_details.getJSONObject(i);
                            tv_total_price.setText(BookingDetails.this.getResources().getString(R.string.Rs) + " " + booking.getString( "total_price"));
                            tv_qty.setText(booking.getString("no_of_tickets"));
                        }
                    } else if(resp_code == 401) {
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    } else {
                        error_occurred.setVisibility(View.VISIBLE);
                        constraint_layout_booking_details.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BookingDetails.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("booking_id", booking_id);
                return params;
            }
        };
        MySingleton.getInstance(BookingDetails.this).addToRequestQueue(request);
    }
}