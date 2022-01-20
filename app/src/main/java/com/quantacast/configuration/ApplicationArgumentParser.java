package com.quantacast.configuration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.time.DateTimeException;
import java.time.LocalDate;

public class ApplicationArgumentParser implements ArgumentParser {

    private static final String DATE_FORMAT = "yyyy-mm-dd";
    private static final String DATE_PARSE_ERROR_MESSAGE = "Unable to parse date. Format must be " + DATE_FORMAT;


    private static final CommandLineParser parser = new DefaultParser();
    private static final HelpFormatter formatter = new HelpFormatter();

    private final CommandLine cmd;

    /**
     * Parse command line arguments
     *
     * @param args contains input file path and a specific date.
     * @throws ParseException if the argument does not contains [-f] of [-d] arguments or parser unable to parse the arguments.
     */
    public ApplicationArgumentParser(String args[]) throws ParseException {
        this.cmd = parse(initCommandLineOptions(), args);
    }

    protected Options initCommandLineOptions() {
        Options options = new Options();
        Option input = new Option("f", "file", true, "input file path");
        input.setRequired(true);
        options.addOption(input);

        Option date = new Option("d", "date", true, "Search date. Format must be: " + DATE_FORMAT);
        date.setRequired(true);
        options.addOption(date);
        return options;
    }

    private CommandLine parse(Options options, String[] args) throws ParseException {
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            throw e;
        }
    }

    public File getInputFile() {
        return new File(cmd.getOptionValue("f"));
    }

    public LocalDate getDate() {
        try {
            return LocalDate.parse(cmd.getOptionValue("d"));
        } catch (DateTimeException e) {
            throw new RuntimeException(DATE_PARSE_ERROR_MESSAGE);
        }
    }
}
