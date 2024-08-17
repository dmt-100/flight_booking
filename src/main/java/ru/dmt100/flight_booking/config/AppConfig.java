package ru.dmt100.flight_booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.dmt100.flight_booking.sql.provider.GeneralSqlQueryProvider;
import ru.dmt100.flight_booking.sql.SqlQuery;

@Configuration
public class AppConfig {

    @Bean
    public GeneralSqlQueryProvider generalSqlQueryProvider(SqlQuery sqlQuery) {
        return new GeneralSqlQueryProvider(sqlQuery);
    }
}

