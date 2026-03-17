package main;

import main.model.Tap;
import main.model.TapPair;
import main.model.Trip;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main() throws IOException {

        TapCsvReader tapCsvReader = new TapCsvReader();
        List<Tap> taps = tapCsvReader.readTaps("src/main/resources/taps.csv");

        // todo should these be static
        TapMatcher tapMatcher = new TapMatcher();
        List<TapPair> tapPairs = tapMatcher.matchTaps(taps);

        FareCalculator fareCalculator = new FareCalculator();
        List<Trip> processedTrips = fareCalculator.calculateFare(tapPairs);

        TripCsvWriter tripCsvWriter = new TripCsvWriter();
        tripCsvWriter.writeTrips("src/main/resources/trips.csv", processedTrips);

    }
}
