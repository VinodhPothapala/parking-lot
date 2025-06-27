package com.example.demo.Models;

public class Vehicle {
    private String licensePlate;
    private SlotSize size;

    public Vehicle() {
        // Jackson
    }
    public Vehicle(String p, SlotSize s) {
        this.licensePlate = p; this.size = s;
    }
    public String getLicensePlate() {
        return licensePlate;
    }
    public SlotSize getSize(){
        return size;
    }
    public void setLicensePlate(String l){
        this.licensePlate = l;
    }
    public void setSize(SlotSize s){
        this.size = s;
    }

}
