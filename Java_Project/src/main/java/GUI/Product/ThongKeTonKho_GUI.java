package GUI.Product;

import BUS.SanPham_BUS;
import DTO.SanPham_DTO;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeTonKho_GUI extends JDialog {

    private final SanPham_BUS sanPhamBus;
    private JPanel chartPanel;
    private JLabel lblTongTonKho;

    public ThongKeTonKho_GUI(Frame owner, SanPham_BUS sanPhamBus) {
        super(owner, "Thống kê số lượng tồn kho", true);
        this.sanPhamBus = sanPhamBus;

        setSize(900, 650);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        selectPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        JButton btnXem = new JButton("Xem thống kê tồn kho");
        btnXem.setPreferredSize(new Dimension(200, 40));
        btnXem.setFont(new Font("Segoe UI", Font.BOLD, 14));
        selectPanel.add(btnXem);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Mã SP", "Tên sản phẩm", "Đơn vị", "Số lượng tồn", "Giá bán", "Tổng giá trị tồn"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);

        lblTongTonKho = new JLabel("TỔNG SỐ LƯỢNG TỒN: 0");
        lblTongTonKho.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongTonKho.setForeground(new Color(0, 102, 0));
        lblTongTonKho.setHorizontalAlignment(SwingConstants.CENTER);
        lblTongTonKho.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(800, 350));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Tỷ lệ tồn kho theo sản phẩm"));

        resultPanel.add(chartPanel, BorderLayout.NORTH);
        resultPanel.add(scroll, BorderLayout.CENTER);
        resultPanel.add(lblTongTonKho, BorderLayout.SOUTH);

        resultPanel.setVisible(false);

        btnXem.addActionListener(e -> {
            try {
                List<SanPham_DTO> list = sanPhamBus.getAllSanPhamAvavilable();

                if (list == null || list.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Hiện tại không có sản phẩm nào trong kho.",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                model.setRowCount(0);
                long tongTon = 0;
                double tongGiaTriTon = 0.0;

                for (SanPham_DTO sp : list) {
                    int soLuong = sp.getSoLuongTon();
                    double giaBan = sp.getGiaBan();
                    double giaTri = soLuong * giaBan;

                    tongTon += soLuong;
                    tongGiaTriTon += giaTri;

                    model.addRow(new Object[]{
                            sp.getMaSP(),
                            sp.getTenSP(),
                            sp.getDonVi(),
                            soLuong,
                            String.format("%,.0f ₫", giaBan),
                            String.format("%,.0f ₫", giaTri)
                    });
                }

                lblTongTonKho.setText(String.format(
                        "TỔNG SỐ LƯỢNG TỒN: %,d    |    TỔNG GIÁ TRỊ TỒN KHO: %,.0f ₫",
                        tongTon, tongGiaTriTon));

                chartTonKho(list);

                resultPanel.setVisible(true);
                resultPanel.revalidate();
                resultPanel.repaint();

                scroll.revalidate();
                scroll.repaint();

                pack();
                setLocationRelativeTo(getOwner());
                revalidate();
                repaint();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton btnDong = new JButton("Đóng");
        btnDong.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnDong);

        add(selectPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void chartTonKho(List<SanPham_DTO> list) {
        List<String> tenSP = new ArrayList<>();
        List<Integer> tonKho = new ArrayList<>();

        for (SanPham_DTO sp : list) {
            int sl = sp.getSoLuongTon();
            if (sl > 0) {
                tenSP.add(sp.getTenSP());
                tonKho.add(sl);
            }
        }

        if (tonKho.isEmpty()) {
            chartPanel.removeAll();
            chartPanel.add(new JLabel("Không có dữ liệu tồn kho để vẽ biểu đồ", SwingConstants.CENTER), BorderLayout.CENTER);
            chartPanel.revalidate();
            chartPanel.repaint();
            return;
        }

        PieChart chart = new PieChartBuilder()
                .width(800)
                .height(350)
                .title("Tỷ lệ tồn kho theo sản phẩm")
                .theme(ChartTheme.Matlab)
                .build();

        chart.getStyler().setLabelsVisible(false);

        for (int i = 0; i < tenSP.size(); i++) {
            chart.addSeries(tenSP.get(i), tonKho.get(i));
        }

        chartPanel.removeAll();
        chartPanel.add(new org.knowm.xchart.XChartPanel<>(chart), BorderLayout.CENTER);

        chartPanel.revalidate();
        chartPanel.repaint();
    }
}