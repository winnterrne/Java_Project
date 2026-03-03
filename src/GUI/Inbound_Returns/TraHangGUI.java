package GUI.Inbound_Returns;
import BUS.ChiTietPhieuNhap_BUS;
import BUS.NhaCungCap_BUS;
import BUS.PhieuNhap_BUS;
import BUS.SanPham_BUS;
import DAO.ChiTietPhieuNhap_DAO;
import DAO.SanPham_DAO;
import DTO.ChiTietPhieuNhap_DTO;
import DTO.NhaCungCap_DTO;
import DTO.PhieuNhap_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;

import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class TraHangGUI extends JPanel {
    JPanel lPanel, lbPanel, rPanel, rbPanel, pSearch, pTinhTongTien;
    JTable tbChiTietNhap, tbChiTietTra;
    DefaultTableModel modelChiTietTra, modelChiTietNhap;
    JScrollPane spChiTietNhap, spChiTietTra;

    //left Panel
    JTextField tfTimKiem, tfSoLuong, tfNCC;
    JButton btnTimKiem, btnLamMoi;
    JLabel lSoLuong;
    JButton btnThem;

    //right Panel
    JLabel lMaPN, lNhaCC, lNguoiTaoPhieu, lTongTien, lTinhTongTien;
    JTextField tfNguoiTaoPhieu;
    JComboBox cbMaPN;
    JButton btnNhapExcel, btnSuaSL, btnXoaSP, btnNhapHang;

    ChiTietPhieuNhap_BUS ctpnBUS = new ChiTietPhieuNhap_BUS();
    PhieuNhap_BUS pnBUS = new  PhieuNhap_BUS();

    public TraHangGUI() {
        initGUI();
        loadMaPN();
        addEvents();
        if(cbMaPN.getItemCount()>0){
            loadTableChiTietPN();
            loadNCC();
        }
    }

    public void initGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);

        setLayout(new GridLayout(1, 2, 10, 10));
        lPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lgbc =  new GridBagConstraints();
        lgbc.fill = GridBagConstraints.BOTH;
        lgbc.weightx = 1.0;
        pSearch = new JPanel(new GridBagLayout());
        GridBagConstraints searchgbc =  new GridBagConstraints();
        searchgbc.gridx = 0;
        searchgbc.gridy = 0;
        searchgbc.gridwidth = 1;
        searchgbc.insets = new Insets(5, 5, 5, 5);
        tfTimKiem = new JTextField(20);
        pSearch.add(tfTimKiem, searchgbc);

        searchgbc.gridx = 1;
        searchgbc.gridy = 0;
        searchgbc.gridwidth = 1;
        searchgbc.insets = new Insets(5, 5, 5, 5);
        btnTimKiem = new JButton("Tìm Kiếm");
        pSearch.add(btnTimKiem,  searchgbc);

        searchgbc.gridx = 2;
        searchgbc.gridy = 0;
        searchgbc.gridwidth = 1;
        btnLamMoi = new JButton("Làm mới");
        pSearch.add(btnLamMoi,  searchgbc);
        pSearch.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED)
                ,"Tìm Kiếm"));

        lgbc.fill = GridBagConstraints.BOTH;
        lgbc.gridx = 0;
        lgbc.gridy = 0;
        lgbc.gridheight = 1;
        lPanel.add(pSearch, lgbc);

        lgbc.gridx = 0;
        lgbc.gridy = 1;
        lgbc.weighty = 1.0;
        lgbc.insets = new Insets(5, 5, 5, 5);
        String spCol[] = {"STT", "Mã SP", "Tên sản phẩm", "Giá nhập (VNĐ)", "Số lượng nhập", "Số lượng tồn kho"};
        modelChiTietNhap = new DefaultTableModel(spCol, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbChiTietNhap = new JTable(modelChiTietNhap);
        tbChiTietNhap.setRowHeight(20);
        tbChiTietNhap.getTableHeader().setReorderingAllowed(false);
        TableColumn colTenSP = tbChiTietNhap.getColumnModel().getColumn(2);
        colTenSP.setMinWidth(180);
        spChiTietNhap = new JScrollPane(tbChiTietNhap);
        lPanel.add(spChiTietNhap, lgbc);

        lbPanel = new JPanel(new GridBagLayout());
        GridBagConstraints lbgbc =  new GridBagConstraints();
        lbgbc.gridx = 0;
        lbgbc.gridy = 0;
        lSoLuong = new JLabel("Số lượng");
        lbPanel.add(lSoLuong, lbgbc);

        lbgbc.gridx = 1;
        lbgbc.gridy = 0;
        lbgbc.insets = new Insets(5, 15, 5, 15);
        tfSoLuong = new JTextField(5);
        tfSoLuong.setHorizontalAlignment(JTextField.CENTER);
        lbPanel.add(tfSoLuong, lbgbc);

        lbgbc.gridx = 2;
        lbgbc.gridy = 0;
        btnThem = new JButton("Thêm");
        lbPanel.add(btnThem, lbgbc);

        lgbc.gridx = 0;
        lgbc.gridy = 2;
        lgbc.weighty = 0;
        lgbc.insets = new Insets(25, 0, 25, 0);
        lPanel.add(lbPanel, lgbc);

        rPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rgbc =  new GridBagConstraints();
        rgbc.fill = GridBagConstraints.BOTH;
        rgbc.weightx = 1.0;
        rgbc.insets = new Insets(5, 5, 5, 5);

        rgbc.gridx = 0;
        rgbc.gridy = 0;
        lMaPN = new JLabel("Mã phiếu nhập");
        rPanel.add(lMaPN, rgbc);

        rgbc.gridx = 1;
        rgbc.gridy = 0;
        cbMaPN = new JComboBox();
        rPanel.add(cbMaPN, rgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 1;
        lNhaCC = new JLabel("Nhà cung cấp");
        rPanel.add(lNhaCC, rgbc);

        rgbc.gridx = 1;
        rgbc.gridy = 1;
        tfNCC = new JTextField(15);
        tfNCC.setEditable(false);
        tfNCC.setText("");
        rPanel.add(tfNCC, rgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 2;
        lNguoiTaoPhieu = new JLabel("Người tạo phiếu");
        rPanel.add(lNguoiTaoPhieu, rgbc);

        rgbc.gridx = 1;
        rgbc.gridy = 2;
        tfNguoiTaoPhieu = new JTextField(15);
        tfNguoiTaoPhieu.setEditable(false);
        tfNguoiTaoPhieu.setText("admin");
        rPanel.add(tfNguoiTaoPhieu, rgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 3;
        rgbc.insets = new Insets(5, 5, 5, 5);
        rgbc.gridwidth = 2;
        rgbc.weighty = 1.0;
        String chiTietCol[] = {"STT", "Mã SP", "Tên SP", "Số lượng trả", "Đơn giá"};
        modelChiTietTra = new DefaultTableModel(chiTietCol, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbChiTietTra = new JTable(modelChiTietTra);
        tbChiTietTra.setRowHeight(20);
        tbChiTietTra.getTableHeader().setReorderingAllowed(false);
        TableColumn colSTT = tbChiTietTra.getColumnModel().getColumn(0);
        colSTT.setMaxWidth(50);
        TableColumn colChiTiet = tbChiTietTra.getColumnModel().getColumn(2);
        colChiTiet.setMinWidth(200);
        spChiTietTra = new JScrollPane(tbChiTietTra);
        rPanel.add(spChiTietTra, rgbc);

        rbPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rbgbc =  new GridBagConstraints();

        rbgbc.fill = GridBagConstraints.BOTH;
        rbgbc.insets = new Insets(5, 5, 5, 5);

        rbgbc.gridx = 0;
        rbgbc.gridy = 0;
        rbgbc.weightx = 0;
        rbgbc.fill = GridBagConstraints.NONE;
        btnSuaSL = new JButton("Sửa số lượng");
        rbPanel.add(btnSuaSL, rbgbc);

        rbgbc.gridx = 1;
        rbgbc.gridy = 0;
        rbgbc.fill = GridBagConstraints.NONE;
        btnXoaSP = new JButton("Xóa sản phẩm");
        rbPanel.add(btnXoaSP, rbgbc);

        rbgbc.gridx = 0;
        rbgbc.gridy = 1;
        rbgbc.gridwidth = 2;
        pTinhTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        lTongTien = new JLabel("Tổng tiền: ");
        lTongTien.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lTinhTongTien = new JLabel("");
        lTinhTongTien.setFont(new Font("Tahoma", Font.BOLD, 12));
        lTinhTongTien.setForeground(Color.RED);
        pTinhTongTien.add(lTongTien);
        pTinhTongTien.add(lTinhTongTien);
        rbPanel.add(pTinhTongTien, rbgbc);

        rbgbc.gridx = 0;
        rbgbc.gridy = 2;
        rbgbc.gridwidth = 2;
        btnNhapHang = new JButton("Trả hàng");
        rbPanel.add(btnNhapHang, rbgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 4;
        rgbc.weighty = 0;
        rPanel.add(rbPanel, rgbc);

        add(lPanel);
        add(rPanel);
        setSize(1000, 700);
        setVisible(true);

    }

    public void loadTableChiTietPN() {
        PhieuNhap_DTO pn = (PhieuNhap_DTO) cbMaPN.getSelectedItem();
        if(pn==null) return;
        String maPN = pn.getMaPhieuNhap();
        ArrayList<ChiTietPhieuNhap_DTO> listCTPN = ctpnBUS.getChiTietPhieuNhapByMaPN(maPN);

        modelChiTietNhap.setRowCount(0);
        int stt = 1;
        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(ChiTietPhieuNhap_DTO ctp : listCTPN) {
            String tenSP = ctpnBUS.getTenSPByMaSP(ctp.getMaSP());
            int soLuong = ctpnBUS.getSoLuongTonByMaSP(ctp.getMaSP());
            String giaNhapFormatted = nf.format(ctp.getGiaNhap());
            modelChiTietNhap.addRow(new Object[]{
                    stt++,
                    ctp.getMaSP(),
                    tenSP,
                    giaNhapFormatted,
                    ctp.getSoLuong(),
                    soLuong
            });
        }
    }

    public void loadTableChiTietPN(String keyword) {
        ArrayList<ChiTietPhieuNhap_DTO> listCTPN = ctpnBUS.getChiTietPhieuNhapByMaPN(keyword);

        modelChiTietNhap.setRowCount(0);
        int stt = 1;
        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(ChiTietPhieuNhap_DTO ctp : listCTPN) {
            String tenSP = ctpnBUS.getTenSPByMaSP(ctp.getMaSP());
            int soLuong = ctpnBUS.getSoLuongTonByMaSP(ctp.getMaSP());
            String giaNhapFormatted = nf.format(ctp.getGiaNhap());

            modelChiTietNhap.addRow(new Object[]{
                    stt++,
                    ctp.getMaSP(),
                    tenSP,
                    giaNhapFormatted,
                    ctp.getSoLuong(),
                    soLuong
            });
        }
    }

    public void loadMaPN() {
        ArrayList<PhieuNhap_DTO> list = pnBUS.getAllPhieuNhap();
        cbMaPN.removeAllItems();
        for (PhieuNhap_DTO pn : list) {
            cbMaPN.addItem(pn);
        }
    }

    public void loadNCC() {
        PhieuNhap_DTO pn = (PhieuNhap_DTO) cbMaPN.getSelectedItem();
        if(pn==null) return;

        tfNCC.setText(pnBUS.getTenNCCByMaPN(pn.getMaPhieuNhap()));
    }

    private void updateThongTinPhieuNhap(String maPN) {

        // 1. Cập nhật tên nhà cung cấp
        String tenNCC = pnBUS.getTenNCCByMaPN(maPN);
        tfNCC.setText(tenNCC);

        // 2. Load lại bảng bên trái
        loadTableChiTietPN(maPN);

        // 3. Reset bảng trả hàng bên phải
        modelChiTietTra.setRowCount(0);
        lTinhTongTien.setText("");
    }

    public void addEvents() {
        btnTimKiem.addActionListener(e -> {
            String keyword = tfTimKiem.getText().trim();
            if(keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm cần tìm");
                return;
            }
            loadTableChiTietPN(keyword);
        });

        btnLamMoi.addActionListener(e -> {
            tfTimKiem.setText("");
            loadTableChiTietPN();
        });

        cbMaPN.addActionListener(e -> {
            String maPNSelected = cbMaPN.getSelectedItem().toString();
            updateThongTinPhieuNhap(maPNSelected);
        });

        tbChiTietNhap.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int selectedRow = tbChiTietNhap.getSelectedRow();
                if(selectedRow >= 0) {
                    tfSoLuong.requestFocus();
                    tfSoLuong.selectAll();
                }
            }
        });

        btnThem.addActionListener(e -> {
            int selectedRow = tbChiTietNhap.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thêm");
                return;
            }
            int soLuong;
            try {
                soLuong = Integer.parseInt(tfSoLuong.getText().trim());
                if(soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ");
                    return;
                }
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
                return;
            }

            String maSP = tbChiTietNhap.getValueAt(selectedRow, 1).toString().trim();
            String tenSP = tbChiTietNhap.getValueAt(selectedRow, 2).toString().trim();
            String donGia = tbChiTietNhap.getValueAt(selectedRow, 3).toString().trim();

            boolean found = false;
            for(int i = 0; i < modelChiTietTra.getRowCount(); i++) {
                String maSPTrongChiTietNhap = modelChiTietTra.getValueAt(i, 1).toString().trim();
                if(maSP.equals(maSPTrongChiTietNhap)) {
                    int soLuongCu = Integer.parseInt(modelChiTietTra.getValueAt(i, 3).toString());
                    modelChiTietTra.setValueAt(soLuongCu + soLuong, i, 3);
                    found = true;
                    break;
                }
            }

            if(!found) {
                int stt = modelChiTietTra.getRowCount() + 1;
                modelChiTietTra.addRow(new Object[]{stt, maSP, tenSP, soLuong, donGia});
            }
            tfSoLuong.setText("");
            lTinhTongTien.setText(tinhTongTien());
        });

        btnSuaSL.addActionListener(e -> {
            int selectedRow = tbChiTietTra.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa số lượng");
                return;
            }
            String input  = JOptionPane.showInputDialog(this, "Nhập số lượng mới: ",
                    "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
            if(input == null) return;
            try {
                int soLuongMoi = Integer.parseInt(input.trim());
                if(soLuongMoi <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                    return;
                }
                modelChiTietTra.setValueAt(soLuongMoi, selectedRow, 3);
                lTinhTongTien.setText(tinhTongTien());
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
            }
        });

        btnXoaSP.addActionListener(e -> {
            int selectedRow = tbChiTietTra.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa sản phẩm này",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if(confirm == JOptionPane.YES_OPTION) {
                modelChiTietTra.removeRow(selectedRow);
                for(int i = 0; i < modelChiTietTra.getRowCount(); i++) {
                    modelChiTietTra.setValueAt(i+1, i, 0);
                }
                lTinhTongTien.setText(tinhTongTien());
            }
        });

//        btnNhapHang.addActionListener(e -> {
//            try {
//                String maPN = cbMaPN.getText();
//                LocalDate ngayNhapHang =  LocalDate.now();
//                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
//                Number number = nf.parse(lTinhTongTien.getText().trim());
//                double tongTien = number.doubleValue();
//                NhaCungCap_DTO ncc = (NhaCungCap_DTO) cbMaPN.getSelectedItem();
//                String maNCC = ncc.getMaNCC();
//                String maNV = "NV001";
//                PhieuNhap_DTO pn = new PhieuNhap_DTO(maPN, ngayNhapHang, tongTien, maNCC, maNV);
//                ArrayList<ChiTietPhieuNhap_DTO> listCT = new ArrayList<>();
//
//                for(int i = 0; i < modelChiTietTra.getRowCount(); i++) {
//                    String maSP = tbChiTiet.getValueAt(i, 1).toString().trim();
//                    int soLuong = Integer.parseInt(modelChiTiet.getValueAt(i, 3).toString());
//                    String giaNhapStr =  modelChiTiet.getValueAt(i, 4).toString().trim();
//                    Number numberTong = nf.parse(giaNhapStr);
//                    double giaNhap = numberTong.doubleValue();
//
//
//                    ChiTietPhieuNhap_DTO ct = new ChiTietPhieuNhap_DTO(maPN, maSP, soLuong, giaNhap, ngayNhapHang, ngayNhapHang, ngayNhapHang);
//                    listCT.add(ct);
//                }
//                boolean result = pnBUS.themPhieuNhapVaChiTiet(pn, listCT);
//                if(result) {
//                    JOptionPane.showMessageDialog(this, "Nhập hàng thành công");
//                } else {
//                    JOptionPane.showMessageDialog(this, "Nhập hàng thất bại");
//                }
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu");
//            }
//        });
    }

    public String tinhTongTien() {
        Double tongTien = 0.0;
        Double donGia;
        int soLuong;
        for(int i = 0; i < modelChiTietTra.getRowCount(); i++) {
            soLuong = Integer.parseInt(modelChiTietTra.getValueAt(i, 3).toString());
            donGia = Double.parseDouble(modelChiTietTra.getValueAt(i, 4).toString().replace(".", ""));
            tongTien = tongTien + soLuong*donGia;
        }
        NumberFormat nf =  NumberFormat.getInstance(new  Locale("vi", "VN"));
        String numberFormatted =  nf.format(tongTien);
        return numberFormatted;
    }

}