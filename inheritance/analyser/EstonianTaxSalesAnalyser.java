package inheritance.analyser;

import java.time.LocalDate;
import java.util.List;

public final class EstonianTaxSalesAnalyser extends SalesAnalyser {
    private static final LocalDate VAT_CHANGE_2009 = LocalDate.parse("2009-07-01");
    private static final LocalDate VAT_CHANGE_2024 = LocalDate.parse("2024-01-01");
    private static final LocalDate VAT_CHANGE_2025 = LocalDate.parse("2025-07-01");

    public EstonianTaxSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double applyTax(double amount, LocalDate date) {
        if (date.isBefore(VAT_CHANGE_2009)) {
            return amount / 1.18;
        } else if (date.isBefore(VAT_CHANGE_2024)) {
            return amount / 1.20;
        } else if (date.isBefore(VAT_CHANGE_2025)) {
            return amount / 1.22;
        } else {
            return amount / 1.24;
        }
    }
}
