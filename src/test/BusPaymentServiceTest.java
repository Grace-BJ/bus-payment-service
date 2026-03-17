package test;

import main.FareCalculator;
import main.TapCsvReader;
import main.TapMatcher;
import main.TripCsvWriter;
import main.model.Tap;
import main.model.TapPair;
import main.model.Trip;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusPaymentServiceTest {

    @Test
    void shouldProcessInputCsvAndWriteExpectedTripsCsv() throws IOException {
        TapCsvReader tapCsvReader = new TapCsvReader();
        List<Tap> taps = tapCsvReader.readTaps("src/test/resources/taps.csv");

        TapMatcher tapMatcher = new TapMatcher();
        List<TapPair> tapPairs = tapMatcher.matchTaps(taps);

        FareCalculator fareCalculator = new FareCalculator();
        List<Trip> trips = fareCalculator.calculateFare(tapPairs);

        Path output = Files.createTempFile("trips", ".csv");
        new TripCsvWriter().writeTrips(output.toString(), trips);

        List<String> lines = Files.readAllLines(output);

        assertEquals(4, lines.size());

        List<String> actualTripLines = lines.subList(1, lines.size()).stream().sorted().toList();

        assertEquals(
                List.of(
                        "22-01-2023 13:00:00,22-01-2023 13:05:00,300,Stop1,Stop2,$3.25,Company1,Bus37,5500005555555559,COMPLETED",
                        "23-01-2023 08:00:00,23-01-2023 08:02:00,120,Stop1,Stop1,$0,Company1,Bus37,4111111111111111,CANCELLED",
                        "22-01-2023 09:20:00,,0,Stop3,Stop3,$7.30,Company1,Bus36,4111111111111111,INCOMPLETE"
                ).stream().sorted().toList(),
                actualTripLines
        );
    }
}
