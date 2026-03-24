package School_Management_System.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Theme {

    // Colors
    public static final Color PRIMARY = new Color(52, 152, 219);
    public static final Color PRIMARY_DARK = new Color(41, 128, 185);
    public static final Color BACKGROUND = new Color(245, 247, 250);
    public static final Color SIDEBAR = new Color(44, 62, 80);
    public static final Color TEXT_LIGHT = Color.WHITE;

    // Fonts
    public static final Font TITLE_FONT = new Font("SansSerif", Font.BOLD, 22);
    public static final Font NORMAL_FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    // Apply style to a button
    public static void styleButton(JButton button) {

        button.setFont(BUTTON_FONT);

        button.setBackground(PRIMARY);
        button.setForeground(TEXT_LIGHT);

        button.setFocusPainted(false);
        button.setBorderPainted(false);

        button.setContentAreaFilled(false);  // ❗ disable default painting
        button.setOpaque(true);              // ❗ allow custom background

        button.setBorder(BorderFactory.createEmptyBorder(10,25,10,25));
    }

    // Add hover effect
    public static void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(PRIMARY_DARK); }
            public void mouseExited(MouseEvent e) { button.setBackground(PRIMARY); }
        });
    }
}