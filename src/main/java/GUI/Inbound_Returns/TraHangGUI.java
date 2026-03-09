package GUI.Inbound_Returns;
import BUS.*;
import DAO.ChiTietPhieuNhap_DAO;
import DAO.SanPham_DAO;
import DTO.*;

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
import java.util.Objects;

public class TraHangGUI extends JPanel {
    JPanel lPanel, lbPanel, pMaPN, rPanel, rbPanel, pSearch, pTinhTongTien;
    JTable tbChiTietNhap, tbChiTietTra;
    DefaultTableModel modelChiTietTra, modelChiTietNhap;
    JScrollPane spChiTietNhap, spChiTietTra;

    
    JTextField tfTimKiem, tfSoLuong, tfNCC;
    JButton btnTimKiem, btnLamMoi;
    JLabel lSoLuong, lMaPN;
    JButton btnThem;
    JComboBox cbMaPN;

    
    JLabel lMaPT, lNhaCC, lNguoiTaoPhieu, lTongTien, lTinhTongTien;
    JTextField tfNguoiTaoPhieu, tfMaPT;

    JButton btnSuaSL, btnXoaSP, btnTraHang;

    ChiTietPhieuNhap_BUS ctpnBUS = new ChiTietPhieuNhap_BUS();
    PhieuNhap_BUS pnBUS = new PhieuNhap_BUS();
    PhieuTra_BUS ptBUS = new PhieuTra_BUS();
    SanPham_BUS spBUS = new SanPham_BUS();
    public TraHangGUI() {
        initGUI();
        loadMaPN();
        addEvents();
        resetForm();
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
        lgbc.fill = GridBagConstraints.HORIZONTAL;
        lgbc.weightx = 1.0;
        lgbc.gridwidth = 2;
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
        pMaPN = new JPanel(new BorderLayout(10, 10));
        lMaPN = new JLabel("Mã phiếu nhập");
        cbMaPN = new JComboBox();
        pMaPN.add(lMaPN, BorderLayout.WEST);
        pMaPN.add(cbMaPN, BorderLayout.CENTER);
        lPanel.add(pMaPN, lgbc);


        lgbc.gridx = 0;
        lgbc.gridy = 2;
        lgbc.weightx = 1.0;
        lgbc.weighty = 1.0;
        lgbc.insets = new Insets(5, 5, 5, 5);
        String spCol[] = {"STT", "Mã SP", "Tên sản phẩm", "Giá nhập", "Số lượng nhập", "Số lượng tồn"};
        modelChiTietNhap = new DefaultTableModel(spCol, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbChiTietNhap = new JTable(modelChiTietNhap);
        tbChiTietNhap.getTableHeader().setPreferredSize(new Dimension(
                tbChiTietNhap.getTableHeader().getWidth(), 35));
        tbChiTietNhap.setRowHeight(20);
        tbChiTietNhap.getTableHeader().setReorderingAllowed(false);
        TableColumn colTenSP = tbChiTietNhap.getColumnModel().getColumn(2);
        colTenSP.setMinWidth(120);
        TableColumn colGiaNhap  = tbChiTietNhap.getColumnModel().getColumn(3);
        colGiaNhap.setMinWidth(70);
        TableColumn colSLNhap =  tbChiTietNhap.getColumnModel().getColumn(4);
        colSLNhap.setMinWidth(90);
        TableColumn colSLTon = tbChiTietNhap.getColumnModel().getColumn(5);
        colSLTon.setMinWidth(90);
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
        lgbc.gridy = 3;
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
        lMaPT = new JLabel("Mã phiếu Trả");
        rPanel.add(lMaPT, rgbc);

        rgbc.gridx = 1;
        rgbc.gridy = 0;
        tfMaPT = new JTextField(15);
        tfMaPT.setEditable(false);
        tfMaPT.setText(ptBUS.taoMaPhieuTraTuDong());
        rPanel.add(tfMaPT, rgbc);

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
        btnTraHang = new JButton("Tạo phiếu trả");
        rbPanel.add(btnTraHang, rbgbc);

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
        PhieuNhap_DTO pn  = (PhieuNhap_DTO) cbMaPN.getSelectedItem();
        if(pn==null) return;
        String maPN = pn.getMaPhieuNhap();
        ArrayList<ChiTietPhieuNhap_DTO> listCTPN = ctpnBUS.timSanPhamTheoTenTrongCTPN(maPN,keyword);

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

    public void resetForm() {
        tfMaPT.setText(ptBUS.taoMaPhieuTraTuDong());
        lTinhTongTien.setText("");
        modelChiTietTra.setRowCount(0);
        if(cbMaPN.getItemCount()>0){
            loadTableChiTietPN();
            loadNCC();
        }
    }

    private void updateThongTinPhieuNhap(String maPN) {

        String tenNCC = pnBUS.getTenNCCByMaPN(maPN);
        tfNCC.setText(tenNCC);

        loadTableChiTietPN();

        modelChiTietTra.setRowCount(0);
        lTinhTongTien.setText("");
    }

    public void addEvents() {
        btnTimKiem.addActionListener(e -> {
            String keyword = tfTimKiem.getText().trim();
            if (keyword.isEmpty()) {
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
            String maPNSelected = Objects.requireNonNull(cbMaPN.getSelectedItem()).toString();
            updateThongTinPhieuNhap(maPNSelected);
        });

        tbChiTietNhap.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tbChiTietNhap.getSelectedRow();
                if (selectedRow >= 0) {
                    tfSoLuong.requestFocus();
                    tfSoLuong.selectAll();
                }
            }
        });

        btnThem.addActionListener(e -> {
            int selectedRow = tbChiTietNhap.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần thêm");
                return;
            }

            int soLuongNhapThem;
            try {
                soLuongNhapThem = Integer.parseInt(tfSoLuong.getText().trim());
                if (soLuongNhapThem <= 0) {
                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng hợp lệ");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên lớn hơn 0");
                return;
            }

            int soLuongNhap = Integer.parseInt(tbChiTietNhap.getValueAt(selectedRow, 4).toString());
            int soLuongTon = Integer.parseInt(tbChiTietNhap.getValueAt(selectedRow, 5).toString());

            String maSP = tbChiTietNhap.getValueAt(selectedRow, 1).toString().trim();
            String tenSP = tbChiTietNhap.getValueAt(selectedRow, 2).toString().trim();
            String donGia = tbChiTietNhap.getValueAt(selectedRow, 3).toString().trim();

            boolean found = false;
            for (int i = 0; i < modelChiTietTra.getRowCount(); i++) {
                String maSPTrongChiTietTra = modelChiTietTra.getValueAt(i, 1).toString().trim();
                if (maSP.equals(maSPTrongChiTietTra)) {
                    int soLuongTra = Integer.parseInt(modelChiTietTra.getValueAt(i, 3).toString());
                    int tongSoLuongTra = soLuongTra + soLuongNhapThem;

                    if (tongSoLuongTra > soLuongNhap) {
                        JOptionPane.showMessageDialog(this,
                                "Tổng số lượng trả không được lớn hơn số lượng nhập (" + soLuongNhap + ")");
                        return;
                    }
                    if (tongSoLuongTra > soLuongTon) {
                        JOptionPane.showMessageDialog(this,
                                "Tổng số lượng trả không được lớn hơn số lượng tồn (" + soLuongTon + ")");
                        return;
                    }

                    modelChiTietTra.setValueAt(tongSoLuongTra, i, 3);
                    found = true;
                    break;
                }
            }

            if (!found) {
                if (soLuongNhapThem > soLuongNhap) {
                    JOptionPane.showMessageDialog(this,
                            "Số lượng trả không được lớn hơn số lượng nhập (" + soLuongNhap + ")");
                    return;
                }
                if (soLuongNhapThem > soLuongTon) {
                    JOptionPane.showMessageDialog(this,
                            "Số lượng trả không được lớn hơn số lượng tồn (" + soLuongTon + ")");
                    return;
                }

                int stt = modelChiTietTra.getRowCount() + 1;
                modelChiTietTra.addRow(new Object[]{stt, maSP, tenSP, soLuongNhapThem, donGia});
            }

            tfSoLuong.setText("");
            lTinhTongTien.setText(tinhTongTien());
        });


        btnSuaSL.addActionListener(e -> {
            int selectedRow = tbChiTietTra.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa số lượng");
                return;
            }
            String input = JOptionPane.showInputDialog(this, "Nhập số lượng mới: ",
                    "Thay đổi số lượng", JOptionPane.QUESTION_MESSAGE);
            if (input == null) return;
            try {
                int soLuongMoi = Integer.parseInt(input.trim());
                if (soLuongMoi <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
                    return;
                }
                String maSP = modelChiTietTra.getValueAt(selectedRow, 1).toString().trim();
                PhieuNhap_DTO pn = (PhieuNhap_DTO) cbMaPN.getSelectedItem();
                if(pn==null) return;
                String maPN = pn.getMaPhieuNhap();
                int soLuongNhap = pnBUS.getSoLuongNhap(maPN, maSP);
                if (soLuongMoi > soLuongNhap) {
                    JOptionPane.showMessageDialog(this, "Số lượng trả không được vượt quá số lượng nhập!");
                    return;
                }
                int soLuongTon = spBUS.getSoLuongTon(maSP);
                if (soLuongMoi > soLuongTon) {
                    JOptionPane.showMessageDialog(this, "Số lượng trả không được vượt quá số lượng tồn kho!");
                    return;
                }

                modelChiTietTra.setValueAt(soLuongMoi, selectedRow, 3);
                lTinhTongTien.setText(tinhTongTien());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
            }
        });

        btnXoaSP.addActionListener(e -> {
            int selectedRow = tbChiTietTra.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có muốn xóa sản phẩm này",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                modelChiTietTra.removeRow(selectedRow);
                for (int i = 0; i < modelChiTietTra.getRowCount(); i++) {
                    modelChiTietTra.setValueAt(i + 1, i, 0);
                }
                lTinhTongTien.setText(tinhTongTien());
            }
        });

        btnTraHang.addActionListener(e -> {
            try {
                if (modelChiTietTra.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "Bạn phải thêm ít nhất một sản phẩm để trả!");
                    return;
                }

                String lyDo = JOptionPane.showInputDialog(this, "Nhập lý do trả hàng:");
                if (lyDo == null || lyDo.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Bạn phải nhập lý do trả hàng!");
                    return;
                }
                String maPT = tfMaPT.getText().trim();
                PhieuNhap_DTO pn = (PhieuNhap_DTO) cbMaPN.getSelectedItem();
                if (pn == null) return;
                String maPN = pn.getMaPhieuNhap();
                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                Number number = nf.parse(lTinhTongTien.getText().trim());
                double tongTien = number.doubleValue();
                LocalDate ngayTra = LocalDate.now();
                PhieuTra_DTO pt = new PhieuTra_DTO(
                        maPT,
                        lyDo,
                        "NV01",
                        pn.getMaNCC(),
                        maPN,
                        tongTien,
                        ngayTra
                );

                ArrayList<ChiTietPhieuTra_DTO> listCT =  new ArrayList<>();
                for(int i = 0; i < modelChiTietTra.getRowCount(); i++) {
                    String maSP =  modelChiTietTra.getValueAt(i, 1).toString();
                    int soLuong = Integer.parseInt(modelChiTietTra.getValueAt(i, 3).toString());
                    String donGiaStr = modelChiTietTra.getValueAt(i, 4).toString().trim();
                    Number numberDonGia = nf.parse(donGiaStr);
                    double donGia =  numberDonGia.doubleValue();
                    ChiTietPhieuTra_DTO ct = new ChiTietPhieuTra_DTO(maPT, maSP, soLuong, donGia, ngayTra);
                    listCT.add(ct);
                }
                boolean result = ptBUS.taoPhieuTraVaChiTiet(pt, listCT);
                if(result) {
                    JOptionPane.showMessageDialog(this, "Trả hàng thành công");
                    loadTableChiTietPN();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Trả hàng thất bại");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi dữ liệu");
            }
        });
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
        return nf.format(tongTien);
    }

}