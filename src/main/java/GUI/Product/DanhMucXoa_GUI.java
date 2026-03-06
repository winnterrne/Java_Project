package GUI.Product;

import BUS.DanhMuc_BUS;
import DTO.DanhMuc_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class DanhMucXoa_GUI extends JDialog {

    private DanhMuc_BUS danhMucBus;
    private JTable tableDaXoa;
    private DefaultTableModel model;
    private DanhMuc_GUI danhMucMainGUI;

    public DanhMucXoa_GUI(Frame owner, DanhMuc_BUS bus, DanhMuc_GUI mainGUI) {
        super(owner, "Danh mục đã xóa (xóa mềm)", true);
        this.danhMucBus = bus;
        this.danhMucMainGUI = mainGUI;

        setSize(900, 600);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // Tiêu đề
        JLabel lblTitle = new JLabel("Danh sách danh mục đã bị xóa mềm");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // Bảng hiển thị
        String[] columns = {"Mã DM", "Tên danh mục", "Số lượng sản phẩm"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Không cho edit trực tiếp
            }
        };
        tableDaXoa = new JTable(model);
        tableDaXoa.setRowHeight(30);
        tableDaXoa.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);  // Cho chọn nhiều dòng
        tableDaXoa.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(tableDaXoa);
        add(scrollPane, BorderLayout.CENTER);

        // Panel nút dưới
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnKhoiPhuc = new JButton("Khôi phục danh mục đã chọn");
        btnKhoiPhuc.setPreferredSize(new Dimension(220, 40));
        btnKhoiPhuc.setFont(new Font("Arial", Font.BOLD, 14));
        btnKhoiPhuc.addActionListener(e -> khoiPhucDaChon());

        JButton btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(120, 40));
        btnDong.addActionListener(e -> dispose());

        bottomPanel.add(btnKhoiPhuc);
        bottomPanel.add(btnDong);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load dữ liệu ngay khi mở
        loadDanhSachDaXoa();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void loadDanhSachDaXoa() {
        model.setRowCount(0);
        ArrayList<DanhMuc_DTO> list = danhMucBus.getAllDanhMucDaXoa();  // Gọi hàm từ BUS
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hiện không có danh mục nào bị xóa mềm.");
            return;
        }

        for (DanhMuc_DTO dm : list) {
            model.addRow(new Object[]{
                dm.getMaDM(),
                dm.getTenDM(),
                dm.getSoLuongSP()
            });
        }
    }

    private void khoiPhucDaChon() {
        int[] selectedRows = tableDaXoa.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một danh mục để khôi phục!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn khôi phục " + selectedRows.length + " danh mục đã chọn?",
                "Xác nhận khôi phục",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        int successCount = 0;
        for (int row : selectedRows) {
            String maDM = (String) model.getValueAt(row, 0);
            if (danhMucBus.restoreDanhMuc(maDM)) {
                successCount++;
            }
        }

        if (successCount > 0) {
            JOptionPane.showMessageDialog(this, "Đã khôi phục thành công " + successCount + " danh mục!");
            loadDanhSachDaXoa();
            if(danhMucMainGUI != null) {
                danhMucMainGUI.loadDanhMuc();  // Refresh lại bảng chính nếu có reference
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không khôi phục được danh mục nào!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}