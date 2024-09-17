package com.example.myapplication.Notes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    private NotesListAdapter adapter;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notes");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton fab = findViewById(R.id.fab);

        adapter = new NotesListAdapter();
        recyclerView.setAdapter(adapter);
        // Set the layout manager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
            startActivity(intent);
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        userId = sharedPreferences.getLong("userId", -1);

        fetchNotes();
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchNotes();
    }

    private void fetchNotes() {
        String url = "http://coms-309-046.class.las.iastate.edu:8080/api/users/" + userId + "/notes";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    List<Note> notes = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            long id = jsonObject.getLong("id");
                            String title = jsonObject.getString("title");
                            String content = jsonObject.getString("content");
                            Note note = new Note(id, title, content, userId);
                            notes.add(note);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.setNotes(notes);
                },
                error -> {
                    // Handle the error
                    Toast.makeText(NotesListActivity.this, "Error fetching notes", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private static class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {
        private List<Note> notes;

        public NotesListAdapter() {
            notes = new ArrayList<>();
        }

        @SuppressLint("NotifyDataSetChanged")
        public void setNotes(List<Note> notes) {
            this.notes = notes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Note note = notes.get(position);
            holder.bind(note);
            holder.itemView.setOnClickListener(v -> {
                long noteId = note.getId();
                Intent intent = new Intent(holder.itemView.getContext(), NoteActivity.class);
                intent.putExtra("noteId", noteId);
                holder.itemView.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return notes.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvTitle;
            private final TextView tvContent;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvContent = itemView.findViewById(R.id.tv_content);
            }

            void bind(Note note) {
                tvTitle.setText(note.getTitle());
                tvContent.setText(note.getContent());
            }
        }
    }
}