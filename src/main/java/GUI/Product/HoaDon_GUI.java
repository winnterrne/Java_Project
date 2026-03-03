package GUI.Product;

import BUS.HoaDon_BUS;
import DTO.HoaDon_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class HoaDon_GUI extends JPanel {

    private JTable tableHoaDon;
    private DefaultTableModel modelHoaDon;
    private HoaDon_BUS hoaDonBus = new  HoaDon_BUS();
    ArrayList<HoaDon_DTO> hoaDon = hoaDonBus.layTatCaHD();

    public HoaDon_GUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0xF6F3F3));

        JLabel lblTitle = new JLabel("DANH SÁCH HÓA ĐƠN ĐÃ THANH TOÁN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // 3. Thiết kế Bảng Danh sách Hóa đơn
        String[] columns = {"Mã Hóa Đơn", "Ngày Lập", "Khách Hàng", "Tổng Tiền (VNĐ)"};

        // Dữ liệu mẫu (Dummy Data)
        Object[][] dummyData = {
                {"HD_001", "26/02/2026 08:30", "Nguyễn Văn A", "150,000"},
                {"HD_002", "26/02/2026 09:15", "Trần Thị B", "320,000"},
                {"HD_003", "26/02/2026 10:05", "Khách vãng lai", "45,000"}
        };

        modelHoaDon = new DefaultTableModel(dummyData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Khóa không cho phép sửa trực tiếp trên ô
            }
        };

        tableHoaDon = new JTable(modelHoaDon);
        tableHoaDon.setRowHeight(30);
        tableHoaDon.setFont(new Font("Arial", Font.PLAIN, 16));
        tableHoaDon.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        tableHoaDon.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableHoaDon.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Thêm thanh cuộn cho bảng
        JScrollPane scrollPane = new JScrollPane(tableHoaDon);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // 4. Bắt sự kiện Click chuột
        tableHoaDon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Click đúp chuột trái
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
                // Click chuột phải hiển thị Menu
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
        // [SỬA LỖI Ở ĐÂY]: Tìm cửa sổ gốc (JFrame) đang chứa JPanel này để làm owner cho JDialog
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

        JDialog dialogChiTiet = new JDialog(parentFrame, "Chi Tiết Hóa Đơn - " + maHienThi, true);
        dialogChiTiet.setSize(650, 400);
        dialogChiTiet.setLocationRelativeTo(parentFrame); // Căn giữa theo cửa sổ cha
        dialogChiTiet.setLayout(new BorderLayout(10, 10));

        // Phần Header: Thông tin tóm tắt
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

        // Phần Center: Bảng danh sách mặt hàng
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

        // Phần Bottom: Tổng tiền và nút Đóng
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
                // TODO: Gọi hàm BUS.XoaHoaDon(maHD) tại đây trước khi xóa trên giao diện
                modelHoaDon.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa hóa đơn thành công!");
            }
        });

        popupMenu.add(itemSua);
        popupMenu.addSeparator();
        popupMenu.add(itemXoa);

        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
}