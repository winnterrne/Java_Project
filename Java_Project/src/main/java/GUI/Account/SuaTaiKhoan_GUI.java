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
    JTextField tfMaTaiKhoan, tfTenTaiKhoan, tfEmail,  tfMaNhanVien;
    JButton btnThem, btnHuy;
    DefaultTableModel tm;
    int i;
    JComboBox<String> cbVaiTro, cbTrangThai;
    public SuaTaiKhoan_GUI(Frame frame, DefaultTableModel tm, int i, String matk, String tendn, String email, String vaitro, String trangthai) {
        super(frame,"Sửa tài khoản",true);
        initGui();
        this.tm = tm;
        this.i = i;
        tfMaTaiKhoan.setText(matk);
        tfMaTaiKhoan.setEditable(false);
        tfTenTaiKhoan.setText(tendn);
        tfEmail.setText(email);
        cbVaiTro.setSelectedItem(vaitro);
        cbTrangThai.setSelectedItem(trangthai);

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
        lbTitle = new JLabel("Sửa tài khoản");
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
        add(createLabel("Vai Trò"), gbc);

        gbc.gridx = 1; gbc.gridy = 4;
        String list[] = {"ADMIN", "KHO", "NHANVIENBANHANG", "KHACHHANG"};
        cbVaiTro = new JComboBox<>(list);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cbVaiTro, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        add(createLabel("Trạng thái"),gbc);

        gbc.gridx = 1; gbc.gridy = 5;
        String [] status = {"Hoạt động", "Không hoạt động"};
        cbTrangThai = new JComboBox<>(status);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(cbTrangThai,gbc);


        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2; 
        gbc.fill     = GridBagConstraints.NONE;
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
        tm.setValueAt(cbVaiTro.getSelectedItem().toString(),i,4);
        tm.setValueAt(cbTrangThai.getSelectedItem(),i,5);
        JOptionPane.showMessageDialog(this,"Đã sửa thành công");
        dispose();
    }
}
