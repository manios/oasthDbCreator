package com.manios.oasthdbcreator.dto;

import com.manios.oasthdbcreator.model.GeoPosition;

public class BusVehiclePosition extends GeoPosition {

    private String vehicleId;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    @Override
    public String toString() {
        return "BusVehiclePosition{" + "vehicleId=" + vehicleId + ',' + super.toString() + '}';
    }
}
