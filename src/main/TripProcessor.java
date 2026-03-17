package main;

import main.model.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripProcessor {

    public void process(List<Tap> taps) {

        // match taps on and off to create pairs
        List<TapPair> tapPairs = matchTaps(taps);

        // calculate fare for pairs
        calculateFare(tapPairs);


    }

    public List<Trip> calculateFare(List<TapPair> tapPairs) {
        List<Trip> trips = new ArrayList<>();

        for (TapPair tapPair : tapPairs) {

            Tap tapOn = tapPair.getTapOn();
            Tap tapOff = tapPair.getTapOff();

            if (!tapPair.isComplete()) {
                trips.add(new Trip(
                        tapOn.getDateTime(),
                        null,
                        "0",
                        tapOn.getStopId(),
                        tapOn.getStopId(),
                        maxFareFrom(tapOn.getStopId()),
                        tapOn.getCompanyId(),
                        tapOn.getBusId(),
                        tapOn.getPan(),
                        TripStatus.INCOMPLETE
                ));
                continue;
            }

            if (tapOn.getStopId() == tapOff.getStopId()) {
                trips.add(new Trip(
                        tapOn.getDateTime(),
                        tapOff.getDateTime(),
                        String.valueOf(Duration.between(tapOn.getDateTime(), tapOff.getDateTime()).getSeconds()),
                        tapOn.getStopId(),
                        tapOff.getStopId(),
                        BigDecimal.ZERO,
                        tapOn.getCompanyId(),
                        tapOn.getBusId(),
                        tapOn.getPan(),
                        TripStatus.CANCELLED
                ));
                continue;
            }

            trips.add(new Trip(
                    tapOn.getDateTime(),
                    tapOff.getDateTime(),
                    String.valueOf(Duration.between(tapOn.getDateTime(), tapOff.getDateTime()).getSeconds()),
                    tapOn.getStopId(),
                    tapOff.getStopId(),
                    fareBetween(tapOn.getStopId(), tapOff.getStopId()),
                    tapOn.getCompanyId(),
                    tapOn.getBusId(),
                    tapOn.getPan(),
                    TripStatus.COMPLETE
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




    public static List<TapPair> matchTaps(List<Tap> taps) {
        // Group by account + bus
        Map<AccountBusKey, List<Tap>> grouped = taps.stream()
                .collect(Collectors.groupingBy(t -> new AccountBusKey(t.getPan(), t.getBusId())));

        List<TapPair> pairs = new ArrayList<>();

        for (List<Tap> tapsForGroup : grouped.values()) {
            tapsForGroup.sort(Comparator.comparing(Tap::getDateTime));

            Tap currentOn = null;
            for (Tap tap : tapsForGroup) {
                if (tap.getTapType() == TapType.ON) {
                    if (currentOn != null) pairs.add(new TapPair(currentOn, null));
                    currentOn = tap;
                } else {
                    if (currentOn != null) {
                        pairs.add(new TapPair(currentOn, tap));
                        currentOn = null;
                    } else {
                        pairs.add(new TapPair(null, tap));
                    }
                }
            }

            if (currentOn != null) pairs.add(new TapPair(currentOn, null));
        }

        return pairs;
    }
}
