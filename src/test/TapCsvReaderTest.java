package test;

import main.Tap;
import main.TapCsvReader;
import main.model.TapType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TapCsvReaderTest {

    @Test
    void readTapsCsv() throws IOException {
        TapCsvReader tapCsvReader = new TapCsvReader();
        List<Tap> taps = tapCsvReader.readTaps("src/test/resources/taps.csv");
        System.out.println(taps.toString());
        assertEquals(6, taps.size());


        List<Long> ids = taps.stream().map(Tap::getId).toList();
        assertEquals(
                List.of(1L, 2L, 3L, 4L, 5L, 6L),
                ids
        );

        List<TapType> tapTypes = taps.stream().map(Tap::getTapType).toList();
        assertEquals(
                List.of(TapType.ON, TapType.OFF, TapType.ON, TapType.ON, TapType.OFF, TapType.OFF),
                tapTypes
        );

        List<String> busIds = taps.stream().map(Tap::getBusId).toList();
        assertEquals(
                List.of("Bus37", "Bus37", "Bus36", "Bus37", "Bus37", "Bus37"),
                busIds
        );

        Tap firstTap = taps.stream().findFirst().orElseThrow();
        assertEquals(1L, firstTap.getId());
        assertEquals(LocalDateTime.of(2023, 1, 22, 13, 0, 0), firstTap.getDateTime());

        Tap lastTap = taps.stream().reduce((first, second) -> second).orElseThrow();
        assertEquals(6L, lastTap.getId());
        assertEquals(LocalDateTime.of(2023, 1, 24, 16, 30, 0), lastTap.getDateTime());
    }
}
