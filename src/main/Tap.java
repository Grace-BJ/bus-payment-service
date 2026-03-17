package main;

import main.model.StopId;
import main.model.TapType;

import java.time.LocalDateTime;

public class Tap {

    private final Long id;
    private final LocalDateTime dateTime;
    private final TapType tapType;
    private final StopId stopId;
    private final String companyId;
    private final String busId;
    private final String pan; // Primary Account Number

    public Tap(Long id, LocalDateTime dateTime, TapType tapType, StopId stopId, String companyId, String busId, String pan) {
        this.id = id;
        this.dateTime = dateTime;
        this.tapType = tapType;
        this.stopId = stopId;
        this.companyId = companyId;
        this.busId = busId;
        this.pan = pan;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public TapType getTapType() {
        return tapType;
    }

    public StopId getStopId() {
        return stopId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getBusId() {
        return busId;
    }

    public String getPan() {
        return pan;
    }
}
