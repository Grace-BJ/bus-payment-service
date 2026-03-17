package main;

import main.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TripProcessor {

    public void process(List<Tap> taps) {

        // match taps on and off to create trips
        matchTaps(taps);

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
