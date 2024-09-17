package com.example.myapplication.SOH;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {

    private List<Club> clubs;
//    private boolean isMyClub;
    private Context context;

    public ClubAdapter(List<Club> clubs, boolean isMyClub, Context context) {
        this.clubs = clubs;
        //this.isMyClub = isMyClub;
        this.context = context;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_club, parent, false);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClubViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.textViewClubName.setText(club.getName());

        // Set click listener on the item view to navigate to club details page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ClubDetailsActivity.class);
                intent.putExtra("clubId", club.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClubName;

        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewClubName = itemView.findViewById(R.id.textViewClubName);
        }
    }
}