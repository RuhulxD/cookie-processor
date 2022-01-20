package com.quantacast.configuration;

import java.io.File;
import java.time.LocalDate;

public interface ArgumentParser {
    public File getInputFile();
    public LocalDate getDate();
}
