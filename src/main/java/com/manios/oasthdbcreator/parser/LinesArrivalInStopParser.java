package com.manios.oasthdbcreator.parser;

import com.manios.oasthdbcreator.dto.BusLineArrivalDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.LoggerFactory;

public class LinesArrivalInStopParser {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LinesArrivalInStopParser.class);
    private static final String PATTERN_LINE_ARRIVAL_POSITION = "<span class=.busno.>([^<]*)</span>[^<]*<span class=.busname.>([^<]*)</span>[^<]*<span class=.busdet.>[^<]*</span>[^<]*<span class=.busariv.>[^<]*<strong>([^<]*)</strong>";
    private static final String PATTERN_LINE_ARRIVAL_MINUTES = "([0-9]+)\'";
    private List<BusLineArrivalDTO> linesArrival;
    private String httpResponse;

    public LinesArrivalInStopParser parse() {
        linesArrival = new ArrayList<BusLineArrivalDTO>();

        Matcher matcher = Pattern.compile(PATTERN_LINE_ARRIVAL_POSITION).matcher(httpResponse);

        while (matcher.find()) {
            BusLineArrivalDTO lineArDto = new BusLineArrivalDTO();

            lineArDto.setNumber(matcher.group(1));
            lineArDto.setName(matcher.group(2));

            lineArDto.setArrival(parseArrivalTime(matcher.group(3)));

            linesArrival.add(lineArDto);
        }

        return this;
    }

    private List<Integer> parseArrivalTime(String arrivalTimes) {
        List<Integer> lAr = new ArrayList<Integer>();

        Matcher matcher = Pattern.compile(PATTERN_LINE_ARRIVAL_MINUTES).matcher(arrivalTimes);

        while (matcher.find()) {
            try {
                lAr.add(Integer.valueOf(matcher.group(1)));
            } catch (NumberFormatException exc) {
                logger.error("Exception while parsing line arrival time", exc);
            }
        }
        return lAr;
    }

    public List<BusLineArrivalDTO> getLineArrival() {
        return linesArrival;
    }

    public LinesArrivalInStopParser setHttpResponse(String httpResponse) {
        this.httpResponse = httpResponse;
        return this;
    }
}
