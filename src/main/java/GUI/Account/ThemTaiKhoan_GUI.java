package GUI.Account;

import BUS.NhanVien_BUS;
import BUS.TaiKhoan_BUS;
import DTO.NhanVien_DTO;
import DTO.TaiKhoan_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ThemTaiKhoan_GUI extends JDialog {
    JPanel topPanel, centerPanel, buttonPanel;
    JLabel lbTitle;
    JTextField tfMaTaiKhoan, tfTenTaiKhoan, tfEmail, tfVaiTro, tfTrangThai, tfMaNhanVien;
    JButton btnThem, btnHuy;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    NhanVien_BUS nvbus = new NhanVien_BUS();
    private boolean isSaved = false;
    public ThemTaiKhoan_GUI(Frame frame) {
        super(frame,"Them tai khoan",true);
        initGui();
    }

    public void initGui() {
        setTitle("Them tai khoan");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tiêu đề
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // chiếm 2 cột
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        lbTitle = new JLabel("Them Tai Khoan");
        lbTitle.setFont(new Font("Times New Roman", Font.BOLD, 32));
        topPanel.setBackground(Color.BLUE);
        lbTitle.setForeground(Color.white);
        topPanel.add(lbTitle);
        add(topPanel, gbc);

        // Reset lại cho các label + textfield
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST; // label bám trái
        gbc.insets = new Insets(5, 30, 5, 10);

        // Mã Tai Khoan
        gbc.gridx = 0; gbc.gridy = 1;
        add(createLabel("Mã tai khoan"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        tfMaTaiKhoan = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL; // textfield giãn ngang
        add(tfMaTaiKhoan, gbc);

        // Tên Tai Khoan
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Tên tai khoan"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        tfTenTaiKhoan = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTenTaiKhoan, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Email"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        tfEmail = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfEmail, gbc);

        // Vai tro
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Vai tro"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        tfVaiTro = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfVaiTro, gbc);

        // trang thai
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Trang thai"),gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        tfTrangThai = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTrangThai,gbc);

        // ma nhanvien
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Ma nhan vien"),gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        tfMaNhanVien = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfMaNhanVien,gbc);

        // Panel nút bấm
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2; // chiếm cả 2 cột
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 0, 0);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Thêm");
        buttonPanel.add(btnThem);
        btnHuy = new JButton("Hủy");
        buttonPanel.add(btnHuy);
        add(buttonPanel, gbc);
        setSize(600, 400);
        setLocationRelativeTo(null);
        btnThem.addActionListener(e -> themTaiKhoan());
    }
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }
    public void themTaiKhoan() {
        String matk = tfMaTaiKhoan.getText().trim();
        String tentk = tfTenTaiKhoan.getText().trim();
        String email = tfEmail.getText().trim();
        String vaitro = tfVaiTro.getText().trim();
        String trangthai = tfTrangThai.getText().trim();
        String nhanvien = tfMaNhanVien.getText().trim();
        try {
            if(matk.isEmpty() || tentk.isEmpty() || email.isEmpty() || vaitro.isEmpty() || trangthai.isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Vui long dien day du thong tin");
                return;
            }
            if(!email.matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
                JOptionPane.showMessageDialog(this,"Vui long nhap dung dinh dang @gmail.com");
                return;
            }
            if(!trangthai.equals("1") && !trangthai.equals("2")) {
                JOptionPane.showMessageDialog(this,"Trang thi 1 = hoat dong, 2 = khong hoat dong");
                return;
            }
            if(tkbus.isTenDangNhap(tentk)) {
                JOptionPane.showMessageDialog(this,"Ten danh nhap da ton tai ");
                return;
            }
            if(tkbus.isEmailExist(email)) {
                JOptionPane.showMessageDialog(this,"Email da ton tai");
                return;
            }
            if(tkbus.isMaTonTai(matk)) {
                JOptionPane.showMessageDialog(this,"Ma tai khoan da ton tai");
                return;
            }
            if(!nvbus.isNhanVienExist(nhanvien)) {
               JOptionPane.showMessageDialog(this,"Nhan vien khong ton tai");
               return;
            }
            TaiKhoan_DTO taikhoandto = new TaiKhoan_DTO();
            taikhoandto.setMaTK(matk);
            taikhoandto.setTenDangNhap(tentk);
            taikhoandto.setEmail(email);
            taikhoandto.setMaVaiTro(vaitro);
            taikhoandto.setTrangThai(trangthai.equals("1"));
            taikhoandto.setMaNV(nhanvien);
            NhanVien_DTO nvdto = nvbus.getNhanVienByMa(nhanvien);
            if(nvdto == null) {
                JOptionPane.showMessageDialog(this,"Khong co nhan vien nay");
                return;
            }
            boolean result = tkbus.addTaiKhoan(taikhoandto);
            if(result) {
                JOptionPane.showMessageDialog(this,"Them tai khoan thanh cong / Mat khau mac dinh la 123456");
                isSaved = true;
                this.dispose();
            }else {
                JOptionPane.showMessageDialog(this,"Khong thanh cong them nv");
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean isSaved() {
        return isSaved;
    }
}
