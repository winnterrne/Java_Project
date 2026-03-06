package GUI.Product;

import BUS.*;
import DTO.*;
import Utils.ExportHoaDon;
import com.toedter.calendar.JDateChooser; // Import JDateChooser từ thư viện JCalendar

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static Utils.ExportExcelHoaDon.ExportExcelHoaDon;

public class HoaDon_GUI extends JPanel {

    private JTable tableHoaDon;
    private DefaultTableModel modelHoaDon;
    private HoaDon_BUS hoaDonBus = new HoaDon_BUS();
    private ChiTietHoaDon_BUS cthdBus  = new ChiTietHoaDon_BUS();
    private SanPham_BUS  sanPhamBus = new SanPham_BUS();
    ArrayList<HoaDon_DTO> hoaDon = hoaDonBus.layTatCaHD();
    Vector<String> colChiTiet = new Vector<>(Arrays.asList("Mã SP", "Tên SP", "Số Lượng Mua", "Đơn Giá", "Thành Tiền"));

    Vector<String> columnsName = new Vector<>(Arrays.asList("Mã Hóa Đơn", "Ngày Lập", "Khách Hàng", "Nhân viên", "Tổng Tiền (VNĐ)"));

    JTextField txtTimMaHD, txtTimMaKH;

    JDateChooser dateTimNgayTu, dateTimNgayDen;

    public HoaDon_GUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0xF6F3F3));

        // ==========================================
        // 1. KHU VỰC PHÍA TRÊN (Gồm Tiêu đề + Tìm kiếm)
        // ==========================================
        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(new Color(0xF6F3F3));

        // 1.1 Tiêu đề (Title)
        JLabel lblTitle = new JLabel("DANH SÁCH HÓA ĐƠN ĐÃ THANH TOÁN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        // ==========================================
        // 1.2 Panel Tìm kiếm (Search Panel) - Đã tối ưu Layout
        // ==========================================
        JPanel pnlSearch = new JPanel(new BorderLayout());
        pnlSearch.setBackground(new Color(0xF6F3F3));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"));

        // --- Khu vực nhập liệu (Căn trái) ---
        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInput.setBackground(new Color(0xF6F3F3));

        pnlInput.add(new JLabel("Mã Hóa Đơn:"));
        txtTimMaHD = new JTextField(8); // Giảm nhẹ chiều dài để tiết kiệm không gian
        pnlInput.add(txtTimMaHD);

        pnlInput.add(new JLabel("Từ ngày:"));
        dateTimNgayTu = new JDateChooser();
        dateTimNgayTu.setDateFormatString("dd/MM/yyyy");
        dateTimNgayTu.setPreferredSize(new Dimension(110, 25));
        pnlInput.add(dateTimNgayTu);

        pnlInput.add(new JLabel("Đến:"));
        dateTimNgayDen = new JDateChooser();
        dateTimNgayDen.setDateFormatString("dd/MM/yyyy");
        dateTimNgayDen.setPreferredSize(new Dimension(110, 25));
        pnlInput.add(dateTimNgayDen);

        pnlInput.add(new JLabel("Mã KH:"));
        txtTimMaKH  = new JTextField(8);
        pnlInput.add(txtTimMaKH);

// --- Khu vực nút bấm (Căn phải) ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlButtons.setBackground(new Color(0xF6F3F3));

        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 25));

        // 1. Thêm nút Xuất Excel (Chỉ khai báo UI)
        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(100, 25));

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 25));

        // 2. Add các nút vào Panel theo thứ tự
        pnlButtons.add(btnTimKiem);
        pnlButtons.add(btnExcel); // Nút Excel nằm ở giữa
        pnlButtons.add(btnLamMoi);

        // Ghép 2 khu vực vào pnlSearch
        pnlSearch.add(pnlInput, BorderLayout.CENTER);
        pnlSearch.add(pnlButtons, BorderLayout.EAST);

        pnlTop.add(pnlSearch, BorderLayout.CENTER);
        add(BorderLayout.NORTH, pnlTop);


        // ==========================================
        // 2. KHU VỰC GIỮA (Bảng Danh sách Hóa đơn)
        // ==========================================



        modelHoaDon = new DefaultTableModel(renderHoaDon(hoaDon), columnsName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableHoaDon = new JTable(modelHoaDon);
        tableHoaDon.setRowHeight(30);
        tableHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
        tableHoaDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableHoaDon.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableHoaDon.getTableHeader().setReorderingAllowed(false);
        tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tableHoaDon);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);


        // ==========================================
        // 3. XỬ LÝ SỰ KIỆN (Events)
        // ==========================================

        // Sự kiện cho nút Làm mới (Clear form)
        btnLamMoi.addActionListener(e -> {

            // TODO: Gọi lại hàm load toàn bộ dữ liệu (không có bộ lọc) lên bảng
            refresh();
        });

        // Sự kiện cho nút Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            String maHD = txtTimMaHD.getText().trim();
            String maKH = txtTimMaKH.getText().trim();

            Date tuDate = dateTimNgayTu.getDate();
            Date denDate = dateTimNgayDen.getDate();
            LocalDate selectedDate1 = null, selectedDate2 = null;

            if (tuDate != null) {
                // Chuyển đổi java.util.Date sang java.time.LocalDate
                selectedDate1 = tuDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
            if (denDate != null) {
                selectedDate2 = denDate.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }

            // TODO: Gọi hàm lọc dữ liệu từ BUS truyền vào maHD, ngayFormat, maKH
            modelHoaDon.setDataVector(renderHoaDon(hoaDonBus.boLocTimKiemHD(maHD,maKH,selectedDate1, selectedDate2)), columnsName);
        });

        btnExcel.addActionListener(e -> {
            // 1. Thiết lập đường dẫn lưu vào src/main/resources/excel
            String projectPath = System.getProperty("user.dir");
            String relativePath = "src" + java.io.File.separator + "main" + java.io.File.separator + "resources" + java.io.File.separator + "excel";

            java.io.File excelFolder = new java.io.File(projectPath + java.io.File.separator + relativePath);
            if (!excelFolder.exists()) {
                excelFolder.mkdirs(); // Tự động tạo thư mục nếu chưa có
            }

            // 2. Tên file theo thời gian hiện tại hoặc tên cố định
            String path = excelFolder.getAbsolutePath() + java.io.File.separator + "DanhSachHoaDon.xlsx";

            // 3. Gọi hàm xuất
            ExportExcelHoaDon(tableHoaDon, path);
        });

        // Bắt sự kiện Click chuột cho Table
        tableHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int selectedRow = tableHoaDon.getSelectedRow();
                    if (selectedRow != -1) {
                        String maHD = tableHoaDon.getValueAt(selectedRow, 0).toString();
                        hienThiGiaoDienChiTiet(maHD);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = tableHoaDon.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < tableHoaDon.getRowCount()) {
                        tableHoaDon.setRowSelectionInterval(row, row);
                        String maHD = tableHoaDon.getValueAt(row, 0).toString();
                        hienThiNutSua(e, maHD);
                    }
                }
            }
        });
    }

    // --- GIAO DIỆN CỬA SỔ CHI TIẾT HÓA ĐƠN ---
    private void hienThiGiaoDienChiTiet(String maHienThi) {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        HoaDon_DTO hd = hoaDonBus.layHDTheoMaHD(maHienThi);

        JDialog dialogChiTiet = new JDialog(parentFrame, "Chi Tiết Hóa Đơn - " + hd.getMaHD(), true);
        dialogChiTiet.setSize(650, 400);
        dialogChiTiet.setLocationRelativeTo(parentFrame);
        dialogChiTiet.setLayout(new BorderLayout(10, 10));

        JPanel pnlHeader = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Thông tin hóa đơn")
        ));

        pnlHeader.add(new JLabel("Mã HD: " + hd.getMaHD()));
        pnlHeader.add(new JLabel("Ngày lập: " + hd.getNgayLapHD().toString()));
        pnlHeader.add(new JLabel("Nhân viên: " + new NhanVien_BUS().getNhanVienByMa(hd.getMaNV()).getHoTenNV()));
        pnlHeader.add(new JLabel("Khách hàng: " + new KhachHang_BUS().layKHTheoMaKH(hd.getMaKH()).getHoTenKH()));
        dialogChiTiet.add(pnlHeader, BorderLayout.NORTH);

        DefaultTableModel modelChiTiet = new DefaultTableModel(renderChiTietHoaDon(maHienThi), colChiTiet) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.getTableHeader().setReorderingAllowed(false);
        tableChiTiet.setRowHeight(25);

        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        dialogChiTiet.add(scrollChiTiet, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTongTien = new JLabel("Tổng cộng: " + hd.getTongTien());
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);
        pnlBottom.add(lblTongTien, BorderLayout.WEST);

        JButton btnDong = new JButton("Đóng");
        btnDong.setFocusPainted(false);
        btnDong.addActionListener(e -> dialogChiTiet.dispose());
        pnlBottom.add(btnDong, BorderLayout.EAST);

        dialogChiTiet.add(pnlBottom, BorderLayout.SOUTH);
        dialogChiTiet.setVisible(true);
    }

    // --- MENU CHUỘT PHẢI ---
    public void hienThiNutSua(MouseEvent e, String maHD) {
        JPopupMenu popupMenu = new JPopupMenu();

//        JMenuItem itemSua = new JMenuItem("Sửa Hóa Đơn: " + maHD);
        JMenuItem itemXoa = new JMenuItem("Xóa Hóa Đơn");
        JMenuItem itemXuatPDF  = new JMenuItem("Xuất PDF");

//        itemSua.addActionListener(event -> {
//            JOptionPane.showMessageDialog(this, "Đang mở giao diện sửa cho: " + maHD);
//        });

        itemXoa.addActionListener(event -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa " + maHD + " không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int selectedRow = tableHoaDon.getSelectedRow();
                String ma = tableHoaDon.getValueAt(selectedRow, 0).toString();
                hoaDonBus.deleteHD(hoaDonBus.layHDTheoMaHD(ma));
                modelHoaDon.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn thành công!");
            }
        });

// Sự kiện Xuất PDF (Lưu thẳng vào src/main/resources/pdf)
        itemXuatPDF.addActionListener(event -> {
            // 1. Lấy thông tin Hóa đơn, Khách hàng và Nhân viên
            HoaDon_DTO hd = hoaDonBus.layHDTheoMaHD(maHD);
            KhachHang_DTO kh = new KhachHang_BUS().layKHTheoMaKH(hd.getMaKH());
            NhanVien_DTO nv = new NhanVien_BUS().getNhanVienByMa(hd.getMaNV());

            String tenNhanVien = (nv != null) ? nv.getHoTenNV() : "Không xác định";
            String ngayLap = (hd.getNgayLapHD() != null) ? hd.getNgayLapHD().toString() : "";

            // 2. Tạo một JTable ảo chứa Chi tiết hóa đơn
            DefaultTableModel modelChiTiet = new DefaultTableModel(renderChiTietHoaDon(maHD), colChiTiet);
            JTable tableChiTiet = new JTable(modelChiTiet);

            // ==========================================
            // 3. TỰ ĐỘNG LƯU VÀO src/main/resources/pdf
            // ==========================================
            // Lấy đường dẫn gốc (root path) của dự án
            String projectPath = System.getProperty("user.dir");

            // ĐÃ SỬA: Đường dẫn khớp đúng với cấu trúc trong ảnh của bạn
            String relativePath = "src" + java.io.File.separator + "main" + java.io.File.separator + "resources" + java.io.File.separator + "pdf";

            java.io.File pdfFolder = new java.io.File(projectPath + java.io.File.separator + relativePath);

            // Tạo đường dẫn file hoàn chỉnh
            String fileName = "HoaDon_" + maHD + ".pdf";
            String path = pdfFolder.getAbsolutePath() + java.io.File.separator + fileName;

            // 4. Gọi hàm xuất PDF
            ExportHoaDon.export(
                    hd.getMaHD(),
                    kh.getHoTenKH(),
                    kh.getDiaChi(),
                    tenNhanVien,
                    ngayLap,
                    tableChiTiet,
                    hd.getTongTien(),
                    path
            );
        });

        popupMenu.add(itemXuatPDF);
        popupMenu.addSeparator();
        popupMenu.add(itemXoa);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }


    private Vector<Vector<Object>> renderHoaDon(ArrayList<HoaDon_DTO> hoaDon) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (hoaDon == null || hoaDon.isEmpty()) {
            return duLieuBang;
        }

        // TỐI ƯU: Khởi tạo BUS ở ngoài vòng lặp để tránh việc tạo mới Object liên tục gây nặng RAM
        KhachHang_BUS khBus = new KhachHang_BUS();

        for (HoaDon_DTO hd : hoaDon) {
            Vector<Object> hang = new Vector<>();

            hang.add(hd.getMaHD());
            hang.add(hd.getNgayLapHD().toString());
            hang.add(new KhachHang_BUS().layKHTheoMaKH(hd.getMaKH()).getHoTenKH());
            hang.add(new NhanVien_BUS().getNhanVienByMa(hd.getMaNV()).getHoTenNV());
            hang.add(Double.toString(hd.getTongTien()));

            // LỖI 2 ĐÃ SỬA: Bạn tạo ra biến 'hang' chứa dữ liệu, nhưng lại quên add nó vào 'duLieuBang'
            duLieuBang.add(hang);
        }
        return duLieuBang;
    }


    public Vector<Vector<Object>> renderChiTietHoaDon(String maHD) {
        ArrayList<ChiTietHoaDon_DTO> ds = cthdBus.getChiTietHoaDon(maHD);
        Vector<Vector<Object>> duLieuBang = new Vector<>();

        if (ds != null || !ds.isEmpty()) {
            for  (ChiTietHoaDon_DTO hd : ds) {
                Vector<Object> hang = new Vector<>();
                hang.add(hd.getMaSP());
                hang.add(sanPhamBus.getSanPhamByMaSP(hd.getMaSP()).getTenSP());
                hang.add(hd.getSoLuongMua());
                hang.add(hd.getDonGia());
                hang.add(hd.getThanhTien());
                duLieuBang.add(hang);
            }
        }

        return  duLieuBang;
    }

    public void refresh() {
        txtTimMaHD.setText("");
        dateTimNgayTu.setDate(null);
        dateTimNgayDen.setDate(null);// Trả JDateChooser về trạng thái rỗng
        txtTimMaKH.setText("");
        hoaDon = hoaDonBus.layTatCaHD();
        modelHoaDon.setDataVector(renderHoaDon(hoaDon), columnsName);
    }
}