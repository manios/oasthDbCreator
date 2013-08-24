package com.manios.oasthdbcreator;

import com.manios.oasthdbcreator.services.BusLineService;
import com.manios.oasthdbcreator.model.BusLine;
import com.manios.oasthdbcreator.parser.BusStopPositionParser;
import com.manios.oasthdbcreator.services.BusStopPositionService;
import java.util.List;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 *
 */
// πληροφορίες στάσης
// http://oasth.gr/en/stopinfo/screen/15085/?a=1
// στάσεις γραμμής 67/23 (No2 AS IKEA) direction = a
// http://oasth.opgr/en/stopinfo/route/67/23/a/?a=1
// θέση λεωφορείων γραμμής 
// http://oasth.gr/en/buspositions/go/67/?a=1
// http://oasth.gr/en/buspositions/come/67/?a=1
// πληροφορίες γραμμής 23(No2 AS IKEA)
// http://oasth.gr/en/masterinfo/list/23/-2/?a=1
// πληροφορίες δρομολογίου γραμμής 71/32 (Νο12 Κάτω Τούμπα)
// http://oasth.gr/en/routeinfo/list/71/32/7/?a=1
// 71/32/-2 --> working days
// 71/32/7 --> saturday
// 71/32/8 --> sunday
// 
// direction selection
// http://oasth.gr/en/stopinfo/startstop/63/31/-2//?a=1
// Άφιξη γραμμών στη στάση 01223
// http://oasth.gr/en/stopinfo/updateArrivals/01223/?a=1
public class App {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        
        List<BusLine> bList;
        bList = new BusLineService().getBusLines();
        
        for (BusLine i : bList) {
            logger.debug("{}", i);
        }
        
        new BusStopPositionService().getBusLineStopPosition(67, 0,BusStopPositionService.DIRECTION_MOBILE_GOING);
    }
}
