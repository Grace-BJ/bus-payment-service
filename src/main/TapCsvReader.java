package main;

import main.model.StopId;
import main.model.TapType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TapCsvReader {

    public List<Tap> readTaps(String filePath) throws IOException {
        List<Tap> taps = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                Long id = Long.parseLong(parts[0].trim());
                LocalDateTime dateTime = LocalDateTime.parse(parts[1].trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                TapType tapType = TapType.valueOf(parts[2].trim());
                StopId stopId = StopId.valueOf(parts[3].trim());
                String companyId = parts[4].trim();
                String busId = parts[5].trim();
                String pan = parts[6].trim();

                Tap tap = new Tap(id, dateTime, tapType, stopId, companyId, busId, pan);
                taps.add(tap);
            }
        }

        return taps;
    }
}
