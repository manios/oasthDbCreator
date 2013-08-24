package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.HttpUtil;
import com.manios.oasthdbcreator.model.BusStop;
import java.io.IOException;
import java.util.List;
import org.slf4j.LoggerFactory;

public class BusStopService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopService.class);
    public final static String URL_BUSSTOPS_GR = "http://oasth.gr/el/stopinfo/route/%1$d/%2$d/%c/?a=1";
    public final static String URL_BUSSTOPS_EN = "http://oasth.gr/en/stopinfo/route/%1$d/%2$d/%c/?a=1";
    public final static char DIRECTION_DESKTOP_GOING = 'a';
    public final static char DIRECTION_DESKTOP_RETURN = 'b';

    public List<BusStop> getBusStops(int busLineUid, int busLineGroupUid) {
        String responseGr = "";
        String responseEn = "";

        try {
            responseGr = HttpUtil.get(URL_BUSSTOPS_GR, 0);
            responseEn = HttpUtil.get(URL_BUSSTOPS_GR, 0);


        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }


        //TODO replace null
        return null;

    }
}
