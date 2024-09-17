package com.example.myapplication.CyFind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CyFind_Home extends AppCompatActivity {

    private LinearLayout itemsLayout;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cyfind_home);

        itemsLayout = findViewById(R.id.scrollView).findViewById(R.id.linearLayout); // Update to find the correct layout
        Button foundButton = findViewById(R.id.found);
        foundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CyFind_Home.this, ItemFound.class));
            }
        });

        sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        fetchItems();

        ImageButton backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // This will close the current activity
            }
        });
    }

    private void fetchItems() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/items/";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        populateItems(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest);
    }
    private void populateItems(JSONArray items) {
        LayoutInflater inflater = getLayoutInflater();
        for (int i = 0; i < items.length(); i++) {
            try {
                JSONObject item = items.getJSONObject(i);
                CardView cardView = (CardView) inflater.inflate(R.layout.cyfind_item_card, itemsLayout, false);

                TextView itemId = cardView.findViewById(R.id.textItemId);
                TextView itemName = cardView.findViewById(R.id.textItemName);
                TextView itemCategory = cardView.findViewById(R.id.textCategory);
                ImageView itemImage = cardView.findViewById(R.id.imageItem);

                itemId.setText("ID: " + item.getString("id"));
                itemName.setText(item.getString("itemName"));
                itemCategory.setText(item.getString("category"));

                // Construct the image URL using the item ID
                String imageUrl = "http://coms-309-046.class.las.iastate.edu:8080/api/items/image/" + item.getString("id");

                // Load image using Volley
                loadImageWithVolley(imageUrl, itemImage);

                final String itemIdValue = item.getString("id");
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Save item ID to SharedPreferences
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("itemId", itemIdValue);
                        editor.apply();

                        // Handle card click to navigate to ItemDetails.java
                        startActivity(new Intent(CyFind_Home.this, ItemDetails.class));
                    }
                });

                itemsLayout.addView(cardView);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadImageWithVolley(String imageUrl, ImageView imageView) {
        ImageRequest imageRequest = new ImageRequest(imageUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        // Handle the error, e.g., display a placeholder image
                        imageView.setImageResource(R.drawable.ic_launcher_background);
                    }
                });

        VolleySingleton.getInstance(this).addToRequestQueue(imageRequest);
    }
}