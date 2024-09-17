package com.example.myapplication.CyFind;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.myapplication.R;
import com.example.myapplication.helper.MultipartRequest;
import com.example.myapplication.helper.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ItemFound extends AppCompatActivity {

    private EditText itemNameEditText, descriptionEditText, locationEditText, emailIdEditText;
    private Spinner categorySpinner;
    private TextView selectedDateEditText;
    private Uri selectedImageUri;
    private String selectedDate;

    private static final int REQUEST_IMAGE_PICK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemfound);

        itemNameEditText = findViewById(R.id.item_name_edittext);
        descriptionEditText = findViewById(R.id.description);
        locationEditText = findViewById(R.id.location);
        categorySpinner = findViewById(R.id.categorySpinner);
        Button submitButton = findViewById(R.id.submit_button);
        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        ImageButton datePickerButton = findViewById(R.id.datePickerButton);
        selectedDateEditText = findViewById(R.id.selectedDateEditText);
        emailIdEditText = findViewById(R.id.emailIdEditText);

        // Find the Spinner
        Spinner categorySpinner = findViewById(R.id.categorySpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        categorySpinner.setAdapter(categoryAdapter);

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
            }
        });

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitFoundItem();
                } catch (JSONException | IOException e) {
                    Toast.makeText(ItemFound.this, "Error creating request data.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Handle the selected date here
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR, year);
                        selectedCalendar.set(Calendar.MONTH, monthOfYear);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        selectedDate = sdf.format(selectedCalendar.getTime());
                        selectedDateEditText.setText(selectedDate);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            // Display the selected image or perform any other desired actions
        }
    }

    private void submitFoundItem() throws JSONException, IOException {
        // Create the data to send
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("itemName", itemNameEditText.getText().toString());
        jsonObject.put("description", descriptionEditText.getText().toString());
        jsonObject.put("location", locationEditText.getText().toString());
        jsonObject.put("category", categorySpinner.getSelectedItem().toString());
        jsonObject.put("dateFound", selectedDate);

        String emailId = emailIdEditText.getText().toString();

        // Prepare URL
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/items/";

        // Convert image to byte array
        byte[] imageBytes = getImageBytes();

        // Create the request body
        String boundary = "apiclient-" + System.currentTimeMillis();
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            // Write image data
            if (imageBytes != null) {
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"image.jpg\"" + lineEnd);
                dos.writeBytes(lineEnd);
                dos.write(imageBytes);
                dos.writeBytes(lineEnd);
            }

            // Write JSON data
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"itemName\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jsonObject.optString("itemName"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"description\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jsonObject.optString("description"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"dateFound\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jsonObject.optString("dateFound"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"category\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jsonObject.optString("category"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"emailId\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(emailId);
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"location\"" + lineEnd);
            dos.writeBytes(lineEnd);
            dos.writeBytes(jsonObject.optString("location"));
            dos.writeBytes(lineEnd);

            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a custom request
        Request<String> request = new Request<String>(Request.Method.POST, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle the error here
                Toast.makeText(ItemFound.this, "Failed to submit item: " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "multipart/form-data;boundary=" + boundary;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return bos.toByteArray();
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                try {
                    String responseString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                }
            }

            @Override
            protected void deliverResponse(String response) {
                // Handle the server response here
                Toast.makeText(ItemFound.this, "Item submitted successfully!", Toast.LENGTH_LONG).show();
                finish(); // Finish the activity on successful submission

                // Restart CyFind_Home activity
                Intent intent = new Intent(ItemFound.this, CyFind_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        };

        // Add the request to the VolleySingleton queue for execution
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private byte[] getImageBytes() throws IOException {
        if (selectedImageUri != null) {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }
}