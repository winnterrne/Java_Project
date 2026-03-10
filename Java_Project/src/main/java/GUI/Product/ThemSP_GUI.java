package GUI.Product;

import BUS.SanPham_BUS;
import BUS.DanhMuc_BUS;
import DTO.SanPham_DTO;
import DTO.DanhMuc_DTO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ThemSP_GUI extends JDialog {

    private SanPham_BUS bus;
    private DanhMuc_BUS dmBus;
    private boolean success = false;

    private JTextField txtMaSP, txtTenSP, txtMoTa, txtGiaBan, txtDonVi, txtSoLuong, txtMaKhuyenMai, txtViTri;
    private JComboBox<String> cbMaDM;
    private List<DanhMuc_DTO> danhMucList;

    private JLabel lblHinhAnh;
    private JButton btnChonAnh;
    private String duongDanAnhDatabase = "";


    public ThemSP_GUI(Frame owner, SanPham_BUS bus) {
        super(owner, "Thêm sản phẩm mới", true);
        this.bus = bus;
        this.dmBus = new DanhMuc_BUS();

        setSize(750, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        loadDanhMuc();

        JPanel formPanel = new JPanel(new GridLayout(0, 2, 10, 12));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        txtMaSP    = new JTextField(15);
        txtMaSP.setEditable(false);
        txtTenSP   = new JTextField(25);
        txtMoTa    = new JTextField(30);
        txtGiaBan  = new JTextField(15);
        txtDonVi   = new JTextField(10);
        txtSoLuong = new JTextField(10);
        txtSoLuong.setEditable(false);
        txtSoLuong.setText("0");
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

        JPanel pnlHinhAnh = new JPanel();
        pnlHinhAnh.setLayout(new BoxLayout(pnlHinhAnh, BoxLayout.Y_AXIS));
        pnlHinhAnh.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 20));

        lblHinhAnh = new JLabel("Chưa có ảnh", SwingConstants.CENTER);
        lblHinhAnh.setPreferredSize(new Dimension(200, 200));
        lblHinhAnh.setMaximumSize(new Dimension(200, 200));
        lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblHinhAnh.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnChonAnh.addActionListener(e -> chonAnh());

        pnlHinhAnh.add(lblHinhAnh);
        pnlHinhAnh.add(Box.createRigidArea(new Dimension(0, 10)));
        pnlHinhAnh.add(btnChonAnh);


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");

        btnLuu.addActionListener(e -> luuSanPham());
        btnHuy.addActionListener(e -> dispose());

        buttonPanel.add(btnLuu);
        buttonPanel.add(btnHuy);

        add(formPanel, BorderLayout.CENTER);
        add(pnlHinhAnh, BorderLayout.EAST);
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

    private void chonAnh() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn ảnh sản phẩm");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Hình ảnh (JPG, PNG, JPEG)", "jpg", "png", "jpeg");
        fileChooser.setFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                String projectPath = System.getProperty("user.dir");
                String destDirPath = projectPath + "/Image";
                File destDir = new File(destDirPath);
                if (!destDir.exists()) destDir.mkdirs();

                String originalName = selectedFile.getName();
                String extension = originalName.substring(originalName.lastIndexOf("."));
                String newFileName = "SP_" + System.currentTimeMillis() + extension;

                Path destPath = Paths.get(destDirPath, newFileName);
                Files.copy(selectedFile.toPath(), destPath, StandardCopyOption.REPLACE_EXISTING);

                duongDanAnhDatabase = "Image/" + newFileName;

                ImageIcon originalIcon = new ImageIcon(destPath.toString());
                Image img = originalIcon.getImage();
                Image resizedImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                lblHinhAnh.setIcon(new ImageIcon(resizedImg));
                lblHinhAnh.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi lưu ảnh: " + ex.getMessage());
            }
        }
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
        if(txtTenSP.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this,"Tên sản phẩm không được để trống");
            return;
        }

        SanPham_DTO sp = new SanPham_DTO();

        sp.setMaSP(txtMaSP.getText().trim());
        sp.setTenSP(txtTenSP.getText().trim());
        sp.setMoTa(txtMoTa.getText().trim());
        sp.setDonVi(txtDonVi.getText().trim());
        sp.setMaKhuyenMai(txtMaKhuyenMai.getText().trim());
        sp.setViTri(txtViTri.getText().trim());
        sp.setPath(duongDanAnhDatabase);

        int selectedIndex = cbMaDM.getSelectedIndex();
        if(selectedIndex >= 0){
            sp.setMaDM(danhMucList.get(selectedIndex).getMaDM());
        }

        try {
            sp.setGiaBan(Double.parseDouble(txtGiaBan.getText().trim()));
            sp.setSoLuongTon(Integer.parseInt(txtSoLuong.getText().trim()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Giá bán hoặc số lượng không hợp lệ!");
            return;
        }

        boolean result = bus.luuSanPham(sp);

        if(result){
            success = true;
            JOptionPane.showMessageDialog(this,"Thêm sản phẩm thành công!");
            
            dispose();
        }else{
            JOptionPane.showMessageDialog(this,"Thêm sản phẩm thất bại!");
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