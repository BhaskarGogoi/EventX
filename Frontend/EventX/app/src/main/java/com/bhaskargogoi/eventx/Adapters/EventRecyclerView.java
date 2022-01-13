package com.bhaskargogoi.eventx.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhaskargogoi.eventx.EventDetails;
import com.bhaskargogoi.eventx.Models.Event;
import com.bhaskargogoi.eventx.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class EventRecyclerView extends RecyclerView.Adapter<EventRecyclerView.ShowHolder> {
    private Context context;
    private List<Event> showsList;
    private String coverImageUrl;

    public EventRecyclerView(Context context, List<Event> showsList, String coverImageUrl) {
        this.context = context;
        this.showsList = showsList;
        this.coverImageUrl = coverImageUrl;
    }

    @NonNull
    @Override
    public EventRecyclerView.ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_rv_card, parent, false);
        return new ShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerView.ShowHolder holder, int position) {
        final Event event = showsList.get(position);
        holder.title.setText(event.getEvent_name());
        holder.subTitle.setText(event.getEvent_type() + " - " + event.getHosted_by());
        final String imageUrl = coverImageUrl + event.getEvent_id() + ".jpg";
        Glide.with(context).load(imageUrl).into(holder.coverImage);

        holder.event_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("event_id" , event.getEvent_id());
                bundle.putString("event_name" , event.getEvent_name());
                bundle.putString("imageUrl" , imageUrl);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public class ShowHolder extends RecyclerView.ViewHolder{

        TextView title, subTitle;
        ImageView coverImage;
        CardView event_card;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            coverImage = itemView.findViewById(R.id.coverImage);
            event_card = itemView.findViewById(R.id.event_rv_card);

        }
    }
}
