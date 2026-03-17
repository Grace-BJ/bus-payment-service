package test;

import main.TapMatcher;
import main.model.StopId;
import main.model.Tap;
import main.model.TapPair;
import main.model.TapType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

public class TapMatcherTest {

    @ParameterizedTest
    @MethodSource("matchTapsCases")
    void matchTapsShouldCreateExpectedPairs(List<Tap> taps, List<TapPair.TapStatus> expectedOutcome) {
        TapMatcher tapMatcher = new TapMatcher();
        List<TapPair.TapStatus> actualOutcome = tapMatcher.matchTaps(taps).stream().map(TapPair::getStatus).toList();
        assertEquals(expectedOutcome, actualOutcome);
    }

    private static Stream<Arguments> matchTapsCases() {
        return Stream.of(
                of(
                        List.of(
                                new Tap(1L, LocalDateTime.of(2023, 1, 1, 10, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan1"),
                                new Tap(2L, LocalDateTime.of(2023, 1, 1, 10, 5), TapType.OFF, StopId.Stop2, "company1", "bus1", "pan1")
                        ),
                        List.of(TapPair.TapStatus.COMPLETE)
                ),
                of(
                        List.of(
                                new Tap(3L, LocalDateTime.of(2023, 1, 1, 11, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan2")
                        ),
                        List.of(TapPair.TapStatus.INCOMPLETE)
                ),
                of(
                        List.of(
                                new Tap(4L, LocalDateTime.of(2023, 1, 1, 12, 0), TapType.OFF, StopId.Stop2, "company1", "bus1", "pan3")
                        ),
                        List.of(TapPair.TapStatus.INCOMPLETE)
                ),
                of(
                        List.of(
                                new Tap(5L, LocalDateTime.of(2023, 1, 1, 13, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan4"),
                                new Tap(6L, LocalDateTime.of(2023, 1, 1, 13, 2), TapType.ON, StopId.Stop2, "company1", "bus1", "pan4"),
                                new Tap(7L, LocalDateTime.of(2023, 1, 1, 13, 10), TapType.OFF, StopId.Stop3, "company1", "bus1", "pan4")
                        ),
                        List.of(
                                TapPair.TapStatus.INCOMPLETE,
                                TapPair.TapStatus.COMPLETE
                        )
                ),
                of(
                        List.of(
                                new Tap(8L, LocalDateTime.of(2023, 1, 1, 14, 0), TapType.OFF, StopId.Stop1, "company1", "bus1", "pan5"),
                                new Tap(9L, LocalDateTime.of(2023, 1, 1, 14, 5), TapType.ON, StopId.Stop2, "company1", "bus1", "pan5")
                        ),
                        List.of(
                                TapPair.TapStatus.INCOMPLETE,
                                TapPair.TapStatus.INCOMPLETE
                        )
                )
        );
    }
}

