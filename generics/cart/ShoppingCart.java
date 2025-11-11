package generics.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShoppingCart<T extends CartItem> {

    private final Map<String, Double> couponToDiscount = Map.of(
            "Sale5", 5.0,
            "Sale8", 8.0,
            "Sale10", 10.0);

    private final List<CartEntry> entries = new ArrayList<>();
    private final List<Double> discounts = new ArrayList<>();
    private Double couponDiscount = 0.0;

    public void add(T item) {
        CartEntry existingEntry = null;
        for (CartEntry entry : entries) {
            if (entry.item.id().equals(item.id())) {
                existingEntry = entry;
                break;
            }
        }

        if (existingEntry != null) {
            existingEntry.quantity++;
        } else {
            entries.add(new CartEntry(item, 1));
        }
    }

    public void removeById(String id) {
        for (int i = entries.size() - 1; i >= 0; i--) {
            if (entries.get(i).item.id().equals(id)) {
                entries.remove(i);
            }
        }
    }

    public Double getTotal() {
        double subtotal = 0.0;
        for (CartEntry entry : entries) {
            subtotal += entry.item.price() * entry.quantity;
        }

        for (Double discount : discounts) {
            subtotal = subtotal * (1 - discount / 100.0);
        }

        subtotal = subtotal * (1 - couponDiscount / 100.0);

        return subtotal;
    }

    public List<CartEntry> getContents() {
        return new ArrayList<>(entries);
    }

    public void increaseQuantity(String id) {
        for (CartEntry entry : entries) {
            if (entry.item.id().equals(id)) {
                entry.quantity++;
                break;
            }
        }
    }

    public void applyDiscountPercentage(Double discount) {
        discounts.add(discount);
    }

    public boolean applyCoupon(String coupon) {
        if (couponToDiscount.containsKey(coupon)) {
            couponDiscount = couponToDiscount.get(coupon);
            return true;
        }
        return false;
    }

    public Double getTotalDiscount() {
        double subtotal = 0.0;
        for (CartEntry entry : entries) {
            subtotal += entry.item.price() * entry.quantity;
        }

        if (subtotal == 0.0) {
            double multiplier = 1.0;

            for (Double discount : discounts) {
                multiplier *= (1 - discount / 100.0);
            }

            multiplier *= (1 - couponDiscount / 100.0);

            return (1 - multiplier) * 100;
        }

        double originalTotal = subtotal;
        double discountedTotal = getTotal();
        return originalTotal - discountedTotal;
    }

    public void removeLastDiscount() {
        if (!discounts.isEmpty()) {
            discounts.remove(discounts.size() - 1);
        }
    }

    public void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    @Override
    public String toString() {
        if (entries.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entries.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(entries.get(i).toString());
        }
        return sb.toString();
    }
}