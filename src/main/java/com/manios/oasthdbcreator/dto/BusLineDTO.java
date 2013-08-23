package com.manios.oasthdbcreator.dto;

import com.manios.oasthdbcreator.model.BusLine;
import com.manios.oasthdbcreator.model.BusStop;
import java.util.ArrayList;
import java.util.List;

public class BusLineDTO extends BusLine {

    private List<BusStop> goingStops;
    private List<BusStop> returnStops;

    public BusLineDTO() {
        goingStops = new ArrayList<BusStop>();
        returnStops = new ArrayList<BusStop>();
    }

    public List<BusStop> getGoingStops() {
        return goingStops;
    }

    public void setGoingStops(List<BusStop> goingStops) {
        this.goingStops = goingStops;
    }

    public List<BusStop> getReturnStops() {
        return returnStops;
    }

    public void setReturnStops(List<BusStop> returnStops) {
        this.returnStops = returnStops;
    }
}
