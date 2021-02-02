package dev.krzysztof.cardownload.util;

import dev.krzysztof.cardownload.constants.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDateTime() {
        DateFormat dateFormat = new SimpleDateFormat(Constants.TIME_FORMAT);
        return dateFormat.format(new Date());
    }
}
