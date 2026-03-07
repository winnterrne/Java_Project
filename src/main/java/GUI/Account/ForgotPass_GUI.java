package GUI.Account;
import DAO.*;
import DTO.*;
import BUS.*;

import javax.mail.*;
import javax.swing.*;
import javax.net.*;
import javax.mail.internet.*;
import java.awt.*;

public class ForgotPass_GUI extends JDialog {
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    JLabel lbkhoiPhuc, lbnhapEmail, lbguiMaText, lbnhapMa, lbNewPass, lbNewEmail;
    JTextField txnhapEmail, txnhapMa, txNewPass, txEmailNewPass;
    JButton btnForgotPass, btnXacNhan, btnNewPass;
    JPanel panelOTP, panelResetPass, panelNewPass;
    public ForgotPass_GUI(JFrame parent) {
        super(parent,"Khôi phục mật khẩu ", true);
        setSize(600,300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        lbkhoiPhuc = new JLabel("KHÔI PHỤC MẬT KHẨU");
        lbkhoiPhuc.setBounds(180,30,250,40);
        lbkhoiPhuc.setFont(new Font("Arial", Font.BOLD, 20));
        lbnhapEmail = new JLabel("Nhập địa chỉ email");
        lbnhapEmail.setBounds(60,105,150,23);

        txnhapEmail = new JTextField();
        txnhapEmail.setBounds(60,130,240,30);
        btnForgotPass = new JButton("Gửi mã xác nhận");
        btnForgotPass.setBounds(350,130,130,30);
        panelOTP = new JPanel();
        panelOTP.setLayout(null);
        panelOTP.setBounds(0,0,600,300);
        panelOTP.add(lbkhoiPhuc);
        panelOTP.add(lbnhapEmail);
        panelOTP.add(txnhapEmail);
        panelOTP.add(btnForgotPass);

        lbkhoiPhuc = new JLabel("KHÔI PHỤC MẬT KHẨU");
        lbkhoiPhuc.setBounds(180,30,250,40);
        lbkhoiPhuc.setFont(new Font("Arial", Font.BOLD, 20));
        lbnhapMa = new JLabel("Nhập mã OTP ");
        lbnhapMa.setBounds(60,80,200,30);
        lbguiMaText = new JLabel("Mã OTP đã gửi vào địa chỉ email");
        lbguiMaText.setBounds(60,65,250,23);

        txnhapMa = new JTextField();
        txnhapMa.setBounds(60,110,200,30);
        btnXacNhan = new JButton("Xác nhận ");
        btnXacNhan.setBounds(350,110,150,30);
        panelResetPass = new JPanel();
        panelResetPass.setLayout(null);
        panelResetPass.setBounds(0,0,600,300);
        panelResetPass.setVisible(false);
        panelResetPass.add(lbkhoiPhuc);
        panelResetPass.add(lbguiMaText);
        panelResetPass.add(lbnhapMa);
        panelResetPass.add(txnhapMa);
        panelResetPass.add(btnXacNhan);


        lbkhoiPhuc = new JLabel("KHÔI PHỤC MẬT KHẨU");
        lbkhoiPhuc.setBounds(180,30,250,40);
        lbkhoiPhuc.setFont(new Font("Arial", Font.BOLD, 20));
        lbNewPass = new JLabel("Nhập mật khẩu mới ");
        lbNewPass.setBounds(60,130,200,30);
        txNewPass = new JTextField();
        txNewPass.setBounds(60,160,200,30);
        lbNewEmail = new JLabel("Nhập tài khoản email ");
        lbNewEmail.setBounds(60,70,200,30);
        txEmailNewPass = new JTextField();
        txEmailNewPass.setBounds(60,100,200,30);
        btnNewPass = new JButton("Xác nhận");
        btnNewPass.setBounds(350,100,150,30);
        panelNewPass = new JPanel();
        panelNewPass.setLayout(null);
        panelNewPass.setVisible(false);
        panelNewPass.setBounds(0,0,600,300);
        panelNewPass.add(lbkhoiPhuc);
        panelNewPass.add(lbNewPass);
        panelNewPass.add(lbNewEmail);
        panelNewPass.add(txNewPass);
        panelNewPass.add(btnNewPass);
        panelNewPass.add(txEmailNewPass);

        add(panelOTP);
        add(panelResetPass);
        add(panelNewPass);
        btnForgotPass.addActionListener(e -> sendOTP());
        btnXacNhan.addActionListener(e -> xacNhanOTP());
        btnNewPass.addActionListener(e -> doiMatKhau());
    }

    public void sendOTP() {
        String getEmail = txnhapEmail.getText();
        if(getEmail.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Ban chua nhap tai khaon email");
        }
        if (!tkbus.isEmailExist(getEmail)) {
            JOptionPane.showMessageDialog(this, "Email không tồn tại");
            return;
        }

        boolean result = tkbus.guiOTP(getEmail);
        if (result) {
            JOptionPane.showMessageDialog(this, "Đã gửi email");

            panelOTP.setVisible(false);
            panelResetPass.setVisible(true);
            revalidate();
            repaint();
        }else {
            JOptionPane.showMessageDialog(this,"Email không tồn tại");
        }
    }
    public void xacNhanOTP() {
        String email = txnhapEmail.getText().trim();
        String inputOTP = txnhapMa.getText().trim();
        if(tkbus.checkOTP(inputOTP,email)) {
            JOptionPane.showMessageDialog(this,"Bạn đã nhập đúng mã OTP");
            panelResetPass.setVisible(false);
            panelNewPass.setVisible(true);

            revalidate();
            repaint();
        }else {
            JOptionPane.showMessageDialog(this,"OTP không hợp lệ");
        }
    }
    public void doiMatKhau() {
        String password = txNewPass.getText().trim();
        String email = txEmailNewPass.getText().trim();
        if(password.isEmpty() || password == null) {
            JOptionPane.showMessageDialog(this,"Nhập mật khẩu");
            return;
        }
        boolean result = tkbus.updatePassWordForgot(password,email);
        if(result) {
            JOptionPane.showMessageDialog(this,"Đã đổi mật khẩu");
            dispose();
        }else {
            JOptionPane.showMessageDialog(this,"Không đổi đuợc mật khẩu");
        }

    }
}


