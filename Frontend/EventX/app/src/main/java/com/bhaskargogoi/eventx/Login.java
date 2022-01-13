package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Common.UrlPrefix;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    Button btn_skip, btn_login;
    EditText et_phone;
    ProgressBar loginProgressBar;
    String urlPostFix = "api/generate-otp.php";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        btn_skip = findViewById(R.id.btn_skip);
        btn_login = findViewById(R.id.btn_login);
        et_phone = findViewById(R.id.et_phone);
        loginProgressBar = findViewById(R.id.loginProgressBar);

        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_phone.getText().toString().trim().matches("")) {
                    Toast.makeText(Login.this, "Phone is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_phone.getText().toString().trim().length() != 10) {
                    Toast.makeText(Login.this, "Incomplete Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.PHONE.matcher(et_phone.getText().toString().trim()).matches()){
                    Toast.makeText(Login.this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginProgressBar.setVisibility(View.VISIBLE);
                btn_login.setVisibility(View.GONE);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loginProgressBar.setVisibility(View.GONE);
                        try {
                            JSONObject respObj = new JSONObject(response);
                            int resp = respObj.getInt("response_code");
                            if(resp == 201){
                                String ref_no = respObj.getString("ref_no");
                                Intent intent = new Intent(Login.this, VerifyLoginOtp.class);
                                intent.putExtra("phone", et_phone.getText().toString().trim());
                                intent.putExtra("ref_no", ref_no);
                                startActivity(intent);
                                finish();
                            } else {
                                loginProgressBar.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                                Toast.makeText(Login.this, "Error sending the OTP", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loginProgressBar.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(Login.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("phone", et_phone.getText().toString().trim());
                        return params;
                    }
                };
                MySingleton.getInstance(Login.this).addToRequestQueue(request);
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

}