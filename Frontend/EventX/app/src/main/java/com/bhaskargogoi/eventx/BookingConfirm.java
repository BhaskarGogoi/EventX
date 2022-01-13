package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Common.Logout;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookingConfirm extends AppCompatActivity implements PaymentResultListener {
    String url, getProfileInfoUrl, email, phone;
    String urlPostFix = "api/book.php", getProfileInfoUrlPostFix = "api/profile-information.php";
    TextView tv_event_name, tv_event_type, tv_tickets_left, tv_no_of_tickets, tv_total_price, tv_number_of_tickets, tv_price;
    ImageView coverImage, decrease, increase;
    int no_of_tickets = 1;
    Button btn_booking_confirm;
    ProgressBar progressBar;
    String token, event_id;
    int totalPrice;
    LinearLayout parentLayoutBookingConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);
        getSupportActionBar().setTitle("Confirm Booking");

        Checkout.preload(getApplicationContext());

        parentLayoutBookingConfirm = findViewById(R.id.parentLayoutBookingConfirm);
        tv_event_name = findViewById(R.id.tv_event_name);
        tv_event_type = findViewById(R.id.event_type);
        tv_tickets_left = findViewById(R.id.ticketsLeft);
        tv_no_of_tickets = findViewById(R.id.ticket_qty);
        coverImage = findViewById(R.id.coverImage);
        increase = findViewById(R.id.increase);
        decrease = findViewById(R.id.decrease);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_number_of_tickets = findViewById(R.id.tv_number_of_tickets);
        tv_price = findViewById(R.id.ticket_price);
        btn_booking_confirm = findViewById(R.id.btn_booking_confirm);
        progressBar = findViewById(R.id.progressBar);

        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;
        getProfileInfoUrl = urlPrefix.getUrlPrefix() + getProfileInfoUrlPostFix;

        Bundle bundle = getIntent().getExtras();
        event_id = bundle.getString("event_id");
        String event_name = bundle.getString("event_name");
        String event_type = bundle.getString("event_type");
        String imageUrl = bundle.getString("imageUrl");
        String hosted_by = bundle.getString("hosted_by");
        final String price = bundle.getString("price");
        String tickets_left = bundle.getString("tickets_left");

        Glide.with(BookingConfirm.this).load(imageUrl).into(coverImage);
        tv_event_name.setText(event_name);
        tv_event_type.setText(event_type + " - " + hosted_by);
        tv_tickets_left.setText(tickets_left + " Ticket(s) Left");
        tv_no_of_tickets.setText(String.valueOf(no_of_tickets));
        tv_number_of_tickets.setText(String.valueOf(no_of_tickets));
        tv_price.setText(BookingConfirm.this.getResources().getString(R.string.Rs) + " " + price);
        tv_total_price.setText(BookingConfirm.this.getResources().getString(R.string.Rs) + " " + price);

        totalPrice = Integer.parseInt(price);

        //check ticket quantity increase/decrease button clickables
        checkButtons();

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_of_tickets = no_of_tickets + 1;
                totalPrice = no_of_tickets * Integer.parseInt(price);
                tv_no_of_tickets.setText(String.valueOf(no_of_tickets));
                tv_number_of_tickets.setText(String.valueOf(no_of_tickets));
                tv_total_price.setText(BookingConfirm.this.getResources().getString(R.string.Rs) + " " + String.valueOf(totalPrice));
                checkButtons();
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                no_of_tickets = no_of_tickets - 1;
                totalPrice = no_of_tickets * Integer.parseInt(price);
                tv_no_of_tickets.setText(String.valueOf(no_of_tickets));
                tv_number_of_tickets.setText(String.valueOf(no_of_tickets));
                tv_total_price.setText(BookingConfirm.this.getResources().getString(R.string.Rs) + " " + String.valueOf(totalPrice));
                checkButtons();
            }
        });

        btn_booking_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getProfileInformation();
            }
        });
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

    void checkButtons() {
        if (no_of_tickets == 1) {
            increase.setEnabled(true);
            decrease.setEnabled(false);
        } else {
            increase.setEnabled(true);
            decrease.setEnabled(true);
        }
    }

    void getProfileInformation() {
        if (checkIfLoggedIn() == true) {
            progressBar.setVisibility(View.VISIBLE);
            btn_booking_confirm.setVisibility(View.GONE);
            StringRequest request = new StringRequest(Request.Method.POST, getProfileInfoUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject respObj = new JSONObject(response);
                        if (respObj.getInt("response_code") == 200) {
                            email = respObj.getString("email");
                            phone = respObj.getString("phone");
                            startPayment();
                        } else if(respObj.getInt("response_code") == 403){
                            Toast.makeText(BookingConfirm.this, "Your account has been blocked", Toast.LENGTH_SHORT).show();
                            Logout logout = new Logout(BookingConfirm.this);
                            logout.logoutNow();
                            finish();
                        } else if (respObj.getInt("response_code") == 401) {
                            Toast.makeText(BookingConfirm.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btn_booking_confirm.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(BookingConfirm.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    btn_booking_confirm.setVisibility(View.VISIBLE);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token", token);
                    return params;
                }
            };
            MySingleton.getInstance(BookingConfirm.this).addToRequestQueue(request);
        }
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_nHpLdYSKcrTcZJ");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "EventX");
            options.put("description", "Reference No. " + event_id);
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", totalPrice * 100);//pass amount in currency subunits
            options.put("prefill.email", email);
            options.put("prefill.contact", phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e("myLog", "Error Occurred!", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 201) {
                        String booking_ref_id = respObj.getString("booking_ref_id");
                        Intent intent = new Intent(BookingConfirm.this, BookingSuccess.class);
                        intent.putExtra("booking_ref_id", booking_ref_id);
                        intent.putExtra("payment_ref_id", s);
                        startActivity(intent);
                        finish();
                    } else {
                        Snackbar.make(parentLayoutBookingConfirm, "Error", Snackbar.LENGTH_LONG)
                                .setAction("CLOSE", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                btn_booking_confirm.setVisibility(View.VISIBLE);
                Toast.makeText(BookingConfirm.this, "Error" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                params.put("event_id", event_id);
                params.put("total_price", String.valueOf(totalPrice));
                params.put("qty", tv_no_of_tickets.getText().toString().trim());
                params.put("payment_ref_id", s);
                return params;
            }
        };
        MySingleton.getInstance(BookingConfirm.this).addToRequestQueue(request);
    }


    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
        btn_booking_confirm.setVisibility(View.VISIBLE);
    }
}