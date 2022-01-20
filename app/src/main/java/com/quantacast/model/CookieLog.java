package com.quantacast.model;

import java.time.LocalDate;

public class CookieLog {
    private final String cookie;
    private final LocalDate date;

    public CookieLog(String name, LocalDate date) {
        this.cookie = name;
        this.date = date;
    }

    public String getCookie() {
        return cookie;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "CookieLog{" +
               "cookie='" + cookie + '\'' +
               ", date=" + date +
               '}';
    }
}
