package GUI.Product;

import BUS.ChiTietHoaDon_BUS;
import BUS.TaoHoaDon;
import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import java.util.Arrays;

public class SellingForm extends JPanel {
    JTextField sdtKH, tenKH, timTxt, diaChiKH;
    JButton timBtn, xoaGioBtn;
    JLabel lblTotalValue;

    // Dữ liệu cho 2 bảng
    Vector<SanPham_DTO> danhSachKhoHang = new Vector<>();
    Vector<ChiTietHoaDon_BUS> gioHang = new Vector<>();

    // CÁC BIẾN CHO CARDLAYOUT
    CardLayout cardLayout;
    JPanel cardPanel;
    public static final String CARD_BAN_HANG = "ManHinhBanHang";
    public static final String CARD_THANH_TOAN = "ManHinhThanhToan";

    HoaDon_DTO hoaDon;

    public SellingForm() {
        setLayout(new BorderLayout());
        cardLayout = new CardLayout();

        JPanel panelBanHang = new JPanel(new BorderLayout(10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm hiện có"));

        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
        searchPanel.add(new JLabel("Tìm kiếm (Tên/Mã):"), BorderLayout.WEST);
        timTxt = new JTextField();
        searchPanel.add(timTxt, BorderLayout.CENTER);
        timBtn = new JButton("Tìm");
        searchPanel.add(timBtn, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        Vector<String> columnsLeft = new Vector<>(Arrays.asList("Ảnh SP", "Mã SP", "Tên SP", "Đơn giá", "Tồn kho"));
        DefaultTableModel modelLeft = new DefaultTableModel(renderKhoHang(danhSachKhoHang), columnsLeft) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Icon.class;
                return super.getColumnClass(columnIndex);
            }
        };
        JTable tableLeft = new JTable(modelLeft);
        capNhatKichThuocBang(tableLeft);
        leftPanel.add(new JScrollPane(tableLeft), BorderLayout.CENTER);

        // ---------------------------------------------------------
        // KHOẢNG PHẢI: GIỎ HÀNG VÀ THANH TOÁN
        // ---------------------------------------------------------
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

        // --- A. THÔNG TIN KHÁCH HÀNG (Phía trên cùng) ---
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 5, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
        infoPanel.add(new JLabel("Số điện thoại KH:"));
        sdtKH = new JTextField();
        infoPanel.add(sdtKH);
        infoPanel.add(new JLabel("Tên KH:"));
        tenKH = new JTextField();
        infoPanel.add(tenKH);
        infoPanel.add(new JLabel("Địa chỉ KH:"));
        diaChiKH = new JTextField();
        infoPanel.add(diaChiKH);
        rightPanel.add(infoPanel, BorderLayout.NORTH);

        // --- B. BẢNG GIỎ HÀNG VÀ NÚT XÓA GIỎ (Nằm ở giữa) ---

        JPanel tableWrapperPanel = new JPanel(new BorderLayout(0, 5)); // Panel bọc bảng và nút xóa

        Vector<String> columnsRight = new Vector<>(Arrays.asList("Ảnh", "Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"));
        DefaultTableModel modelRight = new DefaultTableModel(renderGioHang(gioHang), columnsRight) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Icon.class;
                return super.getColumnClass(columnIndex);
            }
        };
        JTable tableRight = new JTable(modelRight);
        capNhatKichThuocBang(tableRight);

        JScrollPane scrollPaneRight = new JScrollPane(tableRight);
        scrollPaneRight.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm:"));
        tableWrapperPanel.add(scrollPaneRight, BorderLayout.CENTER); // Đưa bảng vào giữa

        // Nút Xóa giỏ nằm ngay dưới đuôi bảng
        JPanel pnlClearCart = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        pnlClearCart.setBorder(BorderFactory.createEmptyBorder(5, 0, 15, 0));
        xoaGioBtn = new JButton("Xóa giỏ");
        xoaGioBtn.setPreferredSize(new Dimension(100, 35));
        xoaGioBtn.setBackground(new Color(220, 53, 69));
        xoaGioBtn.setForeground(Color.WHITE);
        pnlClearCart.add(xoaGioBtn);

        tableWrapperPanel.add(pnlClearCart, BorderLayout.SOUTH);

        rightPanel.add(tableWrapperPanel, BorderLayout.CENTER);


        JPanel checkoutPanel = new JPanel(new BorderLayout(0, 10));
        checkoutPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)); // Kẻ 1 đường line mỏng phân cách
        checkoutPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 5, 5, 5)
        ));


        JPanel pnlPrices = new JPanel(new GridLayout(2, 2, 10, 5));

        JLabel kmLbl = new JLabel("Khuyến mãi giảm: ", SwingConstants.RIGHT);
        kmLbl.setFont(new Font("Arial", Font.PLAIN, 14));
        JLabel lblKhuyenMaiValue = new JLabel("0 đ", SwingConstants.RIGHT);
        lblKhuyenMaiValue.setFont(new Font("Arial", Font.BOLD, 14));
        lblKhuyenMaiValue.setForeground(Color.RED);

        JLabel ttLbl = new JLabel("Tổng thanh toán: ", SwingConstants.RIGHT);
        ttLbl.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalValue = new JLabel("0 đ", SwingConstants.RIGHT);
        lblTotalValue.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalValue.setForeground(Color.RED);

        pnlPrices.add(kmLbl);
        pnlPrices.add(lblKhuyenMaiValue);
        pnlPrices.add(ttLbl);
        pnlPrices.add(lblTotalValue);

        checkoutPanel.add(pnlPrices, BorderLayout.CENTER);


        JButton btnCheckout = new JButton("THANH TOÁN");
        btnCheckout.setPreferredSize(new Dimension(0, 55));
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 18));
        btnCheckout.setBackground(new Color(40, 167, 69));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.addActionListener(e -> chuyenManHinh(CARD_THANH_TOAN));

        checkoutPanel.add(btnCheckout, BorderLayout.SOUTH);

        rightPanel.add(checkoutPanel, BorderLayout.SOUTH);


        timBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JOptionPane.showMessageDialog(null, "Bạn vừa click vào nút tìm kiếm! " + timTxt.getText());
                }
            }
        });

        tableRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int row = tableRight.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < gioHang.size()) {
                        gioHang.remove(row);
                        modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                        capNhatKichThuocBang(tableRight);
                        tinhTongTien();
                    }
                }
            }
        });

        tableLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int row = tableLeft.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < tableLeft.getRowCount()) {
                        tableLeft.setRowSelectionInterval(row, row);
                        SanPham_DTO spDuocChon = danhSachKhoHang.get(row);
                        BUS.TaoHoaDon.tinhTien(gioHang, spDuocChon, 1);
                        modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                        capNhatKichThuocBang(tableRight);
                        tinhTongTien();
                    }
                }
            }
        });

        modelRight.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                    int row = e.getFirstRow();
                    try {
                        int soLuongMoi = Integer.parseInt(modelRight.getValueAt(row, 3).toString());
                        if (soLuongMoi <= 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                            modelRight.setValueAt(1, row, 3);
                            return;
                        }
                        ChiTietHoaDon_BUS cthd = gioHang.get(row);
                        cthd.setSoLuongMua(soLuongMoi);
                        cthd.setThanhTien(soLuongMoi * cthd.getDonGia());
                        modelRight.setValueAt(cthd.getThanhTien(), row, 5);
                        tinhTongTien();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên hợp lệ!");
                        modelRight.setValueAt(1, row, 3);
                    }
                }
            }
        });

        xoaGioBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gioHang.clear();
                modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                try { TaoHoaDon.tinhTien(gioHang, null, 0); } catch (Exception ex) { }
                tinhTongTien();
                JOptionPane.showMessageDialog(null, "Giỏ hàng đã được làm mới!");
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.55);

        panelBanHang.add(splitPane, BorderLayout.CENTER);
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(panelBanHang, CARD_BAN_HANG);
        cardPanel.add(new ThanhToanGUI(this), CARD_THANH_TOAN);



        add(cardPanel, BorderLayout.CENTER);
    }

    // =====================================================================
    // CÁC HÀM HỖ TRỢ BÊN DƯỚI (GIỮ NGUYÊN)
    // =====================================================================
    public void chuyenManHinh(String tenManHinh) {
        cardLayout.show(cardPanel, tenManHinh);
    }

    private void tinhTongTien() {
        double tong = 0;
        for (ChiTietHoaDon_BUS cthd : gioHang) {
            tong += cthd.getThanhTien();
        }
        lblTotalValue.setText(String.format("%,.0f đ", tong));
    }

    public void capNhatKichThuocBang(JTable table) {
        int maxColumnWidth = 80;
        for (int row = 0; row < table.getRowCount(); row++) {
            Object value = table.getValueAt(row, 0);
            if (value instanceof Icon) {
                Icon icon = (Icon) value;
                table.setRowHeight(row, icon.getIconHeight() + 10);
                if (icon.getIconWidth() > maxColumnWidth) maxColumnWidth = icon.getIconWidth();
            } else {
                table.setRowHeight(row, 60);
            }
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(maxColumnWidth + 10);
        if (table.getColumnCount() >= 6) {
            table.getColumnModel().getColumn(3).setPreferredWidth(40);
        }
    }

    private Vector<Vector<Object>> renderKhoHang(Vector<SanPham_DTO> danhSach) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (danhSach == null) return duLieuBang;
        for (SanPham_DTO sp : danhSach) {
            Vector<Object> hang = new Vector<>();
            hang.add(loadAnh(sp.getPath()));
            hang.add(sp.getMaSP());
            hang.add(sp.getTenSP());
            hang.add(sp.getGiaBan());
            hang.add(sp.getSoLuongTon());
            duLieuBang.add(hang);
        }
        return duLieuBang;
    }

    private Vector<Vector<Object>> renderGioHang(Vector<ChiTietHoaDon_BUS> gioHangList) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (gioHangList == null) return duLieuBang;
        for (ChiTietHoaDon_BUS cthd : gioHangList) {
            SanPham_DTO sp = cthd.getSanPham();
            if (sp == null) continue;
            Vector<Object> hang = new Vector<>();
            hang.add(loadAnh(sp.getPath()));
            hang.add(sp.getMaSP());
            hang.add(sp.getTenSP());
            hang.add(cthd.getSoLuongMua());
            hang.add(cthd.getDonGia());
            hang.add(cthd.getThanhTien());
            duLieuBang.add(hang);
        }
        return duLieuBang;
    }

    private Object loadAnh(String path) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                int maxH = 80;
                int imgW = icon.getIconWidth();
                int imgH = icon.getIconHeight();
                int newW = (imgW * maxH) / imgH;
                Image scaledImg = icon.getImage().getScaledInstance(newW, maxH, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        } catch (Exception e) {}
        return "No Image";
    }
}
