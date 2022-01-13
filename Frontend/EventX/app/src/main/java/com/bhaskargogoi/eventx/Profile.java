package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Common.Logout;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    String url, updateProfileUrl;
    String urlPostFix = "api/profile-information.php";
    String updateProfileUrlPostFix = "api/update-profile-info.php";
    Button btn_edit_profile;
    EditText et_firstname, et_lastname, et_email, et_phone;
    String token;
    ScrollView profileScrollView;
    ShimmerFrameLayout profileShimmerLayout;
    ConstraintLayout profileInformationParentLayout;
    String mode = "edit_profile";
    ProgressBar edit_profile_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile Information");

        profileInformationParentLayout = findViewById(R.id.profileInformationParentLayout);
        profileScrollView = findViewById(R.id.profileScrollView);
        profileShimmerLayout = findViewById(R.id.profileShimmerLayout);
        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        edit_profile_progress_bar = findViewById(R.id.edit_profile_progress_bar);

        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;
        updateProfileUrl = urlPrefix.getUrlPrefix() + updateProfileUrlPostFix;

        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        token = sp.getString("token", null);

        getProfileInformation();
        profileShimmerLayout.startShimmerAnimation();

        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mode.equals("edit_profile")){
                    et_lastname.setEnabled(true);
                    et_lastname.requestFocus();
                    et_lastname.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.input_field_style));

                    et_email.setEnabled(true);
                    et_email.requestFocus();
                    et_email.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.input_field_style));

                    et_firstname.setEnabled(true);
                    et_firstname.requestFocus();
                    et_firstname.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.input_field_style));

                    mode = "save_details";
                    btn_edit_profile.setText("Save Information");
                } else {
                    btn_edit_profile.setVisibility(View.GONE);
                    edit_profile_progress_bar.setVisibility(View.VISIBLE);
                    StringRequest request = new StringRequest(Request.Method.POST, updateProfileUrl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject respObj = new JSONObject(response);
                                if(respObj.getInt("response_code") == 204){
                                    mode = "edit_profile";
                                    btn_edit_profile.setVisibility(View.VISIBLE);
                                    edit_profile_progress_bar.setVisibility(View.GONE);
                                    btn_edit_profile.setText("Edit Profile");
                                    Toast.makeText(Profile.this, "Updated", Toast.LENGTH_SHORT).show();
                                    et_lastname.setEnabled(false);
                                    et_email.setEnabled(false);
                                    et_firstname.setEnabled(false);
                                    getProfileInformation();

                                } else if(respObj.getInt("response_code") == 401){
                                    startActivity(new Intent(getApplicationContext(), Login.class));
                                    finish();
                                } else {
                                    Toast.makeText(Profile.this, "Error occurred", Toast.LENGTH_SHORT).show();
                                    btn_edit_profile.setVisibility(View.VISIBLE);
                                    edit_profile_progress_bar.setVisibility(View.GONE);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Snackbar.make(profileInformationParentLayout, "Something went wrong!", Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("token", token);
                            params.put("firstname", et_firstname.getText().toString().trim());
                            params.put("lastname", et_lastname.getText().toString().trim());
                            params.put("email", et_email.getText().toString().trim());
                            return params;
                        }
                    };
                    MySingleton.getInstance(Profile.this).addToRequestQueue(request);
                }
            }
        });
    }

    void getProfileInformation(){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                profileShimmerLayout.stopShimmerAnimation();
                profileScrollView.setVisibility(View.VISIBLE);
                profileShimmerLayout.setVisibility(View.GONE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    if(respObj.getInt("response_code") == 200){
                        et_firstname.setText(respObj.getString("firstname"));
                        et_lastname.setText(respObj.getString("lastname"));
                        et_email.setText(respObj.getString("email"));
                        et_phone.setText(respObj.getString("phone"));
                    } else if(respObj.getInt("response_code") == 403){
                        Toast.makeText(Profile.this, "Your account has been blocked", Toast.LENGTH_SHORT).show();
                        Logout logout = new Logout(Profile.this);
                        logout.logoutNow();
                        finish();
                    } else if(respObj.getInt("response_code") == 401){
                        startActivity(new Intent(getApplicationContext(), Login.class));
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(profileInformationParentLayout, "Something went wrong!", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                        .show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token", token);
                return params;
            }
        };
        MySingleton.getInstance(Profile.this).addToRequestQueue(request);
    }

}