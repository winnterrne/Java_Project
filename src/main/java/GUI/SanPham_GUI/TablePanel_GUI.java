package GUI.SanPham_GUI;
import BUS.SanPham_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TablePanel_GUI extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private SanPham_BUS bus;

    public TablePanel_GUI(SanPham_BUS bus) {
        this.bus = bus;
        setLayout(new BorderLayout());

        String[] columns = {"Mã SP", "Tên sản phẩm", "Mô tả", "Giá bán", "Tồn kho", "Mã DM", "Vị trí"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void loadSanPham() {
        model.setRowCount(0);
        ArrayList<SanPham_DTO> list = bus.getAllSanPham();
        for (SanPham_DTO sp : list) {
            String moTaShort = (sp.getMoTa() != null && sp.getMoTa().length() > 50)
                    ? sp.getMoTa().substring(0, 47) + "..." : sp.getMoTa();
            model.addRow(new Object[]{
                    sp.getMaSP(), sp.getTenSP(), moTaShort,
                    sp.getGiaBan(), sp.getSoLuongTon(), sp.getMaDM(), sp.getViTri()
            });
        }
    }

    public void timKiem(String keyword) {
        model.setRowCount(0);
        ArrayList<SanPham_DTO> list = bus.getAllSanPham();
        for (SanPham_DTO sp : list) {
            if (sp.getTenSP().toLowerCase().contains(keyword.toLowerCase()) ||
                sp.getMaSP().toLowerCase().contains(keyword.toLowerCase())) {
                model.addRow(new Object[]{
                        sp.getMaSP(), sp.getTenSP(), sp.getMoTa(),
                        sp.getGiaBan(), sp.getSoLuongTon(), sp.getMaDM(), sp.getViTri()
                });
            }
        }
    }

    public JTable getTable() { return table; }
}