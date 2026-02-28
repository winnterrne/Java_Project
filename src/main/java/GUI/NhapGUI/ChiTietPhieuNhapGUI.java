package GUI.NhapGUI;

import BUS.ChiTietPhieuNhap_BUS;
import BUS.PhieuNhap_BUS;
import DAO.ChiTietPhieuNhap_DAO;
import DAO.PhieuNhap_DAO;
import DTO.ChiTietPhieuNhap_DTO;
import DTO.PhieuNhap_DTO;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ChiTietPhieuNhapGUI extends JFrame {
    JPanel topPanel, tiltlePanel, contentPanel, centerPanel, botPanel, pTongTien;

    JLabel lTitle, lTongTien, lTinhTongTien;
    JButton btnXuatPDF;

    JTable tbSanPham;
    DefaultTableModel modelSanPham;
    JScrollPane spSanPham;

    private String maPN;
    PhieuNhap_DAO pnDAO = new PhieuNhap_DAO();
    PhieuNhap_BUS pnBUS = new PhieuNhap_BUS();
    PhieuNhap_DTO pnDTO;
    String tenNCC;

    ChiTietPhieuNhap_BUS ctpnBUS =  new ChiTietPhieuNhap_BUS();
    ChiTietPhieuNhap_DAO ctpnDAO = new ChiTietPhieuNhap_DAO();

    public ChiTietPhieuNhapGUI(String maPN) {
        this.maPN = maPN;
        this.pnDTO = pnDAO.getPhieuNhapbyMaPN(maPN);
        this.tenNCC = pnDAO.getTenNCCByMaPN(maPN);
        initGUI();
        loadTableChiTietPhieuNhap();
    }

    public void initGUI() {

        setTitle("Chi tiết phiếu nhập");
        setLayout(new BorderLayout(10, 10));
        topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        tiltlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        lTitle = new JLabel("CHI TIẾT PHIẾU NHẬP");
        lTitle.setFont(new Font("Times New Roman", Font.BOLD, 32));
        lTitle.setForeground(Color.white);
        tiltlePanel.add(lTitle);
        gbc.gridx = 0;
        gbc.gridy = 0;
        tiltlePanel.setBackground(Color.BLUE);
        topPanel.add(tiltlePanel, gbc);

        contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc_contentPanel = new GridBagConstraints();
        gbc_contentPanel.insets = new Insets(20, 15, 20, 15);
        gbc_contentPanel.weightx = 1.0;
        gbc_contentPanel.fill = GridBagConstraints.BOTH;
        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 0;
        contentPanel.add(createPanel("Mã phiếu nhập: ", maPN), gbc_contentPanel);

        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 0;
        contentPanel.add(createPanel("Người tạo: ", "admin"),  gbc_contentPanel);

        gbc_contentPanel.gridx = 0;
        gbc_contentPanel.gridy = 1;
        contentPanel.add(createPanel("Nhà cung cấp: ", tenNCC), gbc_contentPanel);

        gbc_contentPanel.gridx = 1;
        gbc_contentPanel.gridy = 1;
        contentPanel.add(createPanel("Ngày tạo: ", pnDTO.getNgayNhapHang().toString()), gbc_contentPanel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(contentPanel, gbc);

        centerPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        String cols[] = {"STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Hạn sử dụng"};
        modelSanPham = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbSanPham = new JTable(modelSanPham);
        tbSanPham.setRowHeight(20);
        tbSanPham.getTableHeader().setReorderingAllowed(false);
        TableColumn col1 =  tbSanPham.getColumnModel().getColumn(0);
        col1.setMaxWidth(50);
        TableColumn col2 =  tbSanPham.getColumnModel().getColumn(2);
        col2.setMinWidth(200);
        spSanPham = new JScrollPane(tbSanPham);
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 20, 10, 20);
        spSanPham.setBorder(new CompoundBorder(line, margin));
        centerPanel.add(spSanPham);

        botPanel = new  JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        lTongTien = new JLabel("Tổng tiền: ");
        lTongTien.setFont(new Font("Times New Roman", Font.BOLD, 15));
        pTongTien.add(lTongTien);
        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        String tongTienFormmatted = nf.format(pnDTO.getTongTien());
        lTinhTongTien = new JLabel(tongTienFormmatted);
        lTinhTongTien.setFont(new Font("Tahoma", Font.BOLD, 12));
        lTinhTongTien.setForeground(Color.RED);
        pTongTien.add(lTinhTongTien);
        botPanel.add(pTongTien);
        btnXuatPDF = new JButton("Xuất PDF");
        botPanel.add(btnXuatPDF);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(botPanel, BorderLayout.SOUTH);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public JLabel createLabel(String text) {
        JLabel lblNewLabel = new JLabel(text);
        return lblNewLabel;
    }

    public JPanel createPanel(String txt1, String txt2)  {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel lb1 = new JLabel(txt1);
        JLabel lb2 = new JLabel(txt2);
        panel.add(lb1);
        panel.add(lb2);
        return panel;
    }

    public void loadTableChiTietPhieuNhap() {
        ArrayList<ChiTietPhieuNhap_DTO> listCTPN = ctpnBUS.getChiTietPhieuNhapByMaPN(maPN);

        modelSanPham.setRowCount(0);
        int stt = 1;
        for(ChiTietPhieuNhap_DTO ctp : listCTPN) {
            String tenSP = ctpnDAO.getTenSPByMaSP(ctp.getMaSP());
            modelSanPham.addRow(new Object[]{
                    stt++,
                    ctp.getMaSP(),
                    tenSP,
                    ctp.getSoLuong(),
                    ctp.getGiaNhap(),
                    ctp.getHanSuDung()
            });
        }
    }


}
