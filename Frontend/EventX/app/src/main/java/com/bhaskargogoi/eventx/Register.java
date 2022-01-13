package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.android.volley.toolbox.Volley;
import com.bhaskargogoi.eventx.Common.UrlPrefix;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    ImageView back_button;
    Button btn_register;
    EditText et_firstname, et_lastname, et_email;
    ProgressBar registerProgressBar;
    String urlPostFix = "api/register.php";
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        back_button = findViewById(R.id.back_button);
        btn_register = findViewById(R.id.btn_register);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_email = findViewById(R.id.et_email);
        registerProgressBar = findViewById(R.id.registerProgressBar);
        //getting complete url
        UrlPrefix urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_firstname.getText().toString().trim().matches("")) {
                    Toast.makeText(Register.this, "Firstname is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_lastname.getText().toString().trim().matches("")) {
                    Toast.makeText(Register.this, "Lastname is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_email.getText().toString().trim().matches("")) {
                    Toast.makeText(Register.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString().trim()).matches()){
                    Toast.makeText(Register.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                    return;
                }

                btn_register.setVisibility(View.GONE);
                registerProgressBar.setVisibility(View.VISIBLE);
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        registerProgressBar.setVisibility(View.GONE);
                        try {
                            JSONObject respObj = new JSONObject(response);
                            int resp_code = respObj.getInt("response_code");
                            if(resp_code == 201){
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
                                Intent intent = new Intent(Register.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else if(resp_code == 403){
                                btn_register.setVisibility(View.VISIBLE);
                                String message = respObj.getString("message");
                                Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                            } else {
                                btn_register.setVisibility(View.VISIBLE);
                                Toast.makeText(Register.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        btn_register.setVisibility(View.VISIBLE);
                        registerProgressBar.setVisibility(View.GONE);
                        Toast.makeText(Register.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("firstname", et_firstname.getText().toString().trim());
                        params.put("lastname", et_lastname.getText().toString().trim());
                        params.put("email", et_email.getText().toString().trim());
                        params.put("phone", phone);
                        return params;
                    }
                };
                MySingleton.getInstance(Register.this).addToRequestQueue(request);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}