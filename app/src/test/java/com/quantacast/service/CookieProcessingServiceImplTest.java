package com.quantacast.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CookieProcessingServiceImplTest {
    CookieProcessingService cookieProcessingService;

    @ParameterizedTest
    @MethodSource("generateValidCases")
    void shouldFindMostActiveCookie(File file, LocalDate date, List<String> expected) throws IOException {
        // given
        cookieProcessingService = new CookieProcessingServiceImpl(file, date);
        // when
        Collection<String> result = cookieProcessingService.findMostUsedCookies();
        // then
        assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionWhenFileNotExists() {
        // given
        File file = new File("file_not_exists.csv");
        LocalDate date = LocalDate.now();

        //when
        FileNotFoundException e = assertThrows(FileNotFoundException.class,
                                               () -> new CookieProcessingServiceImpl(file, date));
        //then
        assertEquals("file_not_exists.csv (No such file or directory)", e.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenInvalidSeperatorUsed() throws URISyntaxException, FileNotFoundException {
        // given
        File file = getFileFromResource("failed/cookie_log_invalid_csv_format.csv");
        LocalDate date = LocalDate.now();

        //when
        CookieProcessingServiceImpl service = new CookieProcessingServiceImpl(file, date);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, service::findMostUsedCookies);

        //then
        assertEquals("Unable to parse line: SAZuXPGUrfbcn5UA 2018-12-10T23:19:00-06:00", e.getMessage());
    }

    public static Stream<Arguments> generateValidCases() throws Exception {
        return Stream.of(
                Arguments.of(getFileFromResource("success/cookie_log_in_same_time_zone_utc_0.csv"),
                             LocalDate.parse("2022-01-20"), List.of("aaa")),
                Arguments.of(getFileFromResource("success/cookie_log_in_same_time_zone_utc_0.csv"),
                             LocalDate.parse("2022-01-19"), List.of("bbb")),
                Arguments.of(getFileFromResource("success/cookie_log_in_same_time_zone_utc_0.csv"),
                             LocalDate.parse("2022-01-18"), List.of("ccc")),
                Arguments.of(getFileFromResource("success/cookie_log_different_time_zone.csv"),
                             LocalDate.parse("2018-12-10"), List.of("SAZuXPGUrfbcn5UA")),
                Arguments.of(getFileFromResource("success/cookie_log_different_time_zone.csv"),
                             LocalDate.parse("2018-12-09"), List.of("AtY0laUfhglK3lC7", "SAZuXPGUrfbcn5UA")),
                Arguments.of(getFileFromResource("success/cookie_log_different_time_zone.csv"),
                             LocalDate.parse("2018-12-08"), List.of("fbcn5UAVanZf6UtG", "4sMM2LxV07bPJzwf")),
                // another date
                // should be empty
                Arguments.of(getFileFromResource("success/cookie_log_different_time_zone.csv"),
                             LocalDate.parse("2018-01-08"), List.of()),
                // empty file is also a valid file
                Arguments.of(getFileFromResource("success/cookie_log_empty_file.csv"),
                             LocalDate.parse("2018-01-08"), List.of())
                        );
    }

    private static File getFileFromResource(String fileName) throws URISyntaxException {
        ClassLoader classLoader = CookieProcessingServiceImplTest.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }
}