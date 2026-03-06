package DAO;
import DTO.NhanVien_DTO;
import DTO.TaiKhoan_DTO;

import java.awt.image.AreaAveragingScaleFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class TaiKhoan_DAO {
    private Connection conn;
    public boolean openConnection() {
        try {
            String url =
                    "jdbc:sqlserver://localhost:1433;"
                            + "databaseName=DoAn;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true";
            String username = "sa";
            String password = "123456";
            conn = DriverManager.getConnection(url,username,password);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public void closeConnection() {
        try {
            if(conn != null) {
                conn.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Ham lay het danh sach tai khoam
    public ArrayList<TaiKhoan_DTO> getALL() {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        if (openConnection()) {
            try {
                String sql = "SELECT * FROM TaiKhoan where trangthai = 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TaiKhoan_DTO tk = new TaiKhoan_DTO();
                    tk.setTenDangNhap(rs.getString("tendangnhap"));
                    tk.setPassWord(rs.getString("matkhau"));
                    tk.setMaTK(rs.getString("mataikhoan"));
                    tk.setMaVaiTro(rs.getString("mavaitro"));
                    tk.setEmail(rs.getString("email"));
                    tk.setTrangThai(rs.getBoolean("trangthai"));
                    tk.setMaNV(rs.getString("maNV"));
                    tk.setMaKH(rs.getString("maKH"));
                    list.add(tk);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return list;
    }
    public ArrayList<TaiKhoan_DTO> getToShowTable() {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        if (openConnection()) {
            try {
                String sql = "SELECT tk.mataikhoan, nv.hoTenNV, tk.tendangnhap, tk.email, tk.mavaitro, tk.trangthai FROM TaiKhoan tk JOIN NhanVien nv ON tk.maNV = nv.maNV where tk.trangthai = 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    TaiKhoan_DTO tk = new TaiKhoan_DTO();
                    tk.setMaTK(rs.getString("mataikhoan"));
                    tk.setHoTen(rs.getString("hoTenNV"));
                    tk.setTenDangNhap(rs.getString("tendangnhap"));
                    tk.setEmail(rs.getString("email"));
                    tk.setMaVaiTro(rs.getString("mavaitro"));
                    tk.setTrangThai(rs.getBoolean("trangthai"));
                    list.add(tk);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return list;
    }
    // Ham kiem tra xem co ma tai khoan
    public boolean isUsernameExist(String maTaikhoan) {
        boolean result = false;
        if(openConnection()) {
            try {
                String sql = "SELECT 1 from TaiKhoan where mataikhoan =?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,maTaikhoan);
                ResultSet rs = ps.executeQuery();
                result = rs.next();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return result;
    }
    public boolean isEmailExist(String email) {
        boolean result = false;
        if(openConnection()) {
            try {
                String sql = "SELECT * from TaiKhoan where email=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,email);
                ResultSet rs = ps.executeQuery();
                result = rs.next();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return result;
    }
    // Kiem tra ten dang nhap vao he thong
    public boolean isTenDangNhap(String tentk) {
        boolean result = false;
        if (openConnection()) {
            try {
                String sql = "SELECT * from TaiKhoan where tendangnhap=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,tentk);
                ResultSet rs= ps.executeQuery();
                result = rs.next();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return result;
    }
    // Ham them tai khoan
    public boolean addTaiKhoan(TaiKhoan_DTO taikhoan) {
        if(openConnection()) {
            try {
                String sql = "INSERT INTO TaiKhoan(mataikhoan, tendangnhap, matkhau, mavaitro, email, trangthai, maNV)" + "VALUES(?,?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,taikhoan.getMaTK());
                ps.setString(2,taikhoan.getTenDangNhap());
                ps.setString(3,taikhoan.getPassWord());
                ps.setString(4,taikhoan.getMaVaiTro());
                ps.setString(5, taikhoan.getEmail());
                ps.setBoolean(6,taikhoan.isTrangThai());
                ps.setString(7,taikhoan.getMaNV());
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham xoa tai khoan
    public boolean deleteTaiKhoan(String maTaikhoan) {
        if (openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET trangthai = 0 where mataikhoan=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,maTaikhoan);
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham sua tai khoan
    public boolean updateTaikhoan(TaiKhoan_DTO taikhoan) {
        if(openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET tendangnhap=?, mavaitro=?, email=?, trangthai=?, otp = NULL, otp_expire = NULL WHERE mataikhoan=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,taikhoan.getTenDangNhap());
                ps.setString(2, taikhoan.getMaVaiTro());
                ps.setString(3, taikhoan.getEmail());
                ps.setBoolean(4,taikhoan.isTrangThai());
                ps.setString(5,taikhoan.getMaTK());
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham cap nhat mat khau
    public boolean updatePasswordForgot(String matkhau, String email) {
        if (openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET " + "matkhau=?" + "where email=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,matkhau);
                ps.setString(2,email);
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham cap nhat mat khau trong trang chinh
    public boolean updatePassword(String tendangnhap, String matkhaucu, String matkhaumoi) {
        if(openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET matkhau=? WHERE tendangnhap=? and matkhau=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,matkhaumoi);
                ps.setString(2,tendangnhap);
                ps.setString(3,matkhaucu);
                 return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }

    // Phan dang nhap cho tai khoan
    public TaiKhoan_DTO login (String tendangnhap, String matkhau) {
        TaiKhoan_DTO taikhoan= null;
        if (openConnection()) {
            try {
                String sql = "SELECT tk.*, nv.hoTenNV FROM TaiKhoan tk JOIN NhanVien nv on tk.maNV = nv.maNV WHERE tk.tendangnhap=? AND tk.matkhau=? AND tk.trangthai = 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,tendangnhap);
                ps.setString(2,matkhau);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    taikhoan = new TaiKhoan_DTO();
                    taikhoan.setMaTK(rs.getString("mataikhoan"));
                    taikhoan.setTenDangNhap(rs.getString("tendangnhap"));
                    taikhoan.setPassWord(rs.getString("matkhau"));
                    taikhoan.setMaVaiTro(rs.getString("mavaitro"));
                    taikhoan.setEmail(rs.getString("email"));
                    taikhoan.setTrangThai(rs.getBoolean("trangthai"));
                    taikhoan.setHoTen(rs.getString("hoTenNV"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return taikhoan;
    }
    // Ham luu ma OTP
    public boolean updateOTPByEmail(String otp, String email) {
        if (openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET otp=?, otp_expire=DATEADD(MINUTE,5,GETDATE()) WHERE email=?";
                PreparedStatement ps= conn.prepareStatement(sql);
                ps.setString(1,otp);
                ps.setString(2,email);
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham kiem tra ma OTP
    public boolean checkOTP(String otp, String email) {
        if (openConnection()) {
            try {
                String sql = "SELECT 1 from TaiKhoan where otp=? and email=? and otp_expire > GETDATE()";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,otp);
                ps.setString(2,email);
                ResultSet rs = ps.executeQuery();
                return rs.next();
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    public boolean clearOTP(String email) {
        if(openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET otp=NULL, otp_expire=NULL where email=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,email);
                return ps.executeUpdate() > 0;
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return false;
    }
    // Ham tim kiem theo ten
    public ArrayList<TaiKhoan_DTO> sortName(String name) {
        ArrayList<TaiKhoan_DTO> list = new ArrayList<>();
        if(openConnection()) {
            try {
                String sql =  "SELECT tk.mataikhoan, nv.hoTenNV, tk.tendangnhap, tk.email, tk.mavaitro, tk.trangthai FROM TaiKhoan tk JOIN NhanVien nv ON tk.maNV = nv.maNV where tk.tendangnhap LIKE? and tk.trangthai = 1";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + name + "%");
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    TaiKhoan_DTO tk = new TaiKhoan_DTO();
                    tk.setMaTK(rs.getString("mataikhoan"));
                    tk.setHoTen(rs.getString("hoTenNV"));
                    tk.setTenDangNhap(rs.getString("tendangnhap"));
                    tk.setEmail(rs.getString("email"));
                    tk.setMaVaiTro(rs.getString("mavaitro"));
                    tk.setTrangThai(rs.getBoolean("trangthai"));
                    list.add(tk);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return list;
    }
}