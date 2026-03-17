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


        JButton btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setPreferredSize(new Dimension(100, 25));

        pnlButtons.add(btnTimKiem);
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


        tableKhachHang.addMouseListener(new MouseAdapter() {

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

            
            hang.add(kh.getEmail() != null ? kh.getEmail() : "Không có");
            hang.add(kh.getDiemTichLuy()); 

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
            hienThiDialogSuaKhachHang(maKH);
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

    private void hienThiDialogSuaKhachHang(String maKH) {
        
        KhachHang_DTO kh = khBus.layKHTheoMaKH(maKH);
        if (kh == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin khách hàng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        Window window = SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new JDialog((Frame) window, "Sửa Thông Tin Khách Hàng", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        
        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 10, 15));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        
        pnlForm.add(new JLabel("Mã KH:"));
        JTextField txtMaKH = new JTextField(kh.getMaKH());
        txtMaKH.setEditable(false); 
        txtMaKH.setFocusable(false);
        pnlForm.add(txtMaKH);

        pnlForm.add(new JLabel("Họ tên:"));
        JTextField txtHoTen = new JTextField(kh.getHoTenKH());
        pnlForm.add(txtHoTen);

        pnlForm.add(new JLabel("Số điện thoại:"));
        JTextField txtSDT = new JTextField(kh.getSoDT());
        pnlForm.add(txtSDT);

        pnlForm.add(new JLabel("Địa chỉ:"));
        JTextField txtDiaChi = new JTextField(kh.getDiaChi());
        pnlForm.add(txtDiaChi);

        pnlForm.add(new JLabel("Email:"));
        JTextField txtEmail = new JTextField(kh.getEmail());
        pnlForm.add(txtEmail);

        pnlForm.add(new JLabel("Điểm tích lũy:"));
        JTextField txtDiem = new JTextField(String.valueOf(kh.getDiemTichLuy()));
        txtDiem.setEditable(false); 
        txtDiem.setFocusable(false);
        pnlForm.add(txtDiem);

        dialog.add(pnlForm, BorderLayout.CENTER);

        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu thay đổi");
        JButton btnHuy = new JButton("Hủy bỏ");

        pnlButtons.add(btnLuu);
        pnlButtons.add(btnHuy);
        dialog.add(pnlButtons, BorderLayout.SOUTH);

        
        btnHuy.addActionListener(e -> dialog.dispose());

        btnLuu.addActionListener(e -> {
            
            String tenMoi = txtHoTen.getText().trim();
            String sdtMoi = txtSDT.getText().trim();
            String diaChiMoi = txtDiaChi.getText().trim();
            String emailMoi = txtEmail.getText().trim();

            if (tenMoi.isEmpty() || sdtMoi.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập đủ Họ tên và Số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            
            kh.setHoTenKH(tenMoi);
            kh.setSoDT(sdtMoi);
            kh.setDiaChi(diaChiMoi);
            kh.setEmail(emailMoi);

            
            boolean thanhCong = khBus.updateKhachHang(kh); 

            if (thanhCong) {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thành công!");
                dialog.dispose();
                refresh(); 
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        dialog.setVisible(true);
    }
}