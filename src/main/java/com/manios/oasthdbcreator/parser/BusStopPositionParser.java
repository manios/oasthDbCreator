package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.OasthWebPageParser;
import com.manios.oasthdbcreator.model.StopPosition;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.LoggerFactory;

public class BusStopPositionParser {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopPositionParser.class);
    private final static String PATTERN_STOP_POSITION = "<span ornt=\"([^,]*),([^,]*)\" lbl=\"([^\"]*)\" >";
    private final static String PATTERN_LINE_STOP_POSITION_BEF_DECODING = "<div class=\"ttl\"><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">([^<]*)</div><div style=\"display:none;\">[^<]*</div>";
    private String stopPositionResponse;

    public List<StopPosition> getStopPositions() {
        StringBuilder stopPos = new StringBuilder();

        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(
                PATTERN_LINE_STOP_POSITION_BEF_DECODING).matcher(stopPositionResponse);

        // Check all occurrences
        while (matcher.find()) {
            stopPos.append(OasthWebPageParser.aniMarker(matcher.group(2)));
        }

        stopPositionResponse = stopPos.toString();

        return parseDecodedStopPositions();

    }

    private List<StopPosition> parseDecodedStopPositions() {
        ArrayList<StopPosition> posList = new ArrayList<StopPosition>();

        Matcher matcher = Pattern.compile(PATTERN_STOP_POSITION).matcher(stopPositionResponse);


        while (matcher.find()) {
            StopPosition stopPos = new StopPosition();

            double longitude;
            double latitude;

            try {
                latitude = Double.valueOf(matcher.group(1));
            } catch (NumberFormatException exc) {
                latitude = 0;

                logger.error("Exception while parsing stop latitude", exc);
            }

            try {
                longitude = Double.valueOf(matcher.group(2));
            } catch (NumberFormatException exc) {
                longitude = 0;

                logger.error("Exception while parsing stop longitude", exc);
            }

            stopPos.setLatitude(latitude);
            stopPos.setLongitude(longitude);

            // add stop position to list
            posList.add(stopPos);
        }


        return posList;
    }

    public String getStopPositionResponse() {
        return stopPositionResponse;
    }

    public BusStopPositionParser setStopPositionResponse(String stopPositionResponse) {
        this.stopPositionResponse = stopPositionResponse;

        return this;
    }
}
