package main;

import main.model.Tap;
import main.model.TapPair;
import main.model.Trip;

import java.io.IOException;
import java.util.List;

public class BusPaymentService {

    private final TapCsvReader tapCsvReader = new TapCsvReader();
    private final TapMatcher tapMatcher = new TapMatcher();
    private final FareCalculator fareCalculator = new FareCalculator();
    private final TripCsvWriter tripCsvWriter = new TripCsvWriter();

    public void process(String inputPath, String outputPath) throws IOException {
        List<Tap> taps = tapCsvReader.readTaps(inputPath);
        List<TapPair> tapPairs = tapMatcher.matchTaps(taps);
        List<Trip> trips = fareCalculator.calculateFare(tapPairs);
        tripCsvWriter.writeTrips(outputPath, trips);
    }
}
