package GUI.SanPham_GUI;

import BUS.SanPham_BUS;
import BUS.ThongKe_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
public class SanPhamMain_GUI extends JFrame {

    private SanPham_BUS sanPhamBus = new SanPham_BUS();
    private ThongKe_BUS thongKeBus = new ThongKe_BUS();

    private ToolBarPanel_GUI toolBar;
    private Search_GUI searchPanel;
    private TablePanel_GUI tablePanel;

    public SanPhamMain_GUI() {
        setTitle("Quản Lý Sản Phẩm");
        setSize(1250, 720);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        toolBar = new ToolBarPanel_GUI();
        searchPanel = new Search_GUI();
        tablePanel = new TablePanel_GUI(sanPhamBus);

        add(toolBar, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(searchPanel, BorderLayout.NORTH);
        center.add(tablePanel, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        tablePanel.loadSanPham();

        attachListeners();

        setVisible(true);
    }

    private void attachListeners() {
        toolBar.getBtnLamMoi().addActionListener(e -> tablePanel.loadSanPham());
        toolBar.getBtnThem().addActionListener(e -> themSanPham());
        toolBar.getBtnSua().addActionListener(e -> suaSanPham());
        toolBar.getBtnXoa().addActionListener(e -> xoaSanPham());
        toolBar.getBtnChiTiet().addActionListener(e -> xemChiTiet());
        toolBar.getBtnExport().addActionListener(e -> xuatExcel());
        toolBar.getBtnThongKe().addActionListener(e -> moThongKeDoanhThu());

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
        ThemSP_GUI dialog = new ThemSP_GUI(this, sanPhamBus);
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

        SuaSP_GUI dialog = new SuaSP_GUI(this, sanPhamBus, sp);
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

        new ChiTietSP_GUI(this, sp);
    }

    private void xoaSanPham() {
        int row = tablePanel.getTable().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa!");
            return;
        }

        String maSP = (String) tablePanel.getTable().getModel().getValueAt(row, 0);
        XoaSP_GUI dialog = new XoaSP_GUI(this, sanPhamBus, maSP);
        dialog.setVisible(true);
        if (dialog.isSuccess()) {
            tablePanel.loadSanPham();
        }
    }

    private void xuatExcel() {
        ArrayList<SanPham_DTO> list = sanPhamBus.getAllSanPham();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không có sản phẩm để xuất!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        sanPhamBus.exportToExcel(list);
    }

    private void moThongKeDoanhThu() {
        ThongKeDoanhThu_GUI dialog = new ThongKeDoanhThu_GUI(this, thongKeBus);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SanPhamMain_GUI::new);
    }
}