package GUI.Product;
import BUS.SanPham_BUS;
import DTO.SanPham_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
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
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 3:
                        return Double.class;
                    case 4:
                        return Integer.class;
                    default:
                        return String.class;
                }
            }
        };

        table = new JTable(model);
        table.setRowHeight(28);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        adjustColumnWidths();
    }

    private void adjustColumnWidths() {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);

            int preferredWidth = table.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(table, table.getColumnName(column), false, false, 0, column)
                    .getPreferredSize().width + 30;

            for (int row = 0; row < table.getRowCount(); row++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                int width = comp.getPreferredSize().width + 20;
                preferredWidth = Math.max(preferredWidth, width);
            }

            int maxWidth;
            switch (column) {
                case 0: maxWidth = 100;  break;
                case 1: maxWidth = 280;  break;
                case 2: maxWidth = 350;  break;
                case 3: maxWidth = 130;  break;
                case 4: maxWidth = 100;  break;
                case 5: maxWidth = 110;  break;
                case 6: maxWidth = 130;  break;
                default: maxWidth = 180;
            }

            preferredWidth = Math.min(preferredWidth, maxWidth);

            tableColumn.setPreferredWidth(preferredWidth);
            tableColumn.setMinWidth(preferredWidth);
            tableColumn.setMaxWidth(maxWidth);
        }
    }

    public void loadSanPham() {
        model.setRowCount(0);
        ArrayList<SanPham_DTO> list = bus.getAllSanPhamAvavilable();
        for (SanPham_DTO sp : list) {
            String moTaShort = (sp.getMoTa() != null && sp.getMoTa().length() > 100)
                    ? sp.getMoTa().substring(0, 97) + "..." : sp.getMoTa();

            model.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    moTaShort,
                    sp.getGiaBan(),
                    sp.getSoLuongTon(),
                    sp.getMaDM(),
                    sp.getViTri()
            });
        }

        SwingUtilities.invokeLater(this::adjustColumnWidths);
    }

    public void timKiem(String keyword) {
        model.setRowCount(0);
        ArrayList<SanPham_DTO> list = bus.getAllSanPham();
        for (SanPham_DTO sp : list) {
            if (sp.getTenSP().toLowerCase().contains(keyword.toLowerCase()) ||
                    sp.getMaSP().toLowerCase().contains(keyword.toLowerCase())) {

                String moTaShort = (sp.getMoTa() != null && sp.getMoTa().length() > 100)
                        ? sp.getMoTa().substring(0, 97) + "..." : sp.getMoTa();

                model.addRow(new Object[]{
                        sp.getMaSP(),
                        sp.getTenSP(),
                        moTaShort,
                        sp.getGiaBan(),
                        sp.getSoLuongTon(),
                        sp.getMaDM(),
                        sp.getViTri()
                });
            }
        }

        SwingUtilities.invokeLater(this::adjustColumnWidths);
    }

    public JTable getTable() {
        return table;
    }
}