package com.example.demo.Service;

import com.example.demo.Models.Slot;
import com.example.demo.Models.SlotSize;
import com.example.demo.Models.Vehicle;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ParkingLotService {
    private final List<Slot> slots = new ArrayList<>();
    private boolean initialized = false;

    public void create(int total, int small, int large, int oversize) {
        if (small + large + oversize != total)
            throw new IllegalArgumentException("Counts must sum to total");
        slots.clear();
        int id = 1;
        for(int i=0;i<small;i++)   slots.add(new Slot(id++, SlotSize.SMALL));
        for(int i=0;i<large;i++)   slots.add(new Slot(id++, SlotSize.LARGE));
        for(int i=0;i<oversize;i++)slots.add(new Slot(id++, SlotSize.OVERSIZE));
        initialized = true;
    }

    public int park(String plate, SlotSize size) {
        if (!initialized) throw new IllegalStateException("Not initialized");
        // avoid duplicates
        slots.stream()
                .filter(Slot::isOccupied)
                .map(Slot::getCurrentVehicle)
                .map(Vehicle::getLicensePlate)
                .filter(p -> p.equals(plate))
                .findAny()
                .ifPresent(p -> { throw new IllegalArgumentException("Already parked"); });

        for (Slot s: slots) {
            if (!s.isOccupied() && s.getSize().ordinal() >= size.ordinal()) {
                s.park(new Vehicle(plate, size));
                return s.getId();
            }
        }
        throw new IllegalStateException("No slot available");
    }

    public void leave(String plate) {
        Optional<Slot> found = slots.stream()
                .filter(Slot::isOccupied)
                .filter(s -> s.getCurrentVehicle().getLicensePlate().equals(plate))
                .findFirst();
        if (found.isEmpty())
            throw new IllegalArgumentException("Vehicle not found");
        found.get().leave();
    }

    public List<Slot> status() {
        return List.copyOf(slots);
    }

    public void reset() {
        slots.clear();
        initialized = false;
    }


}
