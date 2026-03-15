package GUI.Product;

import BUS.ThongKe_BUS;
import DTO.ThongKeDoanhThu_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.style.Styler.ChartTheme;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDoanhThu_GUI extends JDialog {

    private ThongKe_BUS thongKeBus;
    private JComboBox<Integer> cbThang, cbNam;
    private JPanel chartPanel;

    public ThongKeDoanhThu_GUI(Frame owner, ThongKe_BUS thongKeBus) {
        super(owner, "Thống kê doanh thu theo tháng", true);
        this.thongKeBus = thongKeBus;
        this.cbThang = new JComboBox<>();
        this.cbNam = new JComboBox<>();

        setSize(900, 650);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JPanel selectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        selectPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));

        for (int i = 1; i <= 12; i++) cbThang.addItem(i);
        cbThang.setSelectedItem(java.time.LocalDate.now().getMonthValue());

        for (int i = 2023; i <= 2026; i++) cbNam.addItem(i);
        cbNam.setSelectedItem(java.time.LocalDate.now().getYear());

        selectPanel.add(new JLabel("Tháng:"));
        selectPanel.add(cbThang);
        selectPanel.add(new JLabel("Năm:"));
        selectPanel.add(cbNam);

        JButton btnXem = new JButton("Xem thống kê");
        btnXem.setPreferredSize(new Dimension(150, 35));
        selectPanel.add(btnXem);

        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"Mã SP", "Tên Sản Phẩm", "Số lượng bán", "Thành tiền"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(table);

        JLabel lblTong = new JLabel("TỔNG DOANH THU: 0 ₫");
        lblTong.setFont(new Font("Arial", Font.BOLD, 18));
        lblTong.setForeground(new Color(0, 102, 204));
        lblTong.setHorizontalAlignment(SwingConstants.CENTER);
        lblTong.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setPreferredSize(new Dimension(800, 350));
        chartPanel.setBorder(BorderFactory.createTitledBorder("Biểu đồ bán hàng"));

        resultPanel.add(chartPanel, BorderLayout.NORTH);
        resultPanel.add(scroll, BorderLayout.CENTER);
        resultPanel.add(lblTong, BorderLayout.SOUTH);

        resultPanel.setVisible(false);

        btnXem.addActionListener(e -> {

            try {
                int thang = (int) cbThang.getSelectedItem();
                int nam = (int) cbNam.getSelectedItem();

                List<ThongKeDoanhThu_DTO> list = thongKeBus.getThongKeDoanhThu(thang, nam);
                double tongDoanhThu = thongKeBus.tongDoanhThuTheoThang(thang, nam);

                model.setRowCount(0);

                for (ThongKeDoanhThu_DTO tk : list) {
                    model.addRow(new Object[]{
                            tk.getMaSP(),
                            tk.getTenSP(),
                            tk.getSoLuong(),
                            String.format("%,.0f ₫", tk.getTongTien())
                    });
                }

                lblTong.setText("TỔNG DOANH THU THÁNG " + thang + "/" + nam + ": "
                        + String.format("%,.0f ₫", tongDoanhThu));

                chartThongKe();
                
                resultPanel.setVisible(true);
                resultPanel.revalidate();   
                resultPanel.repaint();      

                
                if (scroll != null) {
                    scroll.revalidate();
                    scroll.repaint();
                }

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

    public void chartThongKe() {
        int thang = (int) cbThang.getSelectedItem();
        int nam = (int) cbNam.getSelectedItem();

        List<String> tenSP = new ArrayList<>();
        List<Integer> soLuong = new ArrayList<>();

        List<ThongKeDoanhThu_DTO> list = thongKeBus.getThongKeDoanhThu(thang, nam);

        if (list == null || list.isEmpty()) {
            JOptionPane.showMessageDialog(
                this,
                "Tháng này chưa được cập nhật,\nchưa có dữ liệu doanh thu.",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        for (ThongKeDoanhThu_DTO tk : list) {
            tenSP.add(tk.getTenSP());
            soLuong.add(tk.getSoLuong());
        }

        PieChart chart = new PieChartBuilder()
            .width(800)
            .height(350)
            .title("Tỷ lệ bán theo sản phẩm")
            .theme(ChartTheme.Matlab)
            .build();

        chart.getStyler().setLabelsVisible(false);
        for (ThongKeDoanhThu_DTO tk : list) {
            chart.addSeries(tk.getTenSP(), tk.getSoLuong());
        }

        chartPanel.removeAll();
        chartPanel.add(new org.knowm.xchart.XChartPanel<>(chart), BorderLayout.CENTER);

        chartPanel.revalidate();
        chartPanel.repaint();
    }
}