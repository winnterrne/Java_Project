package GUI.Product;

import BUS.SanPham_BUS;
import DTO.SanPham_DTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SanPhamXoa_GUI extends JDialog {
    private SanPham_BUS sanPhamBus;
    private JTable tableDaXoa;
    private DefaultTableModel model;
    private TablePanel_GUI tablePanel;

    public SanPhamXoa_GUI(Frame owner, SanPham_BUS bus, TablePanel_GUI tablePanel) {
        super(owner, "Sản phẩm đã xóa", true);
        this.sanPhamBus = bus;

        setSize(900, 600);
        setLocationRelativeTo(owner);

        // Tạo bảng
        String[] columns = {"Mã SP", "Tên SP", "Giá bán", "Số lượng tồn", "Danh mục"};
        model = new DefaultTableModel(columns, 0);
        tableDaXoa = new JTable(model);
        add(new JScrollPane(tableDaXoa), BorderLayout.CENTER);

        // Nút khôi phục
        JButton btnKhoiPhuc = new JButton("Khôi phục sản phẩm đã chọn");
        btnKhoiPhuc.addActionListener(e -> khoiPhucSanPham());
        JPanel bottom = new JPanel();
        bottom.add(btnKhoiPhuc);
        add(bottom, BorderLayout.SOUTH);

        loadDanhSachDaXoa();
        setVisible(true);
    }

    private void loadDanhSachDaXoa() {
        model.setRowCount(0);
        ArrayList<SanPham_DTO> list = sanPhamBus.getAllSanPhamDaXoa();  // cần thêm hàm này trong BUS
        for (SanPham_DTO sp : list) {
            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    sp.getGiaBan(),
                    sp.getSoLuongTon(),
                    sp.getMaDM()
            });
        }
    }

    private void khoiPhucSanPham() {
        int row = tableDaXoa.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để khôi phục!");
            return;
        }

        String maSP = (String) model.getValueAt(row, 0);
        if (sanPhamBus.restoreSanPham(maSP)) {
            JOptionPane.showMessageDialog(this, "Khôi phục thành công!");
            loadDanhSachDaXoa();
            if (tablePanel != null) {
                tablePanel.loadSanPham();  // Refresh lại bảng chính nếu có reference
            }
        } else {
            JOptionPane.showMessageDialog(this, "Khôi phục thất bại!");
        }
    }
}