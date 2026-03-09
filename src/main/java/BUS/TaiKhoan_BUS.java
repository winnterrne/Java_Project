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
    NhanVien_BUS nvbus = new NhanVien_BUS();
    private static final String Default_Pass = "123456";
    public TaiKhoan_BUS () {
        taikhoan = new TaiKhoan_DAO();
    }
    public ArrayList<TaiKhoan_DTO> getALL() {
        return taikhoan.getALL();
    }
    public ArrayList<TaiKhoan_DTO> getToShowTable() {
        return taikhoan.getToShowTable();
    }
    
    public boolean addTaiKhoan(TaiKhoan_DTO taikhoandto) {
        return taikhoan.addTaiKhoan(taikhoandto);
    }
    public String checkLogic(TaiKhoan_DTO tk, String trangthai) {
        String matk = tk.getMaTK();
        String tendn = tk.getTenDangNhap();
        String email = tk.getEmail();
        String vaitro = tk.getMaVaiTro();
        String manv = tk.getMaNV();

        if(matk == null || matk.trim().isEmpty() ||
                tendn == null || tendn.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                vaitro == null || vaitro.trim().isEmpty() ||
                manv == null || manv.trim().isEmpty()) {
            return "Vui lòng điền đầy đủ thông tin";
        }
        if(!tk.getEmail().matches("^[A-Za-z0-9+_.-]+@gmail\\.com$")) {
            return "Email phải có định dạng @gmail.com";
        }
        if(!trangthai.equals("1") && !trangthai.equals("2")) {
            return "Trạng thái chỉ được 1 = hoạt động 2 = không hoạt động ";
        }
        vaitro = vaitro.trim().toUpperCase();
        if(!vaitro.equals("ADMIN") && !vaitro.equals("KHO") && !vaitro.equals("NHANVIENBANHANG")) {
            return "Vai trò chỉ được admin, kho hoặc, nv bán hàng ";
        }
        if(isTenDangNhap(tk.getTenDangNhap())) {
            return "Tên đăng nhập đã tồn tại";
        }
        if(isEmailExist(tk.getEmail())) {
            return "Email đã tồn tại";
        }
        if(isMaTonTai(tk.getMaTK())) {
            return "Mã tài khoản đã tồn tại";
        }
        if(!nvbus.isNhanVienExist(tk.getMaNV())) {
            return "Mã nhân viên không tồn tại ";
        }
        tk.setTrangThai(trangthai.equals("1"));
        tk.setPassWord(Default_Pass);
        boolean result = taikhoan.addTaiKhoan(tk);
        if(result)
            return "Thành công";
        return "Thêm tài khoản thất bại";
    }

    public boolean isMaTonTai(String matk) {
        return taikhoan.isUsernameExist(matk);
    }
    
    public boolean isTenDangNhap(String tendangnhap) {
        return taikhoan.isTenDangNhap(tendangnhap);
    }
    
    public TaiKhoan_DTO login(String tendangnhap, String matkhau) {
        TaiKhoan_DTO tk =  taikhoan.login(tendangnhap,matkhau);
        CurrentUser.getInstance().login(tk);
        return tk;
    }
    
    public boolean deleteTaiKhoan(String mataikhoan) {
        return taikhoan.deleteTaiKhoan(mataikhoan);

    }
    
    public boolean updateTaiKhoan(TaiKhoan_DTO taikhoandto) {
        if(taikhoandto.getTenDangNhap()== null || taikhoandto.getTenDangNhap() == null) {
            return false;
        }
        return taikhoan.updateTaikhoan(taikhoandto);
    }
    
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
    
    public String taoOTP() {
        int otp = 100000 + new java.util.Random().nextInt(900000);
        return String.valueOf(otp);
    }
    
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
    
    public boolean updatePassword(String tentaikhoan, String matkhaucu, String matkhaumoi) {
        if (tentaikhoan.isEmpty() || matkhaucu.isEmpty() || matkhaumoi.isEmpty()) {
            return false;
        }
        if(matkhaumoi.length() < 3) {
            return false;
        }
        return taikhoan.updatePassword(tentaikhoan,matkhaucu,matkhaumoi);
    }
    public ArrayList<TaiKhoan_DTO > sortName(String str) {
        return taikhoan.sortName(str);
    }
    public String taoMaTuDong() {
        String maTK = taikhoan.getMaxMaTK();
        if(maTK == null) {
            return "QL01";
        }
        String so = maTK.substring(2);
        int soMoi = Integer.parseInt(so) + 1;
        return String.format("QL%02d",soMoi);
    }
}
