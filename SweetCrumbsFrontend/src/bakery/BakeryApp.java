package bakery;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BakeryApp extends JFrame {
    private final AppData data = new AppData();
    private final Cart cart = new Cart();
    private User currentUser;

    private final JPanel root = new JPanel(new BorderLayout());

    public BakeryApp() {
        setTitle("Sweet Crumbs - Home Bakery");
        setSize(1180, 780);
        setMinimumSize(new Dimension(950, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        root.setBackground(Colors.CREAM);
        setContentPane(root);
        showHome();
    }

    public AppData getData() { return data; }
    public Cart getCart() { return cart; }
    public User getCurrentUser() { return currentUser; }

    private void setPage(JPanel page) {
        root.removeAll();
        root.add(createNavBar(), BorderLayout.NORTH);
        root.add(page, BorderLayout.CENTER);
        root.revalidate();
        root.repaint();
    }

    private JPanel createNavBar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Colors.CREAM);
        nav.setBorder(new EmptyBorder(14, 25, 14, 25));

        JLabel logo = new JLabel("🍰 SWEET CRUMBS");
        logo.setFont(new Font("Serif", Font.BOLD, 25));
        logo.setForeground(Colors.BROWN);

        JPanel links = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        links.setOpaque(false);

        JButton home = UI.button("Home");
        JButton menu = UI.button("Menu");
        JButton cartBtn = UI.button("🛒 Cart (" + cart.getItems().size() + ")");
        JButton orders = UI.button("My Orders");
        JButton login = UI.button(currentUser == null ? "Login" : "Logout");

        home.addActionListener(e -> showHome());
        menu.addActionListener(e -> showMenu());
        cartBtn.addActionListener(e -> showCart());
        orders.addActionListener(e -> showOrders());

        login.addActionListener(e -> {
            if (currentUser == null) showLogin();
            else {
                currentUser = null;
                showHome();
            }
        });

        links.add(home);
        links.add(menu);
        links.add(cartBtn);

        if (currentUser != null && !currentUser.isAdmin()) links.add(orders);
        if (currentUser != null && currentUser.isAdmin()) {
            JButton admin = UI.button("Admin");
            admin.addActionListener(e -> showAdmin());
            links.add(admin);
        }

        links.add(login);

        nav.add(logo, BorderLayout.WEST);
        nav.add(links, BorderLayout.EAST);
        return nav;
    }

    public void showHome() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Colors.CREAM);

        JPanel hero = new JPanel();
        hero.setBackground(Colors.CREAM);
        hero.setLayout(new BoxLayout(hero, BoxLayout.Y_AXIS));
        hero.setBorder(new EmptyBorder(55, 30, 35, 30));

        JLabel small = UI.centeredHeading("WELCOME TO", 18);
        JLabel title = UI.centeredHeading("SWEET CRUMBS", 46);
        JLabel slogan = UI.centeredHeading("Homemade with love ❤️", 25);

        JLabel text = new JLabel(
                "<html><center>Fresh cakes, brownies, cupcakes and more,<br>" +
                "baked especially for your special moments.</center></html>"
        );
        text.setFont(new Font("Arial", Font.PLAIN, 18));
        text.setForeground(Colors.LIGHT_BROWN);
        text.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton order = UI.button("ORDER NOW");
        order.setAlignmentX(Component.CENTER_ALIGNMENT);
        order.addActionListener(e -> showMenu());

        hero.add(small);
        hero.add(Box.createVerticalStrut(4));
        hero.add(title);
        hero.add(Box.createVerticalStrut(10));
        hero.add(slogan);
        hero.add(Box.createVerticalStrut(12));
        hero.add(text);
        hero.add(Box.createVerticalStrut(25));
        hero.add(order);

        JPanel specials = new JPanel(new BorderLayout());
        specials.setBackground(Colors.CREAM);
        specials.setBorder(new EmptyBorder(10, 50, 30, 50));

        specials.add(UI.centeredHeading("OUR SPECIALS", 28), BorderLayout.NORTH);

        JPanel cards = new JPanel(new GridLayout(1, 3, 18, 18));
        cards.setOpaque(false);

        Product[] featured = {data.getProducts().get(0),
                data.getProducts().get(5), data.getProducts().get(3)};

        for (Product p : featured) cards.add(createProductCard(p));

        specials.add(cards, BorderLayout.CENTER);

        JPanel about = new JPanel();
        about.setBackground(Colors.WHITE);
        about.setBorder(new EmptyBorder(25, 20, 25, 20));

        JLabel aboutText = new JLabel(
                "<html><center><b>ABOUT OUR BAKERY</b><br><br>" +
                "Sweet Crumbs is a small home bakery creating fresh, " +
                "homemade treats for birthdays, celebrations and everyday cravings." +
                "<br><br>Fresh • Homemade • Made to Order</center></html>"
        );
        aboutText.setFont(new Font("Arial", Font.PLAIN, 16));
        about.add(aboutText);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Colors.CREAM);
        content.add(hero);
        content.add(specials);
        content.add(about);

        page.add(new JScrollPane(content), BorderLayout.CENTER);
        setPage(page);
    }

    private JPanel createProductCard(Product product) {
        JPanel card = UI.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel emoji = new JLabel(product.getEmoji());
        emoji.setFont(new Font("Arial", Font.PLAIN, 55));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = new JLabel(product.getName());
        name.setFont(new Font("Arial", Font.BOLD, 17));
        name.setForeground(Colors.BROWN);
        name.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel price = new JLabel("₹" + money(product.getPrice()));
        price.setFont(new Font("Arial", Font.BOLD, 16));
        price.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton details = UI.button("VIEW DETAILS");
        details.setAlignmentX(Component.CENTER_ALIGNMENT);
        details.addActionListener(e -> showProductDetails(product));

        card.add(emoji);
        card.add(Box.createVerticalStrut(8));
        card.add(name);
        card.add(Box.createVerticalStrut(5));
        card.add(price);
        card.add(Box.createVerticalStrut(10));
        card.add(details);

        return card;
    }

    public void showMenu() {
        JPanel page = new JPanel(new BorderLayout(10, 10));
        page.setBackground(Colors.CREAM);
        page.setBorder(new EmptyBorder(20, 30, 20, 30));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JLabel title = UI.centeredHeading("OUR MENU", 34);

        JTextField search = new JTextField();
        search.setMaximumSize(new Dimension(600, 38));
        search.setBorder(BorderFactory.createTitledBorder("Search products"));

        JPanel filters = new JPanel(new FlowLayout());
        filters.setOpaque(false);

        JPanel grid = new JPanel(new GridLayout(0, 3, 18, 18));
        grid.setBackground(Colors.CREAM);
        JPanel[] gridHolder = {grid};

        String[] cats = {"All", "Cake", "Cupcake", "Brownie", "Cookie", "Pastry", "Gift Box"};
        for (String cat : cats) {
            JButton b = UI.button(cat);
            b.addActionListener(e -> updateProductGrid(gridHolder, search.getText(), cat));
            filters.add(b);
        }

        top.add(title);
        top.add(Box.createVerticalStrut(15));
        top.add(search);
        top.add(filters);

        populateGrid(grid, "", "All");

        search.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() {
                updateProductGrid(gridHolder, search.getText(), "All");
            }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });

        page.add(top, BorderLayout.NORTH);
        page.add(new JScrollPane(grid), BorderLayout.CENTER);
        setPage(page);
    }

    private void updateProductGrid(JPanel[] holder, String search, String category) {
        JPanel grid = holder[0];
        populateGrid(grid, search, category);
        grid.revalidate();
        grid.repaint();
    }

    private void populateGrid(JPanel grid, String search, String category) {
        grid.removeAll();
        String q = search == null ? "" : search.toLowerCase().trim();

        for (Product p : data.getProducts()) {
            boolean categoryMatch = category.equals("All")
                    || p.getCategory().equalsIgnoreCase(category);
            boolean searchMatch = q.isEmpty()
                    || p.getName().toLowerCase().contains(q)
                    || p.getCategory().toLowerCase().contains(q);

            if (categoryMatch && searchMatch) grid.add(createProductCard(p));
        }
    }

    private void showProductDetails(Product product) {
        JDialog dialog = new JDialog(this, product.getName(), true);
        dialog.setSize(700, 680);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(Colors.CREAM);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(25, 45, 25, 45));

        JLabel emoji = new JLabel(product.getEmoji());
        emoji.setFont(new Font("Arial", Font.PLAIN, 85));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel name = UI.centeredHeading(product.getName(), 30);

        JTextArea desc = new JTextArea(product.getDescription());
        desc.setWrapStyleWord(true);
        desc.setLineWrap(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setFont(new Font("Arial", Font.PLAIN, 16));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel price = UI.centeredHeading("₹" + money(product.getPrice()), 24);

        JTextField cakeMessage = new JTextField();
        cakeMessage.setMaximumSize(new Dimension(500, 38));
        cakeMessage.setBorder(BorderFactory.createTitledBorder(
                "Message on cake / product (optional)"));

        JTextArea instructions = new JTextArea(4, 40);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        JScrollPane instructionScroll = new JScrollPane(instructions);
        instructionScroll.setBorder(BorderFactory.createTitledBorder(
                "Special instructions for baker (optional)"));

        JPanel quantityPanel = new JPanel();
        quantityPanel.setOpaque(false);

        JLabel quantity = new JLabel("Quantity: 1");
        JButton minus = UI.button("-");
        JButton plus = UI.button("+");
        final int[] qty = {1};

        minus.addActionListener(e -> {
            if (qty[0] > 1) {
                qty[0]--;
                quantity.setText("Quantity: " + qty[0]);
            }
        });

        plus.addActionListener(e -> {
            qty[0]++;
            quantity.setText("Quantity: " + qty[0]);
        });

        quantityPanel.add(minus);
        quantityPanel.add(quantity);
        quantityPanel.add(plus);

        JButton add = UI.button("🛒 ADD TO CART");
        add.setAlignmentX(Component.CENTER_ALIGNMENT);
        add.addActionListener(e -> {
            cart.addItem(product, qty[0], cakeMessage.getText().trim(),
                    instructions.getText().trim());
            dialog.dispose();
            showMenu();
            JOptionPane.showMessageDialog(this,
                    product.getName() + " added to your cart!",
                    "Added to Cart",
                    JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(emoji);
        panel.add(Box.createVerticalStrut(8));
        panel.add(name);
        panel.add(Box.createVerticalStrut(10));
        panel.add(desc);
        panel.add(Box.createVerticalStrut(10));
        panel.add(price);
        panel.add(Box.createVerticalStrut(15));
        panel.add(cakeMessage);
        panel.add(Box.createVerticalStrut(10));
        panel.add(instructionScroll);
        panel.add(Box.createVerticalStrut(10));
        panel.add(quantityPanel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(add);

        dialog.add(new JScrollPane(panel));
        dialog.setVisible(true);
    }

    public void showCart() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Colors.CREAM);
        page.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel title = UI.centeredHeading("YOUR CART 🛒", 32);
        page.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setBackground(Colors.CREAM);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        if (cart.isEmpty()) {
            JLabel empty = UI.centeredHeading("Your cart is empty.", 22);
            JButton browse = UI.button("BROWSE MENU");
            browse.setAlignmentX(Component.CENTER_ALIGNMENT);
            browse.addActionListener(e -> showMenu());
            content.add(Box.createVerticalStrut(80));
            content.add(empty);
            content.add(Box.createVerticalStrut(20));
            content.add(browse);
        } else {
            for (CartItem item : cart.getItems()) {
                content.add(createCartItem(item));
                content.add(Box.createVerticalStrut(12));
            }

            JButton addMore = UI.button("+ ADD MORE ITEMS");
            addMore.setAlignmentX(Component.CENTER_ALIGNMENT);
            addMore.addActionListener(e -> showMenu());

            JTextArea bakerNote = new JTextArea(4, 50);
            bakerNote.setLineWrap(true);
            bakerNote.setWrapStyleWord(true);
            bakerNote.setBorder(BorderFactory.createTitledBorder(
                    "📝 Additional note to baker"));

            JLabel subtotal = UI.centeredHeading(
                    "Subtotal: ₹" + money(cart.getSubtotal()), 22);

            JButton checkout = UI.button("PROCEED TO CHECKOUT");
            checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
            checkout.addActionListener(e -> showCheckout(bakerNote.getText()));

            content.add(addMore);
            content.add(Box.createVerticalStrut(18));
            content.add(bakerNote);
            content.add(Box.createVerticalStrut(15));
            content.add(subtotal);
            content.add(Box.createVerticalStrut(15));
            content.add(checkout);
        }

        page.add(new JScrollPane(content), BorderLayout.CENTER);
        setPage(page);
    }

    private JPanel createCartItem(CartItem item) {
        JPanel row = UI.card();
        row.setLayout(new BorderLayout(15, 5));

        JLabel info = new JLabel(
                "<html><b>" + item.getProduct().getEmoji() + " "
                        + item.getProduct().getName() + "</b><br>"
                        + "₹" + money(item.getProduct().getPrice())
                        + " each<br>"
                        + "Subtotal: ₹" + money(item.getSubtotal())
                        + "</html>"
        );

        JPanel controls = new JPanel(new FlowLayout());
        JButton minus = UI.button("-");
        JButton plus = UI.button("+");
        JButton remove = UI.button("Remove");

        JLabel qty = new JLabel(" " + item.getQuantity() + " ");

        minus.addActionListener(e -> {
            item.decrease();
            showCart();
        });

        plus.addActionListener(e -> {
            item.increase();
            showCart();
        });

        remove.addActionListener(e -> {
            cart.removeItem(item);
            showCart();
        });

        controls.add(minus);
        controls.add(qty);
        controls.add(plus);
        controls.add(remove);

        String notes = "";
        if (!item.getCakeMessage().isEmpty()) {
            notes += "<br>Message: " + escape(item.getCakeMessage());
        }
        if (!item.getSpecialInstructions().isEmpty()) {
            notes += "<br>Instructions: " + escape(item.getSpecialInstructions());
        }

        if (!notes.isEmpty()) {
            info.setText("<html><b>" + item.getProduct().getEmoji() + " "
                    + item.getProduct().getName() + "</b><br>"
                    + "₹" + money(item.getProduct().getPrice())
                    + " each<br>Subtotal: ₹" + money(item.getSubtotal())
                    + notes + "</html>");
        }

        row.add(info, BorderLayout.CENTER);
        row.add(controls, BorderLayout.EAST);
        return row;
    }

    private void showCheckout(String bakerNote) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 7, 7, 7);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JTextField name = new JTextField(currentUser == null ? "" : currentUser.getName());
        JTextField phone = new JTextField(currentUser == null ? "" : currentUser.getPhone());
        JTextArea address = new JTextArea(3, 25);
        JTextField date = new JTextField("DD/MM/YYYY");

        int r = 0;
        addField(panel, c, r++, "Name *", name);
        addField(panel, c, r++, "Phone *", phone);
        addField(panel, c, r++, "Delivery Address *", new JScrollPane(address));
        addField(panel, c, r++, "Delivery Date *", date);

        JLabel total = new JLabel("Order Total: ₹" + money(cart.getSubtotal()));
        total.setFont(new Font("Arial", Font.BOLD, 17));

        c.gridx = 0; c.gridy = r; c.gridwidth = 2;
        panel.add(total, c);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Checkout", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result != JOptionPane.OK_OPTION) return;

        if (name.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty()
                || address.getText().trim().isEmpty()
                || date.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Missing Details", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Order order = new Order(
                name.getText().trim(),
                phone.getText().trim(),
                address.getText().trim(),
                date.getText().trim(),
                bakerNote == null ? "" : bakerNote.trim(),
                cart.getItems(),
                cart.getSubtotal()
        );

        data.addOrder(order);
        cart.clear();

        JOptionPane.showMessageDialog(this,
                "<html><h2>Order placed successfully! 🎉</h2>"
                        + "Order ID: <b>#" + order.getOrderId() + "</b><br>"
                        + "Total: <b>₹" + money(order.getTotal()) + "</b><br>"
                        + "Status: <b>Pending</b></html>",
                "Order Confirmed",
                JOptionPane.INFORMATION_MESSAGE);

        showOrders();
    }

    private void addField(JPanel panel, GridBagConstraints c, int row,
                          String label, Component field) {
        c.gridx = 0; c.gridy = row; c.gridwidth = 1;
        panel.add(new JLabel(label), c);
        c.gridx = 1; c.gridy = row; c.gridwidth = 1;
        panel.add(field, c);
    }

    public void showLogin() {
        JPanel page = new JPanel(new GridBagLayout());
        page.setBackground(Colors.CREAM);

        JPanel box = UI.card();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBorder(new EmptyBorder(30, 45, 30, 45));

        JLabel title = UI.centeredHeading("WELCOME BACK", 30);

        JTextField email = new JTextField();
        email.setMaximumSize(new Dimension(380, 38));
        email.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField password = new JPasswordField();
        password.setMaximumSize(new Dimension(380, 38));
        password.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton login = UI.button("LOGIN");
        login.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton register = UI.button("CREATE ACCOUNT");
        register.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel demo = new JLabel(
                "<html><center>Demo customer:<br>"
                        + "customer@sweetcrumbs.com / 1234<br><br>"
                        + "Admin:<br>admin@sweetcrumbs.com / admin</center></html>");
        demo.setAlignmentX(Component.CENTER_ALIGNMENT);

        login.addActionListener(e -> {
            User user = data.authenticate(
                    email.getText().trim(),
                    new String(password.getPassword()));

            if (user == null) {
                JOptionPane.showMessageDialog(this,
                        "Invalid email or password.",
                        "Login Failed", JOptionPane.ERROR_MESSAGE);
            } else {
                currentUser = user;
                if (user.isAdmin()) showAdmin();
                else showHome();
            }
        });

        register.addActionListener(e -> showRegister());

        box.add(title);
        box.add(Box.createVerticalStrut(20));
        box.add(email);
        box.add(Box.createVerticalStrut(10));
        box.add(password);
        box.add(Box.createVerticalStrut(18));
        box.add(login);
        box.add(Box.createVerticalStrut(8));
        box.add(register);
        box.add(Box.createVerticalStrut(20));
        box.add(demo);

        page.add(box);
        setPage(page);
    }

    private void showRegister() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));

        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();
        JPasswordField password = new JPasswordField();

        panel.add(new JLabel("Full Name:"));
        panel.add(name);
        panel.add(new JLabel("Email:"));
        panel.add(email);
        panel.add(new JLabel("Phone:"));
        panel.add(phone);
        panel.add(new JLabel("Password:"));
        panel.add(password);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Create Account",
                JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION) return;

        if (name.getText().trim().isEmpty()
                || email.getText().trim().isEmpty()
                || phone.getText().trim().isEmpty()
                || new String(password.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (data.emailExists(email.getText().trim())) {
            JOptionPane.showMessageDialog(this, "Email already exists.");
            return;
        }

        User user = new User(
                name.getText().trim(),
                email.getText().trim(),
                phone.getText().trim(),
                new String(password.getPassword()),
                false
        );

        data.addUser(user);
        currentUser = user;

        JOptionPane.showMessageDialog(this,
                "Account created successfully!");
        showHome();
    }

    public void showOrders() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Colors.CREAM);
        page.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel title = UI.centeredHeading("MY ORDERS", 32);
        page.add(title, BorderLayout.NORTH);

        JPanel list = new JPanel();
        list.setBackground(Colors.CREAM);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        boolean found = false;

        for (Order order : data.getOrders()) {
            if (currentUser == null
                    || order.getPhone().equals(currentUser.getPhone())) {
                list.add(createOrderCard(order));
                list.add(Box.createVerticalStrut(12));
                found = true;
            }
        }

        if (!found) {
            list.add(Box.createVerticalStrut(70));
            list.add(UI.centeredHeading("No orders yet.", 22));
        }

        page.add(new JScrollPane(list), BorderLayout.CENTER);
        setPage(page);
    }

    private JPanel createOrderCard(Order order) {
        JPanel card = UI.card();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel header = new JLabel(
                "Order #" + order.getOrderId()
                        + "    •    " + order.getStatus());
        header.setFont(new Font("Arial", Font.BOLD, 18));
        header.setForeground(Colors.DARK_PINK);

        card.add(header);
        card.add(Box.createVerticalStrut(8));

        for (CartItem item : order.getItems()) {
            card.add(new JLabel(
                    "• " + item.getProduct().getName()
                            + " × " + item.getQuantity()));
        }

        card.add(Box.createVerticalStrut(7));
        card.add(new JLabel("Delivery: " + order.getDeliveryDate()));
        card.add(new JLabel("Total: ₹" + money(order.getTotal())));

        return card;
    }

    public void showAdmin() {
        JPanel page = new JPanel(new BorderLayout());
        page.setBackground(Colors.CREAM);
        page.setBorder(new EmptyBorder(25, 30, 25, 30));

        JLabel title = UI.centeredHeading("ADMIN DASHBOARD", 32);
        page.add(title, BorderLayout.NORTH);

        JPanel tabs = new JPanel(new CardLayout());
        tabs.setBackground(Colors.CREAM);

        JPanel overview = createAdminOverview();
        JPanel orders = createAdminOrders();
        JPanel products = createAdminProducts();

        tabs.add(overview, "OVERVIEW");
        tabs.add(orders, "ORDERS");
        tabs.add(products, "PRODUCTS");

        JPanel buttons = new JPanel();
        JButton overviewBtn = UI.button("Overview");
        JButton ordersBtn = UI.button("Orders");
        JButton productsBtn = UI.button("Products");

        CardLayout cl = (CardLayout) tabs.getLayout();

        overviewBtn.addActionListener(e -> cl.show(tabs, "OVERVIEW"));
        ordersBtn.addActionListener(e -> cl.show(tabs, "ORDERS"));
        productsBtn.addActionListener(e -> cl.show(tabs, "PRODUCTS"));

        buttons.add(overviewBtn);
        buttons.add(ordersBtn);
        buttons.add(productsBtn);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(Colors.CREAM);
        center.add(buttons, BorderLayout.NORTH);
        center.add(tabs, BorderLayout.CENTER);

        page.add(center, BorderLayout.CENTER);
        setPage(page);
    }

    private JPanel createAdminOverview() {
        JPanel p = new JPanel(new GridLayout(1, 3, 15, 15));
        p.setBackground(Colors.CREAM);
        p.setBorder(new EmptyBorder(30, 30, 30, 30));

        p.add(adminStat("PRODUCTS", String.valueOf(data.getProducts().size())));
        p.add(adminStat("ORDERS", String.valueOf(data.getOrders().size())));
        p.add(adminStat("CUSTOMERS", String.valueOf(data.getUsers().size() - 1)));

        return p;
    }

    private JPanel adminStat(String label, String value) {
        JPanel p = UI.card();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        JLabel v = UI.centeredHeading(value, 35);
        JLabel l = UI.centeredHeading(label, 16);

        p.add(Box.createVerticalGlue());
        p.add(v);
        p.add(Box.createVerticalStrut(8));
        p.add(l);
        p.add(Box.createVerticalGlue());
        return p;
    }

    private JPanel createAdminOrders() {
        JPanel p = new JPanel();
        p.setBackground(Colors.CREAM);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        if (data.getOrders().isEmpty()) {
            p.add(Box.createVerticalStrut(40));
            p.add(UI.centeredHeading("No orders received yet.", 20));
            return p;
        }

        for (Order order : data.getOrders()) {
            JPanel card = UI.card();
            card.setLayout(new BorderLayout(10, 10));

            JTextArea info = new JTextArea();
            info.setEditable(false);
            info.setOpaque(false);

            StringBuilder s = new StringBuilder();
            s.append("Order #").append(order.getOrderId()).append("\n");
            s.append("Customer: ").append(order.getCustomerName()).append("\n");
            s.append("Phone: ").append(order.getPhone()).append("\n");
            s.append("Address: ").append(order.getAddress()).append("\n");
            s.append("Delivery: ").append(order.getDeliveryDate()).append("\n");
            s.append("Total: ₹").append(money(order.getTotal())).append("\n\n");
            s.append("Items:\n");

            for (CartItem item : order.getItems()) {
                s.append("• ").append(item.getProduct().getName())
                        .append(" × ").append(item.getQuantity()).append("\n");
            }

            if (!order.getBakerNote().isEmpty()) {
                s.append("\nBaker note: ").append(order.getBakerNote());
            }

            info.setText(s.toString());

            JComboBox<String> status = new JComboBox<>(
                    new String[]{"Pending", "Confirmed", "Preparing",
                            "Ready", "Delivered", "Cancelled"});
            status.setSelectedItem(order.getStatus());

            JButton update = UI.button("UPDATE STATUS");
            update.addActionListener(e -> {
                order.setStatus((String) status.getSelectedItem());
                JOptionPane.showMessageDialog(this, "Order status updated.");
                showAdmin();
            });

            JPanel action = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            action.add(status);
            action.add(update);

            card.add(new JScrollPane(info), BorderLayout.CENTER);
            card.add(action, BorderLayout.SOUTH);

            p.add(card);
            p.add(Box.createVerticalStrut(10));
        }

        return p;
    }

    private JPanel createAdminProducts() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Colors.CREAM);

        JPanel list = new JPanel();
        list.setBackground(Colors.CREAM);
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));

        for (Product product : data.getProducts()) {
            JPanel row = UI.card();
            row.setLayout(new BorderLayout());

            JLabel info = new JLabel(
                    product.getEmoji() + "  " + product.getName()
                            + "  |  " + product.getCategory()
                            + "  |  ₹" + money(product.getPrice()));
            row.add(info, BorderLayout.CENTER);
            list.add(row);
            list.add(Box.createVerticalStrut(8));
        }

        p.add(new JScrollPane(list), BorderLayout.CENTER);
        return p;
    }

    private String money(double value) {
        if (value == (long) value) return String.valueOf((long) value);
        return String.format("%.2f", value);
    }

    private String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
