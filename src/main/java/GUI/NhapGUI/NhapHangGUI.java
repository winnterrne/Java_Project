package GUI.NhapGUI; //source root: folder java
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class NhapHangGUI extends JFrame {
    JPanel lPanel, lbPanel, rPanel, rbPanel, pSearch, pTinhTongTien;
    JTable tbSanPham, tbChiTiet;
    DefaultTableModel modelSanPham, modelChiTiet;
    JScrollPane spSanPham, spChiTiet;

    //left Panel
    JTextField tfTimKiem, tfSoLuong;
    JButton btnTimKiem, btnLamMoi;
    JLabel lSoLuong;
    JButton btnThem;

    //right Panel
    JLabel lMaPN, lNhaCC, lNguoiTaoPhieu, lTongTien, lTinhTongTien;
    JTextField tfMaPN, tfNguoiTaoPhieu;
    JComboBox cbNhaCC;
    JButton btnNhapExcel, btnSuaSL, btnXoaSP, btnNhapHang;

    SanPham_BUS spBUS = new  SanPham_BUS();
    PhieuNhap_BUS pnBUS = new  PhieuNhap_BUS();
    NhaCungCap_BUS nccBUS = new NhaCungCap_BUS();

    public NhapHangGUI() {
        initGUI();
        loadTableSanPham();
        loadNCC();
        addEvents();
    }

    public void initGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);
        setTitle("Quản lý nhập hàng");
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
        searchgbc.gridheight = 1;
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
        lPanel.add(pSearch, lgbc);

        lgbc.gridx = 0;
        lgbc.gridy = 1;
        lgbc.gridwidth = 1;
        lgbc.insets = new Insets(5, 5, 5, 5);
        String spCol[] = {"Mã SP", "Tên SP", "Đơn giá", "Số lượng"};
        modelSanPham = new DefaultTableModel(spCol, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbSanPham = new JTable(modelSanPham);
        tbSanPham.setRowHeight(20);
        tbSanPham.getTableHeader().setReorderingAllowed(false);
        TableColumn colTenSP = tbSanPham.getColumnModel().getColumn(1);
        colTenSP.setMinWidth(200);
        spSanPham = new JScrollPane(tbSanPham);
        lPanel.add(spSanPham, lgbc);


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
        tfMaPN = new JTextField(15);
        tfMaPN.setText(pnBUS.taoMaPN());
        tfMaPN.setEditable(false);
        rPanel.add(tfMaPN, rgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 1;
        lNhaCC = new JLabel("Nhà cung cấp");
        rPanel.add(lNhaCC, rgbc);

        rgbc.gridx = 1;
        rgbc.gridy = 1;
        cbNhaCC = new JComboBox();
        rPanel.add(cbNhaCC, rgbc);

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
        String chiTietCol[] = {"STT", "Mã SP", "Tên SP", "Số lượng", "Đơn giá"};
        modelChiTiet = new DefaultTableModel(chiTietCol, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbChiTiet = new JTable(modelChiTiet);
        tbChiTiet.setRowHeight(20);
        tbChiTiet.getTableHeader().setReorderingAllowed(false);
        TableColumn colSTT = tbChiTiet.getColumnModel().getColumn(0);
        colSTT.setMaxWidth(50);
        TableColumn colChiTiet = tbChiTiet.getColumnModel().getColumn(2);
        colChiTiet.setMinWidth(200);
        spChiTiet = new JScrollPane(tbChiTiet);
        rPanel.add(spChiTiet, rgbc);

        rbPanel = new JPanel(new GridBagLayout());
        GridBagConstraints rbgbc =  new GridBagConstraints();

        rbgbc.fill = GridBagConstraints.BOTH;
        rbgbc.insets = new Insets(5, 5, 5, 5);

        rbgbc.gridx = 0;
        rbgbc.gridy = 0;
        btnSuaSL = new JButton("Sửa số lượng");
        rbPanel.add(btnSuaSL, rbgbc);

        rbgbc.gridx = 1;
        rbgbc.gridy = 0;
        btnXoaSP = new JButton("Xóa sản phẩm");
        rbPanel.add(btnXoaSP, rbgbc);

        rbgbc.gridx = 0;
        rbgbc.gridy = 1;
        pTinhTongTien = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        lTongTien = new JLabel("Tổng tiền: ");
        lTongTien.setFont(new Font("Times New Roman", Font.BOLD, 15));
        lTinhTongTien = new JLabel("");
        lTinhTongTien.setFont(new Font("Tahoma", Font.BOLD, 12));
        lTinhTongTien.setForeground(Color.RED);
        pTinhTongTien.add(lTongTien);
        pTinhTongTien.add(lTinhTongTien);
        rbPanel.add(pTinhTongTien, rbgbc);

        rbgbc.gridx = 1;
        rbgbc.gridy = 1;
        btnNhapHang = new JButton("Nhập hàng");
        rbPanel.add(btnNhapHang, rbgbc);

        rgbc.gridx = 0;
        rgbc.gridy = 4;
        rPanel.add(rbPanel, rgbc);


        add(lPanel);
        add(rPanel);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void loadTableSanPham() {
        ArrayList<SanPham_DTO> ListSP = spBUS.getAllSanPham();
        modelSanPham.setRowCount(0);
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
        for (SanPham_DTO sp : ListSP) {
            String GiaBanFormatted = nf.format(sp.getGiaBan());
            modelSanPham.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                GiaBanFormatted,
                sp.getSoLuongTon()
            });
        }
    }

    public void loadTableSanPham(String keyword) {
        ArrayList<SanPham_DTO> listSP = spBUS.timSanPhamTheoTen(keyword);
        modelSanPham.setRowCount(0);
        for(SanPham_DTO sp : listSP) {
            NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
            String GiaBanFormatted = nf.format(sp.getGiaBan());
            modelSanPham.addRow(new Object[]{
                    sp.getMaSP(),
                    sp.getTenSP(),
                    GiaBanFormatted,
                    sp.getSoLuongTon()
            });
        }
    }

    public void loadNCC() {
        ArrayList<NhaCungCap_DTO> ncc = nccBUS.getAllNhaCungCap();
        cbNhaCC.removeAllItems();
        for(NhaCungCap_DTO nc : ncc) {
            cbNhaCC.addItem(nc);
        }
    }

    public void addEvents() {
        btnTimKiem.addActionListener(e -> {
            String keyword = tfTimKiem.getText().trim();
            if(keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sản phẩm cần tìm");
                return;
            }
            loadTableSanPham(keyword);
        });

        btnLamMoi.addActionListener(e -> {
            tfTimKiem.setText("");
            loadTableSanPham();
        });

        tbSanPham.getSelectionModel().addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()) {
                int selectedRow = tbSanPham.getSelectedRow();
                if(selectedRow >= 0) {
                    tfSoLuong.requestFocus();
                    tfSoLuong.selectAll();
                }
            }
        });

        btnThem.addActionListener(e -> {
            int selectedRow = tbSanPham.getSelectedRow();
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

            String maSP = tbSanPham.getValueAt(selectedRow, 0).toString().trim();
            String tenSP = tbSanPham.getValueAt(selectedRow, 1).toString().trim();
            String donGia = tbSanPham.getValueAt(selectedRow, 2).toString().trim();

            boolean found = false;
            for(int i = 0; i < modelChiTiet.getRowCount(); i++) {
                String maSPTrongChiTiet = modelChiTiet.getValueAt(i, 1).toString().trim();
                if(maSP.equals(maSPTrongChiTiet)) {
                    int soLuongCu = Integer.parseInt(modelChiTiet.getValueAt(i, 3).toString());
                    modelChiTiet.setValueAt(soLuongCu + soLuong, i, 3);
                    found = true;
                    break;
                }
            }

            if(!found) {
                int stt = modelChiTiet.getRowCount() + 1;
                modelChiTiet.addRow(new Object[]{stt, maSP, tenSP, soLuong, donGia});
            }
            tfSoLuong.setText("");
            lTinhTongTien.setText(tinhTongTien());
        });

        btnSuaSL.addActionListener(e -> {
            int selectedRow = tbChiTiet.getSelectedRow();
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
                modelChiTiet.setValueAt(soLuongMoi, selectedRow, 3);
                lTinhTongTien.setText(tinhTongTien());
            } catch(NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên");
            }
        });

        btnXoaSP.addActionListener(e -> {
            int selectedRow = tbChiTiet.getSelectedRow();
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
                modelChiTiet.removeRow(selectedRow);
                for(int i = 0; i < modelChiTiet.getRowCount(); i++) {
                    modelChiTiet.setValueAt(i+1, i, 0);
                }
                lTinhTongTien.setText(tinhTongTien());
            }
        });

        btnNhapHang.addActionListener(e -> {
            try {
                String maPN = tfMaPN.getText();
                LocalDate ngayNhapHang =  LocalDate.now();
                NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
                Number number = nf.parse(lTinhTongTien.getText().trim());
                double tongTien = number.doubleValue();
                NhaCungCap_DTO ncc = (NhaCungCap_DTO) cbNhaCC.getSelectedItem();
                String maNCC = ncc.getMaNCC();
                String maNV = "NV001";
                PhieuNhap_DTO pn = new PhieuNhap_DTO(maPN, ngayNhapHang, tongTien, maNCC, maNV);
                ArrayList<ChiTietPhieuNhap_DTO> listCT = new ArrayList<>();

                for(int i = 0; i < modelChiTiet.getRowCount(); i++) {
                    String maSP = tbChiTiet.getValueAt(i, 1).toString().trim();
                    int soLuong = Integer.parseInt(modelChiTiet.getValueAt(i, 3).toString());
                    String giaNhapStr =  modelChiTiet.getValueAt(i, 4).toString().trim();
                    Number numberTong = nf.parse(giaNhapStr);
                    double giaNhap = numberTong.doubleValue();


                    ChiTietPhieuNhap_DTO ct = new ChiTietPhieuNhap_DTO(maPN, maSP, soLuong, giaNhap, ngayNhapHang, ngayNhapHang, ngayNhapHang);
                    listCT.add(ct);
                }
                boolean result = pnBUS.themPhieuNhapVaChiTiet(pn, listCT);
                if(result) {
                    JOptionPane.showMessageDialog(this, "Nhập hàng thành công");
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập hàng thất bại");
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
        for(int i = 0; i < modelChiTiet.getRowCount(); i++) {
            soLuong = Integer.parseInt(modelChiTiet.getValueAt(i, 3).toString());
            donGia = Double.parseDouble(modelChiTiet.getValueAt(i, 4).toString().replace(".", ""));
            tongTien = tongTien + soLuong*donGia;
        }
        NumberFormat nf =  NumberFormat.getInstance(new  Locale("vi", "VN"));
        String numberFormatted =  nf.format(tongTien);
        return numberFormatted;
    }


    public static void main(String[] args) {
        new NhapHangGUI();
    }
}