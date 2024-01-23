package com.kawcix.Utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
    /**
     * @return Actual time without milliseconds
     */
    public static String getActuallTime() {
        return LocalTime.now()
                .truncatedTo(ChronoUnit.SECONDS)
                .format(DateTimeFormatter.ISO_LOCAL_TIME);
    }

}
