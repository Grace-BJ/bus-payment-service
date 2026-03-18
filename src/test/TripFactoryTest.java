package test;

import main.TripFactory;
import main.model.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TripFactoryTest {

    @ParameterizedTest
    @MethodSource("buildTripsCases")
    void buildTripsShouldCreateExpectedTrip(
            TapPair tapPair,
            TripStatus expectedStatus,
            BigDecimal expectedFare,
            StopId expectedFromStop,
            StopId expectedToStop,
            String expectedDurationSecs
    ) {

        TripFactory tripFactory = new TripFactory();
        List<Trip> result = tripFactory.buildTrips(List.of(tapPair));

        assertEquals(1, result.size());

        Trip trip = result.getFirst();
        assertEquals(expectedStatus, trip.status());
        assertEquals(expectedFare, trip.chargeAmount());
        assertEquals(expectedFromStop, trip.fromStopId());
        assertEquals(expectedToStop, trip.toStopId());
        assertEquals(expectedDurationSecs, trip.durationSecs());
    }

    private static Stream<Arguments> buildTripsCases() {
        return Stream.of(
                arguments(
                        new TapPair(
                                new Tap(1L, LocalDateTime.of(2023, 1, 1, 10, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan1"),
                                new Tap(2L, LocalDateTime.of(2023, 1, 1, 10, 5), TapType.OFF, StopId.Stop2, "company1", "bus1", "pan1")
                        ),
                        TripStatus.COMPLETED,
                        new BigDecimal("3.25"),
                        StopId.Stop1,
                        StopId.Stop2,
                        "300"
                ),
                arguments(
                        new TapPair(
                                new Tap(3L, LocalDateTime.of(2023, 1, 1, 11, 0), TapType.ON, StopId.Stop2, "company1", "bus1", "pan2"),
                                new Tap(4L, LocalDateTime.of(2023, 1, 1, 11, 10), TapType.OFF, StopId.Stop3, "company1", "bus1", "pan2")
                        ),
                        TripStatus.COMPLETED,
                        new BigDecimal("5.50"),
                        StopId.Stop2,
                        StopId.Stop3,
                        "600"
                ),
                arguments(
                        new TapPair(
                                new Tap(5L, LocalDateTime.of(2023, 1, 1, 12, 0), TapType.ON, StopId.Stop3, "company1", "bus1", "pan3"),
                                new Tap(6L, LocalDateTime.of(2023, 1, 1, 12, 20), TapType.OFF, StopId.Stop1, "company1", "bus1", "pan3")
                        ),
                        TripStatus.COMPLETED,
                        new BigDecimal("7.30"),
                        StopId.Stop3,
                        StopId.Stop1,
                        "1200"
                ),
                arguments(
                        new TapPair(
                                new Tap(7L, LocalDateTime.of(2023, 1, 1, 13, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan4"),
                                new Tap(8L, LocalDateTime.of(2023, 1, 1, 13, 2), TapType.OFF, StopId.Stop1, "company1", "bus1", "pan4")
                        ),
                        TripStatus.CANCELLED,
                        BigDecimal.ZERO,
                        StopId.Stop1,
                        StopId.Stop1,
                        "120"
                ),
                arguments(
                        new TapPair(
                                new Tap(9L, LocalDateTime.of(2023, 1, 1, 14, 0), TapType.ON, StopId.Stop1, "company1", "bus1", "pan5"),
                                null
                        ),
                        TripStatus.INCOMPLETE,
                        new BigDecimal("7.30"),
                        StopId.Stop1,
                        null,
                        "0"
                ),
                arguments(
                        new TapPair(
                                new Tap(10L, LocalDateTime.of(2023, 1, 1, 15, 0), TapType.ON, StopId.Stop2, "company1", "bus1", "pan6"),
                                null
                        ),
                        TripStatus.INCOMPLETE,
                        new BigDecimal("5.50"),
                        StopId.Stop2,
                        null,
                        "0"
                ),
                arguments(
                        new TapPair(
                                new Tap(11L, LocalDateTime.of(2023, 1, 1, 16, 0), TapType.ON, StopId.Stop3, "company1", "bus1", "pan7"),
                                null
                        ),
                        TripStatus.INCOMPLETE,
                        new BigDecimal("7.30"),
                        StopId.Stop3,
                        null,
                        "0"
                )
        );
    }
}
