package com.bhaskargogoi.eventx.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.bhaskargogoi.eventx.Login;

import static android.content.Context.MODE_PRIVATE;

public class Logout {
    Context context;
    public Logout(Context context) {
        this.context = context;
    }

    public void logoutNow(){
        SharedPreferences sp = context.getSharedPreferences("credentials", MODE_PRIVATE);
        sp.edit().remove("phone").commit();
        sp.edit().remove("token").commit();
        sp.edit().remove("firstname").commit();
        sp.edit().apply();
        Intent intent = new Intent(context, Login.class);
        context.startActivity(intent);
    }
}
