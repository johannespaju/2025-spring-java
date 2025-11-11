package fp.sales;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Analyser {

    private final Repository repository;
    private final AccountingService accountingService;

    public Analyser(Repository repository,
                    AccountingService accountingService) {
        this.repository = repository;
        this.accountingService = accountingService;
    }

    public Double getTotalSales() {
        return repository.getEntries()
                .stream()
                .mapToDouble(Entry::amount)
                .sum();
    }

    public Double getSalesByCategory(String category) {
        return repository.getEntries()
                .stream()
                .filter(entry -> category.equals(entry.category()))
                .mapToDouble(Entry::amount)
                .sum();
    }

    public Double getSalesBetween(LocalDate start, LocalDate end) {
        return repository.getEntries()
                .stream()
                .filter(entry -> !entry.date().isBefore(start) && !entry.date().isAfter(end))
                .mapToDouble(Entry::amount)
                .sum();
    }

    public String mostExpensiveItems() {
        return repository.getEntries()
                .stream()
                .sorted(Comparator.comparing(Entry::amount).reversed())
                .limit(3)
                .map(Entry::productId)
                .sorted()
                .collect(Collectors.joining(", "));
    }

    public String statesWithBiggestSales() {
        return repository.getEntries()
                .stream()
                .collect(Collectors.groupingBy(
                        Entry::state,
                        Collectors.summingDouble(Entry::amount)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(", "));
    }

    public String findMostProfitableItems() {
        return repository.getEntries()
                .stream()
                .collect(Collectors.groupingBy(
                        Entry::productId,
                        Collectors.summingDouble(Entry::amount)))
                .entrySet()
                .stream()
                .map(entry -> {
                    String productId = entry.getKey();
                    Double sales = entry.getValue();
                    Double profitMargin = accountingService.getProfitMargin(productId);
                    Double profit = sales * profitMargin;
                    return new ProductProfit(productId, profit);
                })
                .sorted(Comparator.comparing(ProductProfit::profit).reversed())
                .limit(3)
                .map(ProductProfit::productId)
                .collect(Collectors.joining(", "));
    }

    public List<Entry> getAllRecordsPaged(int pageNumber, int pageSize) {
        return repository.getEntries()
                .stream()
                .sorted(Comparator.comparing(Entry::date))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .toList();
    }

    public List<String> getCategoryList() {
        // only needed for icd0019app

        return List.of();
    }

    public int getRecordCount() {
        // only needed for icd0019app

        return 0;
    }

    // Helper record for profit calculations
    private record ProductProfit(String productId, Double profit) {
    }
}