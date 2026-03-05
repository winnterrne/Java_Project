package GUI.Product;

import BUS.HoaDon_BUS;
import BUS.KhachHang_BUS;
import BUS.SanPham_BUS;
import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import DTO.KhachHang_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;

public class BanHang_GUI extends JPanel {
    JTextField sdtKH, tenKH, timTxt, diaChiKH;
    JButton timBtn, xoaGioBtn;
    JLabel lblTotalValue;
    HoaDon_BUS hoaDonBus = new HoaDon_BUS();
    SanPham_BUS spBus = new SanPham_BUS();

    // Dữ liệu cho 2 bảng
    ArrayList<SanPham_DTO> danhSachKhoHang = spBus.layDsSanPhamConTon();
    ArrayList<ChiTietHoaDon_DTO> gioHang = new ArrayList<>();

    // Các biến Model & Cột (Đưa lên toàn cục để truy cập từ Event)
    DefaultTableModel modelLeft;
    DefaultTableModel modelRight;
    Vector<String> columnsLeft = new Vector<>(Arrays.asList("Ảnh SP", "Mã SP", "Tên SP", "Đơn giá", "Tồn kho"));
    Vector<String> columnsRight = new Vector<>(Arrays.asList("Ảnh", "Mã SP", "Tên SP", "SL", "Đơn giá", "Thành tiền"));

    // CÁC BIẾN CHO CARDLAYOUT
    CardLayout cardLayout;

    private ThanhToanGUI ttG;
    JPanel cardPanel;
    public static final String CARD_BAN_HANG = "ManHinhBanHang";
    public static final String CARD_THANH_TOAN = "ManHinhThanhToan";

    HoaDon_DTO hoaDon;

    public BanHang_GUI() {
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

        modelLeft = new DefaultTableModel(renderKhoHang(danhSachKhoHang), columnsLeft) {
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
        tableLeft.getTableHeader().setReorderingAllowed(false);
        leftPanel.add(new JScrollPane(tableLeft), BorderLayout.CENTER);

        // ---------------------------------------------------------
        // KHOẢNG PHẢI: GIỎ HÀNG VÀ THANH TOÁN
        // ---------------------------------------------------------
        JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

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

        JPanel tableWrapperPanel = new JPanel(new BorderLayout(0, 5));

        modelRight = new DefaultTableModel(renderGioHang(gioHang), columnsRight) {
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
        tableRight.getTableHeader().setReorderingAllowed(false);
        capNhatKichThuocBang(tableRight);

        JScrollPane scrollPaneRight = new JScrollPane(tableRight);
        scrollPaneRight.setBorder(BorderFactory.createTitledBorder("Danh sách sản phẩm:"));
        tableWrapperPanel.add(scrollPaneRight, BorderLayout.CENTER);

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
        checkoutPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 5, 5, 5)
        ));

        JPanel pnlPrices = new JPanel(new GridLayout(2, 2, 10, 5));

        JLabel ttLbl = new JLabel("Tổng thanh toán: ", SwingConstants.RIGHT);
        ttLbl.setFont(new Font("Arial", Font.BOLD, 16));
        String totalCost = Double.toString(hoaDonBus.tinhTien(gioHang));
        lblTotalValue = new JLabel(totalCost, SwingConstants.RIGHT);
        lblTotalValue.setFont(new Font("Arial", Font.BOLD, 18));
        lblTotalValue.setForeground(Color.RED);

        pnlPrices.add(ttLbl);
        pnlPrices.add(lblTotalValue);

        checkoutPanel.add(pnlPrices, BorderLayout.CENTER);

        JButton btnCheckout = new JButton("THANH TOÁN");
        btnCheckout.setPreferredSize(new Dimension(0, 55));
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 18));
        btnCheckout.setBackground(new Color(40, 167, 69));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.addActionListener(e -> {
            chuyenManHinh(CARD_THANH_TOAN);
            }
        );

        checkoutPanel.add(btnCheckout, BorderLayout.SOUTH);
        rightPanel.add(checkoutPanel, BorderLayout.SOUTH);

        // --- CÁC SỰ KIỆN LẮNG NGHE (LISTENERS) ---

        timBtn.addActionListener(e -> {
            // Lấy chữ và tự động cắt khoảng trắng dư thừa ở hai đầu
            String text = timTxt.getText().trim();

            // Nếu ô tìm kiếm trống, tải lại toàn bộ danh sách sản phẩm
            if (text.isEmpty()) {
                danhSachKhoHang.clear();
                ArrayList<SanPham_DTO> tatCaSP = spBus.getAllSanPham();
                if (tatCaSP != null) {
                    danhSachKhoHang.addAll(tatCaSP);
                }
            } else {
                // Có nhập chữ -> Tiến hành tìm kiếm
                ArrayList<SanPham_DTO> dsSanPham = spBus.timSanPhamTheoTen(text);
                SanPham_DTO sanPham2 = spBus.getSanPhamByMaSP(text);

                danhSachKhoHang.clear();

                // Ưu tiên: Nếu gõ đúng mã SP thì hiện 1 cái đó thôi
                if (sanPham2 != null) {
                    danhSachKhoHang.add(sanPham2);
                }
                // Nếu gõ không ra mã SP, thì dò theo tên
                else if (dsSanPham != null && !dsSanPham.isEmpty()) {
                    danhSachKhoHang.addAll(dsSanPham);
                }
                // Báo lỗi cho người dùng biết nếu không tìm thấy gì
                else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm nào khớp với: " + text, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                }
            }

            // Cập nhật lại giao diện bảng Kho hàng
            modelLeft.setDataVector(renderKhoHang(danhSachKhoHang), columnsLeft);
            capNhatKichThuocBang(tableLeft);
        });

        tableLeft.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int row = tableLeft.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < tableLeft.getRowCount()) {
                        tableLeft.setRowSelectionInterval(row, row);

                        // Lấy sản phẩm từ danh sách đang hiển thị thay vì chọc xuống bảng
                        SanPham_DTO spDuocChon = danhSachKhoHang.get(row);

                        // Gọi hàm thêm vào giỏ đã viết ở BUS
                        hoaDonBus.themVaoGioHang(gioHang, spDuocChon, 1);

                        // Cập nhật GUI
                        modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                        updateTongTien(Double.toString(hoaDonBus.tinhTien(gioHang)));
                        capNhatKichThuocBang(tableRight);
                    }
                }
            }
        });

        tableRight.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int row = tableRight.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < gioHang.size()) {
                        String maSPCanXoa = gioHang.get(row).getMaSP();

                        // Xóa sản phẩm khỏi mảng giỏ hàng
                        gioHang.removeIf(cthd -> cthd.getMaSP().equals(maSPCanXoa));

                        // Cập nhật GUI
                        modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                        updateTongTien(Double.toString(hoaDonBus.tinhTien(gioHang)));
                        capNhatKichThuocBang(tableRight);
                    }
                }
            }
        });

        modelRight.addTableModelListener(new TableModelListener() {
            boolean isUpdating = false; // Cờ chặn vòng lặp vô hạn

            @Override
            public void tableChanged(TableModelEvent e) {
                if (isUpdating) return;

                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 3) {
                    int row = e.getFirstRow();
                    try {
                        isUpdating = true; // Bật cờ khóa
                        int soLuongMoi = Integer.parseInt(modelRight.getValueAt(row, 3).toString());
                        ChiTietHoaDon_DTO cthd = gioHang.get(row);

                        if (soLuongMoi <= 0) {
                            JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0!");
                            modelRight.setValueAt(cthd.getSoLuongMua(), row, 3); // Trả lại số cũ
                        } else {
                            // Tính toán lại khoảng chênh lệch và cập nhật
                            int chenhLech = soLuongMoi - cthd.getSoLuongMua();
                            SanPham_DTO sanPham = spBus.getSanPhamByMaSP(cthd.getMaSP());
                            hoaDonBus.capNhatGioHang(gioHang, sanPham, chenhLech);

                            // Cập nhật GUI
                            modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                            updateTongTien(Double.toString(hoaDonBus.tinhTien(gioHang)));
                            capNhatKichThuocBang(tableRight);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Vui lòng nhập số nguyên hợp lệ!");
                        modelRight.setValueAt(gioHang.get(row).getSoLuongMua(), row, 3);
                    } finally {
                        isUpdating = false; // Tắt cờ mở khóa
                    }
                }
            }
        });

        // Đã thay đổi từ MouseListener sang ActionListener chuẩn xác hơn
        xoaGioBtn.addActionListener(e -> {
            // 1. Báo lỗi nếu giỏ hàng đã trống để người dùng không bị hoang mang
            if (gioHang.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Giỏ hàng hiện đang trống!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // 2. Hỏi xác nhận
            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn xóa toàn bộ giỏ hàng?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Làm sạch dữ liệu mảng
                gioHang.clear();

                // Cập nhật lại giao diện
                modelRight.setDataVector(renderGioHang(gioHang), columnsRight);
                updateTongTien("0.0");
                capNhatKichThuocBang(tableRight);

                JOptionPane.showMessageDialog(null, "Giỏ hàng đã được làm mới!");
            }
        });

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.55);

        panelBanHang.add(splitPane, BorderLayout.CENTER);
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(panelBanHang, CARD_BAN_HANG);
        ttG = new ThanhToanGUI(this);
        cardPanel.add(ttG, CARD_THANH_TOAN);

        add(cardPanel, BorderLayout.CENTER);
    }

    // =====================================================================
    // CÁC HÀM HỖ TRỢ BÊN DƯỚI
    // =====================================================================
    public void chuyenManHinh(String tenManHinh) {
        //sdtKH, tenKH, timTxt, diaChiKH
        if (tenManHinh.equals(CARD_THANH_TOAN)) {
            String sdtValue = sdtKH.getText();
            if (sdtValue == null || sdtValue.equals("")) {
                JOptionPane.showMessageDialog(null,"Thieeus sdtKH","Canh Bao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String tenKHVal = tenKH.getText();
            if (tenKHVal == null || tenKHVal.equals("")) {
                JOptionPane.showMessageDialog(null,"Thieeus tenKH","Canh Bao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String diaChiVal = diaChiKH.getText();
            if (diaChiVal == null || diaChiVal.equals("")) {
                JOptionPane.showMessageDialog(null,"Thieeus diaChiKH","Canh Bao", JOptionPane.WARNING_MESSAGE);
                return;
            }
            KhachHang_DTO khachHang = new KhachHang_DTO();
            KhachHang_BUS kbBus = new KhachHang_BUS();
            khachHang.setMaKH(kbBus.layMaKHmoiNhat());
            khachHang.setHoTenKH(tenKHVal);
            khachHang.setDiaChi(diaChiVal);
            khachHang.setSoDT(sdtValue);
            ttG.capNhatThongTin(khachHang, gioHang);
        }
        cardLayout.show(cardPanel, tenManHinh);
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

    private Vector<Vector<Object>> renderKhoHang(ArrayList<SanPham_DTO> danhSach) {
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

    private Vector<Vector<Object>> renderGioHang(ArrayList<ChiTietHoaDon_DTO> gioHangList) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (gioHangList == null || gioHangList.isEmpty()) {
            return duLieuBang;
        }

        for (ChiTietHoaDon_DTO cthd : gioHangList) {
            Vector<Object> hang = new Vector<>();

            // Tối ưu: Lấy 1 lần thay vì 2 lần để giảm query Database
            SanPham_DTO sanPhamGoc = spBus.getSanPhamByMaSP(cthd.getMaSP());

            if (sanPhamGoc != null) {
                hang.add(loadAnh(sanPhamGoc.getPath()));
                hang.add(cthd.getMaSP());
                hang.add(sanPhamGoc.getTenSP());
                hang.add(cthd.getSoLuongMua());
                hang.add(cthd.getDonGia());
                hang.add(cthd.getThanhTien());
                duLieuBang.add(hang);
            }
        }
        return duLieuBang;
    }

    private Object loadAnh(String path) {
        try {
            if(path != null && !path.isEmpty()) {
                java.net.URL imgURL = getClass().getResource("/Image/images.jpeg"); // Điều chỉnh lại thư mục chứa ảnh nếu cần
                if (imgURL != null) {
                    ImageIcon icon = new ImageIcon(imgURL);
                    int maxH = 80;
                    int imgW = icon.getIconWidth();
                    int imgH = icon.getIconHeight();
                    int newW = (imgW * maxH) / imgH;
                    Image scaledImg = icon.getImage().getScaledInstance(newW, maxH, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi tải ảnh: " + path);
        }
        return "No Image";
    }

    public void updateTongTien(String text) {
        lblTotalValue.setText(text);
    }
}