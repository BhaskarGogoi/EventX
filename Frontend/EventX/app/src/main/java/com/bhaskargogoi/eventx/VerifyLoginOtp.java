package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.google.firebase.FirebaseNetworkException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.view.View.GONE;

public class VerifyLoginOtp extends AppCompatActivity implements TextWatcher {
    EditText otp_no_1, otp_no_2, otp_no_3, otp_no_4;
    TextView tv_resend_otp;
    ProgressBar verifyOtpProgressBar, resendOtpProgressBar;
    Button btn_continue;
    String urlPostFix = "api/login.php", resendOtpUrlPostFix = "api/generate-otp.php";
    String url, resend_url, ref_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login_otp);
        getSupportActionBar().hide();
        verifyOtpProgressBar = findViewById(R.id.verifyOtpProgressBar);
        resendOtpProgressBar = findViewById(R.id.resendOtpProgressBar);
        btn_continue = findViewById(R.id.btn_continue2);
        otp_no_1 = findViewById(R.id.otp_no_1);
        otp_no_2 = findViewById(R.id.otp_no_2);
        otp_no_3 = findViewById(R.id.otp_no_3);
        otp_no_4 = findViewById(R.id.otp_no_4);
        tv_resend_otp = findViewById(R.id.tv_resend_otp);

        otp_no_1.addTextChangedListener(this);
        otp_no_2.addTextChangedListener(this);
        otp_no_3.addTextChangedListener(this);
        otp_no_4.addTextChangedListener(this);

        //creating complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;
        resend_url = urlPrefix.getUrlPrefix() + resendOtpUrlPostFix;

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");
        ref_no = intent.getStringExtra("ref_no");

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (otp_no_1.getText().toString().trim().matches("")) {
                    Toast.makeText(VerifyLoginOtp.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (otp_no_2.getText().toString().trim().matches("")) {
                    Toast.makeText(VerifyLoginOtp.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (otp_no_3.getText().toString().trim().matches("")) {
                    Toast.makeText(VerifyLoginOtp.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (otp_no_4.getText().toString().trim().matches("")) {
                    Toast.makeText(VerifyLoginOtp.this, "Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String otp1 = otp_no_1.getText().toString().trim();
                String otp2 = otp_no_2.getText().toString().trim();
                String otp3 = otp_no_3.getText().toString().trim();
                String otp4 = otp_no_4.getText().toString().trim();
                String otp = otp1 + otp2 + otp3 + otp4;

                verifyOtpProgressBar.setVisibility(View.VISIBLE);
                btn_continue.setVisibility(View.INVISIBLE);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        verifyOtpProgressBar.setVisibility(GONE);
                        btn_continue.setVisibility(View.VISIBLE);
                        try {
                            JSONObject respObj = new JSONObject(response);
                            int resp_code = respObj.getInt("response_code");
                            String authentication = respObj.getString("authentication");
                            if(resp_code == 200 && authentication.equals("success")) {
                                int isRegistered = respObj.getInt("isRegistered");
                                if (isRegistered == 1){
                                    //Redirect the user to home screen
                                    String firstname = respObj.getString("firstname");
                                    String token = respObj.getString("access_token");
                                    SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("phone", phone);
                                    editor.putString("firstname", firstname);
                                    editor.putString("token", token);
                                    editor.commit();
                                    editor.apply();
                                    Intent intent = new Intent(VerifyLoginOtp.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    //Redirect the user to create profile
                                    Intent intent = new Intent(VerifyLoginOtp.this, Register.class);
                                    intent.putExtra("phone", phone);
                                    startActivity(intent);
                                    finish();
                                }
                            } else if(resp_code == 200 && authentication.equals("blocked")){
                                Toast.makeText(VerifyLoginOtp.this, "Your account has been blocked", Toast.LENGTH_SHORT).show();
                            } else if(resp_code == 200 && authentication.equals("wrong otp")){
                                Toast.makeText(VerifyLoginOtp.this, "Please provide correct OTP", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(VerifyLoginOtp.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        verifyOtpProgressBar.setVisibility(View.GONE);
                        btn_continue.setVisibility(View.VISIBLE);
                        Toast.makeText(VerifyLoginOtp.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", phone);
                        params.put("otp", otp);
                        params.put("ref_no", ref_no);
                        return params;
                    }
                };
                MySingleton.getInstance(VerifyLoginOtp.this).addToRequestQueue(request);
            }
        });

        tv_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resendOtpProgressBar.setVisibility(View.VISIBLE);
                tv_resend_otp.setVisibility(GONE);
                otp_no_1.setText("");
                otp_no_2.setText("");
                otp_no_3.setText("");
                otp_no_4.setText("");
                StringRequest request = new StringRequest(Request.Method.POST, resend_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        resendOtpProgressBar.setVisibility(GONE);
                        tv_resend_otp.setVisibility(View.VISIBLE);
                        Toast.makeText(VerifyLoginOtp.this, "OTP Successfully sent!", Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject respObj = new JSONObject(response);
                            int resp = respObj.getInt("response_code");
                            if(resp == 201){
                                ref_no = respObj.getString("ref_no");
                            } else {
                                Toast.makeText(VerifyLoginOtp.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        resendOtpProgressBar.setVisibility(GONE);
                        tv_resend_otp.setVisibility(View.VISIBLE);
                        Toast.makeText(VerifyLoginOtp.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", phone);
                        return params;
                    }
                };
                MySingleton.getInstance(VerifyLoginOtp.this).addToRequestQueue(request);
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() == 1){
            if(otp_no_1.length() == 1){
                otp_no_2.requestFocus();
            }
            if(otp_no_2.length()==1){
                otp_no_3.requestFocus();
            }
            if (otp_no_3.length()==1){
                otp_no_4.requestFocus();
            }
        } else if(s.length() == 0){
            if (otp_no_4.length() == 0){
                otp_no_3.requestFocus();
            }
            if(otp_no_3.length() == 0){
                otp_no_2.requestFocus();
            }
            if (otp_no_2.length() ==0){
                otp_no_1.requestFocus();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(VerifyLoginOtp.this, Login.class);
        startActivity(intent);
        finish();
    }
}