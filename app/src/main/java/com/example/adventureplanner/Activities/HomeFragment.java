package com.example.adventureplanner.Activities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.adventureplanner.Adapters.PopularTripsAdapter;
import com.example.adventureplanner.Classes.ApiService;
import com.example.adventureplanner.Classes.RetrofitClient;
import com.example.adventureplanner.Classes.items;
import com.example.adventureplanner.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    Button CreatePlan,ViewPlan;
    private PopularTripsAdapter adapter;
    private RecyclerView historicaltrips,religoustrips,populartrips;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        CreatePlan = view.findViewById(R.id.createplan);
        ViewPlan = view.findViewById(R.id.viewplan);
        populartrips = (RecyclerView) view.findViewById(R.id.populartrips);
        historicaltrips = view.findViewById(R.id.historicaltrips);
        religoustrips = view.findViewById(R.id.religioustrip);

         populartrips.setLayoutManager(new LinearLayoutManager(getContext()));
         adapter = new PopularTripsAdapter();
         populartrips.setAdapter(adapter);

         
        CreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapViewFragment mapViewFragment = new MapViewFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framelayout, mapViewFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        ViewPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPlanFragment viewPlanFragment = new ViewPlanFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.framelayout,viewPlanFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }
    public void onViewCreated(@NonNull View view,@NonNull Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        fetchData();
    }
    public void fetchData()
    {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<List<items>> call = apiService.getItems();

        call.enqueue(new Callback<List<items>>() {
            @Override
            public void onResponse(Call<List<items>> call, Response<List<items>> response) {
                if (response.isSuccessful()) {
                    List<items> itemList = response.body();
                    adapter.setItems(itemList);
                } else {
                    Log.e("HomeFragment", "Failed to fetch data: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<List<items>> call, Throwable throwable) {
                Throwable t = null;
                Log.e("HomeFragment", "Error fetching data", t);
            }

        });
    }
}