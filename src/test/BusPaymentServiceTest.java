package test;

import main.BusPaymentService;
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

        // arrange
        String inputPath = "src/test/resources/taps.csv";
        String outputPath = "src/test/resources/trips.csv";

        // act
        BusPaymentService busPaymentService = new BusPaymentService();
        busPaymentService.process(inputPath, outputPath);

        // assert
        List<String> lines = Files.readAllLines(Path.of(outputPath));

        assertEquals(16, lines.size());
        assertEquals(
                "Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status",
                lines.getFirst()
        );

        List<String> actualTripLines = lines.subList(1, lines.size()).stream().sorted().toList();

        assertEquals(
                Stream.of(
                        "01-03-2023 08:00:00,01-03-2023 08:10:00,600,Stop1,Stop2,$3.25,CompanyZ,Bus11,1000000000000001,COMPLETED",
                        "01-03-2023 08:20:00,01-03-2023 08:30:00,600,Stop2,Stop1,$3.25,CompanyZ,Bus12,1000000000000002,COMPLETED",
                        "01-03-2023 08:40:00,01-03-2023 08:55:00,900,Stop2,Stop3,$5.50,CompanyZ,Bus13,1000000000000003,COMPLETED",
                        "01-03-2023 09:00:00,01-03-2023 09:10:00,600,Stop3,Stop2,$5.50,CompanyZ,Bus14,1000000000000004,COMPLETED",
                        "01-03-2023 09:20:00,01-03-2023 09:40:00,1200,Stop1,Stop3,$7.30,CompanyZ,Bus15,1000000000000005,COMPLETED",
                        "01-03-2023 09:50:00,01-03-2023 10:10:00,1200,Stop3,Stop1,$7.30,CompanyZ,Bus16,1000000000000006,COMPLETED",
                        "01-03-2023 10:20:00,01-03-2023 10:25:00,300,Stop1,Stop1,$0,CompanyZ,Bus17,1000000000000007,CANCELLED",
                        "01-03-2023 10:30:00,01-03-2023 10:40:00,600,Stop2,Stop2,$0,CompanyZ,Bus18,1000000000000008,CANCELLED",
                        "01-03-2023 10:45:00,01-03-2023 10:50:00,300,Stop3,Stop3,$0,CompanyZ,Bus19,1000000000000009,CANCELLED",
                        "01-03-2023 11:00:00,,0,Stop1,,$7.30,CompanyZ,Bus20,1000000000000010,INCOMPLETE",
                        "01-03-2023 11:05:00,,0,Stop2,,$5.50,CompanyZ,Bus20,1000000000000010,INCOMPLETE",
                        "01-03-2023 11:15:00,,0,Stop3,,$7.30,CompanyZ,Bus21,1000000000000011,INCOMPLETE",
                        "01-03-2023 11:25:00,,0,Stop1,,$7.30,CompanyZ,Bus22,1000000000000012,INCOMPLETE",
                        "01-03-2023 11:40:00,01-03-2023 11:50:00,600,Stop2,Stop3,$5.50,CompanyZ,Bus24,1000000000000013,COMPLETED",
                        "01-03-2023 12:00:00,,0,Stop1,,$7.30,CompanyZ,Bus24,1000000000000013,INCOMPLETE"
                ).sorted().toList(),
                actualTripLines
        );
        //todo validate the data for incomplete

        // this test covers a large range of edge cases
        // complete trip (stop 1 -> stop 2)
        // complete trip (stop 2 -> stop 1)
        // complete trip (stop 2 -> stop 3)
        // complete trip (stop 3 -> stop 2)
        // complete trip (stop 1 -> stop 3)
        // complete trip (stop 3 -> stop 1)
        // canceled trip (stop 1 -> stop 1, same stop)
        // canceled trip (stop 2 -> stop 2, same stop)
        // canceled trip (stop 3 -> stop 3, same stop)
        // incomplete ON (stop 1)
        // incomplete ON (stop 2)
        // incomplete ON (stop 3)
        // same PAN different bus should not match (stop 1 -> stop 2) (taps on different buses)
        // complete then incomplete on same account+bus (stop 2-> stop 3 complete, stop 1 incomplete)
    }
}
