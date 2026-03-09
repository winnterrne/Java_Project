package GUI.Product;

import DAO.DanhMuc_DAO;
import DAO.SanPham_DAO;
import DTO.DanhMuc_DTO;
import DTO.SanPham_DTO;
import Utils.ExportExcel;

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
    private JButton btnLamMoi;
    private JButton btnXemDanhMucDaXoa;
    private JTextField txtSearch;
    private JButton btnExportExcel;

    public DanhMuc_GUI() {
        super(new BorderLayout());
        System.out.println(">>> DanhMuc_GUI khởi tạo - bắt đầu initComponents");

        initComponents();
        System.out.println(">>> Đã initComponents xong, bắt đầu attachListeners");

        attachListeners();
        System.out.println(">>> Đã attachListeners xong");

        loadDanhMuc();
        System.out.println(">>> Đã loadDanhMuc xong");
    }

    private void initComponents() {
        JToolBar toolBar = new JToolBar("Công cụ");
        toolBar.setFloatable(false);

        btnThem     = new JButton("Thêm");
        btnSua      = new JButton("Sửa");
        btnXoa      = new JButton("Xóa");
        btnChiTiet  = new JButton("Chi Tiết");
        btnXemDanhMucDaXoa = new JButton("Xem Danh Mục Đã Xóa");
        btnExportExcel = new JButton("Xuất Excel");


        toolBar.add(btnThem);
        toolBar.add(btnSua);
        toolBar.add(btnXoa);
        toolBar.addSeparator();
        toolBar.add(btnChiTiet);
        toolBar.add(btnXemDanhMucDaXoa);
        toolBar.addSeparator();
        toolBar.add(btnExportExcel);


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
        System.out.println(">>> attachListeners được gọi - đang gắn listener cho btnXemDanhMucDaXoa");

        btnLamMoi.addActionListener(e -> loadDanhMuc());
        btnThem.addActionListener(e -> themDanhMuc());
        btnSua.addActionListener(e -> suaDanhMuc());
        btnXoa.addActionListener(e -> xoaDanhMuc());
        btnChiTiet.addActionListener(e -> xemChiTiet());
        btnExportExcel.addActionListener(e -> exprtExcel());
//        btnImportExcel.addActionListener(e -> new);

        btnXemDanhMucDaXoa.addActionListener(e -> {
            System.out.println(">>> NÚT 'Xem Danh Mục Đã Xóa' ĐÃ ĐƯỢC NHẤN!");
            XemDanhMucDaXoa();
        });

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

    public void loadDanhMuc() {
        model.setRowCount(0);
        ArrayList<DanhMuc_DTO> list = dao.getAllDanhMucAvailable();
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

            DanhMuc_DTO dm = new DanhMuc_DTO(maDM, tenDM, (byte) 1);
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

    public void XemDanhMucDaXoa() {
        System.out.println(">>> Vào hàm XemDanhMucDaXoa");

        Window parent = SwingUtilities.getWindowAncestor(table);
        if (parent == null) {
            parent = SwingUtilities.getWindowAncestor(this);
        }

        if (parent == null) {
            System.out.println(">>> Parent vẫn null!");
            JOptionPane.showMessageDialog(this, "Không tìm thấy cửa sổ cha để mở dialog!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        System.out.println(">>> Parent tìm thấy: " + parent.getClass().getName() + " - Title: " + ((Frame) parent).getTitle());

        new DanhMucXoa_GUI((Frame) parent, dmBus, this);
        System.out.println(">>> Đã tạo DanhMucXoa_GUI");
    }

    private void exprtExcel() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new java.io.File("DanhMuc.xlsx"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filPath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filPath.toLowerCase().endsWith(".xlsx")) {
                filPath += ".xlsx";
            }
            ExportExcel.exportTablePNToExcel(table, filPath);
        }
    }

    private void exportPDF() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new java.io.File("DanhMuc.pdf"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            ExportPDFSP.exportTableToPDF(table, filePath, "Danh Mục Sản Phẩm");
        }
    }
}