package com.bhaskargogoi.eventx.Models;

public class Booking {
    private String booking_id;
    private String event_id;


    private String event_name;
    private String no_of_tickets;
    private String total_price;
    private String booking_date;

    public Booking(){

    }

    public Booking(String booking_id, String event_id, String event_name, String user_id, String no_of_tickets, String total_price, String booking_date) {
        this.booking_id = booking_id;
        this.event_id = event_id;
        this.event_name = event_name;
        this.no_of_tickets = no_of_tickets;
        this.total_price = total_price;
        this.booking_date = booking_date;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getNo_of_tickets() {
        return no_of_tickets;
    }

    public void setNo_of_tickets(String no_of_tickets) {
        this.no_of_tickets = no_of_tickets;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getBooking_date() {
        return booking_date;
    }

    public void setBooking_date(String booking_date) {
        this.booking_date = booking_date;
    }
}
