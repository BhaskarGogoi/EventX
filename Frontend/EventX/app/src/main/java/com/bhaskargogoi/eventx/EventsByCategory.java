package com.bhaskargogoi.eventx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Adapters.ViewAllEventsRecyclerView;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bhaskargogoi.eventx.Models.Event;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsByCategory extends AppCompatActivity {
    UrlPrefix urlPrefix;
    String urlPostFix = "api/get-event-by-category.php";
    String url;
    RecyclerView events_by_category_rv;
    String event_category;
    List<Event> eventList;
    ShimmerFrameLayout events_by_category_shimmer_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_by_category);
        events_by_category_rv = findViewById(R.id.events_by_category_rv);
        events_by_category_shimmer_layout = findViewById(R.id.events_by_category_shimmer_layout);
        event_category = getIntent().getStringExtra("event_category");
        getSupportActionBar().setTitle(event_category);
        events_by_category_shimmer_layout.startShimmerAnimation();
        //getting complete url
        urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;
        events_by_category_rv.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        getEvents();
    }

    void getEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                events_by_category_shimmer_layout.stopShimmerAnimation();
                events_by_category_shimmer_layout.setVisibility(View.GONE);
                events_by_category_rv.setVisibility(View.VISIBLE);
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink = urlPrefix.getUrlPrefix() + respObj.getString("image_url");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            showmodel.setPrice(show.getString("price"));
                            eventList.add(showmodel);
                        }

                    } else {
                        Toast.makeText(EventsByCategory.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ViewAllEventsRecyclerView adapter = new ViewAllEventsRecyclerView(EventsByCategory.this, eventList, imageLink);
                events_by_category_rv.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EventsByCategory.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_category", event_category);
                return params;
            }
        };
        MySingleton.getInstance(EventsByCategory.this).addToRequestQueue(request);
    }
}