package com.example.demo.Models;

public class Slot {
    private int id;
    private SlotSize size;
    private boolean occupied;
    private Vehicle currentVehicle;

    public Slot() {

    }
    public Slot(int id, SlotSize size) {
        this.id = id; this.size = size; this.occupied = false;
    }

    public int getId(){
        return id;
    }
    public SlotSize getSize(){
        return size;
    }
    public boolean isOccupied(){
        return occupied;
    }
    public Vehicle getCurrentVehicle(){
        return currentVehicle;
    }
    public void park(Vehicle v){
        this.currentVehicle = v; this.occupied = true;
    }
    public void leave(){
        this.currentVehicle = null; this.occupied = false;
    }
}
