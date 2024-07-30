package ru.dmt100.bookingflight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookingFlightApp {

//    public static void measureExecutionTime(Runnable codeBlock) {
//        long startTime = System.nanoTime();
//        codeBlock.run();
//        long endTime  = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println("Elapsed %,9.3f ms\\n " + duration / 1_000_000.0);
//    }

    public static void main(String[] args) {
        SpringApplication.run(BookingFlightApp.class, args);
//        System.out.println(getAvgPriceByDistanceRange(1000));
//        String dateStart = "2017-06-01";
//        String dateEnd = "2017-06-31";
//
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
//
//        try {
//            Date date1 = dateFormatter.parse(dateStart);
//            Date date2 = dateFormatter.parse(dateEnd);
////
////            measureExecutionTime(() -> {
//                List<Flight> flights;
//
//                flights = FlightService_Test.getFlightsForPeriodAndAirport("AER", date1, date2);
//
//                for (Flight flight: flights) {
//                    System.out.println(flight);
//                }
////            }
////            );
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
    }

}
