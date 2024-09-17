package com.example.myapplication.Notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.myapplication.R;
import com.example.myapplication.helper.VolleySingleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class NoteActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etNote;
    private long noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etTitle = findViewById(R.id.et_title);
        etNote = findViewById(R.id.et_note);
        FloatingActionButton fabSave = findViewById(R.id.fab_save);
        FloatingActionButton fabUpdate = findViewById(R.id.update_note);
        ImageButton btnDelete = findViewById(R.id.btn_delete);

        // Get noteId from intent extras
        noteId = getIntent().getLongExtra("noteId", -1);

        if (noteId != -1) {
            // Fetch note details from the server using noteId
            fetchNoteDetails(noteId);
            fabSave.setVisibility(View.GONE);
            fabUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            fabSave.setVisibility(View.VISIBLE);
            fabUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
        }

        fabSave.setOnClickListener(v -> {
            try {
                saveNote();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        fabUpdate.setOnClickListener(v -> {
            try {
                updateNote(noteId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

        btnDelete.setOnClickListener(v -> {
            deleteNote(noteId);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void fetchNoteDetails(long noteId) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + getUserId() + "/notes/" + noteId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String title = response.getString("title");
                        String content = response.getString("content");
                        etTitle.setText(title);
                        etNote.setText(content);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(NoteActivity.this, "Error fetching note details", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void saveNote() throws JSONException {
        String title = etTitle.getText().toString().trim();
        String content = etNote.getText().toString().trim();

        long userId = getUserId();

        JSONObject noteJson = new JSONObject();
        noteJson.put("title", title);
        noteJson.put("content", content);

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId + "/notes";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, noteJson,
                response -> {
                    Toast.makeText(NoteActivity.this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(NoteActivity.this, "Error saving note", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void updateNote(long noteId) throws JSONException {
        String title = etTitle.getText().toString().trim();
        String content = etNote.getText().toString().trim();

        JSONObject noteJson = new JSONObject();
        noteJson.put("title", title);
        noteJson.put("content", content);

        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + getUserId() + "/notes/" + noteId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, noteJson,
                response -> {
                    Toast.makeText(NoteActivity.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(NoteActivity.this, "Error updating note", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private void deleteNote(long noteId) {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + getUserId() + "/notes/" + noteId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                response -> {
                    Toast.makeText(NoteActivity.this, "Note deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Toast.makeText(NoteActivity.this, "Error deleting note", Toast.LENGTH_SHORT).show();
                });

        VolleySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    private long getUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        return sharedPreferences.getLong("userId", -1);
    }
}