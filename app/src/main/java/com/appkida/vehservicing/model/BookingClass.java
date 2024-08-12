package com.appkida.vehservicing.model;

public class BookingClass {
    String bookingId;
    String date;
    String time;
    String servicetype;
    String customername;
    String address;

    String centerName;

    String email;
    String userId;
    String status;

    public BookingClass() {

    }


    public BookingClass(String bookingId, String date, String time, String servicetype, String customername, String address, String centerName, String email, String userId, String status) {
        this.bookingId = bookingId;
        this.date = date;
        this.time = time;
        this.servicetype = servicetype;
        this.customername = customername;
        this.address = address;
        this.centerName = centerName;
        this.email = email;
        this.userId = userId;
        this.status = status;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}