package poly.customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class RegularCustomer extends AbstractCustomer {

    private LocalDate lastOrderDate;

    public RegularCustomer(String id, String name,
                           int bonusPoints, LocalDate lastOrderDate) {

        super(id, name, bonusPoints);
        this.lastOrderDate = lastOrderDate;
    }

    @Override
    public void collectBonusPointsFrom(Order order) {
        if (order.total() >= 100) {
            int points = Math.toIntExact(Math.round(order.total()));

            // Kui eelmine tellimus oli vähem kui kuu aega tagasi, siis 1.5x punktid
            if (lastOrderDate != null &&
                    ChronoUnit.DAYS.between(lastOrderDate, order.date()) < 30) {
                points = Math.toIntExact(Math.round(order.total() * 1.5));
            }

            this.bonusPoints += points;
        }

        // Uuenda viimase tellimuse kuupäeva
        this.lastOrderDate = order.date();
    }


    @Override
    public String asString() {
        String dateStr = lastOrderDate != null ? lastOrderDate.toString() : "";
        return "REGULAR;" + id + ";" + name + ";" + bonusPoints + ";" + dateStr;
    }

    public LocalDate getLastOrderDate() {
        return lastOrderDate;
    }
}