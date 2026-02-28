package GUI.SanPham_GUI;

import BUS.SanPham_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;

public class SuaSP_GUI extends JDialog {

    private SanPham_BUS bus;
    private SanPham_DTO sp;
    private boolean success = false;

    private JTextField txtTenSP, txtGiaBan, txtSoLuong;

    public SuaSP_GUI(Frame owner, SanPham_BUS bus, SanPham_DTO sp) {
        super(owner, "Sửa sản phẩm " + sp.getMaSP(), true);
        this.bus = bus;
        this.sp = sp;

        setSize(450, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTenSP   = new JTextField(sp.getTenSP(), 25);
        txtGiaBan  = new JTextField(String.valueOf(sp.getGiaBan()), 15);
        txtSoLuong = new JTextField(String.valueOf(sp.getSoLuongTon()), 10);

        formPanel.add(new JLabel("Tên SP:"));    formPanel.add(txtTenSP);
        formPanel.add(new JLabel("Giá bán:"));   formPanel.add(txtGiaBan);
        formPanel.add(new JLabel("Tồn kho:"));   formPanel.add(txtSoLuong);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu thay đổi");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> luuSua());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnLuu);
    }

    private void luuSua() {
        try {
            sp.setTenSP(txtTenSP.getText().trim());
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
            sp.setSoLuongTon(Integer.parseInt(txtSoLuong.getText().trim()));

            if (bus.updateSanPham(sp)) {
                success = true;
                JOptionPane.showMessageDialog(this, "Sửa thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá bán và tồn kho phải là số!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}