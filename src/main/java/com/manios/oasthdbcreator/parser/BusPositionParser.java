package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.dto.BusVehiclePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class BusPositionParser {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopPositionParser.class);
    private static final String PATTERN_VEHICLE_POSITION = "([^-\"]*)-([^-\"]*)-([^-\"]*)[|][|]";
    private List<BusVehiclePosition> busPositions;
    private String httpResponse;

    public BusPositionParser() {
        busPositions = new ArrayList<BusVehiclePosition>();
    }

    public BusPositionParser parse() {
        if ((httpResponse == null) || (httpResponse.trim().length() == 0)) {
            return this;
        }

        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_VEHICLE_POSITION).matcher(httpResponse);

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {
            BusVehiclePosition vehiclePos = new BusVehiclePosition();

            double longitude;
            double latitude;

            // set vehicle id
            vehiclePos.setVehicleId(matcher.group(1));

            // get vehicle latitude
            try {
                latitude = Double.valueOf(matcher.group(3));
            } catch (NumberFormatException exc) {
                latitude = 0;

                logger.error("Exception while vehicle position latitude", exc);
            }

            // get vehicle longitude
            try {
                longitude = Double.valueOf(matcher.group(2));
            } catch (NumberFormatException exc) {
                longitude = 0;

                logger.error("Exception while parsing vehicle position longitude", exc);
            }

            vehiclePos.setLatitude(latitude);
            vehiclePos.setLongitude(longitude);

            busPositions.add(vehiclePos);
        }

        return this;
    }

    public List<BusVehiclePosition> getBusPositions() {
        return busPositions;
    }

    public BusPositionParser setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;

        return this;
    }
}
