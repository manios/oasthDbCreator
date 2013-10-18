package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.dto.BusStopDTO;
import com.manios.oasthdbcreator.util.HttpUtil;
import com.manios.oasthdbcreator.model.BusStop;
import com.manios.oasthdbcreator.model.GeoPosition;
import com.manios.oasthdbcreator.parser.BusStopParser;
import com.manios.oasthdbcreator.parser.BusStopPositionParser;
import com.manios.oasthdbcreator.parser.OasthDecoder;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.LoggerFactory;

public class BusStopService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(BusStopService.class);
    public final static String URL_BUSSTOPS = "http://oasth.gr/%1$s/routeinfo/list/%2$d/%3$d/1/?a=1";
    public final static String LANGUAGE_EN = "en";
    public final static String LANGUAGE_EL = "el";
    private List<BusStop> stopsOutward;
    private List<BusStop> stopsReturn;
    private boolean isCircular = false;

    public BusStopService() {
        isCircular = false;
    }

    public List<BusStop> getBusStopsOutward() {

        return stopsOutward;
    }

    public List<BusStop> getBusStopsReturn() {

        return stopsReturn;
    }

    public List<BusStopDTO> getBusStopsOutwardDTO() {
        List<BusStopDTO> bListDTO = new ArrayList<BusStopDTO>();
        int sorder = 0;

        for (BusStop i : stopsOutward) {
            BusStopDTO tmpDto = new BusStopDTO(i);

            tmpDto.setOrder(sorder++);
            bListDTO.add(tmpDto);

        }
        return bListDTO;
    }

    public List<BusStopDTO> getBusStopsReturnDTO() {
        List<BusStopDTO> bListDTO = new ArrayList<BusStopDTO>();
        int sorder = 0;

        for (BusStop i : stopsReturn) {
            BusStopDTO tmpDto = new BusStopDTO(i);

            tmpDto.setOrder(sorder++);
            bListDTO.add(tmpDto);

        }
        return bListDTO;
    }

    public BusStopService getBusStops(int busLineUid, int busLineGroupUid) {
        isCircular = false;

        String responseGr = null;
        String responseEn = null;

        String urlStopsEn = String.format(URL_BUSSTOPS, LANGUAGE_EN, busLineUid, busLineGroupUid);
        String urlStopsGr = String.format(URL_BUSSTOPS, LANGUAGE_EL, busLineUid, busLineGroupUid);

        int sorder = 0;
        try {
            responseEn = HttpUtil.get(urlStopsEn, 0);
            responseGr = HttpUtil.get(urlStopsGr, 0);

        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }

        // decode responses
        responseEn = OasthDecoder.aniMarker(responseEn);
        responseGr = OasthDecoder.aniMarker(responseGr);

        BusStopParser stopParser = new BusStopParser().setStopResponseEn(responseEn).setStopResponseGr(responseGr).parse();
        stopsOutward = stopParser.getStopsOutward();
        stopsReturn = stopParser.getStopsReturn();

        // parse bus stop geographical positions
        BusStopPositionParser stopPositionParser = new BusStopPositionParser().setHttpResponse(responseGr).parse();
        List<GeoPosition> stopPositionsOutward = stopPositionParser.getPositionsOutward();
        List<GeoPosition> stopPositionsReturn = stopPositionParser.getPositionsReturn();

        isCircular = ((stopsReturn == null) || (stopsReturn.size() <= 0));

        // set stop positions to outward bus stops
        for (BusStop i : stopsOutward) {
            i.setPosition(stopPositionsOutward.get(sorder++));
        }

        // set stop positions to return bus stops
        // if bus line is not circular
        if (!isCircular) {
            sorder = 0;
            for (BusStop i : stopsReturn) {
                i.setPosition(stopPositionsReturn.get(sorder++));
            }
        }
        return this;
    }
}
