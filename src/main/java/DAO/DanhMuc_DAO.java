package DAO;
import java.util.ArrayList;
import java.sql.*;

import DTO.DanhMuc_DTO;
import Utils.databaseConnection;

public class DanhMuc_DAO {

    /*public static Connection getConnectionSQL() {
        Connection con = null;
        try {
            String url = "jdbc:sqlserver://localhost:1433;" +
                         "databaseName=SieuThiMini;" +
                         "encrypt=true;" +
                         "trustServerCertificate=true";
            String user = "sa";
            String pass = "123456";
            con = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }*/

    public ArrayList<DanhMuc_DTO> getAllDanhMuc() {
        ArrayList<DanhMuc_DTO> list = new ArrayList<>();
        String sql ="SELECT dm.MaDM, " +
                    "       dm.TenDM, " +
                    "       COUNT(sp.MaSP) AS SoLuongSanPham " +
                    "FROM DanhMuc dm " +
                    "LEFT JOIN SanPham sp ON dm.MaDM = sp.MaDM " +
                    "GROUP BY dm.MaDM, dm.TenDM " +
                    "ORDER BY dm.MaDM";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("MaDM"));
                dm.setTenDM(rs.getString("TenDM"));
                dm.setSoLuongSP(rs.getInt("SoLuongSanPham"));
                list.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DanhMuc_DTO getDanhMucByMaDM(String maDM) {
        String sql = "SELECT MaDM, TenDM FROM DanhMuc WHERE MaDM = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("MaDM"));
                dm.setTenDM(rs.getString("TenDM"));
                return dm;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertDanhMuc(DanhMuc_DTO dm) {
        if (isMaDMExists(dm.getMaDM())) {
            System.out.println("Mã danh mục đã tồn tại!");
            return false;
        }

        String sql = "INSERT INTO DanhMuc (MaDM, TenDM) VALUES (?, ?)";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dm.getMaDM());
            ps.setString(2, dm.getTenDM());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isTenDMExists(String tenDM) {
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE TenDM = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenDM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateDanhMuc(DanhMuc_DTO dm) {
        String sql = "UPDATE DanhMuc SET TenDM = ? WHERE MaDM = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDM());
            ps.setString(2, dm.getMaDM());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDanhMuc(String maDM) {
        String checkSQL = "SELECT COUNT(*) FROM SanPham WHERE MaDM = ?";
        String deleteSQL = "DELETE FROM DanhMuc WHERE MaDM = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement check = con.prepareStatement(checkSQL);
             PreparedStatement delete = con.prepareStatement(deleteSQL)) {

            check.setString(1, maDM);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Không thể xóa: Danh mục còn sản phẩm!");
                return false;
            }

            delete.setString(1, maDM);
            return delete.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isMaDMExists(String maDM) {
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE MaDM = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}