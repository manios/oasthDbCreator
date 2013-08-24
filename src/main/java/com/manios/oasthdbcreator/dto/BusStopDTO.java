package com.manios.oasthdbcreator.dto;

import com.manios.oasthdbcreator.model.BusStop;

public class BusStopDTO extends BusStop {

    private int order;

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
