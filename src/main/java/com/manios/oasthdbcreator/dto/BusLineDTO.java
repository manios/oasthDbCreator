package com.manios.oasthdbcreator.dto;

import com.manios.oasthdbcreator.model.BusLine;
import java.util.ArrayList;
import java.util.List;

public class BusLineDTO extends BusLine {

    private List<BusStopDTO> goingStops;
    private List<BusStopDTO> returnStops;

    public BusLineDTO() {
        goingStops = new ArrayList<BusStopDTO>();
        returnStops = new ArrayList<BusStopDTO>();
    }

    public BusLineDTO(BusLine bl) {
        super(bl);
    }

    public List<BusStopDTO> getGoingStops() {
        return goingStops;
    }

    public void setGoingStops(List<BusStopDTO> goingStops) {
        this.goingStops = goingStops;
    }

    public List<BusStopDTO> getReturnStops() {
        return returnStops;
    }

    public void setReturnStops(List<BusStopDTO> returnStops) {
        this.returnStops = returnStops;
    }

    @Override
    public String toString() {
        return "BusLineDTO{" + "goingStops=" + goingStops + ", returnStops=" + returnStops + '}';
    }
}
