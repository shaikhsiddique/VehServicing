package com.appkida.vehservicing.model;

import java.util.List;

public class ServicingCenter {
    private String serviceName;
    private String address;
    private String phoneNumber;
    private String email;
    private String openingHours;
     private String managerName;
    private String managerEmail;

    private String image_url;

    private List<String> facilities; // Changed to list of strings
    private List<String> services;   // Changed to list of strings

    // Default constructor for Firebase
    public ServicingCenter() {
        // Default constructor required for calls to DataSnapshot.getValue(ServiceCenter.class)
    }

    // Parameterized constructor


    public ServicingCenter(String serviceName, String address, String phoneNumber, String email, String openingHours, String managerName, String managerEmail, String image_url, List<String> facilities, List<String> services) {
        this.serviceName = serviceName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.openingHours = openingHours;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
        this.image_url = image_url;
        this.facilities = facilities;
        this.services = services;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }


    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerEmail() {
        return managerEmail;
    }

    public void setManagerEmail(String managerEmail) {
        this.managerEmail = managerEmail;
    }

    // Add methods to set and get facilities and services
    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
