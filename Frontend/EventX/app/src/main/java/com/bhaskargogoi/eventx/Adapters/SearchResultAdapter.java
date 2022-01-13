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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultHolder> {

    Context context;
    List<Event> eventList;
    String imageUrl;

    public SearchResultAdapter(Context context, List<Event> eventList, String imageUrl) {
        this.context = context;
        this.eventList = eventList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_result_card, parent, false);
        return new SearchResultAdapter.ResultHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        final Event event = eventList.get(position);
        holder.tv_title.setText(event.getEvent_name());
        holder.tv_event_type.setText(event.getEvent_type());
        holder.tv_hosted_by.setText(event.getHosted_by());
        final String completeImageUrl = imageUrl + event.getEvent_id() + ".jpg";
        Glide.with(context).load(completeImageUrl).into(holder.coverImage);

        holder.search_result_card.setOnClickListener(new View.OnClickListener() {
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

    public class ResultHolder extends RecyclerView.ViewHolder {
        TextView tv_title, tv_event_type, tv_hosted_by;
        ImageView coverImage;
        CardView search_result_card;
        public ResultHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.title);
            tv_event_type = itemView.findViewById(R.id.tv_event_type);
            tv_hosted_by = itemView.findViewById(R.id.tv_hosted_by);
            coverImage = itemView.findViewById(R.id.coverImage);
            search_result_card = itemView.findViewById(R.id.search_result_card);
        }
    }
}
