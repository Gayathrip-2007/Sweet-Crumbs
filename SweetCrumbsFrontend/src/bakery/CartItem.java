package bakery;

public class CartItem {
    private final Product product;
    private int quantity;
    private String cakeMessage;
    private String specialInstructions;

    public CartItem(Product product, int quantity, String cakeMessage,
                    String specialInstructions) {
        this.product = product;
        this.quantity = quantity;
        this.cakeMessage = cakeMessage;
        this.specialInstructions = specialInstructions;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public String getCakeMessage() { return cakeMessage; }
    public String getSpecialInstructions() { return specialInstructions; }

    public void increase() { quantity++; }

    public void decrease() {
        if (quantity > 1) quantity--;
    }

    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
