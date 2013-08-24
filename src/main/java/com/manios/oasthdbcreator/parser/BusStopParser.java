package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.BusStop;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusStopParser {

    private final static String PATTERN_STOP_NAME = "<option value=\"([^\"]*)\">([^<]*)</option>";
    private String stopResponseEnglishGoing;
    private String stopResponseEnglishReturn;
    private String stopResponseGreekGoing;
    private String stopResponseGreekReturn;

    public String getStopResponseEnglishGoing() {
        return stopResponseEnglishGoing;
    }

    public BusStopParser setStopResponseEnglishGoing(String stopResponseEnglishGoing) {
        this.stopResponseEnglishGoing = stopResponseEnglishGoing;

        return this;
    }

    public String getStopResponseEnglishReturn() {
        return stopResponseEnglishReturn;
    }

    public BusStopParser setStopResponseEnglishReturn(String stopResponseEnglishReturn) {
        this.stopResponseEnglishReturn = stopResponseEnglishReturn;

        return this;
    }

    public String getStopResponseGreekGoing() {
        return stopResponseGreekGoing;
    }

    public BusStopParser setStopResponseGreekGoing(String stopResponseGreekGoing) {
        this.stopResponseGreekGoing = stopResponseGreekGoing;

        return this;
    }

    public String getStopResponseGreekReturn() {
        return stopResponseGreekReturn;
    }

    public BusStopParser setStopResponseGreekReturn(String stopResponseGreekReturn) {
        this.stopResponseGreekReturn = stopResponseGreekReturn;

        return this;
    }

    public List<BusStop> parse(String httpString, String httpStringEng) {
        ArrayList<BusStop> stopList = new ArrayList<BusStop>();
        int sorder = 0;


        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_STOP_NAME).matcher(httpString);

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {
            // add bus line to list
        }



        return stopList;

    }
}
