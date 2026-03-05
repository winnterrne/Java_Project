package GUI.Product;

import BUS.HoaDon_BUS;
import BUS.KhachHang_BUS;
import DTO.*;
import com.toedter.calendar.JDateChooser; // Import JDateChooser từ thư viện JCalendar

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Vector;

public class HoaDon_GUI extends JPanel {

    private JTable tableHoaDon;
    private DefaultTableModel modelHoaDon;
    private HoaDon_BUS hoaDonBus = new HoaDon_BUS();
    ArrayList<HoaDon_DTO> hoaDon = hoaDonBus.layTatCaHD();

    Vector<String> columnsName = new Vector<>(Arrays.asList("Mã Hóa Đơn", "Ngày Lập", "Khách Hàng", "Nhân viên", "Tổng Tiền (VNĐ)"));

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

        // 1.2 Panel Tìm kiếm (Search Panel)
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlSearch.setBackground(new Color(0xF6F3F3));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"));

        // Các thành phần của bộ lọc
        pnlSearch.add(new JLabel("Mã Hóa Đơn:"));
        JTextField txtTimMaHD = new JTextField(10);
        pnlSearch.add(txtTimMaHD);

        pnlSearch.add(new JLabel("Ngày lập:"));
        // --- SỬ DỤNG JDATECHOOSER TẠI ĐÂY ---
        JDateChooser dateTimNgay = new JDateChooser();
        dateTimNgay.setDateFormatString("dd/MM/yyyy"); // Định dạng ngày tháng hiển thị
        dateTimNgay.setPreferredSize(new Dimension(130, 25)); // Đặt kích thước cho ô chọn ngày
        pnlSearch.add(dateTimNgay);

        pnlSearch.add(new JLabel("Mã KH:"));
        JTextField txtTimMaKH = new JTextField(10);
        pnlSearch.add(txtTimMaKH);

        JButton btnTimKiem = new JButton("Tìm kiếm");
        JButton btnLamMoi = new JButton("Làm mới");
        pnlSearch.add(btnTimKiem);
        pnlSearch.add(btnLamMoi);

        pnlTop.add(pnlSearch, BorderLayout.CENTER);

        // Đưa toàn bộ khu vực phía trên vào NORTH của Layout chính
        add(pnlTop, BorderLayout.NORTH);


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
            txtTimMaHD.setText("");
            dateTimNgay.setDate(null); // Trả JDateChooser về trạng thái rỗng
            txtTimMaKH.setText("");
            // TODO: Gọi lại hàm load toàn bộ dữ liệu (không có bộ lọc) lên bảng
        });

        // Sự kiện cho nút Tìm kiếm
        btnTimKiem.addActionListener(e -> {
            String maHD = txtTimMaHD.getText().trim();
            String maKH = txtTimMaKH.getText().trim();

            // Xử lý lấy ngày tháng từ JDateChooser
            Date selectedDate = dateTimNgay.getDate();
            String ngayFormat = "";
            if (selectedDate != null) {
                // Chuyển đối tượng Date thành String theo định dạng để truyền xuống DB hoặc BUS (nếu cần)
                // Lưu ý: Tùy thuộc vào CSDL của bạn lưu ngày theo dạng nào (yyyy-MM-dd hay dd/MM/yyyy)
                // Ở đây mình ví dụ định dạng yyyy-MM-dd là chuẩn thông dụng nhất trong SQL
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                ngayFormat = sdf.format(selectedDate);
            }

            // TODO: Gọi hàm lọc dữ liệu từ BUS truyền vào maHD, ngayFormat, maKH
            JOptionPane.showMessageDialog(this,
                    "Đang tìm kiếm...\nMã HD: " + maHD +
                            "\nNgày (format cho SQL): " + (ngayFormat.isEmpty() ? "Không chọn" : ngayFormat) +
                            "\nMã KH: " + maKH);
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

        JDialog dialogChiTiet = new JDialog(parentFrame, "Chi Tiết Hóa Đơn - " + maHienThi, true);
        dialogChiTiet.setSize(650, 400);
        dialogChiTiet.setLocationRelativeTo(parentFrame);
        dialogChiTiet.setLayout(new BorderLayout(10, 10));

        JPanel pnlHeader = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createTitledBorder("Thông tin hóa đơn")
        ));

        pnlHeader.add(new JLabel("Mã HD: " + maHienThi));
        pnlHeader.add(new JLabel("Ngày lập: 26/02/2026 08:30"));
        pnlHeader.add(new JLabel("Nhân viên: NV_01"));
        pnlHeader.add(new JLabel("Khách hàng: Nguyễn Văn A"));
        dialogChiTiet.add(pnlHeader, BorderLayout.NORTH);

        String[] colChiTiet = {"STT", "Mã SP", "Tên SP", "SL", "Đơn Giá", "Thành Tiền"};
        Object[][] dummyDetailData = {
                {"1", "SP01", "Cà phê đen", "2", "20,000", "40,000"},
                {"2", "SP05", "Trà đá", "1", "5,000", "5,000"}
        };

        DefaultTableModel modelChiTiet = new DefaultTableModel(dummyDetailData, colChiTiet) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tableChiTiet = new JTable(modelChiTiet);
        tableChiTiet.setRowHeight(25);

        JScrollPane scrollChiTiet = new JScrollPane(tableChiTiet);
        scrollChiTiet.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        dialogChiTiet.add(scrollChiTiet, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTongTien = new JLabel("Tổng cộng: 45,000 VNĐ");
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

        JMenuItem itemSua = new JMenuItem("Sửa Hóa Đơn: " + maHD);
        JMenuItem itemXoa = new JMenuItem("Xóa Hóa Đơn: " + maHD);

        itemSua.addActionListener(event -> {
            JOptionPane.showMessageDialog(this, "Đang mở giao diện sửa cho: " + maHD);
        });

        itemXoa.addActionListener(event -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa " + maHD + " không?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                int selectedRow = tableHoaDon.getSelectedRow();
                modelHoaDon.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn thành công!");
            }
        });

        popupMenu.add(itemSua);
        popupMenu.addSeparator();
        popupMenu.add(itemXoa);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }


    private Vector<Vector<Object>> renderHoaDon(ArrayList<HoaDon_DTO> hoaDon) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (hoaDon == null || hoaDon.isEmpty()) {
            return duLieuBang;
        }

        for (HoaDon_DTO cthd : hoaDon) {
            Vector<Object> hang = new Vector<>();
//            NhanVien_DTO nv;
            KhachHang_BUS khBus = new KhachHang_BUS();
            KhachHang_DTO kh = khBus.layKHTheoMaKH(cthd.getMaHD());
            hang.add(cthd.getMaHD());
            hang.add(cthd.getNgayLapHD().toString());
            hang.add(kh.getHoTenKH());
            hang.add(cthd.getMaNV());
            hang.add(cthd.getTongTien());
        }
        return duLieuBang;
    }
}