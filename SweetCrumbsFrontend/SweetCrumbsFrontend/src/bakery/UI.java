package bakery;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class UI {
    private UI() {}

    public static JButton button(String text) {
        JButton b = new JButton(text);
        b.setFocusPainted(false);
        b.setFont(new Font("Arial", Font.BOLD, 14));
        b.setBackground(Colors.PINK);
        b.setForeground(Colors.BROWN);
        b.setBorder(new EmptyBorder(9, 18, 9, 18));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    public static JLabel heading(String text, int size) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Serif", Font.BOLD, size));
        label.setForeground(Colors.BROWN);
        return label;
    }

    public static JLabel centeredHeading(String text, int size) {
        JLabel label = heading(text, size);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public static JPanel card() {
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Colors.BORDER),
                new EmptyBorder(16, 16, 16, 16)
        ));
        return p;
    }
}
