package inheritance.analyser;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

sealed abstract class SalesAnalyser permits EstonianTaxSalesAnalyser, TaxFreeSalesAnalyser, FinnishSalesAnalyser{
    protected final List<SalesRecord> records;

    public SalesAnalyser(List<SalesRecord> records) {
        this.records = records;
    }

    protected abstract double applyTax(double tax, LocalDate date);

    public double getTotalSales() {
        double totalSales = 0.0;
        for (SalesRecord record : records) {
            double salesAmount = record.productPrice() * record.itemsSold();
            totalSales += applyTax(salesAmount, record.date());
        }
        return totalSales;
    }

    public double getTotalSalesByProductId(String productId) {
        double totalSales = 0.0;
        for (SalesRecord record : records) {
            if (record.productId().equals(productId)) {
                double salesAmount = record.productPrice() * record.itemsSold();
                totalSales += applyTax(salesAmount, record.date());
            }
        }
        return totalSales;
    }

    public List<String> getTop3PopularItems() {
        Map<String, Integer> quantitySoldByProduct = new HashMap<>();
        for (SalesRecord record : records) {
            String productId = record.productId();
            int itemsSold = record.itemsSold();

            if (quantitySoldByProduct.containsKey(productId)) {
                quantitySoldByProduct.put(productId, quantitySoldByProduct.get(productId) + itemsSold);
            } else {
                quantitySoldByProduct.put(productId, itemsSold);
            }
        }


        List<String> topProducts = new ArrayList<>();
        for (int i = 0; i < 3 && !quantitySoldByProduct.isEmpty(); i++) {
            String topProduct = null;
            int maxQuantity = 0;

            for (Map.Entry<String, Integer> entry : quantitySoldByProduct.entrySet()) {
                if (entry.getValue() > maxQuantity) {
                    maxQuantity = entry.getValue();
                    topProduct = entry.getKey();
                }
            }

            if (topProduct != null) {
                topProducts.add(topProduct);
                quantitySoldByProduct.remove(topProduct);
            }
        }

        return topProducts;
    }

    public double getLargestTotalSalesAmountForSingleItem() {
        Map<String, Double> salesByProduct = new HashMap<>();
        
        for (SalesRecord record : records) {
            String productId = record.productId();
            double salesAmount = applyTax(record.productPrice() * record.itemsSold(), record.date());
            
            if (salesByProduct.containsKey(productId)) {
                salesByProduct.put(productId, salesByProduct.get(productId) + salesAmount);
            } else {
                salesByProduct.put(productId, salesAmount);
            }
        }
        double maxSalesAmount = 0.0;
        for (double amount : salesByProduct.values()) {
            if (amount > maxSalesAmount) {
                maxSalesAmount = amount;
            }
        }
        return maxSalesAmount;
    }
}
