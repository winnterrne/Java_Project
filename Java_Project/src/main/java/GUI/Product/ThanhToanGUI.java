package GUI.Product;

import BUS.HoaDon_BUS;
import BUS.SanPham_BUS;
import DTO.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class ThanhToanGUI extends JPanel {

    private JLabel lblTenKH, lblSdt, lblDiaChi, lblMaHoaDon, lblTongTienHang, lblKhuyenMai, lblPhaiTra, lblNgayLapHD;
    private JTable tblSanPham;
    private DefaultTableModel modelSanPham;
    private JScrollPane scrollTable;

    private KhachHang_DTO kh;
    private HoaDon_DTO hoaDon;
    private HoaDon_BUS hoaDonBus = new HoaDon_BUS();
    private SanPham_BUS spBus = new SanPham_BUS();
    private BanHang_GUI main;

    ArrayList<ChiTietHoaDon_DTO> dsCTHD;

    private double tongTienHangVal = 0, phaiTraVal = 0, khuyenMaiVal = 0;
    private Vector<String> columnsName = new Vector<>(Arrays.asList("STT", "Tên SP", "Số lượng", "Đơn giá", "Khuyến mãi", "Thành tiền"));

    public ThanhToanGUI(BanHang_GUI mainPanel) {
        this.main = mainPanel;
        setLayout(new BorderLayout(10, 10));

        Font fontValue = new Font("Arial", Font.BOLD, 18);
        Font fontLabel = new Font("Arial", Font.BOLD, 15);
        Color colorText = new Color(0, 51, 102);
        Color colorDiscount = new Color(0, 153, 51);

        lblTenKH = new JLabel("---"); lblTenKH.setFont(fontValue); lblTenKH.setForeground(colorText);
        lblSdt = new JLabel("---"); lblSdt.setFont(fontValue); lblSdt.setForeground(colorText);
        lblDiaChi = new JLabel("---"); lblDiaChi.setFont(fontValue); lblDiaChi.setForeground(colorText);
        lblMaHoaDon = new JLabel("---"); lblMaHoaDon.setFont(fontValue); lblMaHoaDon.setForeground(colorText);
        lblNgayLapHD = new JLabel("---"); lblNgayLapHD.setFont(fontValue);

        lblTongTienHang = new JLabel("0 đ"); lblTongTienHang.setFont(fontValue); lblTongTienHang.setForeground(colorText);
        lblKhuyenMai = new JLabel("0 đ"); lblKhuyenMai.setFont(fontValue); lblKhuyenMai.setForeground(colorDiscount);
        lblPhaiTra = new JLabel("0 đ"); lblPhaiTra.setFont(new Font("Arial", Font.BOLD, 22)); lblPhaiTra.setForeground(Color.RED);

        modelSanPham = new DefaultTableModel(new Vector<>(), columnsName) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblSanPham = new JTable(modelSanPham);
        tblSanPham.getTableHeader().setReorderingAllowed(false);
        tblSanPham.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        scrollTable = new JScrollPane(tblSanPham);

        canChinhDoRongCot();
        capNhatKichThuocBang(tblSanPham);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        JPanel pnlKhachHang = new JPanel(new GridLayout(3, 2, 5, 5));
        pnlKhachHang.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));
        pnlKhachHang.add(new JLabel("Tên khách hàng:") {{ setFont(fontLabel); }}); pnlKhachHang.add(lblTenKH);
        pnlKhachHang.add(new JLabel("Số điện thoại:") {{ setFont(fontLabel); }}); pnlKhachHang.add(lblSdt);
        pnlKhachHang.add(new JLabel("Địa chỉ:") {{ setFont(fontLabel); }}); pnlKhachHang.add(lblDiaChi);

        JPanel pnlHoaDon = new JPanel(new BorderLayout(5, 5));
        pnlHoaDon.setBorder(BorderFactory.createTitledBorder("Thông thư hóa đơn"));
        JPanel pnlMaHD = new JPanel();
        pnlMaHD.setLayout(new BoxLayout(pnlMaHD, BoxLayout.Y_AXIS));

        JPanel pnlDong1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        pnlDong1.add(new JLabel("Mã hóa đơn:") {{ setFont(fontLabel); }}); pnlDong1.add(lblMaHoaDon);
        JPanel pnlDong2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        pnlDong2.add(new JLabel("Ngày lập:") {{ setFont(fontLabel); }}); pnlDong2.add(lblNgayLapHD);

        pnlMaHD.add(pnlDong1); pnlMaHD.add(pnlDong2);
        pnlHoaDon.add(pnlMaHD, BorderLayout.NORTH);
        pnlHoaDon.add(scrollTable, BorderLayout.CENTER);

        JPanel pnlTongTien = new JPanel(new GridLayout(3, 2, 5, 8));
        pnlTongTien.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        pnlTongTien.add(new JLabel("Tổng tiền hàng:") {{ setFont(fontLabel); }}); pnlTongTien.add(lblTongTienHang);
        pnlTongTien.add(new JLabel("Khuyến mãi:") {{ setFont(fontLabel); }}); pnlTongTien.add(lblKhuyenMai);
        pnlTongTien.add(new JLabel("Khách cần trả:") {{ setFont(fontLabel); }}); pnlTongTien.add(lblPhaiTra);

        pnlHoaDon.add(pnlTongTien, BorderLayout.SOUTH);

        topPanel.add(pnlKhachHang);
        topPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        topPanel.add(pnlHoaDon);
        add(topPanel, BorderLayout.NORTH);

        JPanel pnlPhuongThuc = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        pnlPhuongThuc.setBorder(BorderFactory.createTitledBorder("Chọn phương thức thanh toán"));

        JButton btnTienMat = new JButton("TIỀN MẶT") {{ setFont(new Font("Arial", Font.BOLD, 20)); }};
        btnTienMat.setPreferredSize(new Dimension(150, 60));
        btnTienMat.setBackground(new Color(40, 167, 69));
        btnTienMat.setForeground(Color.WHITE);
        btnTienMat.addActionListener(e -> {
            if (phaiTraVal <= 0) {
                JOptionPane.showMessageDialog(this, "Chưa có sản phẩm để thanh toán!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            hienThiDialogTienMat();
        });

        JButton btnChuyenKhoan = new JButton("CHUYỂN KHOẢN") {{ setFont(new Font("Arial", Font.BOLD, 20)); }};
        btnChuyenKhoan.setPreferredSize(new Dimension(200, 60));
        btnChuyenKhoan.setBackground(new Color(0, 123, 255));
        btnChuyenKhoan.setForeground(Color.WHITE);
        btnChuyenKhoan.addActionListener(e -> {
            if (phaiTraVal <= 0) {
                JOptionPane.showMessageDialog(this, "Chưa có sản phẩm để thanh toán!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            hienThiDialogChuyenKhoan();
        });

        pnlPhuongThuc.add(btnTienMat);
        pnlPhuongThuc.add(btnChuyenKhoan);
        add(pnlPhuongThuc, BorderLayout.CENTER);

        JPanel pnlHuy = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnHuy = new JButton("Hủy bỏ thanh toán");
        btnHuy.setContentAreaFilled(false);
        btnHuy.setBorderPainted(false);
        btnHuy.setFont(new Font("Arial", Font.BOLD, 14));
        btnHuy.setForeground(Color.GRAY);
        btnHuy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnHuy.addActionListener(e -> main.chuyenManHinh("ManHinhBanHang"));

        pnlHuy.add(btnHuy);
        add(pnlHuy, BorderLayout.SOUTH);
    }

    public void capNhatThongTin(KhachHang_DTO khachHang, ArrayList<ChiTietHoaDon_DTO> gioHang) {
        this.kh = khachHang;
        dsCTHD = new ArrayList<>(gioHang);

        // Hiển thị thông tin khách hàng hoặc để mặc định là Khách lẻ
        String ten = (kh != null && kh.getHoTenKH() != null && !kh.getHoTenKH().isEmpty()) ? kh.getHoTenKH() : "Khách lẻ";
        String sdt = (kh != null && kh.getSoDT() != null && !kh.getSoDT().isEmpty()) ? kh.getSoDT() : "Không có";
        String diaChi = (kh != null && kh.getDiaChi() != null && !kh.getDiaChi().isEmpty()) ? kh.getDiaChi() : "Không có";

        lblTenKH.setText(ten);
        lblSdt.setText(sdt);
        lblDiaChi.setText(diaChi);

        hoaDon = hoaDonBus.taoHoaDon();
        lblMaHoaDon.setText(hoaDon.getMaHD());
        LocalDate ngayLapHD = LocalDate.now();
        hoaDon.setNgayLapHD(ngayLapHD);
        lblNgayLapHD.setText(ngayLapHD.toString());

        modelSanPham.setRowCount(0);

        tongTienHangVal = 0;
        khuyenMaiVal = 0;

        if (dsCTHD != null) {
            int i = 1;
            for (ChiTietHoaDon_DTO cthd : dsCTHD) {
                SanPham_DTO sp = spBus.getSanPhamByMaSP(cthd.getMaSP());

                // Tính tiền hàng chưa giảm giá của sản phẩm hiện tại
                double tienHangCuaSP = cthd.getDonGia() * cthd.getSoLuongMua();

                modelSanPham.addRow(new Object[]{
                        i++,
                        sp.getTenSP(),
                        cthd.getSoLuongMua(),
                        String.format("%,.0f đ", cthd.getDonGia()),
                        String.format("%d %%", cthd.getKhuyenMai()), // Định dạng an toàn tránh crash app
                        String.format("%,.0f đ", cthd.getThanhTien())
                });

                tongTienHangVal += tienHangCuaSP;
                khuyenMaiVal += (tienHangCuaSP - cthd.getThanhTien());
            }
        }

        phaiTraVal = tongTienHangVal - khuyenMaiVal;
        hoaDon.setTongTien(phaiTraVal);

        lblTongTienHang.setText(String.format("%,.0f đ", tongTienHangVal));
        lblKhuyenMai.setText(String.format("%,.0f đ", khuyenMaiVal));
        lblPhaiTra.setText(String.format("%,.0f đ", phaiTraVal));

        capNhatKichThuocBang(tblSanPham);
    }

    public void canChinhDoRongCot() {
        if (tblSanPham.getColumnModel().getColumnCount() == 6) { // Kiểm tra đúng 6 cột
            tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(40);
            tblSanPham.getColumnModel().getColumn(0).setMaxWidth(40);
            tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(70);
            tblSanPham.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblSanPham.getColumnModel().getColumn(4).setPreferredWidth(80);
            tblSanPham.getColumnModel().getColumn(5).setPreferredWidth(120);

            DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
            rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
            tblSanPham.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
            tblSanPham.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
            tblSanPham.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
            tblSanPham.getColumnModel().getColumn(5).setCellRenderer(rightRenderer); // Căn phải Thành tiền

            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            tblSanPham.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        }
    }

    public void capNhatKichThuocBang(JTable table) {
        int rowHeight = 30;
        table.setRowHeight(rowHeight);
        int headerHeight = table.getTableHeader().getPreferredSize().height;
        int rowCount = table.getRowCount();
        int estimatedHeight = headerHeight + (rowCount == 0 ? (rowHeight * 2) : (rowHeight * rowCount));
        int maxHeight = 250;
        int finalHeight = Math.min(estimatedHeight, maxHeight);

        if (scrollTable != null) {
            scrollTable.setPreferredSize(new Dimension(400, finalHeight));
            scrollTable.revalidate();
            this.revalidate();
            this.repaint();
        }
    }

    private void hoanTatThanhToan(JDialog dialog) {
        boolean thanhCong = hoaDonBus.thanhToanGiaoDich(hoaDon, kh, dsCTHD, "NV01");

        if (thanhCong) {
            JOptionPane.showMessageDialog(dialog, "Thanh toán thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
            resetDuLieu();
            main.chuyenManHinh("ManHinhBanHang");
        } else {
            JOptionPane.showMessageDialog(dialog, "Đã xảy ra lỗi khi lưu hóa đơn!", "Lỗi hệ thống", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hienThiDialogTienMat() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parentWindow, "Thanh toán Tiền mặt", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 300);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(parentWindow);

        JPanel pnlCenter = new JPanel(new GridLayout(3, 2, 10, 20));
        pnlCenter.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        Font font16 = new Font("Arial", Font.BOLD, 16);

        pnlCenter.add(new JLabel("Khách cần trả:") {{ setFont(font16); }});
        pnlCenter.add(new JLabel(String.format("%,.0f đ", phaiTraVal)) {{ setFont(new Font("Arial", Font.BOLD, 18)); setForeground(Color.RED); }});
        pnlCenter.add(new JLabel("Tiền khách đưa:") {{ setFont(font16); }});

        JTextField txtTienKhachDua = new JTextField();
        txtTienKhachDua.setFont(font16);
        pnlCenter.add(txtTienKhachDua);

        pnlCenter.add(new JLabel("Tiền thừa:") {{ setFont(font16); }});
        JLabel lblTienThua = new JLabel("0 đ");
        lblTienThua.setFont(font16);
        lblTienThua.setForeground(new Color(0, 153, 51));
        pnlCenter.add(lblTienThua);

        txtTienKhachDua.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                try {
                    String input = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
                    if (input.isEmpty()) {
                        lblTienThua.setText("0 đ");
                        lblTienThua.setForeground(Color.BLACK);
                        return;
                    }
                    double tienKhachDua = Double.parseDouble(input);
                    double tienThua = tienKhachDua - phaiTraVal;

                    if (tienThua >= 0) {
                        lblTienThua.setText(String.format("%,.0f đ", tienThua));
                        lblTienThua.setForeground(new Color(0, 153, 51));
                    } else {
                        lblTienThua.setText("Khách đưa thiếu!");
                        lblTienThua.setForeground(Color.RED);
                    }
                } catch (NumberFormatException e) {
                    lblTienThua.setText("Lỗi nhập số!");
                    lblTienThua.setForeground(Color.RED);
                }
            }
        });

        JButton btnXacNhan = new JButton("XÁC NHẬN THANH TOÁN");
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 16));
        btnXacNhan.setBackground(new Color(40, 167, 69));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setPreferredSize(new Dimension(100, 50));

        btnXacNhan.addActionListener(e -> {
            try {
                String input = txtTienKhachDua.getText().replaceAll("[^0-9]", "");
                double tienKhachDua = input.isEmpty() ? 0 : Double.parseDouble(input);

                if (tienKhachDua < phaiTraVal) {
                    JOptionPane.showMessageDialog(dialog, "Khách đưa chưa đủ tiền!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                hoanTatThanhToan(dialog);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số tiền hợp lệ!");
            }
        });
        txtTienKhachDua.addActionListener(e -> btnXacNhan.doClick());
        dialog.add(pnlCenter, BorderLayout.CENTER);
        dialog.add(btnXacNhan, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void hienThiDialogChuyenKhoan() {
        Window parentWindow = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog(parentWindow, "Thanh toán Chuyển khoản", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(400, 550);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setLocationRelativeTo(parentWindow);

        JPanel pnlInfo = new JPanel(new GridLayout(3, 1, 5, 5));
        pnlInfo.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        pnlInfo.add(new JLabel("Ngân hàng: MB Bank - STK: 123456789", SwingConstants.CENTER) {{ setFont(new Font("Arial", Font.BOLD, 15)); }});
        pnlInfo.add(new JLabel("Số tiền cần chuyển: " + String.format("%,.0f đ", phaiTraVal), SwingConstants.CENTER) {{ setForeground(Color.RED); setFont(new Font("Arial", Font.BOLD, 18)); }});
        pnlInfo.add(new JLabel("Nội dung: " + lblMaHoaDon.getText(), SwingConstants.CENTER) {{ setFont(new Font("Arial", Font.BOLD, 16)); setForeground(Color.BLUE); }});

        JLabel lblQR = new JLabel("Đang tải ảnh QR...", SwingConstants.CENTER);
        try {
            java.net.URL imgUrl = getClass().getResource("/Image/qrcode_317985092_38f020b86f99a92aa2555bd8345cd937.png");
            if (imgUrl != null) {
                ImageIcon qrIcon = new ImageIcon(imgUrl);
                Image img = qrIcon.getImage().getScaledInstance(280, 280, Image.SCALE_SMOOTH);
                lblQR.setIcon(new ImageIcon(img));
                lblQR.setText("");
            } else {
                lblQR.setText("Không tìm thấy ảnh QR");
            }
        } catch (Exception ex) {
            lblQR.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            lblQR.setText("Lỗi hiển thị ảnh QR");
        }

        JButton btnXacNhan = new JButton("XÁC NHẬN ĐÃ NHẬN TIỀN");
        btnXacNhan.setPreferredSize(new Dimension(100, 50));
        btnXacNhan.setBackground(new Color(0, 123, 255));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 16));

        btnXacNhan.addActionListener(event -> hoanTatThanhToan(dialog));

        dialog.add(pnlInfo, BorderLayout.NORTH);
        dialog.add(lblQR, BorderLayout.CENTER);
        dialog.add(btnXacNhan, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    public void resetDuLieu() {
        kh = null;
        hoaDon = null;
        if (dsCTHD != null) dsCTHD.clear();
        tongTienHangVal = 0;
        phaiTraVal = 0;
        khuyenMaiVal = 0;

        lblTenKH.setText("---"); lblSdt.setText("---"); lblDiaChi.setText("---");
        lblMaHoaDon.setText("---"); lblNgayLapHD.setText("---");
        lblTongTienHang.setText("0 đ"); lblKhuyenMai.setText("0 đ"); lblPhaiTra.setText("0 đ");
        modelSanPham.setRowCount(0);
    }
}