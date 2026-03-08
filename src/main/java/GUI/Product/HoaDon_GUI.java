package GUI.Product;

import BUS.*;
import DTO.*;
import Utils.ExportHoaDon;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import static Utils.ExportExcelHoaDon.ExportExcelHoaDon;

public class HoaDon_GUI extends JPanel {

    private JTable tableHoaDon;
    private DefaultTableModel modelHoaDon;
    private HoaDon_BUS hoaDonBus = new HoaDon_BUS();
    private ChiTietHoaDon_BUS cthdBus  = new ChiTietHoaDon_BUS();
    private SanPham_BUS sanPhamBus = new SanPham_BUS();


    private KhachHang_BUS khBus = new KhachHang_BUS();
    private NhanVien_BUS nvBus = new NhanVien_BUS();

    ArrayList<HoaDon_DTO> hoaDon = hoaDonBus.layTatCaHD();
    Vector<String> colChiTiet = new Vector<>(Arrays.asList("Mã SP", "Tên SP", "Số Lượng Mua", "Đơn Giá", "Thành Tiền"));
    Vector<String> columnsName = new Vector<>(Arrays.asList("Mã Hóa Đơn", "Ngày Lập", "Khách Hàng", "Nhân viên", "Tổng Tiền (VNĐ)"));

    JTextField txtTimMaHD, txtTimMaKH;
    JDateChooser dateTimNgayTu, dateTimNgayDen;

    public HoaDon_GUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0xF6F3F3));




        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(new Color(0xF6F3F3));

        JLabel lblTitle = new JLabel("DANH SÁCH HÓA ĐƠN ĐÃ THANH TOÁN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlSearch = new JPanel(new BorderLayout());
        pnlSearch.setBackground(new Color(0xF6F3F3));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"));

        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInput.setBackground(new Color(0xF6F3F3));

        pnlInput.add(new JLabel("Mã Hóa Đơn:"));
        txtTimMaHD = new JTextField(8);
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

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlButtons.setBackground(new Color(0xF6F3F3));

        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 25));

        JButton btnExcel = new JButton("Xuất Excel");
        btnExcel.setPreferredSize(new Dimension(100, 25));

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 25));

        pnlButtons.add(btnTimKiem);
        pnlButtons.add(btnExcel);
        pnlButtons.add(btnLamMoi);

        pnlSearch.add(pnlInput, BorderLayout.CENTER);
        pnlSearch.add(pnlButtons, BorderLayout.EAST);

        pnlTop.add(pnlSearch, BorderLayout.CENTER);
        add(BorderLayout.NORTH, pnlTop);





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






        btnLamMoi.addActionListener(e -> refresh());

        btnTimKiem.addActionListener(e -> {
            String maHD = txtTimMaHD.getText().trim();
            String maKH = txtTimMaKH.getText().trim();

            Date tuDate = dateTimNgayTu.getDate();
            Date denDate = dateTimNgayDen.getDate();
            LocalDate selectedDate1 = null, selectedDate2 = null;

            if (tuDate != null) {
                selectedDate1 = tuDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            if (denDate != null) {
                selectedDate2 = denDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            modelHoaDon.setDataVector(renderHoaDon(hoaDonBus.boLocTimKiemHD(maHD, maKH, selectedDate1, selectedDate2)), columnsName);
        });

        btnExcel.addActionListener(e -> {

            String path = taoDuongDanXuatFile("excel", "DanhSachHoaDon.xlsx");
            ExportExcelHoaDon(tableHoaDon, path);
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công tại:\n" + path);
        });

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


        NhanVien_DTO nv = nvBus.getNhanVienByMa(hd.getMaNV());
        KhachHang_DTO kh = khBus.layKHTheoMaKH(hd.getMaKH());
        String tenNV = nv != null ? nv.getHoTenNV() : "Không xác định";
        String tenKH = kh != null ? kh.getHoTenKH() : "Khách lẻ";

        pnlHeader.add(new JLabel("Mã HD: " + hd.getMaHD()));
        pnlHeader.add(new JLabel("Ngày lập: " + hd.getNgayLapHD().toString()));
        pnlHeader.add(new JLabel("Nhân viên: " + tenNV));
        pnlHeader.add(new JLabel("Khách hàng: " + tenKH));
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

        JLabel lblTongTien = new JLabel(String.format("Tổng cộng: %,.0f đ", hd.getTongTien()));
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


    public void hienThiNutSua(MouseEvent e, String maHD) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemXoa = new JMenuItem("Xóa Hóa Đơn");
        JMenuItem itemXuatPDF  = new JMenuItem("Xuất PDF");

        itemXoa.addActionListener(event -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa " + maHD + " không?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int selectedRow = tableHoaDon.getSelectedRow();
                String ma = tableHoaDon.getValueAt(selectedRow, 0).toString();
                hoaDonBus.deleteHD(hoaDonBus.layHDTheoMaHD(ma));
                modelHoaDon.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn thành công!");
            }
        });

        itemXuatPDF.addActionListener(event -> {
            HoaDon_DTO hd = hoaDonBus.layHDTheoMaHD(maHD);
            KhachHang_DTO kh = khBus.layKHTheoMaKH(hd.getMaKH());
            NhanVien_DTO nv = nvBus.getNhanVienByMa(hd.getMaNV());

            String tenKhachHang = (kh != null) ? kh.getHoTenKH() : "Khách lẻ";
            String diaChiKhach = (kh != null) ? kh.getDiaChi() : "";
            String tenNhanVien = (nv != null) ? nv.getHoTenNV() : "Không xác định";
            String ngayLap = (hd.getNgayLapHD() != null) ? hd.getNgayLapHD().toString() : "";

            DefaultTableModel modelChiTiet = new DefaultTableModel(renderChiTietHoaDon(maHD), colChiTiet);
            JTable tableChiTiet = new JTable(modelChiTiet);


            String path = taoDuongDanXuatFile("pdf", "HoaDon_" + maHD + ".pdf");

            ExportHoaDon.export(
                    hd.getMaHD(),
                    tenKhachHang,
                    diaChiKhach,
                    tenNhanVien,
                    ngayLap,
                    tableChiTiet,
                    hd.getTongTien(),
                    path
            );
            JOptionPane.showMessageDialog(this, "Xuất PDF thành công tại:\n" + path);
        });

        popupMenu.add(itemXuatPDF);
        popupMenu.addSeparator();
        popupMenu.add(itemXoa);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }





    private Vector<Vector<Object>> renderHoaDon(ArrayList<HoaDon_DTO> danhSach) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (danhSach == null || danhSach.isEmpty()) {
            return duLieuBang;
        }

        for (HoaDon_DTO hd : danhSach) {
            Vector<Object> hang = new Vector<>();
            hang.add(hd.getMaHD());
            hang.add(hd.getNgayLapHD().toString());


            KhachHang_DTO kh = khBus.layKHTheoMaKH(hd.getMaKH());
            NhanVien_DTO nv = nvBus.getNhanVienByMa(hd.getMaNV());

            hang.add(kh != null ? kh.getHoTenKH() : "Khách lẻ");
            hang.add(nv != null ? nv.getHoTenNV() : "Không xác định");
            hang.add(String.format("%,.0f", hd.getTongTien()));

            duLieuBang.add(hang);
        }
        return duLieuBang;
    }

    public Vector<Vector<Object>> renderChiTietHoaDon(String maHD) {
        ArrayList<ChiTietHoaDon_DTO> ds = cthdBus.getChiTietHoaDon(maHD);
        Vector<Vector<Object>> duLieuBang = new Vector<>();


        if (ds != null && !ds.isEmpty()) {
            for  (ChiTietHoaDon_DTO hd : ds) {
                Vector<Object> hang = new Vector<>();
                hang.add(hd.getMaSP());

                SanPham_DTO sp = sanPhamBus.getSanPhamByMaSP(hd.getMaSP());
                hang.add(sp != null ? sp.getTenSP() : "Sản phẩm không tồn tại");

                hang.add(hd.getSoLuongMua());
                hang.add(String.format("%,.0f", hd.getDonGia()));
                hang.add(String.format("%,.0f", hd.getThanhTien()));
                duLieuBang.add(hang);
            }
        }
        return duLieuBang;
    }

    public void refresh() {
        txtTimMaHD.setText("");
        dateTimNgayTu.setDate(null);
        dateTimNgayDen.setDate(null);
        txtTimMaKH.setText("");
        hoaDon = hoaDonBus.layTatCaHD();
        modelHoaDon.setDataVector(renderHoaDon(hoaDon), columnsName);
    }


    private String taoDuongDanXuatFile(String thuMucCon, String tenFile) {
        String projectPath = System.getProperty("user.dir");
        String relativePath = "src" + java.io.File.separator + "main" + java.io.File.separator + "resources" + java.io.File.separator + thuMucCon;
        java.io.File folder = new java.io.File(projectPath + java.io.File.separator + relativePath);

        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.getAbsolutePath() + java.io.File.separator + tenFile;
    }
}