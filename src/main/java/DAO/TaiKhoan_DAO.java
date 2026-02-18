package DAO;


import DTO.TaiKhoan_DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class TaiKhoan_DAO {
    private Connection conn;
    public boolean openConnection() {
        try {
            String url =
                    "jdbc:sqlserver://localhost:1433;"
                            + "databaseName=baitapjava;"
                            + "encrypt=true;"
                            + "trustServerCertificate=true";
            String username = "sa";
            String password = "123456";
            conn = DriverManager.getConnection(url,username,password);
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
                String sql = "SELECT * FROM TaiKhoan";
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
    // Ham kiem tra xem co ma ng dung chua
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
    // Ham them tai khoan
    public boolean addTaiKhoan(TaiKhoan_DTO taikhoan) {
        if(openConnection()) {
            try {
                String sql = "INSERT INTO TaiKhoan(mataikhoan, tendangnhap, matkhau, mavaitro, email trangthai)" + "VALUES(?,?,?,?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,taikhoan.getMaTK());
                ps.setString(2,taikhoan.getTenDangNhap());
                ps.setString(3,taikhoan.getPassWord());
                ps.setString(4,taikhoan.getMaVaiTro());
                ps.setString(5, taikhoan.getEmail());
                ps.setBoolean(6,taikhoan.isTrangThai());
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
                String sql = "DELETE from TaiKhoan where mataikhoan=?";
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
                String sql = "UPDATE TaiKhoan SET " + "tendangnhap=?, mavaitro=?, email=?, trangthai=?" + "where mataikhoan=?";
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
    public boolean updatePassword(String matkhau, String mataikhoan) {
        if (openConnection()) {
            try {
                String sql = "UPDATE TaiKhoan SET " + "matkhau=?" + "where mataikhoan=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,mataikhoan);
                ps.setString(2,matkhau);
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
    public TaiKhoan_DTO login (String matkhau, String tendangnhap) {
        TaiKhoan_DTO taikhoan= null;
        if (openConnection()) {
            try {
                String sql = "SELECT * from TaiKhoan" + "where tendangnhap=? and matkhau=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,matkhau);
                ps.setString(2,tendangnhap);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    taikhoan = new TaiKhoan_DTO();
                    taikhoan.setMaTK(rs.getString("mataikhoan"));
                    taikhoan.setTenDangNhap(rs.getString("tendanhnhap"));
                    taikhoan.setPassWord(rs.getString("matkhau"));
                    taikhoan.setMaVaiTro(rs.getString("mavaitro"));
                    taikhoan.setEmail(rs.getString("email"));
                    taikhoan.setTrangThai(rs.getBoolean("trangthai"));
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return taikhoan;
    }

}