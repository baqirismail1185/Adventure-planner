package com.example.adventureplanner.Classes;

import com.squareup.okhttp.ResponseBody;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiService {
    @POST("User/SignUp")
    Call<Void> signup(@Body UserData userData/*@Query("Name") String name, @Query("UserName")String username, @Query("ContactNo")String email, @Query("Password")String password,@Query("ProfileImg")String userimg*/);
    @Multipart
    @POST
    Call<String> doSignUp(@Url String url
            , @PartMap Map<String, RequestBody> partBody
            , @Part MultipartBody.Part imagePart);

    @FormUrlEncoded
    @POST
    @GET("User/Login")
    Call<LoginResponse> login(@Query("UserName") String email,@Query("Password") String password);

    /*@POST
    @FormUrlEncoded
    Call<ResponseBody> */
    @GET("User/getdata")
    Call<List<items>> getItems();

}
