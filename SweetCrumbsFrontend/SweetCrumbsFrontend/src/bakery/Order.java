package bakery;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int nextId = 1001;

    private final int orderId;
    private final String customerName;
    private final String phone;
    private final String address;
    private final String deliveryDate;
    private final String bakerNote;
    private final List<CartItem> items;
    private final double total;
    private String status;

    public Order(String customerName, String phone, String address,
                 String deliveryDate, String bakerNote,
                 List<CartItem> cartItems, double total) {
        this.orderId = nextId++;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.deliveryDate = deliveryDate;
        this.bakerNote = bakerNote;
        this.items = new ArrayList<>(cartItems);
        this.total = total;
        this.status = "Pending";
    }

    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getDeliveryDate() { return deliveryDate; }
    public String getBakerNote() { return bakerNote; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
