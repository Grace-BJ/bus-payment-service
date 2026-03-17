package main.model;

public class TapPair {
    private final Tap tapOn;
    private final Tap tapOff; // could be null if unpaired

    public TapPair(Tap tapOn, Tap tapOff) {
        this.tapOn = tapOn;
        this.tapOff = tapOff;
    }

    public boolean isComplete() {
        return this.tapOn != null && this.tapOff != null;
    }

    public boolean isIncompleteOn() {
        return this.tapOn != null && this.tapOff == null;
    }

    public boolean isIncompleteOff() {
        return this.tapOn == null && this.tapOff != null;
    }

    public TapStatus getStatus() {
        if (isComplete()) {
            return TapStatus.COMPLETE;
        } else if (isIncompleteOn()) {
            return TapStatus.INCOMPLETE_ON;
        } else {
            return TapStatus.INCOMPLETE_OFF;
        }
    }


    @Override
    public String toString() {
        return "TapPair{" +
                "tapOn=" + tapOn.toString() +
                ", tapOff=" + tapOff.toString() +
                '}';
    }

    public enum TapStatus {
        COMPLETE,
        INCOMPLETE_ON,
        INCOMPLETE_OFF
    }

    // Optional: calculate duration, fare, etc.
}