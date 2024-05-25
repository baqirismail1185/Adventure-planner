package com.example.adventureplanner.Activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.adventureplanner.R;
public class ViewPlanFragment extends Fragment {
    EditText fromtxt,totxt;
    Button searchbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_plan, container, false);
        fromtxt = view.findViewById(R.id.fromtxt);
        totxt = view.findViewById(R.id.totxt);
        searchbtn = view.findViewById(R.id.searchbtn);

        return view;
    }
}