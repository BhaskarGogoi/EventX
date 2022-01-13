package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Adapters.BookingsAdapter;
import com.bhaskargogoi.eventx.Common.Logout;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bhaskargogoi.eventx.Models.Booking;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bookings extends AppCompatActivity {
    String token;
    String url;
    String urlPostFix = "api/bookings.php";
    private List bookingsList;
    ConstraintLayout bookingsConstraintLayout;
    RecyclerView rv_booking;
    LinearLayout ll_not_found;
    ShimmerFrameLayout bookings_shimmer_layout;
    String get_event_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        getSupportActionBar().setTitle("Bookings");

        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;

        bookingsConstraintLayout = findViewById(R.id.bookingsConstraintLayout);
        bookings_shimmer_layout = findViewById(R.id.bookings_shimmer_layout);
        rv_booking = findViewById(R.id.rv_booking);
        ll_not_found = findViewById(R.id.ll_not_found);
        rv_booking.setLayoutManager(new LinearLayoutManager(this));

        bookings_shimmer_layout.startShimmerAnimation();

        Intent intent = getIntent();
        token = intent.getStringExtra("token");

        bookingsList = new ArrayList<>();
        getBookings();
    }

    void getBookings(){
        StringRequest request2 = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                bookings_shimmer_layout.setVisibility(View.GONE);
                bookings_shimmer_layout.stopShimmerAnimation();
                rv_booking.setVisibility(View.VISIBLE);
                String image_url = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        image_url = respObj.getString("image_url");
                        JSONArray data = respObj.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject booking = data.getJSONObject(i);
                            Booking bookingModel = new Booking();
                            bookingModel.setBooking_id(booking.getString("booking_id"));
                            bookingModel.setEvent_id(booking.getString("event_id"));
                            bookingModel.setEvent_name(booking.getString("event_name"));
                            bookingModel.setNo_of_tickets(booking.getString("no_of_tickets"));
                            bookingModel.setTotal_price(booking.getString("total_price"));
                            bookingModel.setBooking_date(booking.getString("booking_date"));
                            bookingsList.add(bookingModel);
                        }
                    } else if(respObj.getInt("response_code") == 403){
                        Toast.makeText(Bookings.this, "Your account has been blocked", Toast.LENGTH_SHORT).show();
                        Logout logout = new Logout(Bookings.this);
                        logout.logoutNow();
                        finish();
                    } else if (resp_code == 401){
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    } else {
                        ll_not_found.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                BookingsAdapter adapter = new BookingsAdapter(Bookings.this, bookingsList);
                rv_booking.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showSnackBar();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(request2);
    }

    void showSnackBar(){
        Snackbar.make(bookingsConstraintLayout, "Something went wrong!", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }
}