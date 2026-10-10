package bakery;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<CartItem> items = new ArrayList<>();

    public List<CartItem> getItems() {
        return items;
    }

    public void addItem(Product product, int quantity, String message,
                        String instructions) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()
                    && item.getCakeMessage().equals(message)
                    && item.getSpecialInstructions().equals(instructions)) {
                for (int i = 0; i < quantity; i++) item.increase();
                return;
            }
        }
        items.add(new CartItem(product, quantity, message, instructions));
    }

    public void removeItem(CartItem item) {
        items.remove(item);
    }

    public double getSubtotal() {
        double total = 0;
        for (CartItem item : items) total += item.getSubtotal();
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}
