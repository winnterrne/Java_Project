package GUI.Product;

import DAO.DanhMuc_DAO;
import DAO.SanPham_DAO;
import DTO.DanhMuc_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DanhMuc_GUI extends JPanel {

    private final DanhMuc_DAO dao = new DanhMuc_DAO();

    private JTable table;
    private DefaultTableModel model;

    private BUS.DanhMuc_BUS dmBus = new BUS.DanhMuc_BUS();

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnChiTiet;
    private JButton btnXemDS;
    private JButton btnLamMoi;
    private JTextField txtSearch;

    public DanhMuc_GUI() {
        super(new BorderLayout());

        initComponents();
        attachListeners();

        loadDanhMuc();
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar("Công cụ");
        toolBar.setFloatable(false);

        btnThem     = new JButton("Thêm");
        btnSua      = new JButton("Sửa");
        btnXoa      = new JButton("Xóa");
        btnChiTiet  = new JButton("Chi Tiết");
        btnXemDS    = new JButton("Xem Danh Sách");

        toolBar.add(btnThem);
        toolBar.add(btnSua);
        toolBar.add(btnXoa);
        toolBar.addSeparator();
        toolBar.add(btnChiTiet);
        toolBar.add(btnXemDS);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblFilter = new JLabel("Tất cả");
        JComboBox<String> cbFilter = new JComboBox<>(new String[]{"Tất cả"});
        txtSearch = new JTextField("", 30);
        btnLamMoi = new JButton("Làm mới");

        searchPanel.add(lblFilter);
        searchPanel.add(cbFilter);
        searchPanel.add(txtSearch);
        searchPanel.add(btnLamMoi);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(toolBar, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        String[] columns = {"Mã DM", "Tên danh mục", "Số lượng sản phẩm"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void attachListeners() {
        btnLamMoi.addActionListener(e -> loadDanhMuc());

        btnThem.addActionListener(e -> themDanhMuc());
        btnSua.addActionListener(e -> suaDanhMuc());
        btnXoa.addActionListener(e -> xoaDanhMuc());
        btnChiTiet.addActionListener(e -> xemChiTiet());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xemChiTiet();
                }
            }
        });

        txtSearch.addActionListener(e -> timKiem(txtSearch.getText().trim()));
    }

    private void loadDanhMuc() {
        model.setRowCount(0);
        ArrayList<DanhMuc_DTO> list = dao.getAllDanhMuc();
        for (DanhMuc_DTO dm : list) {
            model.addRow(new Object[]{
                    dm.getMaDM(),
                    dm.getTenDM(),
                    dm.getSoLuongSP()
            });
        }
    }

    private void themDanhMuc() {
        JTextField txtMaDM = new JTextField(10);
        txtMaDM.setEditable(false);
        JTextField txtTenDM = new JTextField(20);

        txtMaDM.setText(dmBus.taoMaDMTuDong());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Mã DM:"));
        panel.add(txtMaDM);
        panel.add(new JLabel("Tên DM:"));
        panel.add(txtTenDM);

        int result = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this),
                panel, "Thêm danh mục mới", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String maDM = txtMaDM.getText().trim();
            String tenDM = txtTenDM.getText().trim();

            if (maDM.isEmpty() || tenDM.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DanhMuc_DTO dm = new DanhMuc_DTO(maDM, tenDM);
            if (dao.insertDanhMuc(dm)) {
                loadDanhMuc();
                JOptionPane.showMessageDialog(this, "Thêm danh mục thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! (Mã trùng hoặc lỗi DB)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void suaDanhMuc() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục để sửa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDM = (String) model.getValueAt(row, 0);
        DanhMuc_DTO dm = dao.getDanhMucByMaDM(maDM);
        if (dm == null) return;

        JTextField txtTenDM = new JTextField(dm.getTenDM(), 20);
        JPanel panel = new JPanel(new GridLayout(1, 2, 10, 10));
        panel.add(new JLabel("Tên DM mới:"));
        panel.add(txtTenDM);

        int result = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(this),
                panel, "Sửa danh mục " + maDM, JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String tenMoi = txtTenDM.getText().trim();
            if (tenMoi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên danh mục không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dm.setTenDM(tenMoi);
            if (dao.updateDanhMuc(dm)) {
                loadDanhMuc();
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaDanhMuc() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục để xóa!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maDM = (String) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa danh mục " + maDM + "?\n(Lưu ý: Không thể xóa nếu còn sản phẩm)",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deleteDanhMuc(maDM)) {
                loadDanhMuc();
                JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại! (Có thể còn sản phẩm hoặc lỗi DB)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xemChiTiet() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        String maDM = (String) model.getValueAt(row, 0);
        DanhMuc_DTO dm = dao.getDanhMucByMaDM(maDM);
        if (dm == null) return;

        ArrayList<SanPham_DTO> sanPhams = new SanPham_DAO().getAllSanPhamByMaDM(maDM);

        StringBuilder sb = new StringBuilder();
        sb.append("Mã danh mục: ").append(dm.getMaDM()).append("\n");
        sb.append("Tên danh mục: ").append(dm.getTenDM()).append("\n");
        sb.append("Số sản phẩm: ").append(sanPhams.size()).append("\n\n");
        sb.append("Danh sách sản phẩm:\n");

        if (sanPhams.isEmpty()) {
            sb.append("   (Chưa có sản phẩm nào)");
        } else {
            for (SanPham_DTO sp : sanPhams) {
                sb.append("   • ").append(sp.getTenSP()).append(" (Mã: ").append(sp.getMaSP()).append(")\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString(), 15, 60);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this),
                scroll, "Chi tiết danh mục", JOptionPane.INFORMATION_MESSAGE);
    }

    private void timKiem(String keyword) {
        model.setRowCount(0);
        ArrayList<DanhMuc_DTO> list = dao.getAllDanhMuc();
        for (DanhMuc_DTO dm : list) {
            if (dm.getTenDM().toLowerCase().contains(keyword.toLowerCase()) ||
                    dm.getMaDM().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{
                        dm.getMaDM(),
                        dm.getTenDM(),
                        dm.getSoLuongSP()
                });
            }
        }
    }
}