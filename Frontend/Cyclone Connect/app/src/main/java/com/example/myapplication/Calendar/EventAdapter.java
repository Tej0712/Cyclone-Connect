package com.example.myapplication.Calendar;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.time.format.DateTimeFormatter;
import java.util.List;
public class EventAdapter extends ArrayAdapter<Event> {
    private OnEventClickListener onEventClickListener;

    public EventAdapter(@NonNull Context context, List<Event> events, OnEventClickListener onEventClickListener) {
        super(context, R.layout.event_cell, events);
        this.onEventClickListener = onEventClickListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_cell, parent, false);
        }

        Event event = getItem(position);
        if (event != null) {
            TextView eventCellTV = convertView.findViewById(R.id.eventCellTV);
            String eventTitle = event.getTitle() + " (" + event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")) + ")";
            eventCellTV.setText(eventTitle);

            convertView.setOnClickListener(v -> onEventClickListener.onEventClick(event));
        }

        return convertView;
    }

    public interface OnEventClickListener {
        void onEventClick(Event event);
    }
}

