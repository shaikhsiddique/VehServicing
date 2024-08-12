package com.appkida.vehservicing.model;

public class Service {
    private String name;
    private int imageResourceId;
     String imgUrl;

    private boolean isSelected; // New field to track selection state

    public Service(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.isSelected = false; // Default to not selected
    }

    public String getName() {
        return name;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}