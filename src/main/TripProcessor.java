package main;

import main.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripProcessor {

    public List<Trip> process(List<Tap> taps) {

        // match taps on and off to create pairs
        List<TapPair> tapPairs = matchTaps(taps);

        // calculate fare for pairs
        FareCalculator fareCalculator = new FareCalculator();
        return fareCalculator.calculateFare(tapPairs);

    }

    public static List<TapPair> matchTaps(List<Tap> taps) {
        // Group by account + bus
        Map<AccountBusKey, List<Tap>> grouped = taps.stream()
                .collect(Collectors.groupingBy(t -> new AccountBusKey(t.pan(), t.busId())));

        List<TapPair> pairs = new ArrayList<>();

        for (List<Tap> tapsForGroup : grouped.values()) {
            tapsForGroup.sort(Comparator.comparing(Tap::dateTime));

            Tap currentOn = null;
            for (Tap tap : tapsForGroup) {
                if (tap.tapType() == TapType.ON) {
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
