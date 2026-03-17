package main.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trip {
    private final LocalDateTime started;
    private final LocalDateTime finished;
    private final String durationSecs;
    private final StopId fromStopId;
    private final StopId toStopId;
    private final BigDecimal chargeAmount;
    private final String companyId;
    private final String busId;
    private final String pan;
    private final TripStatus status;

    public Trip(
            LocalDateTime started,
            LocalDateTime finished,
            String durationSecs,
            StopId fromStopId,
            StopId toStopId,
            BigDecimal chargeAmount,
            String companyId,
            String busId,
            String pan,
            TripStatus status
    ) {
        this.started = started;
        this.finished = finished;
        this.durationSecs = durationSecs;
        this.fromStopId = fromStopId;
        this.toStopId = toStopId;
        this.chargeAmount = chargeAmount;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
        this.status = status;
    }

    public LocalDateTime getStarted() { return started; }
    public LocalDateTime getFinished() { return finished; }
    public String getDurationSecs() { return durationSecs; }
    public StopId getFromStopId() { return fromStopId; }
    public StopId getToStopId() { return toStopId; }
    public BigDecimal getChargeAmount() { return chargeAmount; }
    public String getCompanyId() { return companyId; }
    public String getBusId() { return busId; }
    public String getPan() { return pan; }
    public TripStatus getStatus() { return status; }
}
