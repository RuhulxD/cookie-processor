package com.quantacast.service;

import java.io.IOException;
import java.util.Collection;

public interface CookieProcessingService {
    Collection<String> findMostUsedCookies() throws IOException;
}
