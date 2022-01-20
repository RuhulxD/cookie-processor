package com.quantacast.service;

import com.quantacast.configuration.CookieLogParser;
import com.quantacast.model.CookieLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CookieProcessingServiceImpl implements CookieProcessingService {

    private final BufferedReader reader;
    private final Map<String, Integer> cookieFrequency;
    private int maxValue;
    private final LocalDate date;

    public CookieProcessingServiceImpl(File file, LocalDate date) throws FileNotFoundException {
        reader = new BufferedReader(new FileReader(file));
        cookieFrequency = new HashMap<>();
        maxValue = 0;
        this.date = date;
    }

    public Collection<String> findMostUsedCookies() throws IOException {
        processFile();
        // find max date
        return findMax();
    }

    private void processFile() throws IOException{
        // ignore first line, contains only header
        reader.readLine();

        //continue parsing rest of the line
        String line;
        while ((line = reader.readLine()) != null) {
            CookieLog cookieLog = CookieLogParser.parseCookie(line);
//            System.out.println(cookieLog);
            if (cookieLog.getDate().isEqual(this.date)) {
                addToMap(cookieLog);
            } else if (cookieLog.getDate().isBefore(date)) {
                break;
            }
        }
    }

    private List<String> findMax() {
        return cookieFrequency.entrySet()
                              .stream()
                              .filter(entry -> entry.getValue() == maxValue)
                              .map(Map.Entry::getKey)
                              .collect(Collectors.toList());
    }

    private void addToMap(CookieLog cookieLog) {
        cookieFrequency.compute(cookieLog.getCookie(), (k, v) -> {
            v = v == null ? 1 : v + 1;
            // mark the max value
            // will be used to find the most active users
            maxValue = Math.max(maxValue, v);
            return v;
        });
    }
}
