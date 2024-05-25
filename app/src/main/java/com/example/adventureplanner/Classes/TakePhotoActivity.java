package com.example.adventureplanner.Classes;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;


public class TakePhotoActivity extends AppCompatActivity {

    /**
     * Flag to identify camera access permission is requested
     */
    public static final int TAKE_PHOTO_PERMISSION = 101;
    /**
     * Flag to identify gallery access permission is requested
     */
    public static final int CHOOSE_PHOTO_PERMISSION = 102;
    /**
     * Flag to identify camera intent is called
     */
    public static final int TAKE_PHOTO_RESULT_CODE = 110;
    /**
     * Flag to identify gallery intent is called
     */
    public static final int CHOOSE_PHOTO_RESULT_CODE = 120;
    /**
     * Flag to identify gallery intent is called
     */
    public static final int CHOOSE_MULTIPLE_RESULT_CODE = 130;

    public Uri selectedPhotoUri;
    public ActivityResultLauncher<Intent> imagePickerLauncher;

    public void initializeImagePicker()
    {
        imagePickerLauncher = this.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult o)
            {
                if (o.getResultCode() == Activity.RESULT_OK)
                {
                    if (o.getData() != null)
                    {
                        handlePhotoResult();
                    }
                    else
                        handlePhotoResult();

                }
                else
                {
                    selectedPhotoUri = null;
                }
            }
        });
    }


    /**
     * show user dialog to select either open camera Or gallery
     */
    protected void showChoosePhotoDialog()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (PermissionUtils.checkAndRequestPermissions(TakePhotoActivity.this
                    , new String[]{Manifest.permission.READ_MEDIA_IMAGES}, CHOOSE_PHOTO_PERMISSION))
                dispatchChoosePictureIntent();
        } else
        {
            if (PermissionUtils.checkAndRequestPermissions(TakePhotoActivity.this
                    , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO_PERMISSION))
                dispatchChoosePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CHOOSE_PHOTO_PERMISSION && PermissionUtils.verifyPermission(grantResults))
        {
            dispatchChoosePictureIntent();
        }
        else
            Toast.makeText(this, "permission not granted.", Toast.LENGTH_SHORT).show();
    }


    private void handlePhotoResult()
    {
        Intent resultIntent = new Intent();
        resultIntent.setData(selectedPhotoUri);
        setResult(RESULT_OK, resultIntent);
    }


    /**
     * create and open choose from gallery/photos intent
     */
    public void dispatchChoosePictureIntent()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        imagePickerLauncher.launch(intent);
    }



}
