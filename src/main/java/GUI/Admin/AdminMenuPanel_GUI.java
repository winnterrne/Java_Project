package GUI.Admin;
import DTO.CurrentUser;
import GUI.Product.BanHang_GUI;

import javax.security.auth.login.CredentialException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class AdminMenuPanel_GUI extends JPanel {
    private JButton activeButton = null;
    private AdminContentPanel_GUI panel;
    private JButton btnBanHang, btnQuanLySP, btnDanhMucSP, btnDanhSachHD, btnQuanLyNCC, btnPhieuNhap, btnNhapHang, btnPhieuTra, btnTraHang, btnQuanLyTK, btnDangXuat;

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

        btnBanHang = createMenuButton("Bán Hàng", AdminContentPanel_GUI.CARD_BAN_HANG);
        add(btnBanHang);
        add(Box.createVerticalStrut(10));

        btnQuanLySP = createMenuButton(" Quản Lý Sản Phẩm", AdminContentPanel_GUI.CARD_SAN_PHAM);
        add(btnQuanLySP);
        add(Box.createVerticalStrut(10));

        btnDanhMucSP = createMenuButton(" Danh Mục Sản Phẩm", AdminContentPanel_GUI.CARD_DANH_MUC_SAN_PHAM);
        add(btnDanhMucSP);
        add(Box.createVerticalStrut(10));

        btnDanhSachHD = createMenuButton("Danh Sách Hóa Đơn", AdminContentPanel_GUI.CARD_HOA_DON);
        add(btnDanhSachHD);
        add(Box.createVerticalStrut(10));

        btnQuanLyNCC = createMenuButton("Quản Lý Nhà Cung Cấp", AdminContentPanel_GUI.CARD_NHA_CUNG_CAP);
        add(btnQuanLyNCC);
        add(Box.createVerticalStrut(10));

        btnPhieuNhap = createMenuButton("Phiếu Nhập", AdminContentPanel_GUI.CARD_PHIEU_NHAP);
        add(btnPhieuNhap);
        add(Box.createVerticalStrut(10));

        btnNhapHang = createMenuButton("Nhập Hàng", AdminContentPanel_GUI.CARD_NHAP_HANG);
        add(btnNhapHang);
        add(Box.createVerticalStrut(10));

        btnPhieuTra = createMenuButton("Phiếu Trả", AdminContentPanel_GUI.CARD_PHIEU_TRA);
        add(btnPhieuTra);
        add(Box.createVerticalStrut(10));

        btnTraHang = createMenuButton("Trả Hàng", AdminContentPanel_GUI.CARD_TRA_HANG);
        add(btnTraHang);
        add(Box.createVerticalStrut(10));

        btnQuanLyTK = createMenuButton("Quản Lý Tài Khoản", AdminContentPanel_GUI.CARD_TAI_KHOAN);
        add(btnQuanLyTK);
        add(Box.createVerticalStrut(10));

        btnDangXuat = creatLogOutButton("Đăng xuất");
        add(btnDangXuat);
        add(Box.createVerticalStrut(10));


        applyPermission();
        btnBanHang.doClick();
    }

    private JButton creatLogOutButton(String text) {
        JButton btn = new JButton(text);

        Color normal = new Color(0x121929);
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

        btn.addActionListener(e -> logOut((AdminFrame_GUI) SwingUtilities.getWindowAncestor(this)));
        return btn;
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

            if(cardName.equals(AdminContentPanel_GUI.CARD_SAN_PHAM)) {
                panel.getSanPhamMainGui().refreshTable();
            }
            if(cardName.equals(AdminContentPanel_GUI.CARD_DANH_MUC_SAN_PHAM)) {
                panel.getDanhMucGui().loadDanhMuc();
            }

            if(cardName.equals(AdminContentPanel_GUI.CARD_NHAP_HANG)){
                panel.getNhapHangGUI().resetForm();
                panel.getNhapHangGUI().loadTableSanPham();
            }

            if(cardName.equals(AdminContentPanel_GUI.CARD_PHIEU_NHAP)){
                panel.getPhieuNhapGUI().loadPhieuNhap();
            }

            if(cardName.equals(AdminContentPanel_GUI.CARD_TRA_HANG)) {
                panel.getTraHangGUI().resetForm();
                panel.getTraHangGUI().loadTableChiTietPN();
            }

            if(cardName.equals(AdminContentPanel_GUI.CARD_PHIEU_TRA)) {
                panel.getPhieuTraGUI().loadPhieuTra();
            }

            if(cardName.equals(AdminContentPanel_GUI.CARD_BAN_HANG)) {
                panel.getBanHangGUI().chuyenManHinh("ManHinhBanHang");
            }
            if (cardName.equals(AdminContentPanel_GUI.CARD_HOA_DON)) {
                panel.getHoaDonGUI().refresh();
            }
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
    public void applyPermission() {
        String maVaiTro = CurrentUser.getInstance().getMaQuyen();
        boolean isAdmin = "ADMIN".equalsIgnoreCase(maVaiTro);
        boolean isNVBH = "NHANVIENBANHANG".equalsIgnoreCase(maVaiTro);
        boolean isKho = "KHO".equalsIgnoreCase(maVaiTro);
        boolean isKH = "KHACHHANG".equalsIgnoreCase(maVaiTro);

        disableALL();

        if(isAdmin) {
            enableALL();
        }
        if(isNVBH) {
            btnBanHang.setEnabled(true);
            btnDanhSachHD.setEnabled(true);
        }
        if(isKho) {
            btnPhieuNhap.setEnabled(true);
            btnNhapHang.setEnabled(true);
            btnPhieuTra.setEnabled(true);
            btnTraHang.setEnabled(true);
            btnQuanLyNCC.setEnabled(true);
        }
        if(isKH) {
            btnBanHang.setEnabled(true);
            btnDangXuat.setEnabled(true);
            btnQuanLySP.setVisible(false);
            btnDanhMucSP.setVisible(false);
            btnDanhSachHD.setVisible(false);
            btnQuanLyNCC.setVisible(false);
            btnPhieuNhap.setVisible(false);
            btnNhapHang.setVisible(false);
            btnPhieuTra.setVisible(false);
            btnTraHang.setVisible(false);
            btnQuanLyTK.setVisible(false);

        }
    }
    public void enableALL() {
        Component[] components = getComponents();
        for(Component c : components) {
            if ( c instanceof JButton) {
                ((JButton) c).setEnabled(true);
            }
        }
    }
    public void disableALL() {
        Component[] components = getComponents();
        for(Component c : components) {
            if( c instanceof  JButton) {
                ((JButton)c).setEnabled(false);
            }
        }
    }
    public void logOut(AdminFrame_GUI parent) {
        int confirm = JOptionPane.showConfirmDialog(
                parent,
                "Bạn có muốn đăng xuất không?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );
        if(confirm == JOptionPane.YES_OPTION) {
            LoginForm_GUI  loginForm = new LoginForm_GUI();
            loginForm.setLocationRelativeTo(parent);
            loginForm.setVisible(true);
            parent.dispose();
        }
    }

}