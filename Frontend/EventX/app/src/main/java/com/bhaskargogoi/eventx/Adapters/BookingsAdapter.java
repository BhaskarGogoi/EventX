package com.bhaskargogoi.eventx.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bhaskargogoi.eventx.BookingDetails;
import com.bhaskargogoi.eventx.Models.Booking;
import com.bhaskargogoi.eventx.R;

import java.util.List;

public class BookingsAdapter  extends RecyclerView.Adapter<BookingsAdapter.ShowHolder> {
    private Context context;
    private List<Booking> bookingList;
    private String coverImageUrl;

    public BookingsAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }
    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_booking_item, parent, false);
        return new BookingsAdapter.ShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {
        final Booking booking = bookingList.get(position);
        holder.tv_booking_id.setText("Booking Id: " + booking.getBooking_id());
        holder.tv_event_name.setText(booking.getEvent_name());
        holder.tv_date.setText(booking.getBooking_date());
        holder.tv_no_of_tickets.setText(booking.getNo_of_tickets() + "Ticket(s)");

        holder.booking_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingDetails.class);
                intent.putExtra("booking_id", booking.getBooking_id());
                intent.putExtras(intent);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class ShowHolder extends RecyclerView.ViewHolder {
        TextView tv_booking_id, tv_event_name, tv_date, tv_no_of_tickets;
        CardView booking_card_view;
        public ShowHolder(@NonNull View itemView) {
            super(itemView);
            tv_booking_id = itemView.findViewById(R.id.tv_booking_id);
            tv_event_name = itemView.findViewById(R.id.tv_event_name);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_no_of_tickets = itemView.findViewById(R.id.tv_no_of_tickets);
            booking_card_view = itemView.findViewById(R.id.booking_card_view);
        }
    }
}
