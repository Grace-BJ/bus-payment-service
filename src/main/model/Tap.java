package main.model;

import java.time.LocalDateTime;

public record Tap(Long id, LocalDateTime dateTime, TapType tapType, StopId stopId, String companyId, String busId,
                  String pan) {
}
// pan - Primary Account Number
