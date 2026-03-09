package GUI.Product;

import BUS.SanPham_BUS;
import BUS.DanhMuc_BUS;
import DTO.SanPham_DTO;
import DTO.DanhMuc_DTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class ThemSP_GUI extends JDialog {

    private SanPham_BUS bus;
    private DanhMuc_BUS dmBus;
    private boolean success = false;

    private JTextField txtMaSP, txtTenSP, txtMoTa, txtGiaBan, txtDonVi, txtSoLuong, txtMaKhuyenMai, txtViTri;
    private JComboBox<String> cbMaDM;
    private List<DanhMuc_DTO> danhMucList;

    public ThemSP_GUI(Frame owner, SanPham_BUS bus) {
        super(owner, "Thêm sản phẩm mới", true);
        this.bus = bus;
        this.dmBus = new DanhMuc_BUS();

        setSize(500, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        loadDanhMuc();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtMaSP    = new JTextField(15);
        txtMaSP.setEditable(false);
        txtTenSP   = new JTextField(25);
        txtMoTa    = new JTextField(30);
        txtGiaBan  = new JTextField(15);
        txtDonVi   = new JTextField(10);
        txtSoLuong = new JTextField(10);
        txtMaKhuyenMai = new JTextField(10);
        txtViTri   = new JTextField(15);

        cbMaDM = new JComboBox<>();
        for (DanhMuc_DTO dm : danhMucList) {
            cbMaDM.addItem(dm.getTenDM() + " (" + dm.getMaDM () + ")");
        }
        if (!danhMucList.isEmpty()){
            cbMaDM.setSelectedIndex(0);
            capNhatMaSP();
        }

        cbMaDM.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    capNhatMaSP();
                }
            }
        });

        formPanel.add(new JLabel("Mã SP:"));     formPanel.add(txtMaSP);
        formPanel.add(new JLabel("Tên SP:"));    formPanel.add(txtTenSP);
        formPanel.add(new JLabel("Mô tả:"));     formPanel.add(txtMoTa);
        formPanel.add(new JLabel("Giá bán:"));   formPanel.add(txtGiaBan);
        formPanel.add(new JLabel("Đơn vị:"));    formPanel.add(txtDonVi);
        formPanel.add(new JLabel("Tồn kho:"));   formPanel.add(txtSoLuong);
        formPanel.add(new JLabel("Mã DM:"));     formPanel.add(cbMaDM);
        formPanel.add(new JLabel("Mã khuyến mãi:")); formPanel.add(txtMaKhuyenMai);
        formPanel.add(new JLabel("Vị trí:"));    formPanel.add(txtViTri);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> luuSanPham());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        txtMaSP.addActionListener(e -> txtTenSP.requestFocus());
        txtTenSP.addActionListener(e -> txtMoTa.requestFocus());
        txtMoTa.addActionListener(e -> txtGiaBan.requestFocus());
        txtGiaBan.addActionListener(e -> txtDonVi.requestFocus());
        txtDonVi.addActionListener(e -> txtSoLuong.requestFocus());
        txtSoLuong.addActionListener(e -> txtMaKhuyenMai.requestFocus());
        txtMaKhuyenMai.addActionListener(e -> txtViTri.requestFocus());
        txtViTri.addActionListener(e -> btnLuu.doClick());

        getRootPane().setDefaultButton(btnLuu);
    }

    private void loadDanhMuc() {
        try {
            danhMucList = dmBus.getAllDanhMuc();
            if (danhMucList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không có danh mục nào! Vui lòng thêm danh mục trước.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải danh mục: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void luuSanPham() {
        try {
            SanPham_DTO sp = new SanPham_DTO();
            sp.setMaSP(txtMaSP.getText().trim());
            sp.setTenSP(txtTenSP.getText().trim());
            sp.setMoTa(txtMoTa.getText().trim());
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
            sp.setDonVi(txtDonVi.getText().trim());
            sp.setSoLuongTon(Integer.parseInt(txtSoLuong.getText().trim()));
            sp.setMaKhuyenMai(txtMaKhuyenMai.getText().trim());
            sp.setViTri(txtViTri.getText().trim());
            sp.setTrangThai((byte) 1);
            int selectedIndex = cbMaDM.getSelectedIndex();
            if (selectedIndex >= 0) {
                DanhMuc_DTO dm = danhMucList.get(selectedIndex);
                sp.setMaDM(dm.getMaDM());
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn danh mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (bus.themSanPham(sp)) {
                success = true;
                JOptionPane.showMessageDialog(this, "Thêm sản phẩm thành công!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm thất bại! (Mã SP trùng hoặc lỗi)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá bán, đơn vị, tồn kho phải là số hợp lệ!", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void capNhatMaSP() {
        String maSPMoi = bus.taoMaSPTuDong();
        txtMaSP.setText(maSPMoi);
    }
}