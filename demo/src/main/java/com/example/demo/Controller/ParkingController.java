package com.example.demo.Controller;

import com.example.demo.Models.ParkRequest;
import com.example.demo.Models.Slot;
import com.example.demo.Models.Vehicle;
import com.example.demo.Service.ParkingLotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins="http://localhost:3000")
public class ParkingController {

    private final ParkingLotService svc;

    public ParkingController(ParkingLotService svc){ this.svc = svc; }

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody ParkRequest r){
        if (r.getTotal()==null||r.getSmall()==null||r.getLarge()==null||r.getOversize()==null) {
            return bad("All fields required.");
        }
        long total   = r.getTotal(), small = r.getSmall(),
                large   = r.getLarge(), oversize = r.getOversize();

        if (total<0||small<0||large<0||oversize<0) {
            return bad("Counts cannot be negative.");
        }
        if (small + large + oversize != total) {
            return bad("Total must equal sum of small+large+oversize.");
        }

        if (total>=Integer.MAX_VALUE
                || small>=Integer.MAX_VALUE
                || large>=Integer.MAX_VALUE
                || oversize>=Integer.MAX_VALUE) {
            return bad("Max allowed lots = " + Integer.MAX_VALUE);
        }
        svc.create((int)total, (int)small, (int)large, (int)oversize);
        return ResponseEntity.ok("Created");
    }

    @PostMapping("/park")
    public ResponseEntity<?> park(@RequestBody Vehicle v){
        if (v.getLicensePlate()==null || v.getLicensePlate().isBlank()) {
            return ResponseEntity.badRequest().body("License plate is required.");
        }

        if (v.getSize()==null) {
            return ResponseEntity.badRequest().body("Vehicle size is required.");
        }
        try {
            int slot = svc.park(v.getLicensePlate().trim(), v.getSize());
            return ResponseEntity.ok(Map.of("slot", slot));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PostMapping("/leave")
    public ResponseEntity<String> leave(@RequestBody Vehicle v){
        if (v.getLicensePlate()==null || v.getLicensePlate().isBlank()) {
            return ResponseEntity.badRequest().body("License plate is required.");
        }
        try {
            svc.leave(v.getLicensePlate().trim());
            return ResponseEntity.ok("Left");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/status")
    public List<Slot> status(){
        return svc.status();
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        svc.reset();
        return ResponseEntity.ok("Reset");
    }

    private ResponseEntity<String> bad(String msg) {
        return ResponseEntity.badRequest().body(msg);
    }
}
