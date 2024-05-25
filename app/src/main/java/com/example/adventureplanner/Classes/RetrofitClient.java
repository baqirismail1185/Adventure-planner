package com.example.adventureplanner.Classes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
        private static Retrofit retrofit=null;

        public static Retrofit getClient(){
            if (retrofit == null)
            {
                retrofit=new Retrofit.Builder()
                        .baseUrl("http://192.168.171.191/Adventure%20Planner/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }

}
