package com.example.myapplication.admin.Help.Navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.VolleyError;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;

public class DeleteNavigationItemActivity extends AppCompatActivity {

    private EditText etNavigationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_navigation_item);

        etNavigationId = findViewById(R.id.etNavigationId);
        Button btnDeleteNavigationItem = findViewById(R.id.btnDeleteNavigationItem);

        btnDeleteNavigationItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNavigationItem();
            }
        });
    }

    private void deleteNavigationItem() {
        String id = etNavigationId.getText().toString();
        String url = "http://coms-309-046.class.las.iastate.edu:8080/navigations/" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DeleteNavigationItemActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close this activity and return to the previous activity in the stack
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteNavigationItemActivity.this, "Error deleting item", Toast.LENGTH_SHORT).show();
            }
        });

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}
