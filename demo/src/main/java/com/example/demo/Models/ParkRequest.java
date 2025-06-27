package com.example.demo.Models;

public class ParkRequest {
    private Long total;
    private Long small;
    private Long large;
    private Long oversize;
    public Long getTotal(){
        return total;
    }

    public Long getSmall(){
        return small;
    }

    public Long getLarge(){
        return large;
    }

    public Long getOversize() {
        return oversize;
    }
    public void setTotal(Long t){
        total = t;
    }

    public void setSmall(Long smallSize){
        small = smallSize;
    }
    public void setLarge(Long largeSize){
        large = largeSize;
    }
    public void setOversize(Long oSize){
        oversize = oSize;
    }
}
