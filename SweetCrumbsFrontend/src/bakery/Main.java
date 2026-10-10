package bakery;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BakeryApp().setVisible(true));
    }
}
