package GUI.SanPham_GUI;

import BUS.SanPham_BUS;

import javax.swing.*;
import java.awt.*;

public class XoaSP_GUI extends JDialog {

    private SanPham_BUS bus;
    private String maSP;
    private boolean success = false;

    public XoaSP_GUI(Frame owner, SanPham_BUS bus, String maSP) {
        super(owner, "Xác nhận xóa sản phẩm", true);
        this.bus = bus;
        this.maSP = maSP;

        setSize(450, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JLabel lblMessage = new JLabel("Bạn có chắc chắn muốn xóa sản phẩm mã: " + maSP + " ?");
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessage.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnXacNhan = new JButton("Xác nhận xóa");
        JButton btnHuy = new JButton("Hủy");

        btnXacNhan.setBackground(new Color(220, 53, 69));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);

        btnXacNhan.addActionListener(e -> xacNhanXoa());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnXacNhan);
        buttonPanel.add(btnHuy);

        add(lblMessage, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnHuy);
    }

    private void xacNhanXoa() {
        if (bus.deleteSanPham(maSP)) {
            success = true;
            JOptionPane.showMessageDialog(this, "Xóa sản phẩm thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Xóa thất bại! (Có thể sản phẩm đang được sử dụng hoặc lỗi hệ thống)", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}