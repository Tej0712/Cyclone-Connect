package com.example.myapplication.admin.cyfind;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;

public class admin_delete_cyfind extends AppCompatActivity {

    private EditText editTextItemId;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_cyfind);

        editTextItemId = findViewById(R.id.editTextItemId);
        Button buttonDelete = findViewById(R.id.buttonDelete);
        requestQueue = Volley.newRequestQueue(this);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
    }

    private void deleteItem() {
        String itemId = editTextItemId.getText().toString().trim();

        if (itemId.isEmpty()) {
            Toast.makeText(this, "Please enter an item ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/items/" + itemId;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(admin_delete_cyfind.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        editTextItemId.setText("");
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(admin_delete_cyfind.this, "Error deleting item: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(stringRequest);
    }
}