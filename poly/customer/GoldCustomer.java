package poly.customer;

public final class GoldCustomer extends AbstractCustomer {

    public GoldCustomer(String id, String name, int bonusPoints) {
        super(id, name, bonusPoints);
    }

    @Override
    public void collectBonusPointsFrom(Order order) {
        if (order.total() >= 100) {
            // Kuldklient saab alati tellimuse maksumus * 1.5 boonuspunkti
            bonusPoints += order.total() * 1.5;
        }
    }

    @Override
    public String asString() {
        return "GOLD;" + id + ";" + name + ";" + bonusPoints + ";";
    }
}