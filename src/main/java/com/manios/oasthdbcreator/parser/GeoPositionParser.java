package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.GeoPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class GeoPositionParser {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopPositionParser.class);
    private String patternStringPosition = "<div class=.[^ ]* poly.>([^<]*)</div>";
    private List<GeoPosition> positionsOutward;
    private List<GeoPosition> positionsReturn;
    private String httpResponse;

    public GeoPositionParser parse() {

        Matcher matcher = Pattern.compile(patternStringPosition).matcher(httpResponse);
        int currentPos = 0;

        while (matcher.find()) {
            if (currentPos == 0) {
                positionsOutward = getWayPoints(matcher.group(1));
                currentPos++;
            } else {
                positionsReturn = getWayPoints(matcher.group(1));
            }
        }
        return this;
    }

    private List<GeoPosition> getWayPoints(String responseDirection) {
        ArrayList<GeoPosition> posList = new ArrayList<GeoPosition>();

        if ((responseDirection == null) || (responseDirection.trim().length() == 0)) {
            return posList;
        }
        String decodedDirection = OasthDecoder.aniMarker(responseDirection);

        String posListStr[] = decodedDirection.split("[|]");

        if (posListStr != null) {
            double longitude;
            double latitude;

            for (String i : posListStr) {
                GeoPosition stopPos = new GeoPosition();
                String currentPos[] = i.split(",");

                try {
                    latitude = Double.valueOf(currentPos[0]);
                } catch (NumberFormatException exc) {
                    latitude = 0;

                    logger.error("Exception while parsing stop latitude", exc);
                }

                try {
                    longitude = Double.valueOf(currentPos[1]);
                } catch (NumberFormatException exc) {
                    longitude = 0;

                    logger.error("Exception while parsing stop longitude", exc);
                }

                stopPos.setLatitude(latitude);
                stopPos.setLongitude(longitude);

                // add stop position to list
                posList.add(stopPos);
            }



        }

        return posList;
    }

    public String getPatternStringPosition() {
        return patternStringPosition;
    }

    public void setPatternStringPosition(String patternStringPosition) {
        this.patternStringPosition = patternStringPosition;
    }

    public List<GeoPosition> getPositionsOutward() {
        return positionsOutward;
    }

    public List<GeoPosition> getPositionsReturn() {
        return positionsReturn;
    }

    public GeoPositionParser setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;

        return this;
    }
}
