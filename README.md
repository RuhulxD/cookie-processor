# Quantacast cookie frequency processor

## Description
This is a java command line application. It processes a cookie file and finds the most active cookie on a provided day.

## Requirement
    jdk-11
    gradle 6+

### Build
    gradle clean build
Unit Test result will be available in `app/build/reports/tests` directory
Coverage Test result will be available in `app/build/reports/coverage` directory

### Run
    java -jar app/build/libs/app.jar -f <cookie-file-name> -d <iso date>
example:

    java -jar app/build/libs/app.jar -f <cookie-file-name> -d 2018-12-09

### Run using bash command
    ./run.sh -f <cookie-file-name> -d <iso date>
example:

    ./run.sh app/build/libs/app.jar -f <cookie-file-name> -d 2018-12-09


## Comments:
    For the time limitation, I've ignore some of the minor component test. 