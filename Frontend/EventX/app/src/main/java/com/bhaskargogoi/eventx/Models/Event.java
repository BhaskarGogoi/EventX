package com.bhaskargogoi.eventx.Models;

public class Event {

    private String event_id;
    private String organized_by;
    private String event_name;
    private String event_type;
    private String event_mode;
    private String hosted_by;
    private String date;
    private String time;
    private String duration;
    private String price;
    private String max_tickets;
    private String about;
    private String address_area;
    private String address_locality;
    private String address_city;
    private String address_state;
    private String address_pin;
    private String status;

    public Event(){

    }

    public Event(String event_id, String organized_by, String event_name, String event_type, String event_mode, String hosted_by, String date, String time, String duration, String price, String max_tickets, String about, String status) {
        this.event_id = event_id;
        this.organized_by = organized_by;
        this.event_name = event_name;
        this.event_type = event_type;
        this.event_mode = event_mode;
        this.hosted_by = hosted_by;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.price = price;
        this.max_tickets = max_tickets;
        this.about = about;
        this.status = status;
    }

    public Event(String event_id, String organized_by, String event_name, String event_type, String event_mode, String hosted_by, String date, String time, String duration, String price, String max_tickets, String about, String address_area, String address_locality, String address_city, String address_state, String address_pin, String status) {
        this.event_id = event_id;
        this.organized_by = organized_by;
        this.event_name = event_name;
        this.event_type = event_type;
        this.event_mode = event_mode;
        this.hosted_by = hosted_by;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.price = price;
        this.max_tickets = max_tickets;
        this.about = about;
        this.address_area = address_area;
        this.address_locality = address_locality;
        this.address_city = address_city;
        this.address_state = address_state;
        this.address_pin = address_pin;
        this.status = status;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getOrganized_by() {
        return organized_by;
    }

    public void setOrganized_by(String organized_by) {
        this.organized_by = organized_by;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getEvent_mode() {
        return event_mode;
    }

    public void setEvent_mode(String event_mode) {
        this.event_mode = event_mode;
    }

    public String getHosted_by() {
        return hosted_by;
    }

    public void setHosted_by(String hosted_by) {
        this.hosted_by = hosted_by;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMax_tickets() {
        return max_tickets;
    }

    public void setMax_tickets(String max_tickets) {
        this.max_tickets = max_tickets;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress_area() {
        return address_area;
    }

    public void setAddress_area(String address_area) {
        this.address_area = address_area;
    }

    public String getAddress_locality() {
        return address_locality;
    }

    public void setAddress_locality(String address_locality) {
        this.address_locality = address_locality;
    }

    public String getAddress_city() {
        return address_city;
    }

    public void setAddress_city(String address_city) {
        this.address_city = address_city;
    }

    public String getAddress_state() {
        return address_state;
    }

    public void setAddress_state(String address_state) {
        this.address_state = address_state;
    }

    public String getAddress_pin() {
        return address_pin;
    }

    public void setAddress_pin(String address_pin) {
        this.address_pin = address_pin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
