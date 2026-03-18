package test;

import main.Main;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BusPaymentServiceTest {

    @Test
    void shouldProcessInputCsvAndWriteExpectedTripsCsv() throws IOException {
        // todo maybe extract so this calls a service class
        Main.main(new String[0]);

        Path output = Path.of("src/main/resources/trips.csv");
        List<String> lines = Files.readAllLines(output);

        assertEquals(5, lines.size());

        assertEquals(
                "Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status",
                lines.getFirst()
        );

        List<String> actualTripLines = lines.subList(1, lines.size()).stream().sorted().toList();

        assertEquals(
                Stream.of(
                        "22-01-2023 13:00:00,22-01-2023 13:05:00,300,Stop1,Stop2,$3.25,Company1,Bus37,5500005555555559,COMPLETED",
                        "22-01-2023 09:20:00,,0,Stop3,Stop3,$7.30,Company1,Bus36,4111111111111111,INCOMPLETE",
                        "23-01-2023 08:00:00,23-01-2023 08:02:00,120,Stop1,Stop1,$0,Company1,Bus37,4111111111111111,CANCELLED",
                        "24-01-2023 16:30:00,,0,Stop2,Stop2,$5.50,Company1,Bus37,5500005555555559,INCOMPLETE"
                ).sorted().toList(),
                actualTripLines
        );
    }
}
