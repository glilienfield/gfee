package com.GfeeService.Utilities;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class EpocUtils {

    final static DateTimeFormatter staticFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static long getEpochTime(String timestamp) {
        OffsetDateTime staticOffsetDate = OffsetDateTime.parse(timestamp, staticFormatter);
        return Instant.from(staticOffsetDate).toEpochMilli();
    }
}
