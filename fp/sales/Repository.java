package fp.sales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Repository {

    private static final String FILE_PATH = "src/fp/sales/sales-data.csv";

    private DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("dd.MM.yyyy");

    public List<Entry> getEntries() {
        try {
            return Files.readAllLines(Paths.get(FILE_PATH))
                    .stream()
                    .skip(1) // Skip header line
                    .filter(line -> !line.trim().isEmpty()) // Filter out empty lines
                    .map(this::parseLineToEntry)
                    .filter(entry -> entry != null) // Filter out null entries from parsing errors
                    .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read sales data file", e);
        }
    }

    private Entry parseLineToEntry(String line) {
        try {
            // Split by tab and clean up quotes
            String[] parts = line.split("\t");
            if (parts.length < 7) {
                return null; // Skip malformed lines
            }

            // Clean quotes and extract data
            int rowNo = Integer.parseInt(parts[0].replaceAll("\"", "").trim());
            String dateStr = parts[1].replaceAll("\"", "").trim();
            String state = parts[2].replaceAll("\"", "").trim();
            String productId = parts[3].replaceAll("\"", "").trim();
            String category = parts[4].replaceAll("\"", "").trim();
            // Product name is in parts[5], but we don't need it for Entry
            String amountStr = parts[6].replaceAll("\"", "").replaceAll(",", ".").trim();

            LocalDate date = LocalDate.parse(dateStr, formatter);
            Double amount = Double.parseDouble(amountStr);

            return new Entry(rowNo, productId, date, state, category, amount);
        } catch (Exception e) {
            // Skip lines that can't be parsed
            return null;
        }
    }
}