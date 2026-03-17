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

    public TapStatus getStatus() {
        if (isComplete()) {
            return TapStatus.COMPLETE;
        } else {
            return TapStatus.INCOMPLETE;
        }
    }

    public Tap getTapOn() {
        return tapOn;
    }

    public Tap getTapOff() {
        return tapOff;
    }

    public enum TapStatus {
        COMPLETE,
        INCOMPLETE,
    }

}