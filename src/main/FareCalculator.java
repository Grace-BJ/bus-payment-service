package main;

import main.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FareCalculator {

    public List<Trip> calculateFare(List<TapPair> tapPairs) {
        List<Trip> trips = new ArrayList<>();

        for (TapPair tapPair : tapPairs) {

            Tap tapOn = tapPair.tapOn();
            Tap tapOff = tapPair.tapOff();

            if (!tapPair.isComplete()) {
                trips.add(new Trip(
                        tapOn.dateTime(),
                        null,
                        "0",
                        tapOn.stopId(),
                        tapOn.stopId(),
                        maxFareFrom(tapOn.stopId()),
                        tapOn.companyId(),
                        tapOn.busId(),
                        tapOn.pan(),
                        TripStatus.INCOMPLETE
                ));
                continue;
            }

            if (tapOn.stopId() == tapOff.stopId()) {
                trips.add(new Trip(
                        tapOn.dateTime(),
                        tapOff.dateTime(),
                        String.valueOf(Duration.between(tapOn.dateTime(), tapOff.dateTime()).getSeconds()),
                        tapOn.stopId(),
                        tapOff.stopId(),
                        BigDecimal.ZERO,
                        tapOn.companyId(),
                        tapOn.busId(),
                        tapOn.pan(),
                        TripStatus.CANCELLED
                ));
                continue;
            }

            trips.add(new Trip(
                    tapOn.dateTime(),
                    tapOff.dateTime(),
                    String.valueOf(Duration.between(tapOn.dateTime(), tapOff.dateTime()).getSeconds()),
                    tapOn.stopId(),
                    tapOff.stopId(),
                    fareBetween(tapOn.stopId(), tapOff.stopId()),
                    tapOn.companyId(),
                    tapOn.busId(),
                    tapOn.pan(),
                    TripStatus.COMPLETED
            ));
        }

        return trips;
    }

    private BigDecimal fareBetween(StopId from, StopId to) {
        if (from == to){
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
