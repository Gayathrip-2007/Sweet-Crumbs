package bakery;

import java.util.ArrayList;
import java.util.List;

public class AppData {
    private final List<Product> products = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Order> orders = new ArrayList<>();

    public AppData() {
        loadProducts();

        users.add(new User(
                "Demo Customer",
                "customer@sweetcrumbs.com",
                "9876543210",
                "1234",
                false
        ));

        users.add(new User(
                "Bakery Admin",
                "admin@sweetcrumbs.com",
                "9999999999",
                "admin",
                true
        ));
    }

    private void loadProducts() {
        products.add(new Product(1, "Chocolate Truffle Cake", "Cake", 850,
                "Rich chocolate cake covered with smooth chocolate ganache.", "🎂"));
        products.add(new Product(2, "Red Velvet Cake", "Cake", 900,
                "Soft red velvet cake with creamy cheese frosting.", "🎂"));
        products.add(new Product(3, "Black Forest Cake", "Cake", 950,
                "Classic chocolate cake layered with cream and cherries.", "🎂"));
        products.add(new Product(4, "Chocolate Cupcake", "Cupcake", 60,
                "Soft chocolate cupcake with chocolate frosting.", "🧁"));
        products.add(new Product(5, "Vanilla Cupcake", "Cupcake", 50,
                "Classic vanilla cupcake with creamy vanilla frosting.", "🧁"));
        products.add(new Product(6, "Fudge Brownie", "Brownie", 80,
                "Dense, rich and fudgy chocolate brownie.", "🍫"));
        products.add(new Product(7, "Walnut Brownie", "Brownie", 100,
                "Chocolate brownie with crunchy walnuts.", "🍫"));
        products.add(new Product(8, "Chocolate Chip Cookies", "Cookie", 150,
                "Homemade cookies filled with chocolate chips.", "🍪"));
        products.add(new Product(9, "Butter Cookies", "Cookie", 130,
                "Classic crisp and buttery homemade cookies.", "🍪"));
        products.add(new Product(10, "Cinnamon Roll", "Pastry", 120,
                "Soft cinnamon roll finished with a sweet glaze.", "🥐"));
        products.add(new Product(11, "Fruit Tart", "Pastry", 220,
                "Buttery tart filled with cream and fresh fruit.", "🥧"));
        products.add(new Product(12, "Brownie Box", "Gift Box", 450,
                "A gift box containing six assorted brownies.", "🎁"));
    }

    public List<Product> getProducts() { return products; }
    public List<User> getUsers() { return users; }
    public List<Order> getOrders() { return orders; }

    public User authenticate(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)
                    && user.passwordMatches(password)) {
                return user;
            }
        }
        return null;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}
