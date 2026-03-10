package GUI.Product;

import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;

public class ChiTietSP_GUI extends JDialog {

    public ChiTietSP_GUI(Frame owner, SanPham_DTO sp) {
        super(owner, "Chi tiết sản phẩm " + sp.getMaSP(), true);
        setSize(550, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JTextArea txtInfo = new JTextArea();
        txtInfo.setEditable(false);
        txtInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtInfo.setLineWrap(true);
        txtInfo.setWrapStyleWord(true);
        txtInfo.setMargin(new Insets(15, 15, 15, 15));

        String info = "Mã SP:          " + sp.getMaSP() + "\n\n" +
                "Tên SP:         " + sp.getTenSP() + "\n\n" +
                "Mô tả:          " + (sp.getMoTa() != null ? sp.getMoTa() : "Không có") + "\n\n" +
                "Giá bán:        " + String.format("%,.0f ₫", sp.getGiaBan()) + "\n\n" +
                "Đơn vị:         " + sp.getDonVi() + "\n\n" +
                "Tồn kho:        " + sp.getSoLuongTon() + "\n\n" +
                "Mã danh mục:    " + sp.getMaDM() + "\n\n" +
                "Vị trí:         " + sp.getViTri() + "\n\n" +
                "Ngày sản xuất:  " + sp.getNgaySX() + "\n\n" +
                "Hạn sử dụng:    " + sp.getHanSD();

        txtInfo.setText(info);
        txtInfo.setFocusable(false);

        JScrollPane scroll = new JScrollPane(txtInfo);

        JLabel lblHinhAnh = new JLabel();
        lblHinhAnh.setHorizontalAlignment(SwingConstants.CENTER);
        lblHinhAnh.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
        lblHinhAnh.setPreferredSize(new Dimension(200, 0));

        String duongDanAnh = sp.getPath();
        ImageIcon icon = loadAnh(duongDanAnh);

        if (icon != null) {
            lblHinhAnh.setIcon(icon);
        } else {
            lblHinhAnh.setText("Không có ảnh");
        }

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlCenter.add(lblHinhAnh, BorderLayout.WEST);
        pnlCenter.add(scroll, BorderLayout.CENTER);

        add(pnlCenter, BorderLayout.CENTER);

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(btnDong);
        add(bottom, BorderLayout.SOUTH);

        setVisible(true);
    }

    private ImageIcon loadAnh(String dbPath) {
        try {
            if (dbPath == null || dbPath.trim().isEmpty()) {
                return null;
            }

            String cleanedPath = dbPath.replace("\\", "/").trim();

            String lowerPath = cleanedPath.toLowerCase();
            if (lowerPath.startsWith("image/")) {
                cleanedPath = cleanedPath.substring(6);
            }

            String resourcePath = "Image/" + cleanedPath;

            java.net.URL url = getClass().getResource("/" + resourcePath);

            if (url != null) {
                ImageIcon icon = new ImageIcon(url);
                int targetWidth = 180;
                Image scaledImg = icon.getImage().getScaledInstance(targetWidth, -1, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}