package main.model;

public record TapPair(Tap tapOn, Tap tapOff) {

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

    public enum TapStatus {
        COMPLETE,
        INCOMPLETE,
    }

}