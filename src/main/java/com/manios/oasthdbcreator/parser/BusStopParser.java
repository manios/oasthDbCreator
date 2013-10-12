package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.BusStop;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusStopParser {

    private final static String PATTERN_STOP_DIRECTIONS = "<ul class=.outwardJourney routeStopsList.><li>(.*)</ul><ul class=.returnJourney routeStopsList.>(.*)</ul>";
    private final static String PATTERN_STOP_NAME = "<strong>([^<]*)</strong> *([^<]*)</a>";
    private String stopResponseEn;
    private String stopResponseGr;
    private List<BusStop> stopsOutward;
    private List<BusStop> stopsReturn;
//Η γραμμή δεν έχει δρομολόγια από το τέρμα

    public BusStopParser parse() {
        ArrayList<BusStop> stopList = new ArrayList<BusStop>();
        int sorder = 0;

        String fragmentOutwardGr = null;
        String fragmentOutwardEn = null;
        String fragmentReturnGr = null;
        String fragmentReturnEn = null;

        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_STOP_DIRECTIONS).matcher(stopResponseGr);

        boolean bob = matcher.matches();

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {

            fragmentOutwardGr = matcher.group(1);

            if (matcher.groupCount() > 1) {
                fragmentReturnGr = matcher.group(2);
            }
        }

        // match english stop name patterns
        matcher = Pattern.compile(PATTERN_STOP_DIRECTIONS).matcher(stopResponseEn);

        // add english name to every stop
        while (matcher.find()) {

            fragmentOutwardEn = matcher.group(1);

            if (matcher.groupCount() > 1) {
                fragmentReturnEn = matcher.group(2);
            }
        }

        stopsOutward = parseDirection(fragmentOutwardEn, fragmentOutwardGr);

        stopsReturn = parseDirection(fragmentReturnEn, fragmentReturnGr);

        return this;

    }

    private List<BusStop> parseDirection(String responseDirectionEn, String responseDirectionGr) {
        ArrayList<BusStop> stopList = new ArrayList<BusStop>();

        // if there is no html code for this direction return an empty list
        if ((responseDirectionEn == null) && (responseDirectionGr == null)) {
            return stopList;
        }

        int sorder = 0;


        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_STOP_NAME).matcher(responseDirectionGr);

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {
            BusStop busStopi = new BusStop();

            busStopi.setName(matcher.group(2));
            busStopi.setUid(Integer.valueOf(matcher.group(1)));

            // add bus stop to list
            stopList.add(busStopi);
        }

        // match english stop name patterns
        matcher = Pattern.compile(PATTERN_STOP_NAME).matcher(responseDirectionEn);

        // add english name to every stop
        while (matcher.find()) {
            stopList.get(sorder++).setNameEng(matcher.group(2));
        }


        return stopList;

    }

    public BusStopParser setStopResponseEn(String stopResponseEn) {
        this.stopResponseEn = stopResponseEn;
        return this;
    }

    public BusStopParser setStopResponseGr(String stopResponseGr) {
        this.stopResponseGr = stopResponseGr;
        return this;
    }

    public List<BusStop> getStopsOutward() {
        return stopsOutward;
    }

    public List<BusStop> getStopsReturn() {
        return stopsReturn;
    }
}
