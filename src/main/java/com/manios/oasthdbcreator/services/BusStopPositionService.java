package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.util.HttpUtil;
import com.manios.oasthdbcreator.model.GeoPosition;
import java.io.IOException;
import java.util.List;
import org.slf4j.LoggerFactory;

public class BusStopPositionService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopPositionService.class);
    public static final char DIRECTION_MOBILE_GOING = '1';
    public static final char DIRECTION_MOBILE_RETURN = '2';
    private static final String LINE_AND_STOP_POSITIONS_URL_GR = "http://m.oasth.gr/index.php?md=6&sn=3&line=%d&dir=%c";

    public List<GeoPosition> getBusLineStopPosition(int busLineUid, int busLineGroupUid, char direction) {

        String responseGr = "";
        String urlString = String.format(LINE_AND_STOP_POSITIONS_URL_GR, busLineUid, direction);

        try {
            responseGr = HttpUtil.get(urlString, 0, true, true);
        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }


        return null; // new BusStopPositionParser().setHttpResponse(responseGr).getStopPositions();

    }
}
