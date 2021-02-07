package dev.krzysztof.cardownload.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public class CarUtil {

    public static LocalDate parseStringToDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH));
    }
}
