package main;

import main.model.Trip;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TripCsvWriter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public void writeTrips(String filePath, List<Trip> trips) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status");
            writer.newLine();


            for (Trip trip : trips) {
                writer.write(String.join(",",
                        formatDateTime(trip.started()),
                        formatDateTime(trip.finished()),
                        trip.durationSecs(),
                        formatValue(trip.fromStopId()),
                        formatValue(trip.toStopId()),
                        formatAmount(trip.chargeAmount().toString()),
                        formatValue(trip.companyId()),
                        formatValue(trip.busId()),
                        formatValue(trip.pan()),
                        trip.status().name()
                ));
                writer.newLine();
            }
        }
    }

    private String formatDateTime(java.time.LocalDateTime dateTime) {
        return dateTime == null ? "" : dateTime.format(DATE_TIME_FORMATTER);
    }

    private String formatAmount(String amount) {
        return "$" + amount;
    }

    private String formatValue(Object value) {
        return value == null ? "" : value.toString();
    }
}
