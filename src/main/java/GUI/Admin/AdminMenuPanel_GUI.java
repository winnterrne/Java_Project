package GUI.Admin;
//import javax.security.auth.login.CredentialException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminMenuPanel_GUI extends JPanel {
    private JButton activeButton = null;
    private AdminContentPanel_GUI panel;

    public AdminMenuPanel_GUI(AdminContentPanel_GUI contentpanel) {
        panel = contentpanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 0));

        ImageIcon logo = new ImageIcon(getClass().getResource("/Image/images.jpeg"));
        Image img = logo.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel lbLogo = new JLabel(new ImageIcon(img));
        lbLogo.setAlignmentX(CENTER_ALIGNMENT);
        add(lbLogo);


        setBackground(new Color(0x121929));
        add(Box.createVerticalStrut(20));

        add(createMenuButton("Bán Hàng",AdminContentPanel_GUI.CARD_BAN_HANG));
        add(Box.createVerticalStrut(10));

        add(createMenuButton(" Quản Lý Sản Phẩm",AdminContentPanel_GUI.CARD_SAN_PHAM));
        add(Box.createVerticalStrut(10));

        add(createMenuButton(" Danh Mục Sản Phẩm",AdminContentPanel_GUI.CARD_DANH_MUC_SAN_PHAM));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Danh Sách Hóa Đơn",AdminContentPanel_GUI.CARD_HOA_DON));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản Lý Nhà Cung Cấp",AdminContentPanel_GUI.CARD_NHA_CUNG_CAP));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Phiếu Nhập",AdminContentPanel_GUI.CARD_PHIEU_NHAP));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Nhập Hàng",AdminContentPanel_GUI.CARD_NHAP_HANG));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Quản Lý Tài Khoản",AdminContentPanel_GUI.CARD_TAI_KHOAN));
        add(Box.createVerticalStrut(10));

        add(createMenuButton("Thống kê và báo cáo",AdminContentPanel_GUI.CARD_THONG_KE));
        add(Box.createVerticalStrut(10));

    }

    private JButton createMenuButton(String text,String cardName) {
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
            panel.showManHinh(cardName);
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