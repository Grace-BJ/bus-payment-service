package main;

import main.model.Tap;
import main.model.Trip;

import java.io.IOException;
import java.util.List;

public class Main {

    static void main() throws IOException {

        TapCsvReader tapCsvReader = new TapCsvReader();
        List<Tap> taps = tapCsvReader.readTaps("src/test/resources/taps.csv");

        TripProcessor tripProcessor = new TripProcessor();
        List<Trip> processedTrips = tripProcessor.process(taps);

        TripCsvWriter tripCsvWriter = new TripCsvWriter();
        tripCsvWriter.writeTrips("src/test/resources/trips.csv", processedTrips);

    }
}
