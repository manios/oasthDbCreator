package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.dto.BusLineArrivalDTO;
import com.manios.oasthdbcreator.parser.LinesArrivalInStopParser;
import com.manios.oasthdbcreator.parser.OasthDecoder;
import com.manios.oasthdbcreator.util.HttpUtil;
import java.io.IOException;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for getting updates about bus lines arrival in a
 * specific bus stop
 *
 *
 */
public class LineArrivalsService {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(LineArrivalsService.class);
    private static final String URL_STOP_LINE_ARRIVALS = "http://oasth.gr/%1$s/stopinfo/updateArrivals/%2$d/?a=1";
    public final static String LANGUAGE_EN = "en";
    public final static String LANGUAGE_EL = "el";

    public List<BusLineArrivalDTO> getArrivalsForStop(int stopId) {
        return getArrivalsForStop(stopId, LANGUAGE_EN);
    }

    public List<BusLineArrivalDTO> getArrivalsForStop(int stopId, String language) {

        String responseGr = null;
        String urlStopsArrivals = String.format(URL_STOP_LINE_ARRIVALS, language, stopId);
        try {

            responseGr = HttpUtil.get(urlStopsArrivals, 0);

        } catch (IOException ex) {
            logger.error("Error while downloading", ex);
        }

        // decode response
        responseGr = OasthDecoder.aniMarker(responseGr);

        LinesArrivalInStopParser arrivalPars = new LinesArrivalInStopParser().setHttpResponse(responseGr).parse();
        return arrivalPars.getLineArrival();
    }
}
