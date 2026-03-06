package GUI.Product;

import BUS.SanPham_BUS;
import BUS.ThongKe_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SanPhamMain_GUI extends JPanel {

    private final SanPham_BUS sanPhamBus;
    private final ThongKe_BUS thongKeBus;

    private ToolBarPanel_GUI toolBar;
    private Search_GUI searchPanel;
    private TablePanel_GUI tablePanel;

    public SanPhamMain_GUI(SanPham_BUS sanPhamBus, ThongKe_BUS thongKeBus) {
        super(new BorderLayout());
        this.sanPhamBus = sanPhamBus;
        this.thongKeBus = thongKeBus;

        initComponents();
        attachListeners();

        if (tablePanel != null) {
            tablePanel.loadSanPham();
        }
    }

    private void initComponents() {
        toolBar = new ToolBarPanel_GUI();
        searchPanel = new Search_GUI();
        tablePanel = new TablePanel_GUI(sanPhamBus);

        add(toolBar, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(tablePanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
    }

    private void attachListeners() {
        toolBar.getBtnLamMoi().addActionListener(e -> tablePanel.loadSanPham());
        toolBar.getBtnThem().addActionListener(e -> themSanPham());
        toolBar.getBtnSua().addActionListener(e -> suaSanPham());
        toolBar.getBtnXoa().addActionListener(e -> xoaSanPham());
        toolBar.getBtnChiTiet().addActionListener(e -> xemChiTiet());
        toolBar.getBtnExportExcel().addActionListener(e -> xuatExcel());
        toolBar.getBtnExportPDF().addActionListener(e -> xuatPDF());
        toolBar.getBtnThongKe().addActionListener(e -> moThongKeDoanhThu());
        toolBar.getBtnXemSPXoa().addActionListener(e -> xemSPDaXoa());
        searchPanel.getBtnSearch().addActionListener(e -> {
            String keyword = searchPanel.getTxtSearch().getText().trim();
            tablePanel.timKiem(keyword);
        });

        tablePanel.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    xemChiTiet();
                }
            }
        });
    }

    private void themSanPham() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy cửa sổ cha!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sửa: truyền parent thay vì this
        ThemSP_GUI dialog = new ThemSP_GUI((Frame) parent, sanPhamBus);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            tablePanel.loadSanPham();
        }
    }

    private void suaSanPham() {
        int row = tablePanel.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để sửa!");
            return;
        }
        String maSP = (String) tablePanel.getTable().getModel().getValueAt(row, 0);
        SanPham_DTO sp = sanPhamBus.getSanPhamByMaSP(maSP);
        if (sp == null) return;

        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent == null) return;

        SuaSP_GUI dialog = new SuaSP_GUI((Frame) parent, sanPhamBus, sp);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            tablePanel.loadSanPham();
        }
    }

    private void xemChiTiet() {
        int row = tablePanel.getTable().getSelectedRow();
        if (row < 0) return;

        String maSP = (String) tablePanel.getTable().getModel().getValueAt(row, 0);
        SanPham_DTO sp = sanPhamBus.getSanPhamByMaSP(maSP);
        if (sp == null) return;

        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent == null) return;

        new ChiTietSP_GUI((Frame) parent, sp);  // constructor này không có isSuccess
    }

    private void xoaSanPham() {
        int row = tablePanel.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }
        String maSP = (String) tablePanel.getTable().getModel().getValueAt(row, 0);

        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent == null) return;

        XoaSP_GUI dialog = new XoaSP_GUI((Frame) parent, sanPhamBus, maSP);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            tablePanel.loadSanPham();
        }
    }

    private void moThongKeDoanhThu() {
        System.out.println(">>> NÚT THỐNG KÊ ĐÃ ĐƯỢC NHẤN <<<");

        Container top = getTopLevelAncestor();
        System.out.println("getTopLevelAncestor() trả về: " + top);  // debug quan trọng

        Frame ownerFrame = null;

        if (top instanceof Frame) {
            ownerFrame = (Frame) top;
        } else if (top != null) {
            // Nếu là Window khác (JDialog, ...) thì thử ép kiểu hoặc tìm frame cha
            ownerFrame = (Frame) SwingUtilities.getWindowAncestor(top);
        }

        if (ownerFrame == null) {
            // Fallback cuối: tìm tất cả frame đang mở và lấy cái visible/active
            Frame[] allFrames = Frame.getFrames();
            for (Frame f : allFrames) {
                if (f.isVisible() && f.isActive()) {
                    ownerFrame = f;
                    System.out.println("Fallback: dùng frame active = " + f.getTitle());
                    break;
                }
            }
        }

        if (ownerFrame == null) {
            JOptionPane.showMessageDialog(this,
                    "Không tìm thấy Frame cha nào để mở dialog thống kê.\n"
                            + "Vui lòng kiểm tra hierarchy hoặc dùng cách truyền owner từ ngoài.",
                    "Lỗi Mở Thống Kê", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu đến đây → có ownerFrame → mở dialog
        new ThongKeDoanhThu_GUI(ownerFrame, thongKeBus);
        System.out.println("Đã mở ThongKeDoanhThu_GUI với owner: " + ownerFrame.getTitle());
    }

    private void xuatExcel() {
        ArrayList<SanPham_DTO> list = sanPhamBus.getAllSanPham();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Excel");
        fileChooser.setSelectedFile(new java.io.File("DanhSachSanPham.xlsx"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            String[] columns = {"Mã SP", "Tên SP", "Mã DM", "Số lượng", "Giá nhập", "Giá bán"};
            DefaultTableModel tempModel = new DefaultTableModel(columns, 0);
            for (SanPham_DTO sp : list) {
                tempModel.addRow(new Object[]{
                        sp.getMaSP(),
                        sp.getTenSP(),
                        sp.getMaDM(),
                        sp.getMoTa(),
                        sp.getGiaBan(),
                        sp.getDonVi(),
                        sp.getSoLuongTon(),
                        sp.getMaDM(),
                        sp.getMaKhuyenMai(),
                        sp.getViTri()
                });
            }
            JTable tempTable = new JTable(tempModel);
            Utils.EportExcel.exportTablePNToExcel(tempTable, filePath);
        }
    }

    private void xuatPDF() {
        if (tablePanel.getTable().getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có dữ liệu để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file PDF");
        fileChooser.setSelectedFile(new java.io.File("DanhSachSanPham.pdf"));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".pdf")) {
                filePath += ".pdf";
            }
            ExportPDFSP.exportTableToPDF(tablePanel.getTable(), filePath, "DANH SÁCH SẢN PHẨM");
        }
    }

    private void xemSPDaXoa() {
        Window parent = SwingUtilities.getWindowAncestor(this);
        if (parent == null) return;

        new SanPhamXoa_GUI((Frame) parent, sanPhamBus, tablePanel);
    }
}