package com.example.myapplication.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.myapplication.R;
import com.example.myapplication.admin.AdminPage;
import com.example.myapplication.helper.MultipartRequest;
import com.example.myapplication.helper.VolleySingleton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ProfilePhotoActivity extends AppCompatActivity {

    private static final String TAG = "ProfilePhotoActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView profileImageView;
    private Button captureButton;
    private Button selectButton;
    private Button uploadButton;
    private Button updateButton;
    private Button deleteButton;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_photo);

        profileImageView = findViewById(R.id.profileImageView);
        captureButton = findViewById(R.id.captureButton);
        selectButton = findViewById(R.id.selectButton);
        uploadButton = findViewById(R.id.uploadButton);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton.setVisibility(View.GONE); // Initially hide the update button
        deleteButton.setVisibility(View.GONE); // Initially hide the delete button

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "captureButton clicked");
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Log.e(TAG, "No camera app available");
                }
            }
        });

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "selectButton clicked");
                Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK);
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "uploadButton clicked");
                if (selectedImageUri != null) {
                    uploadImage(selectedImageUri);
                } else {
                    Log.e(TAG, "No image selected");
                    Toast.makeText(ProfilePhotoActivity.this, "Please capture or select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "updateButton clicked");
                if (selectedImageUri != null) {
                    updateImage(selectedImageUri);
                } else {
                    Log.e(TAG, "No image selected");
                    Toast.makeText(ProfilePhotoActivity.this, "Please capture or select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "deleteButton clicked");
                deleteImage();
            }
        });

        // Load the profile photo from the server
        loadProfilePhoto();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Log.d(TAG, "Image captured");
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    if (imageBitmap != null) {
                        selectedImageUri = getImageUri(imageBitmap);
                        profileImageView.setImageBitmap(imageBitmap);
                    } else {
                        Log.e(TAG, "No image data in extras");
                    }
                } else {
                    Log.e(TAG, "No extras in captured image data");
                }
            } else if (requestCode == REQUEST_IMAGE_PICK && data != null) {
                Log.d(TAG, "Image picked");
                selectedImageUri = data.getData();
                profileImageView.setImageURI(selectedImageUri);
            }
        } else {
            Log.e(TAG, "Activity result not OK");
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        Log.d(TAG, "getImageUri: Converting bitmap to URI");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImage(Uri imageUri) {
        Log.d(TAG, "uploadImage: imageUri=" + imageUri);

        byte[] imageData = convertImageUriToBytes(imageUri);
        if (imageData != null) {
            Log.d(TAG, "Image data converted to bytes. Size: " + imageData.length);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            long userId = sharedPreferences.getLong("userId", -1);
            Log.d(TAG, "User ID retrieved from shared preferences: " + userId);

            String uploadUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/" + userId + "/image";
            Log.d(TAG, "Upload URL: " + uploadUrl);

            MultipartRequest multipartRequest = new MultipartRequest(
                    Request.Method.POST,
                    uploadUrl,
                    imageData,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Image upload success. Response: " + response);
                            Toast.makeText(ProfilePhotoActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            updateButton.setVisibility(View.VISIBLE); // Show the update button
                            deleteButton.setVisibility(View.VISIBLE); // Show the delete button
                            uploadButton.setVisibility(View.GONE); // Hide the upload button
//                            Intent intent = new Intent(ProfilePhotoActivity.this, Profile.class);
//                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Image upload failed. Error: " + error.getMessage());
                            Toast.makeText(ProfilePhotoActivity.this, "Image upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            Log.d(TAG, "MultipartRequest created. Adding to request queue.");
            VolleySingleton.getInstance(this).addToRequestQueue(multipartRequest);
        } else {
            Log.e(TAG, "Failed to convert image URI to bytes");
        }
    }

    private void updateImage(Uri imageUri) {
        Log.d(TAG, "updateImage: imageUri=" + imageUri);

        byte[] imageData = convertImageUriToBytes(imageUri);
        if (imageData != null) {
            Log.d(TAG, "Image data converted to bytes. Size: " + imageData.length);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            long userId = sharedPreferences.getLong("userId", -1);
            Log.d(TAG, "User ID retrieved from shared preferences: " + userId);

            String updateUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/" + userId + "/image";
            Log.d(TAG, "Update URL: " + updateUrl);

            MultipartRequest multipartRequest = new MultipartRequest(
                    Request.Method.PUT,
                    updateUrl,
                    imageData,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, "Image update success. Response: " + response);
                            Toast.makeText(ProfilePhotoActivity.this, "Image updated successfully", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(ProfilePhotoActivity.this, Profile.class);
//                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Image update failed. Error: " + error.getMessage());
                            Toast.makeText(ProfilePhotoActivity.this, "Image update failed", Toast.LENGTH_SHORT).show();
                        }
                    }
            );

            Log.d(TAG, "MultipartRequest created. Adding to request queue.");
            VolleySingleton.getInstance(this).addToRequestQueue(multipartRequest);
        } else {
            Log.e(TAG, "Failed to convert image URI to bytes");
        }
    }

    private void deleteImage() {
        Log.d(TAG, "deleteImage");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);
        Log.d(TAG, "User ID retrieved from shared preferences: " + userId);

        String deleteUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/" + userId + "/image";
        Log.d(TAG, "Delete URL: " + deleteUrl);

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                deleteUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Image delete success. Response: " + response);
                        Toast.makeText(ProfilePhotoActivity.this, "Image deleted successfully", Toast.LENGTH_SHORT).show();
                        updateButton.setVisibility(View.GONE); // Hide the update button
                        deleteButton.setVisibility(View.GONE); // Hide the delete button
                        uploadButton.setVisibility(View.VISIBLE); // Show the upload button
                        profileImageView.setImageResource(R.drawable.user); // Set default profile photo
//                        Intent intent = new Intent(ProfilePhotoActivity.this, Profile.class);
//                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Image delete failed. Error: " + error.getMessage());
                        Toast.makeText(ProfilePhotoActivity.this, "Image delete failed", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        Log.d(TAG, "StringRequest created. Adding to request queue.");
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private byte[] convertImageUriToBytes(Uri imageUri) {
        Log.d(TAG, "convertImageUriToBytes: imageUri=" + imageUri);

        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(imageUri);
            if (inputStream == null) {
                Log.e(TAG, "Failed to open InputStream: URI may be invalid.");
                return null;
            }

            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];

            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                byteBuffer.write(buffer, 0, len);
            }

            byte[] imageBytes = byteBuffer.toByteArray();
            Log.d(TAG, "Image converted to bytes. Size: " + imageBytes.length);
            return imageBytes;
        } catch (IOException e) {
            Log.e(TAG, "Error converting image to bytes: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Failed to close InputStream: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void loadProfilePhoto() {
        Log.d(TAG, "loadProfilePhoto: Loading profile photo from server");

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        long userId = sharedPreferences.getLong("userId", -1);
        Log.d(TAG, "User ID retrieved from shared preferences: " + userId);

        String profilePhotoUrl = "http://coms-309-046.class.las.iastate.edu:8080/users/" + userId + "/image";
        Log.d(TAG, "Profile photo URL: " + profilePhotoUrl);

        ImageRequest imageRequest = new ImageRequest(
                profilePhotoUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.d(TAG, "Profile photo loaded successfully");
                        profileImageView.setImageBitmap(response);
                        updateButton.setVisibility(View.VISIBLE); // Show the update button
                        deleteButton.setVisibility(View.VISIBLE); // Show the delete button
                        uploadButton.setVisibility(View.GONE); // Hide the upload button
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error loading profile photo: " + error.toString());
                        profileImageView.setImageResource(R.drawable.user); // Set default profile photo
                        updateButton.setVisibility(View.GONE); // Hide the update button
                        deleteButton.setVisibility(View.GONE); // Hide the delete button
                        uploadButton.setVisibility(View.VISIBLE); // Show the upload button
                    }
                }
        );

        VolleySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }
}