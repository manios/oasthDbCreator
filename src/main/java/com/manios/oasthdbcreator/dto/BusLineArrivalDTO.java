package com.manios.oasthdbcreator.dto;

import java.util.List;

public class BusLineArrivalDTO {

    private String number;
    private String name;
    private List<Integer> arrival;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getArrival() {
        return arrival;
    }

    public void setArrival(List<Integer> arrival) {
        this.arrival = arrival;
    }

    @Override
    public String toString() {
        return "BusLineArrivalDTO{" + "number=" + number + ", name=" + name + ", arrival=" + arrival + '}';
    }
}
