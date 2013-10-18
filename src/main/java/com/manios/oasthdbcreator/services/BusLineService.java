package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.dto.BusLineDTO;
import com.manios.oasthdbcreator.dto.BusStopDTO;
import com.manios.oasthdbcreator.util.HttpUtil;
import com.manios.oasthdbcreator.model.BusLine;
import com.manios.oasthdbcreator.parser.BusLineParser;
import java.io.IOException;

import java.util.List;
import org.slf4j.LoggerFactory;

public class BusLineService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusLineService.class);
    public static String URL_BUSLINES_GR = "http://oasth.gr/";
    public static String URL_BUSLINES_EN = "http://oasth.gr/en/";
    public static String URL_BUSLINE_DIRECTIONS_STARTSTOP_GR = "http://oasth.gr/el/stopinfo/startstop/%1$d/%1$d/-2/?a=1";
    public static String URL_BUSLINE_DIRECTIONS_STARTSTOP_EN = "http://oasth.gr/en/stopinfo/startstop/%1$d/%1$d/-2/?a=1";
    private BusStopService stopService;

    public List<BusLine> getBusLines() {
        String responseGr = "";
        String responseEn = "";

        try {
            responseGr = HttpUtil.get(URL_BUSLINES_GR, 0);
            responseEn = HttpUtil.get(URL_BUSLINES_EN, 0);


        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }



        return BusLineParser.parse(responseGr, responseEn);

    }

    public BusLine getBusLine(int busLineUid) {
        List<BusLine> bList = getBusLines();

        if ((bList == null) || (bList.size() <= 0)) {
            return null;
        }

        for (BusLine i : bList) {
            if (i.getUid() == busLineUid) {
                return i;
            }
        }
        return null;

    }

    public BusLineDTO getBusLineDTO(int busLineUid) {

        BusLine busLino = getBusLine(busLineUid);
        BusLineDTO busLinoToReturn = null;

        if (busLino != null) {
            busLinoToReturn = new BusLineDTO(busLino);
        }

        stopService = new BusStopService();
        stopService.getBusStops(busLinoToReturn.getUid(), busLinoToReturn.getGroupUid());

        List<BusStopDTO> outwardStops = stopService.getBusStopsOutwardDTO();
        List<BusStopDTO> returnStops = stopService.getBusStopsReturnDTO();

        busLinoToReturn.setGoingStops(outwardStops);
        busLinoToReturn.setReturnStops(returnStops);

        return busLinoToReturn;

    }

    public boolean isCircularLine(int busLineUid, int busLineGroupUid) {
        return getBusLineDTO(busLineUid).isCircular();
    }
}
