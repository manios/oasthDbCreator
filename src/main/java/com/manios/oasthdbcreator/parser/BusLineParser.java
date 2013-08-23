package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.model.BusLine;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusLineParser {

    private final static String PATTERN_LINE = "<option value=.([^/]*)/([^/]*)/([^/]*)/>([^<]*)<span class=.lineDetails.[^:]*: ([^<]*)</span>[^<]*</option>";

    public static List<BusLine> parse(String httpString, String httpStringEng) {
        ArrayList<BusLine> bList = new ArrayList<BusLine>();
        int sorder = 0;


        // In case you would like to ignore case sensitivity you could use this
        // statement
        // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = Pattern.compile(PATTERN_LINE).matcher(httpString);

        // Check all occurrences
        // and fill bus line list
        while (matcher.find()) {
            BusLine tmpLine = new BusLine();
            tmpLine.setUid(Integer.valueOf(matcher.group(1)));
            tmpLine.setGroupUid(Integer.valueOf(matcher.group(2)));
            tmpLine.setLineNumber(matcher.group(4));
            tmpLine.setName(matcher.group(5));

            // add bus line to list
            bList.add(tmpLine);
        }

        // parse english line name
        matcher = Pattern.compile(PATTERN_LINE).matcher(httpStringEng);
        while (matcher.find()) {
            // add english description to list
            bList.get(sorder++).setNameEng(matcher.group(5));
        }

        return bList;

    }
}