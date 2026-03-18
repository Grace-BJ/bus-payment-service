package main;

import main.model.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TapPairingService {

    public List<TapPair> pairTaps(List<Tap> taps) {

        // Group taps by account and bus
        Map<AccountBusKey, List<Tap>> tapsByAccountAndBus = groupByAccountAndBus(taps);

        List<TapPair> pairs = new ArrayList<>();
        for (List<Tap> group : tapsByAccountAndBus.values()) {
            pairs.addAll(pairSingleAccountBus(group));
        }
        return pairs;
    }

    private Map<AccountBusKey, List<Tap>> groupByAccountAndBus(List<Tap> taps) {
        return taps.stream()
                .collect(Collectors.groupingBy(t -> new AccountBusKey(t.pan(), t.busId())));
    }

    private List<TapPair> pairSingleAccountBus(List<Tap> group) {
        List<Tap> sorted = group.stream()
                .sorted(Comparator.comparing(Tap::dateTime))
                .toList();

        List<TapPair> pairs = new ArrayList<>();
        Tap currentOn = null;

        for (Tap tap : sorted) {
            if (tap.tapType() == TapType.ON) {
                if (currentOn != null) {
                    pairs.add(new TapPair(currentOn, null));
                }
                currentOn = tap;
            } else if (currentOn != null) {
                pairs.add(new TapPair(currentOn, tap));
                currentOn = null;
            }
        }

        if (currentOn != null) {
            pairs.add(new TapPair(currentOn, null));
        }

        return pairs;
    }
}
