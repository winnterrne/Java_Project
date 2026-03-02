package GUI.Product;

import BUS.ChiTietHoaDon_BUS;
import BUS.HoaDon_BUS;
import DTO.KhachHang_DTO;

import javax.mail.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThanhToanGUI extends JPanel {

    // 1. KHAI BÁO CÁC LABEL THÀNH BIẾN TOÀN CỤC ĐỂ HÀM CẬP NHẬT CÓ THỂ GỌI ĐƯỢC
    private JLabel lblTenKH, lblSdt, lblDiaChi, lblMaHoaDon, lblTongTienHang, lblKhuyenMai, lblPhaiTra;

    // Biến lưu trữ lại hóa đơn hiện tại để lúc bấm "Tiền mặt" mang đi lưu Database
    private HoaDon_BUS hoaDonHienTai;

    private SellingForm main;

    public ThanhToanGUI(SellingForm mainPanel) {
        setLayout(new BorderLayout(10, 10));

        // KHỞI TẠO CÁC LABEL TRỐNG (Hoặc giá trị mặc định ban đầu)
        lblTenKH = new JLabel("...");
        lblSdt = new JLabel("...");
        lblDiaChi = new JLabel("...");
        lblMaHoaDon = new JLabel("...");
        lblTongTienHang = new JLabel("0 đ");
        lblKhuyenMai = new JLabel("- 0 đ");
        lblPhaiTra = new JLabel("0 đ");
        lblPhaiTra.setFont(new Font("Arial", Font.BOLD, 20));
        lblPhaiTra.setForeground(Color.RED);

        // =========================================================
        // 1. PANEL TRÊN CÙNG: THÔNG TIN KHÁCH HÀNG & HÓA ĐƠN
        // =========================================================
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        JPanel pnlKhachHang = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlKhachHang.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlKhachHang.add(new JLabel("Tên khách hàng:"));
        pnlKhachHang.add(lblTenKH);
        pnlKhachHang.add(new JLabel("Số điện thoại:"));
        pnlKhachHang.add(lblSdt);
        pnlKhachHang.add(new JLabel("Địa chỉ:"));
        pnlKhachHang.add(lblDiaChi);

        JPanel pnlHoaDon = new JPanel(new GridLayout(4, 2, 5, 8));
        pnlHoaDon.setBorder(BorderFactory.createTitledBorder("Thông tin hóa đơn"));
        pnlHoaDon.add(new JLabel("Mã hóa đơn:"));
        pnlHoaDon.add(lblMaHoaDon);
        pnlHoaDon.add(new JLabel("Tổng tiền hàng:"));
        pnlHoaDon.add(lblTongTienHang);
        pnlHoaDon.add(new JLabel("Khuyến mãi:"));
        pnlHoaDon.add(lblKhuyenMai);
        pnlHoaDon.add(new JLabel("Khách cần trả:"));
        pnlHoaDon.add(lblPhaiTra);

        topPanel.add(pnlKhachHang);
        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        topPanel.add(pnlHoaDon);
        add(topPanel, BorderLayout.NORTH);

        // =========================================================
        // 2. PANEL Ở GIỮA: PHƯƠNG THỨC THANH TOÁN
        // =========================================================
        JPanel pnlPhuongThuc = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlPhuongThuc.setBorder(BorderFactory.createTitledBorder("Chọn phương thức thanh toán"));

        JButton btnTienMat = new JButton("TIỀN MẶT");
        btnTienMat.setPreferredSize(new Dimension(150, 60));
        btnTienMat.setBackground(new Color(40, 167, 69));
        btnTienMat.setForeground(Color.WHITE);

        JButton btnChuyenKhoan = new JButton("CHUYỂN KHOẢN");
        btnChuyenKhoan.setPreferredSize(new Dimension(200, 60));
        btnChuyenKhoan.setBackground(new Color(0, 123, 255));
        btnChuyenKhoan.setForeground(Color.WHITE);

        pnlPhuongThuc.add(btnTienMat);
        pnlPhuongThuc.add(btnChuyenKhoan);
        add(pnlPhuongThuc, BorderLayout.CENTER);

        // =========================================================
        // PANEL DƯỚI CÙNG & SỰ KIỆN NÚT
        // =========================================================
        JPanel pnlHuy = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnHuy = new JButton("Hủy bỏ thanh toán");
        btnHuy.setContentAreaFilled(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 14));
        btnHuy.setForeground(Color.GRAY);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        main = mainPanel;
        btnHuy.addActionListener( e -> {
            main.chuyenManHinh("ManHinhBanHang");
        });
        // ---------------------------------------------------
        pnlHuy.add(btnHuy);
        add(pnlHuy, BorderLayout.SOUTH);

        // Sự kiện Hủy
//        btnHuy.addActionListener(e -> {
//            Window window = SwingUtilities.getWindowAncestor(ThanhToanGUI.this);
//            if (window instanceof SellingForm) {
//                ((SellingForm) window).chuyenManHinh(SellingForm.CARD_BAN_HANG);
//            }
//        });

        // Sự kiện Tiền mặt
//        btnTienMat.addActionListener(e -> {
//
//            if (hoaDonHienTai == null) return;
//
//            // TODO
//            JOptionPane.showMessageDialog(this, "Thanh toán thành công " + lblPhaiTra.getText());
//
//            Window window = SwingUtilities.getWindowAncestor(ThanhToanGUI.this);
//            if (window instanceof SellingForm) {
//                ((SellingForm) window).chuyenManHinh(SellingForm.CARD_BAN_HANG);
//            }
//        });
    }

    // =========================================================
    // HÀM CẬP NHẬT THÔNG TIN
    // =========================================================
    public void capNhatThongTin(HoaDon_BUS hoaDon) {
        this.hoaDonHienTai = hoaDon;

        KhachHang_DTO kh = hoaDon.getKhachHang();
        String ten = (kh != null && kh.getHoTenKH() != null && !kh.getHoTenKH().isEmpty()) ? kh.getHoTenKH() : "Khách lẻ";
        String sdt = (kh != null && kh.getSoDT() != null && !kh.getSoDT().isEmpty()) ? kh.getSoDT() : "Không có";
        String diaChi = (kh != null && kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) ? kh.getDiaChi() : "Không có";

        lblTenKH.setText(ten);
        lblSdt.setText(sdt);
        lblDiaChi.setText(diaChi);

        String maHD = (hoaDon.getMaHD() != null && !hoaDon.getMaHD().isEmpty()) ? hoaDon.getMaHD() : "Hệ thống tự tạo";
        lblMaHoaDon.setText(maHD);

        double tongTien = 0;
        if (hoaDon.getSanPham() != null) {
            for (ChiTietHoaDon_BUS cthd : hoaDon.getSanPham()) {
                tongTien += cthd.getThanhTien();
            }
        }

        String tienFormat = String.format("%,.0f đ", tongTien);
        lblTongTienHang.setText(tienFormat);
        lblPhaiTra.setText(tienFormat);
    }
}