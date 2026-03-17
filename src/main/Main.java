package main;

import main.model.StopId;
import main.model.TapType;

import java.time.LocalDateTime;

public class Main {

    static void main() {
        Tap tap = new Tap(1L, LocalDateTime.now(), TapType.ON, StopId.Stop1, "company1", "bus1", "pan1");
    }
}
