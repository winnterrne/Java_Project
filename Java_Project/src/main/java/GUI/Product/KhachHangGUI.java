package GUI.Product;

import BUS.KhachHang_BUS;
import DTO.KhachHang_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class KhachHangGUI extends JPanel {

    private JTable tableKhachHang;
    private DefaultTableModel modelKhachHang;
    private KhachHang_BUS khBus = new KhachHang_BUS();

    private ArrayList<KhachHang_DTO> dsKhachHang = khBus.layTatCaKH();

    // Đã cập nhật: Thêm Email, Điểm tích lũy, Trạng thái vào danh sách cột
    private Vector<String> columnsName = new Vector<>(Arrays.asList(
            "Mã KH", "Họ Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ", "Email", "Điểm Tích Lũy"
    ));

    private JTextField txtTimMaKH, txtTimTenKH, txtTimSDT;

    public KhachHangGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);

        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(0xF6F3F3));

        JPanel pnlTop = new JPanel(new BorderLayout());
        pnlTop.setBackground(new Color(0xF6F3F3));

        JLabel lblTitle = new JLabel("DANH SÁCH KHÁCH HÀNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        pnlTop.add(lblTitle, BorderLayout.NORTH);

        JPanel pnlSearch = new JPanel(new BorderLayout());
        pnlSearch.setBackground(new Color(0xF6F3F3));
        pnlSearch.setBorder(BorderFactory.createTitledBorder("Bộ lọc tìm kiếm"));

        JPanel pnlInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlInput.setBackground(new Color(0xF6F3F3));

        pnlInput.add(new JLabel("Mã KH:"));
        txtTimMaKH = new JTextField(8);
        pnlInput.add(txtTimMaKH);

        pnlInput.add(new JLabel("Tên KH:"));
        txtTimTenKH = new JTextField(12);
        pnlInput.add(txtTimTenKH);

        pnlInput.add(new JLabel("Số điện thoại:"));
        txtTimSDT = new JTextField(10);
        pnlInput.add(txtTimSDT);

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        pnlButtons.setBackground(new Color(0xF6F3F3));

        JButton btnTimKiem = new JButton("Tìm kiếm");
        btnTimKiem.setPreferredSize(new Dimension(100, 25));

        JButton btnThemMoi = new JButton("Thêm mới");
        btnThemMoi.setPreferredSize(new Dimension(100, 25));

        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 25));

        pnlButtons.add(btnTimKiem);
        pnlButtons.add(btnThemMoi);
        pnlButtons.add(btnLamMoi);

        pnlSearch.add(pnlInput, BorderLayout.CENTER);
        pnlSearch.add(pnlButtons, BorderLayout.EAST);

        pnlTop.add(pnlSearch, BorderLayout.CENTER);
        add(BorderLayout.NORTH, pnlTop);

        modelKhachHang = new DefaultTableModel(renderKhachHang(dsKhachHang), columnsName) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableKhachHang = new JTable(modelKhachHang);
        tableKhachHang.getTableHeader().setReorderingAllowed(false);
        tableKhachHang.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableKhachHang.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tableKhachHang);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);


        btnLamMoi.addActionListener(e -> refresh());

        btnTimKiem.addActionListener(e -> {
            String maKH = txtTimMaKH.getText().trim();
            String tenKH = txtTimTenKH.getText().trim();
            String sdt = txtTimSDT.getText().trim();

            ArrayList<KhachHang_DTO> ketQuaLoc = khBus.boLocTimKiemKH(maKH, tenKH, sdt);
            modelKhachHang.setDataVector(renderKhachHang(ketQuaLoc), columnsName);
        });

        btnThemMoi.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Hiển thị Form thêm Khách Hàng mới!");
        });

        tableKhachHang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    int selectedRow = tableKhachHang.getSelectedRow();
                    if (selectedRow != -1) {
                        String maKH = tableKhachHang.getValueAt(selectedRow, 0).toString();
//                        hienThiGiaoDienChiTiet(maKH);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int row = tableKhachHang.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < tableKhachHang.getRowCount()) {
                        tableKhachHang.setRowSelectionInterval(row, row);
                        String maKH = tableKhachHang.getValueAt(row, 0).toString();
                        hienThiMenuChuotPhai(e, maKH);
                    }
                }
            }
        });
    }

    // Đã cập nhật: Thêm các thuộc tính mới vào Vector để đẩy lên bảng
    private Vector<Vector<Object>> renderKhachHang(ArrayList<KhachHang_DTO> danhSach) {
        Vector<Vector<Object>> duLieuBang = new Vector<>();
        if (danhSach == null || danhSach.isEmpty()) {
            return duLieuBang;
        }

        for (KhachHang_DTO kh : danhSach) {
            Vector<Object> hang = new Vector<>();
            hang.add(kh.getMaKH());
            hang.add(kh.getHoTenKH() != null ? kh.getHoTenKH() : "");
            hang.add(kh.getSoDT() != null ? kh.getSoDT() : "");
            hang.add(kh.getDiaChi() != null ? kh.getDiaChi() : "");

            // Xử lý các thuộc tính mới, kiểm tra null để tránh lỗi NullPointerException
            hang.add(kh.getEmail() != null ? kh.getEmail() : "Không có");
            hang.add(kh.getDiemTichLuy()); // Giả định trả về kiểu double/float/int

            duLieuBang.add(hang);
        }
        return duLieuBang;
    }

    public void refresh() {
        txtTimMaKH.setText("");
        txtTimTenKH.setText("");
        txtTimSDT.setText("");
        dsKhachHang = khBus.layTatCaKH();
        modelKhachHang.setDataVector(renderKhachHang(dsKhachHang), columnsName);
    }

    public void hienThiMenuChuotPhai(MouseEvent e, String maKH) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemSua = new JMenuItem("Sửa thông tin");
        JMenuItem itemXoa = new JMenuItem("Xóa Khách Hàng");

        itemSua.addActionListener(event -> {
            //hienThiGiaoDienChiTiet(maKH);
        });

        itemXoa.addActionListener(event -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa khách hàng " + maKH + " không?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                KhachHang_DTO kh = khBus.layKHTheoMaKH(maKH);
                khBus.deleteKhachHang(kh);

                int selectedRow = tableKhachHang.getSelectedRow();
                modelKhachHang.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Đã xóa khách hàng thành công!");
            }
        });

        popupMenu.add(itemSua);
        popupMenu.addSeparator();
        popupMenu.add(itemXoa);
        popupMenu.show(e.getComponent(), e.getX(), e.getY());
    }
}