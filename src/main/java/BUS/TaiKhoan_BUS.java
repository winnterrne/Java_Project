package BUS;
import DAO.*;
import DTO.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class TaiKhoan_BUS {
    private TaiKhoan_DAO taikhoan;
    public TaiKhoan_BUS () {
        taikhoan = new TaiKhoan_DAO();
    }
    public ArrayList<TaiKhoan_DTO> getALL() {
        return taikhoan.getALL();
    }
    public ArrayList<TaiKhoan_DTO> getToShowTable() {
        return taikhoan.getToShowTable();
    }
    // kiem tra them tai khoan
    public boolean addTaiKhoan(TaiKhoan_DTO taikhoandto) {
        String defaultPass = "123456";
        taikhoandto.setPassWord(defaultPass);
        return taikhoan.addTaiKhoan(taikhoandto);
    }
    public boolean isMaTonTai(String matk) {
        return taikhoan.isUsernameExist(matk);
    }
    // kiem tra ten dang nhap
    public boolean isTenDangNhap(String tendangnhap) {
        return taikhoan.isTenDangNhap(tendangnhap);
    }
    // ham dang nhap
    public TaiKhoan_DTO login(String tendangnhap, String matkhau) {
        return taikhoan.login(tendangnhap,matkhau);
    }
    // ham xoa tai khoan
    public boolean deleteTaiKhoan(String mataikhoan) {
        return taikhoan.deleteTaiKhoan(mataikhoan);

    }
    // ham cap nhat tai khoan
    public boolean updateTaiKhoan(TaiKhoan_DTO taikhoandto) {
        if(taikhoandto.getTenDangNhap()== null || taikhoandto.getTenDangNhap() == null) {
            return false;
        }
        return taikhoan.updateTaikhoan(taikhoandto);
    }
    // ham cap nhat mat khau
    public boolean updatePassWordForgot(String matkhaumoi, String email) {
        if(matkhaumoi.length() < 6) {
            return false;
        }
        boolean updatepass = taikhoan.updatePasswordForgot(matkhaumoi,email);
        if(updatepass) {
           taikhoan.clearOTP(email);
           return true;
        }else {
            return false;
        }
    }
    // ham tao ma otp
    public String taoOTP() {
        int otp = 100000 + new java.util.Random().nextInt(900000);
        return String.valueOf(otp);
    }
    // ham gui otp
    public boolean guiOTP(String email) {
        if(!taikhoan.isEmailExist(email)) {
            return false;
        }
        String otp = taoOTP();
        boolean update = taikhoan.updateOTPByEmail(otp,email);
        if(update) {
            SendMail(email,otp);
            return true;
        }
        return false;
    }
    // ham kiem tra otp
    public boolean checkOTP(String inputotp,String email ) {
        return taikhoan.checkOTP(inputotp,email);

    }

    public boolean isEmailExist(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        if(taikhoan.isEmailExist(email)) {
            return true;
        }
        return false;
    }
    // Ham gui mail
    private void SendMail(String toEmail, String otp) {
        final String fromEmail = "winnterrne@gmail.com";
        final String password = "hzxvbocfunqiegoh";
        Properties pro = new Properties();
        pro.put("mail.smtp.host","smtp.gmail.com");
        pro.put("mail.smtp.port","587");
        pro.put("mail.smtp.auth","true");
        pro.put("mail.smtp.starttls.enable","true");

        Authenticator au = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail,password);
            }
        };
        Session ses = Session.getInstance(pro,au);
        toEmail = "winnterrne@gmail.com";
        MimeMessage mess = new MimeMessage(ses);
        try {
            mess.addHeader("Content-type","text/plain; charset=UTF-8");
            mess.setFrom(new InternetAddress(fromEmail));
            mess.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            mess.setSubject("ALO VU A VU");
            mess.setText("Ma MIXI cua ban la  " + otp);
            mess.setSentDate(new Date());
            Transport.send(mess);
            System.out.println("Gui mail thanh cong");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    //
    public boolean updatePassword(String tentaikhoan, String matkhaucu, String matkhaumoi) {
        if (tentaikhoan.isEmpty() || matkhaucu.isEmpty() || matkhaumoi.isEmpty()) {
            return false;
        }
        if(matkhaumoi.length() < 3) {
            return false;
        }
        return taikhoan.updatePassword(tentaikhoan,matkhaucu,matkhaumoi);
    }
}
