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
        tapsByAccountAndBus.entrySet().stream()
                // sort tap group order to ensure results are deterministic
                .sorted(Comparator
                        .comparing((Map.Entry<AccountBusKey, List<Tap>> e) -> e.getKey().accountId())
                        .thenComparing(e -> e.getKey().busId()))
                .forEach(entry -> pairs.addAll(pairSingleAccountBus(entry.getValue())));

        return pairs;
    }

    private Map<AccountBusKey, List<Tap>> groupByAccountAndBus(List<Tap> taps) {
        return taps.stream()
                .collect(Collectors.groupingBy(t -> new AccountBusKey(t.pan(), t.busId())));
    }

    private List<TapPair> pairSingleAccountBus(List<Tap> group) {
        List<Tap> sorted = group.stream()
                // order taps within each group to ensure results are deterministic
                .sorted(Comparator.comparing(Tap::dateTime).thenComparing(Tap::id))
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
