package com.desafioQuality.desafioquality.apiTest.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Date format(String date){
        return Date.valueOf(LocalDate.parse(date, formatter));
    }
}
