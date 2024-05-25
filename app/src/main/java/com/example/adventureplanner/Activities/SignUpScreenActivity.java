package com.example.adventureplanner.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.adventureplanner.Classes.ApiService;
import com.example.adventureplanner.Classes.FileUtils;
import com.example.adventureplanner.Classes.RetrofitClient;
import com.example.adventureplanner.Classes.TakePhotoActivity;
import com.example.adventureplanner.Classes.UserData;
import com.example.adventureplanner.R;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpScreenActivity extends TakePhotoActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    //  private static final String BASE_URL = "http://192.168.108.191/Adventure%20Planner/api/";
    Button signupbtn;
    EditText name,username,emailtxt,passwordtxt;
    ImageView chooseimg;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE = 1;
    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        signupbtn = findViewById(R.id.signupbtn);
        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        emailtxt = findViewById(R.id.emailtxt);
        passwordtxt = findViewById(R.id.passwordtxt);
        chooseimg = findViewById(R.id.chooseimg);
        initializeImagePicker();
        chooseimg.setOnClickListener(v -> showChoosePhotoDialog());


        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // Create UserData object with data to be inserted
        //UserData userData = new UserData();


        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signUp();
                } catch (JSONException e) {
                    Log.d("error==>>",e.getMessage());
                }

               /* userData.setName(name.getText().toString());
                userData.setUsername(username.getText().toString());
                userData.setEmail(emailtxt.getText().toString());
                userData.setPassword(passwordtxt.getText().toString());
                byte[] imageData = convertImageToByteArray(selectedImageUri);
                Call<UserData> call = apiService.insertUserData(userData.getName(),userData.getUsername(), userData.getEmail(), userData.getPassword(), String.valueOf(userData.getUserimage()));
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(SignUpScreenActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpScreenActivity.this, home_screenActivity.class));

                        } else {
                            Log.d("", response.errorBody().toString());
                            Toast.makeText(SignUpScreenActivity.this, "Failed to insert data", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Toast.makeText(SignUpScreenActivity.this, "Network error", Toast.LENGTH_SHORT).show();
                    }
                });*/
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {

                selectedImageUri = data.getData();
                loadImageInView();
            } else if (selectedImageUri != null) {
                loadImageInView();
            }
        } else {
            selectedImageUri = null;
            Toast.makeText(this, "Error, Try again selecting images", Toast.LENGTH_SHORT).show();
        }


    }

    private void loadImageInView() {
        chooseimg.setImageURI(selectedImageUri);
    }
    /*private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            chooseimg.setImageURI(selectedImageUri);
        }
    }
*/

    private void signUp() throws JSONException {
        // Get user details
        String Name = name.getText().toString().trim();
        String Username = username.getText().toString().trim();
        String email = emailtxt.getText().toString().trim();
        String password = passwordtxt.getText().toString().trim();

        // Convert selected image to byte array
//        byte[] imageData = convertImageToByteArray(selectedImageUri);
        File file = new File(Objects.requireNonNull(FileUtils.getPath(SignUpScreenActivity.this, selectedImageUri)));
        if (!file.exists()) {
            Toast.makeText(this, "File does not exist.", Toast.LENGTH_SHORT).show();
            return;
        }
        JSONObject object=new JSONObject();
        object.put("name",Name);
        object.put("username",Username);
        object.put("password",password);
        object.put("email",email);
        Map<String, RequestBody> bodyMap = new HashMap<>();
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), Name);
        RequestBody userNameBody = RequestBody.create(MediaType.parse("text/plain"), Username);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("text/plain"), password);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);
        bodyMap.put("name",nameBody);
        bodyMap.put("username",userNameBody);
        bodyMap.put("password",passwordBody);
        bodyMap.put("email",emailBody);


        // Send user details and image data to server using Retrofit
        Call<String> call = RetrofitClient.getClient().create(ApiService.class).doSignUp("User/SignUp",bodyMap,body);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpScreenActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpScreenActivity.this, home_screenActivity.class));
                    // Navigate to the next screen or perform other actions
                } else {
                    try {
                        Log.d("Error>>",response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(SignUpScreenActivity.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error",t.getMessage());
                Toast.makeText(SignUpScreenActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}