package com.bhaskargogoi.eventx.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ViewAllEventsRecyclerView extends RecyclerView.Adapter<ViewAllEventsRecyclerView.EventsHorizontalHolder> {
    private Context context;
    private List<Event> eventList;
    private String imageURL;

    public ViewAllEventsRecyclerView(Context context, List<Event> eventList, String imageURL) {
        this.context = context;
        this.eventList = eventList;
        this.imageURL = imageURL;
    }

    @NonNull
    @Override
    public EventsHorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card_horizontal, parent, false);
        return new ViewAllEventsRecyclerView.EventsHorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsHorizontalHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.tv_title.setText(event.getEvent_name());
        holder.tv_price.setText(context.getString(R.string.Rs) + " " + event.getPrice());
        holder.tv_event_type.setText(event.getEvent_type());
        holder.tv_hosted_by.setText(event.getHosted_by());
        final String completeImageUrl = imageURL + event.getEvent_id() + ".jpg";
        Glide.with(context).load(completeImageUrl).into(holder.coverImage);

        holder.eventHorizontalCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetails.class);
                Bundle bundle = new Bundle();
                bundle.putString("event_id" , event.getEvent_id());
                bundle.putString("event_name" , event.getEvent_name());
                bundle.putString("imageUrl" , completeImageUrl);

                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventsHorizontalHolder extends RecyclerView.ViewHolder{

        ImageView coverImage;
        TextView tv_title, tv_event_type, tv_hosted_by, tv_price;
        CardView eventHorizontalCardView;

        public EventsHorizontalHolder(@NonNull View itemView) {
            super(itemView);
            eventHorizontalCardView = itemView.findViewById(R.id.eventHorizontalCardView);
            coverImage = itemView.findViewById(R.id.coverImage);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_event_type = itemView.findViewById(R.id.tv_event_type);
            tv_hosted_by = itemView.findViewById(R.id.tv_hosted_by);
            tv_price = itemView.findViewById(R.id.tv_price);

        }
    }
}
