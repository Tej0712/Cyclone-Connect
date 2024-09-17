package com.example.myapplication.CyFind;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;
import org.json.JSONObject;

public class ItemDetails extends AppCompatActivity {

    private TextView title, address, dateFound, mail, description, category;
    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemdetails);

        title = findViewById(R.id.title);
        address = findViewById(R.id.address);
        dateFound = findViewById(R.id.dateFound);
        mail = findViewById(R.id.mail);
        description = findViewById(R.id.description);
        category = findViewById(R.id.category);
        img = findViewById(R.id.img);

        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String itemId = sharedPref.getString("itemId", "");

        if (!itemId.isEmpty()) {
            fetchItemDetails(Long.parseLong(itemId));
        }

        findViewById(R.id.backBtn).setOnClickListener(v -> finish());
    }

    private void fetchItemDetails(long itemId) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/items/" + itemId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this::updateUI,
                Throwable::printStackTrace);

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void updateUI(JSONObject item) {
        try {
            title.setText(item.getString("itemName"));
            address.setText(item.getString("location"));
            dateFound.setText(item.getString("dateFound"));
            mail.setText(item.getString("emailId"));
            description.setText(item.getString("description"));
            category.setText(item.getString("category"));

            // Assuming the item ID can be directly used to construct the image URL
            String imageUrl = "http://coms-309-046.class.las.iastate.edu:8080/api/items/image/" + item.getString("id");
            loadImage(imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadImage(String imageUrl) {
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                response -> img.setImageBitmap(response), 0, 0, ImageView.ScaleType.CENTER_CROP, Bitmap.Config.RGB_565,
                error -> {
                    img.setImageResource(R.drawable.ic_launcher_background); // Set a default or error image
                    error.printStackTrace();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }
}