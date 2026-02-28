package GUI.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminMenuPanel extends JPanel {
    private JButton activeButton = null;


    public AdminMenuPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 0));

        ImageIcon logo = new ImageIcon(getClass().getResource("/Image/images.jpeg"));
        Image img = logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel lbLogo = new JLabel(new ImageIcon(img));
        lbLogo.setAlignmentX(CENTER_ALIGNMENT);
        add(lbLogo);


        setBackground(new Color(0x121929));
        add(Box.createVerticalStrut(20));

        add(createMenuButton(" Quản lý sản phẩm"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản lý danh mục sản phẩm"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản lý nhân viên"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản lý khách hàng"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản lý nhập / trả hàng"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản lý hóa đơn"));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Thống kê và báo cáo"));
        add(Box.createVerticalStrut(10));

    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);

        Color normal  = new Color(0x121929);
        Color active = new Color(0xFDFDFD);

        btn.setMaximumSize(new Dimension(200, 40));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.setForeground(Color.WHITE);
        btn.setBackground(normal);

        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);


        btn.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(119, 117, 117), 1, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)));

        btn.addActionListener(e -> {
            if (activeButton != null) {
                activeButton.setBackground(normal);
                activeButton.setForeground(Color.WHITE);
            }
            btn.setBackground(active);
            btn.setForeground(new Color(0x1D4ED8));
            activeButton = btn;
        });

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(normal);
                    btn.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn != activeButton) {
                    btn.setBackground(new Color(0xAEAEAF));
                }
            }


        });

        return btn;
    }
}