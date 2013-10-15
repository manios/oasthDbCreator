package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.GeoPosition;

public class RouteMarkerParser extends GeoPositionParser {

    public static final String PATTERN_ROUTE_MARKER_POSITION = "<div class=.rt. id=.[^\"]*.>([^<]*)</div>";

    public RouteMarkerParser() {
        setPatternStringPosition(PATTERN_ROUTE_MARKER_POSITION);
    }

    @Override
    public RouteMarkerParser parse() {
        super.parse();

        // for some reason the "smart" guys in Oasth have swapped
        // the values of latitude and longitude, so we need to
        // swap them again
        if (getPositionsOutward() != null) {
            for (GeoPosition i : getPositionsOutward()) {
                double latitude = i.getLongitude();

                // swap latitude and longitude values
                i.setLongitude(i.getLatitude());
                i.setLatitude(latitude);
            }
        }


        // for some reason the "smart" guys in Oasth have swapped
        // the values of latitude and longitude, so we need to
        // swap them again
        if (getPositionsReturn() != null) {
            for (GeoPosition i : getPositionsReturn()) {
                double latitude = i.getLongitude();

                // swap latitude and longitude values
                i.setLongitude(i.getLatitude());
                i.setLatitude(latitude);
            }
        }
        return this;
    }

    @Override
    public RouteMarkerParser setHttpResponse(String httpResponse) {
        super.setHttpResponse(httpResponse);

        return this;
    }
}
