package com.quantacast.configuration;

import com.quantacast.model.CookieLog;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Application specific cookie parser. Will receive comma seperated cookie and date and parse it.
 * Expected format is {@code "cookie-id,iso-date"}
 */
public class CookieLogParser {
    private static final String SEPERATOR = ",";
    private static final ZoneId ZONE_ID = ZoneId.of("UTC");

    public static CookieLog parseCookie(String log) {
        if (log == null) {
            throw new RuntimeException("Cookie log must not be null");
        }

        String[] strings = log.split(SEPERATOR);
        if (strings.length != 2) {
            throw new IllegalArgumentException("Unable to parse line: " + log);
        }

        ZonedDateTime dateTime = ZonedDateTime.parse(strings[1]);

        return new CookieLog(
                strings[0],
                dateTime.withZoneSameInstant(ZONE_ID).toLocalDate()
        );
    }
}
