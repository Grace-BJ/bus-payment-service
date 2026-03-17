package test;

import main.TripCsvWriter;
import main.model.StopId;
import main.model.Trip;
import main.model.TripStatus;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TripCsvWriterTest {

    @Test
    void writeTripsShouldWriteExpectedCsv() throws IOException {
        Trip trip = new Trip(
                LocalDateTime.of(2018, 1, 22, 13, 0, 0),
                LocalDateTime.of(2018, 1, 22, 13, 5, 0),
                "300",
                StopId.Stop1,
                StopId.Stop2,
                new BigDecimal("3.25"),
                "Company1",
                "Bus37",
                "5500005555555559",
                TripStatus.COMPLETED
        );

        Path output = Path.of("src/test/resources/trips.csv");
        new TripCsvWriter().writeTrips(output.toString(), List.of(trip));
        List<String> lines = Files.readAllLines(output);
        assertEquals("Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status", lines.get(0));
        assertEquals("22-01-2018 13:00:00,22-01-2018 13:05:00,300,Stop1,Stop2,$3.25,Company1,Bus37,5500005555555559,COMPLETED", lines.get(1));
    }
}
