package inheritance.analyser;

import java.util.List;
import java.time.LocalDate;

public final class FinnishSalesAnalyser extends SalesAnalyser {
    private static final LocalDate VAT_CHANGE_1994 = LocalDate.parse("1994-06-01");
    private static final LocalDate VAT_CHANGE_2010 = LocalDate.parse("2010-07-01");
    private static final LocalDate VAT_CHANGE_2013 = LocalDate.parse("2013-01-01");
    private static final LocalDate VAT_CHANGE_2024 = LocalDate.parse("2024-09-01");

    public FinnishSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double applyTax(double amount, LocalDate date) {
        if (date.isBefore(VAT_CHANGE_1994)) {
            return amount / 1.20;
        } else if (date.isBefore(VAT_CHANGE_2010)) {
            return amount / 1.22;
        } else if (date.isBefore(VAT_CHANGE_2013)) {
            return amount / 1.23;
        } else if (date.isBefore(VAT_CHANGE_2024)) {
            return amount / 1.24;
        } else {
            return amount / 1.255;
        }
    }
}
