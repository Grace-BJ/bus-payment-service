package main;

import main.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TripFactory {

    public List<Trip> buildTrips(List<TapPair> tapPairs) {
        List<Trip> trips = new ArrayList<>();

        for (TapPair tapPair : tapPairs) {

            Tap tapOn = tapPair.tapOn();
            Tap tapOff = tapPair.tapOff();

            // add incomplete trips
            if (!tapPair.isComplete()) {
                trips.add(toIncompleteTrip(tapOn));
                continue;
            }

            // add cancelled trips
            if (tapOn.stopId() == tapOff.stopId()) {
                trips.add(toCancelledTrip(tapOn, tapOff));
                continue;
            }

            // add completed trips
            trips.add(toCompletedTrip(tapOn, tapOff));
        }
        return trips;
    }

    private Trip toIncompleteTrip(Tap tapOn) {
        return new Trip(
                tapOn.dateTime(),
                null,
                "0",
                tapOn.stopId(),
                null,
                maxFareFrom(tapOn.stopId()),
                tapOn.companyId(),
                tapOn.busId(),
                tapOn.pan(),
                TripStatus.INCOMPLETE
        );
    }

    private Trip toCancelledTrip(Tap tapOn, Tap tapOff) {
        return new Trip(
                tapOn.dateTime(),
                tapOff.dateTime(),
                durationSecs(tapOn, tapOff),
                tapOn.stopId(),
                tapOff.stopId(),
                BigDecimal.ZERO,
                tapOn.companyId(),
                tapOn.busId(),
                tapOn.pan(),
                TripStatus.CANCELLED
        );
    }

    private Trip toCompletedTrip(Tap tapOn, Tap tapOff) {
        return new Trip(
                tapOn.dateTime(),
                tapOff.dateTime(),
                durationSecs(tapOn, tapOff),
                tapOn.stopId(),
                tapOff.stopId(),
                fareBetween(tapOn.stopId(), tapOff.stopId()),
                tapOn.companyId(),
                tapOn.busId(),
                tapOn.pan(),
                TripStatus.COMPLETED
        );
    }

    private String durationSecs(Tap tapOn, Tap tapOff) {
        return String.valueOf(Duration.between(tapOn.dateTime(), tapOff.dateTime()).getSeconds());
    }

    private BigDecimal fareBetween(StopId from, StopId to) {
        if (from == to) {
            return new BigDecimal("0.00");
        }
        if ((from == StopId.Stop1 && to == StopId.Stop2) || (from == StopId.Stop2 && to == StopId.Stop1)) {
            return new BigDecimal("3.25");
        }
        if ((from == StopId.Stop2 && to == StopId.Stop3) || (from == StopId.Stop3 && to == StopId.Stop2)) {
            return new BigDecimal("5.50");
        }
        if ((from == StopId.Stop1 && to == StopId.Stop3) || (from == StopId.Stop3 && to == StopId.Stop1)) {
            return new BigDecimal("7.30");
        }
        throw new IllegalArgumentException("Unknown fare from " + from + " to " + to);
    }

    private BigDecimal maxFareFrom(StopId from) {
        return switch (from) {
            case Stop1, Stop3 -> new BigDecimal("7.30");
            case Stop2 -> new BigDecimal("5.50");
        };
    }
}
