package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.util.HttpUtil;
import com.manios.oasthdbcreator.model.BusStop;
import com.manios.oasthdbcreator.parser.BusStopParser;
import java.io.IOException;
import java.util.List;
import org.slf4j.LoggerFactory;

public class BusStopService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopService.class);
    public final static String URL_BUSSTOPS_GR = "http://oasth.gr/el/stopinfo/route/%1$d/%2$d/%3$c/?a=1";
    public final static String URL_BUSSTOPS_EN = "http://oasth.gr/en/stopinfo/route/%1$d/%2$d/%3$c/?a=1";
    public final static char DIRECTION_DESKTOP_GOING = 'a';
    public final static char DIRECTION_DESKTOP_RETURN = 'b';

    public List<BusStop> getBusStopsOutward(int busLineUid, int busLineGroupUid) {

        return getBusStops(busLineUid, busLineGroupUid, DIRECTION_DESKTOP_GOING);
    }

    public List<BusStop> getBusStopsReturn(int busLineUid, int busLineGroupUid) {

        return getBusStops(busLineUid, busLineGroupUid, DIRECTION_DESKTOP_RETURN);
    }

    public List<BusStop> getBusStops(int busLineUid, int busLineGroupUid, char direction) {
        List<BusStop> busStopNames;
        String responseGoingGr = "";
        String responseGoingEn = "";

        String urlEnOutward = String.format(URL_BUSSTOPS_EN, busLineUid, busLineGroupUid, direction);
        String urlGrOutward = String.format(URL_BUSSTOPS_GR, busLineUid, busLineGroupUid, direction);

        try {
            responseGoingEn = HttpUtil.get(urlEnOutward, 0);
            responseGoingGr = HttpUtil.get(urlGrOutward, 0);

        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }

        busStopNames = new BusStopParser().setStopResponseEn(responseGoingEn).setStopResponseGr(responseGoingGr).parse();


        return busStopNames;
    }
}
