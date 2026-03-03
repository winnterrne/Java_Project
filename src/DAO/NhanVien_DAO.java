package DAO;

import DTO.NhanVien_DTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class NhanVien_DAO {
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
    public ArrayList<NhanVien_DTO> getALL() {
        ArrayList<NhanVien_DTO> list = new ArrayList<>();
        if (openConnection()) {
            try {
                String sql = "SELECT * from NhanVien";
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    NhanVien_DTO nvdto = new NhanVien_DTO();
                    nvdto.setMaNV(rs.getString("maNV"));
                    nvdto.setHoTenNV(rs.getString("hoTenNV"));
                    nvdto.setLuong(rs.getDouble("luong"));
                    nvdto.setSoDT(rs.getString("soDT"));
                    nvdto.setEmail(rs.getString("email"));
                    nvdto.setDiaChi(rs.getString("diaChi"));
                    nvdto.setMaChucVu(rs.getString("maCV"));
                    list.add(nvdto);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return list;
    }
    public boolean isNhanVienExist(String manhanvien) {
        boolean result = false;
        if (openConnection()) {
            try {
                String sql = "SELECT * from NhanVien where maNV=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,manhanvien);
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
    public NhanVien_DTO getNhanVienByMa(String manhanvien) {
        if(openConnection()) {
            try {
                String sql = "SELECT * from NhanVien where maNV=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,manhanvien);
                ResultSet rs = ps.executeQuery();
                if(rs.next()) {
                    NhanVien_DTO nvdto = new NhanVien_DTO();
                    nvdto.setMaNV(rs.getString("maNV"));
                    nvdto.setHoTenNV(rs.getString("hoTenNV"));
                    return nvdto;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }finally {
                closeConnection();
            }
        }
        return null;
    }
}
