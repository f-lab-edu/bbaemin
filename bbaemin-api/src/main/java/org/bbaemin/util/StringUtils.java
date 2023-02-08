package org.bbaemin.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {

    private StringUtils() {
    }

    public static String getFormattedPrice(int price) {
        return String.format("%,d원", price);
    }

    public static String getFormattedLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
