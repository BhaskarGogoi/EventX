package com.bhaskargogoi.eventx;

import android.media.Image;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bhaskargogoi.eventx.Adapters.EventRecyclerView;
import com.bhaskargogoi.eventx.Adapters.SearchResultAdapter;
import com.bhaskargogoi.eventx.Common.UrlPrefix;
import com.bhaskargogoi.eventx.Models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    String urlPostFix = "api/search.php";
    String url;
    UrlPrefix urlPrefix;
    SearchView searchView;
    FrameLayout searchFrameLayout;
    List eventList;
    CardView searchBarDummy, searchViewCard;
    RecyclerView searchResultRecyclerView;
    TextView tv_search, tv_search_result;
    ConstraintLayout no_results_found_constraint_layout, progressBar_constraint_layout;
    ImageView search_back_press;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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

        //getting complete url
        urlPrefix = new UrlPrefix();
        url = urlPrefix.getUrlPrefix() + urlPostFix;

        eventList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        searchFrameLayout = view.findViewById(R.id.searchFrameLayout);
        tv_search = view.findViewById(R.id.tv_search);
        tv_search_result = view.findViewById(R.id.tv_search_result);
        searchView = view.findViewById(R.id.searchView);
        searchViewCard = view.findViewById(R.id.searchViewCard);
        searchBarDummy = view.findViewById(R.id.searchBarDummy);
        search_back_press = view.findViewById(R.id.search_back_press);
        searchResultRecyclerView = view.findViewById(R.id.searchResultRecyclerView);
        searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        no_results_found_constraint_layout = view.findViewById(R.id.no_results_found_constraint_layout);
        progressBar_constraint_layout = view.findViewById(R.id.progressBar_constraint_layout);

        searchView.setQueryHint("Search");


        searchBarDummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewCard.setVisibility(View.VISIBLE);
                searchView.onActionViewExpanded();
                searchBarDummy.setVisibility(View.GONE);
                tv_search.setVisibility(View.GONE);
            }
        });


        search_back_press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchViewCard.setVisibility(View.GONE);
                searchView.onActionViewCollapsed();
                searchBarDummy.setVisibility(View.VISIBLE);
                tv_search.setVisibility(View.VISIBLE);
                no_results_found_constraint_layout.setVisibility(View.GONE);
                tv_search_result.setVisibility(View.GONE);
                searchResultRecyclerView.setVisibility(View.GONE);
                no_results_found_constraint_layout.setVisibility(View.GONE);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null && !query.isEmpty()){
                    getEvents(query);
                    progressBar_constraint_layout.setVisibility(View.VISIBLE);
                }
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText != null && !newText.isEmpty()){
//                    getEvents(newText);
//                }
                return false;
            }
        });

        return view;

    }


    void getEvents(final String key){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //to clear the recycler view on new search result received
                eventList.clear();
                progressBar_constraint_layout.setVisibility(View.GONE);
                String imageLink = "";
                try {
                    JSONObject respObj = new JSONObject(response);
                    String status = respObj.getString("status");

                    if (status.equals("success")) {
                        tv_search_result.setVisibility(View.VISIBLE);
                        searchResultRecyclerView.setVisibility(View.VISIBLE);
                        no_results_found_constraint_layout.setVisibility(View.GONE);
                        JSONArray data = respObj.getJSONArray("data");
                        imageLink =  urlPrefix.getUrlPrefix() + respObj.getString("image_url");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject show = data.getJSONObject(i);
                            Event event_model = new Event();
                            event_model.setEvent_id(show.getString("event_id"));
                            event_model.setEvent_name(show.getString("event_name"));
                            event_model.setEvent_type(show.getString("event_type"));
                            event_model.setHosted_by(show.getString("hosted_by"));
                            eventList.add(event_model);
                        }

                    } else if(status.equals("not found")) {
                        tv_search_result.setVisibility(View.GONE);
                        searchResultRecyclerView.setVisibility(View.GONE);
                        no_results_found_constraint_layout.setVisibility(View.VISIBLE);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                SearchResultAdapter adapter = new SearchResultAdapter(getActivity(), eventList, imageLink);
                searchResultRecyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                showSnackBar();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search_key", key);
                return params;
            }
        };
        MySingleton.getInstance(getActivity()).addToRequestQueue(request);
    }
}