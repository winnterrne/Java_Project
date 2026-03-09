package GUI.Account;
import BUS.NhanVien_BUS;
import BUS.TaiKhoan_BUS;
import DTO.NhanVien_DTO;
import DTO.TaiKhoan_DTO;

import javax.print.attribute.standard.JobHoldUntil;
import javax.swing.*;
import java.awt.*;

public class ThemTaiKhoan_GUI extends JDialog {
    JPanel topPanel, buttonPanel;
    JLabel lbTitle;
    JTextField tfMaTaiKhoan, tfTenTaiKhoan, tfEmail, tfVaiTro, tfTrangThai, tfMaNhanVien;
    JButton btnThem, btnHuy;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    private boolean isSaved = false;
    public ThemTaiKhoan_GUI(Frame frame) {
        super(frame,"Thêm tài khoản",true);
        initGui();
        tfMaTaiKhoan.setText(tkbus.taoMaTuDong());
        tfMaTaiKhoan.setEditable(false);
    }

    public void initGui() {
        setTitle("Thêm tài khoản");
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; 
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);

        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        lbTitle = new JLabel("Thêm tài khoản ");
        lbTitle.setFont(new Font("Times New Roman", Font.BOLD, 32));
        topPanel.setBackground(Color.BLUE);
        lbTitle.setForeground(Color.white);
        topPanel.add(lbTitle);
        add(topPanel, gbc);

        
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.insets = new Insets(5, 30, 5, 10);

        
        gbc.gridx = 0; gbc.gridy = 1;
        add(createLabel("Mã tài khoản"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        tfMaTaiKhoan = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        add(tfMaTaiKhoan, gbc);

        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Tên tài khoản"), gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        tfTenTaiKhoan = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTenTaiKhoan, gbc);

        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Email"), gbc);

        gbc.gridx = 1; gbc.gridy = 3;
        tfEmail = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfEmail, gbc);

        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Vai trò"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        tfVaiTro = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfVaiTro, gbc);

        
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Trạng thái"),gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        tfTrangThai = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTrangThai,gbc);

        
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Ma nhan vien"),gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        tfMaNhanVien = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfMaNhanVien,gbc);

        
        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2; 
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
        tfMaTaiKhoan.addActionListener(e -> tfTenTaiKhoan.requestFocus());
        tfTenTaiKhoan.addActionListener(e -> tfEmail.requestFocus());
        tfEmail.addActionListener(e -> tfVaiTro.requestFocus());
        tfVaiTro.addActionListener(e -> tfTrangThai.requestFocus());
        tfTrangThai.addActionListener(e -> tfMaNhanVien.requestFocus());
        tfMaNhanVien.addActionListener(e -> themTaiKhoan());
        btnThem.addActionListener(e -> themTaiKhoan());
    }
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }
    public void themTaiKhoan() {
        try {
            TaiKhoan_DTO tkdto = new TaiKhoan_DTO();
            String matk = tkbus.taoMaTuDong();
            tfMaTaiKhoan.setText(matk);
            tkdto.setMaTK(matk);
            tkdto.setTenDangNhap(tfTenTaiKhoan.getText().trim());
            tkdto.setEmail(tfEmail.getText().trim());
            tkdto.setMaVaiTro(tfVaiTro.getText().trim());
            tkdto.setMaNV(tfMaNhanVien.getText().trim());
            tkdto.setMaVaiTro(tfVaiTro.getText().trim());

            String trangthai = tfTrangThai.getText().trim();
            tkdto.setTrangThai(trangthai.equals("1"));
            String result = tkbus.checkLogic(tkdto, trangthai);
            if(result.equals("Thành công")) {
                JOptionPane.showMessageDialog(this,"Thêm tài khoản thành công");
                isSaved = true;
                dispose();
            }else {
                JOptionPane.showMessageDialog(this,result);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSaved() {
        return isSaved;
    }

}
