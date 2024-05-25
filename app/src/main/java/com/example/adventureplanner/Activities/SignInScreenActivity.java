package com.example.adventureplanner.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.adventureplanner.Classes.ApiService;
import com.example.adventureplanner.Classes.LoginResponse;
import com.example.adventureplanner.Classes.RetrofitClient;
import com.example.adventureplanner.Classes.UserData;
import com.example.adventureplanner.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInScreenActivity extends AppCompatActivity
{
    Button signinbtn;
    EditText usernametxt,passwordtxt;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);
        usernametxt = findViewById(R.id.usernametxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        signinbtn = findViewById(R.id.signinbtn);

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = usernametxt.getText().toString().trim();
                String password = passwordtxt.getText().toString().trim();
                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(SignInScreenActivity.this, "Enter your email and password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Call<LoginResponse> call = apiService.login(email, password);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(SignInScreenActivity.this,"Login Successfull", Toast.LENGTH_SHORT).show();
                            } else {
                                // Handle unsuccessful login response
                                Toast.makeText(SignInScreenActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            // Handle failure
                            Log.e("LoginActivity", "Login failed", t);
                            Toast.makeText(SignInScreenActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                startActivity(new Intent(SignInScreenActivity.this, home_screenActivity.class));
            }
        });
    }
}