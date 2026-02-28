package GUI.SanPham_GUI;

import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;

public class ChiTietSP_GUI extends JDialog {

    public ChiTietSP_GUI(Frame owner, SanPham_DTO sp) {
        super(owner, "Chi tiết sản phẩm " + sp.getMaSP(), true);
        setSize(500, 400);
        setLocationRelativeTo(owner);

        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setMargin(new Insets(15, 15, 15, 15));

        String info = "Mã SP:          " + sp.getMaSP() + "\n" +
                      "Tên SP:         " + sp.getTenSP() + "\n" +
                      "Mô tả:          " + (sp.getMoTa() != null ? sp.getMoTa() : "Không có") + "\n" +
                      "Giá bán:        " + String.format("%,.0f ₫", sp.getGiaBan()) + "\n" +
                      "Đơn vị:         " + sp.getDonVi() + "\n" +
                      "Tồn kho:        " + sp.getSoLuongTon() + "\n" +
                      "Mã danh mục:    " + sp.getMaDM() + "\n" +
                      "Vị trí:         " + sp.getViTri();

        txtInfo.setText(info);

        JScrollPane scroll = new JScrollPane(txtInfo);
        add(scroll, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnDong);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }
}