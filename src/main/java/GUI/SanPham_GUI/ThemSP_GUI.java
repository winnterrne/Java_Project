package GUI.SanPham_GUI;

import BUS.SanPham_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;

public class ThemSP_GUI extends JDialog {

    private SanPham_BUS bus;
    private boolean success = false;

    private JTextField txtMaSP, txtTenSP, txtMoTa, txtGiaBan, txtDonVi, txtSoLuong, txtMaDM, txtViTri;

    public ThemSP_GUI(Frame owner, SanPham_BUS bus) {
        super(owner, "Thêm sản phẩm mới", true);
        this.bus = bus;

        setSize(500, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtMaSP    = new JTextField(15);
        txtTenSP   = new JTextField(25);
        txtMoTa    = new JTextField(30);
        txtGiaBan  = new JTextField(15);
        txtDonVi   = new JTextField(10);
        txtSoLuong = new JTextField(10);
        txtMaDM    = new JTextField(15);
        txtViTri   = new JTextField(15);

        formPanel.add(new JLabel("Mã SP:"));     formPanel.add(txtMaSP);
        formPanel.add(new JLabel("Tên SP:"));    formPanel.add(txtTenSP);
        formPanel.add(new JLabel("Mô tả:"));     formPanel.add(txtMoTa);
        formPanel.add(new JLabel("Giá bán:"));   formPanel.add(txtGiaBan);
        formPanel.add(new JLabel("Đơn vị:"));    formPanel.add(txtDonVi);
        formPanel.add(new JLabel("Tồn kho:"));   formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Mã DM:"));     formPanel.add(txtMaDM);
        formPanel.add(new JLabel("Vị trí:"));    formPanel.add(txtViTri);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> luuSanPham());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnLuu);
    }

    private void luuSanPham() {
        try {
            SanPham_DTO sp = new SanPham_DTO();
            sp.setMaSP(txtMaSP.getText().trim());
            sp.setTenSP(txtTenSP.getText().trim());
            sp.setMoTa(txtMoTa.getText().trim());
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
            sp.setDonVi(Double.parseDouble(txtDonVi.getText().trim()));
            sp.setSoLuongTon(Integer.parseInt(txtSoLuong.getText().trim()));
            sp.setMaDM(txtMaDM.getText().trim());
            sp.setViTri(txtViTri.getText().trim());

            if (bus.themSanPham(sp)) {
                success = true;
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! (Mã SP trùng hoặc lỗi)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá bán, đơn vị, tồn kho phải là số hợp lệ!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }
}