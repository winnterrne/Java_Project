package GUI.Account;

import BUS.NhanVien_BUS;
import BUS.TaiKhoan_BUS;
import DTO.NhanVien_DTO;
import DTO.TaiKhoan_DTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SuaTaiKhoan_GUI extends JDialog {
    JPanel topPanel, centerPanel, buttonPanel;
    JLabel lbTitle;
    JTextField tfMaTaiKhoan, tfTenTaiKhoan, tfEmail, tfVaiTro, tfTrangThai, tfMaNhanVien;
    JButton btnThem, btnHuy;
    DefaultTableModel tm;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    NhanVien_BUS nvbus = new NhanVien_BUS();
    TaiKhoan_GUI tkparent;
    int i;
    public SuaTaiKhoan_GUI(Frame frame, DefaultTableModel tm, int i, String matk, String tendn, String email, String vaitro, String trangthai) {
        super(frame,"Sửa tài khoản",true);
        initGui();
        this.tm = tm;
        this.i = i;
        tfMaTaiKhoan.setText(matk);
        tfTenTaiKhoan.setText(tendn);
        tfEmail.setText(email);
        tfVaiTro.setText(vaitro);
        tfTrangThai.setText(trangthai);

    }

    public void initGui() {
        setTitle("Thêm tài khoản");
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
        lbTitle = new JLabel("Sửa tài khoản");
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
        add(createLabel("Mã tài khoản"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        tfMaTaiKhoan = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL; // textfield giãn ngang
        add(tfMaTaiKhoan, gbc);

        // Tên Tai Khoan
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Tên tài khoản"), gbc);

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
        add(createLabel("Vai Trò"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        tfVaiTro = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfVaiTro, gbc);

        // trang thai
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Trạng thái"),gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        tfTrangThai = new JTextField(20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(tfTrangThai,gbc);

        // ma nhanvien
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Mã nhân viên"),gbc);

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
        btnThem.addActionListener(e -> xuLyLuu());
        setSize(600, 400);
        setLocationRelativeTo(null);
    }
    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }
    public void xuLyLuu() {
        tm.setValueAt(tfMaTaiKhoan.getText(),i,0);
        tm.setValueAt(tfTenTaiKhoan.getText(),i,2);
        tm.setValueAt(tfEmail.getText(),i,3);
        tm.setValueAt(tfVaiTro.getText(),i,4);
        tm.setValueAt(tfTrangThai.getText(),i,5);
        JOptionPane.showMessageDialog(this,"Đã sửa thành công");
        dispose();
    }
}
