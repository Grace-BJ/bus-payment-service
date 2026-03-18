package main;

import java.io.IOException;

public class Main {

    static void main() throws IOException {

        // here we set the input and output paths for processing bus payment data
        String inputPath = "src/main/resources/taps.csv";
        String outputPath = "src/main/resources/trips.csv";
        BusPaymentService busPaymentService = new BusPaymentService();
        busPaymentService.process(inputPath, outputPath);

    }
}
