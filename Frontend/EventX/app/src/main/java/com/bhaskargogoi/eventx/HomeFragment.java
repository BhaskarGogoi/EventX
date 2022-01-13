package com.bhaskargogoi.eventx;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Adapters.EventRecyclerView;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bhaskargogoi.eventx.Models.Event;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    UrlPrefix urlPrefix;
    String urlPostFix = "api/index.php";
    String url;
    RecyclerView recyclerView, musicalEvents, standUpEvents, talksEvents, playEvents, workshopEvents, exhibitionsEvents;
    private List showList, musicalEventsList, standUpList, talksEventsList, playList, workshopList, exhibitionsList;
    ScrollView scrollViewFragmentHome;
    ShimmerFrameLayout shimmerLayoutHomeFragment;
    ImageSlider imageSlider;
    TextView welcomeGreeting, tv_viewAl2, tv_viewAl3, tv_viewAl4, tv_viewAl5, tv_viewAl6, tv_viewAl7;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = view.findViewById(R.id.imageSlider);
        scrollViewFragmentHome = view.findViewById(R.id.scrollViewFragmentHome);
        shimmerLayoutHomeFragment = view.findViewById(R.id.shimmerLayoutHomeFragment);
        welcomeGreeting = view.findViewById(R.id.welcomeGreeting);
        tv_viewAl2 = view.findViewById(R.id.btn_viewAll2);
        tv_viewAl3 = view.findViewById(R.id.btn_viewAll3);
        tv_viewAl4 = view.findViewById(R.id.btn_viewAll4);
        tv_viewAl5 = view.findViewById(R.id.btn_viewAll5);
        tv_viewAl7 = view.findViewById(R.id.btn_viewAll7);
        recyclerView = view.findViewById(R.id.recycler);
        musicalEvents = view.findViewById(R.id.musicEvents);
        standUpEvents = view.findViewById(R.id.standUpEvents);
        talksEvents = view.findViewById(R.id.talksEvents);
        playEvents = view.findViewById(R.id.playEvents);
        exhibitionsEvents = view.findViewById(R.id.exhibitionsEvents);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        musicalEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        standUpEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        talksEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        playEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        exhibitionsEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //getting complete url
        urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;



        shimmerLayoutHomeFragment.startShimmerAnimation();

        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            welcomeGreeting.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            welcomeGreeting.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 24){
            welcomeGreeting.setText("Good Evening");
        }

        ArrayList<SlideModel> images = new ArrayList<>();
        images.add(new SlideModel(R.drawable.slide1, null));
        images.add(new SlideModel(R.drawable.slide2, null));
        images.add(new SlideModel(R.drawable.slide3, null));
        imageSlider.setImageList(images, ScaleTypes.CENTER_CROP);
        
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                //handle here
            }
        });

        getLatestEvents();
        getMusicalEvents();
        getStandUpEvents();
        getTalksEvents();
        getPlays();
        getExhibitions();

        showList = new ArrayList<>();
        musicalEventsList = new ArrayList<>();
        standUpList = new ArrayList<>();
        talksEventsList = new ArrayList<>();
        playList = new ArrayList<>();
        exhibitionsList = new ArrayList<>();

        tv_viewAl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EventsByCategory.class);
                intent.putExtra("event_category", "Music");
                startActivity(intent);
            }
        });

        tv_viewAl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EventsByCategory.class);
                intent.putExtra("event_category", "Stand Up");
                startActivity(intent);
            }
        });

        tv_viewAl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EventsByCategory.class);
                intent.putExtra("event_category", "Talks");
                startActivity(intent);
            }
        });

        tv_viewAl5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EventsByCategory.class);
                intent.putExtra("event_category", "Play");
                startActivity(intent);
            }
        });


        tv_viewAl7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EventsByCategory.class);
                intent.putExtra("event_category", "Exhibition");
                startActivity(intent);
            }
        });

        return view;
    }

    void getLatestEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shimmerLayoutHomeFragment.stopShimmerAnimation();
                shimmerLayoutHomeFragment.setVisibility(View.GONE);
                scrollViewFragmentHome.setVisibility(View.VISIBLE);
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            showList.add(showmodel);
                        }

                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity(), showList, imageLink);
                recyclerView.setAdapter(adapter);

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
                params.put("event_type", "latest");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void getMusicalEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            musicalEventsList.add(showmodel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity() , musicalEventsList, imageLink);
                musicalEvents.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_type", "Music");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void getStandUpEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data =  respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            standUpList.add(showmodel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity() , standUpList, imageLink);
                standUpEvents.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_type", "Stand Up");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void getTalksEvents() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            talksEventsList.add(showmodel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity() , talksEventsList, imageLink);
                talksEvents.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_type", "Talks");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void getPlays() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            playList.add(showmodel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity() , playList, imageLink);
                playEvents.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_type", "Play");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void getExhibitions() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    int resp_code = respObj.getInt("response_code");
                    if (resp_code == 200) {
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event showmodel = new Event();
                            showmodel.setEvent_id(show.getString("event_id"));
                            showmodel.setEvent_name(show.getString("event_name"));
                            showmodel.setEvent_type(show.getString("event_type"));
                            showmodel.setHosted_by(show.getString("hosted_by"));
                            exhibitionsList.add(showmodel);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error loading the data", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                EventRecyclerView adapter = new EventRecyclerView(getActivity() , exhibitionsList, imageLink);
                exhibitionsEvents.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("event_type", "Exhibition");
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }

    void showSnackBar(){
        Snackbar.make(shimmerLayoutHomeFragment, "Something went wrong!", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }

}