package test;

import main.TapCsvReader;
import main.model.Tap;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static main.model.TapType.OFF;
import static main.model.TapType.ON;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TapCsvReaderTest {

    @Test
    void readTapsCsv() throws IOException {
        TapCsvReader tapCsvReader = new TapCsvReader();
        List<Tap> taps = tapCsvReader.readTaps("src/test/resources/taps.csv");
        System.out.println(taps.toString());
        assertEquals(26, taps.size());

        assertEquals(
                List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L,
                        19L, 20L, 21L, 22L, 23L, 24L, 25L, 26L),
                taps.stream().map(Tap::id).toList());

        assertEquals(
                List.of(ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, OFF, ON, ON, ON, ON, OFF, ON, OFF, ON),
                taps.stream().map(Tap::tapType).toList()
        );

        assertEquals(
                List.of("Bus11", "Bus11", "Bus12", "Bus12", "Bus13", "Bus13", "Bus14", "Bus14", "Bus15", "Bus15",
                        "Bus16", "Bus16", "Bus17", "Bus17", "Bus18", "Bus18", "Bus19", "Bus19", "Bus20", "Bus20", "Bus21", "Bus22", "Bus23", "Bus24", "Bus24", "Bus24"),
                taps.stream().map(Tap::busId).toList()
        );

        assertEquals(1L, taps.getFirst().id());
        assertEquals(LocalDateTime.of(2023, 3, 1, 8, 0, 0), taps.getFirst().dateTime());

        assertEquals(26L, taps.getLast().id());
        assertEquals(LocalDateTime.of(2023, 3, 1, 12, 0, 0), taps.getLast().dateTime());
    }
}
