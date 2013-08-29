package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.BusStop;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusStopParser {

    private final static String PATTERN_STOP_NAME = "<option value=\"([^\"]*)\">([^<]*)</option>";
    private String stopResponseEn;
    private String stopResponseGr;

    public List<BusStop> parse() {
        ArrayList<BusStop> stopList = new ArrayList<BusStop>();
        int sorder = 0;


        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_STOP_NAME).matcher(stopResponseEn);

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {
            // add bus line to list
        }



        return stopList;

    }

    public String getStopResponseEn() {
        return stopResponseEn;
    }

    public BusStopParser setStopResponseEn(String stopResponseEn) {
        this.stopResponseEn = stopResponseEn;
        return this;
    }

    public String getStopResponseGr() {
        return stopResponseGr;
    }

    public BusStopParser setStopResponseGr(String stopResponseGr) {
        this.stopResponseGr = stopResponseGr;
        return this;
    }
}
