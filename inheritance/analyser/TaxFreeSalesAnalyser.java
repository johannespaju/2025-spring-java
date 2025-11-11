package inheritance.analyser;

import java.time.LocalDate;
import java.util.List;

public final class TaxFreeSalesAnalyser extends SalesAnalyser {

    public TaxFreeSalesAnalyser(List<SalesRecord> records) {
        super(records);
    }

    @Override
    protected double applyTax(double amount, LocalDate date) {
        return amount;
    }

}
