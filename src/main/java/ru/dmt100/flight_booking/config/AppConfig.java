package ru.dmt100.flight_booking.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = false)
public class AppConfig {

//    @Bean
//    public SqlQuery sqlQuery() {
//        return new SqlQuery();
//    }

//    @Bean(name = "customBookingSqlQuery")
//    public BookingSqlQueryInterface bookingSqlQuery() {
//        return new BookingSqlQuery();
//    }

//    @Bean
//    public BookingWithTicketNosResponseMapper bookingDtoResponseMapper() {
//        return new BookingWithTicketNosResponseMapper(TicketQueryType.TICKETS_BY_BOOK_REF);
//    }

//    @Bean(name = "customBookingSqlQuery")
//    public BookingQuery getQuery() {
//        return new BookingQuery();
//    }


}

