package com.manios.oasthdbcreator.services;

import com.manios.oasthdbcreator.HttpUtil;
import com.manios.oasthdbcreator.model.BusLine;
import com.manios.oasthdbcreator.parser.BusLineParser;
import java.io.IOException;

import java.util.List;
import org.slf4j.LoggerFactory;

public class BusLineService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BusLineService.class);
    public static String URL_BUSLINES_GR = "http://oasth.gr/";
    public static String URL_BUSLINES_EN = "http://oasth.gr/en/";

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
}
