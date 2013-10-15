package com.manios.oasthdbcreator.parser;

/**
 * Parses response from oasth site which contains information about the position
 * of Bus stops and route waypoints of a specific bus line.
 */
public class BusStopPositionParser extends GeoPositionParser {

    public static final String PATTERN_STOPS_POSITION = "<div class=.[^ ]* poly.>([^<]*)</div>";

    public BusStopPositionParser() {
        setPatternStringPosition(PATTERN_STOPS_POSITION);
    }

    @Override
    public BusStopPositionParser parse() {
        super.parse();

        return this;
    }

    @Override
    public BusStopPositionParser setHttpResponse(String httpResponse) {
        super.setHttpResponse(httpResponse);

        return this;
    }
}
