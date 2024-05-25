package com.example.adventureplanner.Activities;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adventureplanner.R;

public class SettingFragment extends Fragment {
    TextView userNameTextView,userEmailTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_setting, container, false);

        userNameTextView = view.findViewById(R.id.usernametxtview);
        userEmailTextView = view.findViewById(R.id.email_txtview);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        String username = sharedPref.getString("username", "");
        String email = sharedPref.getString("email", "");

        userNameTextView.setText(username);
        userEmailTextView.setText(email);
        return view;
    }
}