package com.bhaskargogoi.eventx;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bhaskargogoi.eventx.Common.Logout;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    Button btn_login, btn_logout;
    LinearLayout ll_bookings, ll_about, ll_profile;
    TextView tv_feedback, tv_email, tv_firstname;
    CardView login_card_view, after_login_cardview;
    String token = "", phone = "", firstname = "";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btn_login = view.findViewById(R.id.btn_login);
        btn_logout = view.findViewById(R.id.btn_logout);
        tv_email = view.findViewById(R.id.tv_email);
        tv_firstname = view.findViewById(R.id.tv_firstname);
        tv_feedback = view.findViewById(R.id.tv_feedback);
        ll_about = view.findViewById(R.id.ll_about);
        ll_bookings = view.findViewById(R.id.ll_bookings);
        ll_profile = view.findViewById(R.id.ll_profile);
        login_card_view = view.findViewById(R.id.loginCardView);
        after_login_cardview = view.findViewById(R.id.afterLoginView);

        //set firstname and email on top cardview
        SharedPreferences sp = getActivity().getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.contains("token")) {
            tv_email.setText(sp.getString("phone", null));
            tv_firstname.setText(sp.getString("firstname", null));
            login_card_view.setVisibility(View.GONE);
        }
        else {
            after_login_cardview.setVisibility(View.GONE);
            btn_logout.setVisibility(View.GONE);
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), Login.class));
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout logout = new Logout(getActivity());
                logout.logoutNow();
                getActivity().finish();
            }
        });

        ll_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfLoggedIn() == true) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Bookings.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
            }
        });

        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkIfLoggedIn() == true) {
                    startActivity(new Intent(getActivity().getApplicationContext(), Profile.class));
                }
            }
        });

        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), About.class));
            }
        });

        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://forms.gle/1oSL6Gu7Pf7PgyCy6";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        return view;
    }

    boolean checkIfLoggedIn() {
        //Check if logged in
        SharedPreferences sp = getActivity().getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (sp.contains("token")) {
            token = sp.getString("token", null);
            phone = sp.getString("phone", null);
            firstname = sp.getString("firstname", null);
            return true;
        } else {
            startActivity(new Intent(getActivity().getApplicationContext(), Login.class));
        }

        return false;
    }
}