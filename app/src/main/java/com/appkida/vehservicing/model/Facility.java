package com.appkida.vehservicing.model;

public class Facility {
    private String name;
    private int imageResourceId; // Resource ID for the image in the drawable folder
    private boolean isSelected;

    public Facility(String name, int imageResourceId) {
        this.name = name;
        this.imageResourceId = imageResourceId;
        this.isSelected = false; // By default, a facility is not selected
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
