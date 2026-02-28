package DAO;
import java.util.ArrayList;
import java.sql.*;

import DTO.SanPham_DTO;
import Utils.databaseConnection;

public class SanPham_DAO {
    /*public static Connection getConnectionSQL() {
        Connection con = null;
        try {
            String url =
                "jdbc:sqlserver://localhost:1433;" +
                "databaseName=SieuThiMini;" +
                "encrypt=true;" +
                "trustServerCertificate=true";

            String user = "sa";
            String pass = "123456";

            con = DriverManager.getConnection(url, user, pass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }*/

    public SanPham_DTO getSanPhamByMaSP (String maSP) {
        String sql = "SELECT MaSP, TenSP, MoTa, GiaBan, DonVi, SoLuongTon, MaDM, MaKhuyenMai, ViTri FROM SanPham WHERE MaSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SanPham_DTO sp = new SanPham_DTO();
                    sp.setMaSP(rs.getString("MaSP"));
                    sp.setTenSP(rs.getString("TenSP"));
                    sp.setMoTa(rs.getString("MoTa"));
                    sp.setGiaBan(rs.getDouble("GiaBan"));
                    sp.setDonVi(rs.getDouble("DonVi"));
                    sp.setSoLuongTon(rs.getInt("SoLuongTon"));
                    sp.setMaDM(rs.getString("MaDM"));
                    sp.setMaKhuyenMai(rs.getString("MaKhuyenMai"));
                    sp.setViTri(rs.getString("ViTri"));
                    return sp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SanPham_DTO> getAllSanPham() {
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        String sql ="SELECT maSP, tenSP, moTa, giaBan, donVi, soLuongTon, maDM, MaKhuyenMai, ViTri " +
                    "FROM SanPham " +
                    "ORDER BY tenSP";
        try (Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham_DTO sp = new SanPham_DTO();
                sp.setMaSP(rs.getString("maSP"));
                sp.setTenSP(rs.getString("tenSP"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGiaBan(rs.getDouble("giaBan"));
                sp.setDonVi(rs.getDouble("donVi"));
                sp.setSoLuongTon(rs.getInt("soLuongTon"));
                sp.setMaDM(rs.getString("maDM"));
                sp.setMaKhuyenMai(rs.getString("MaKhuyenMai"));
                sp.setViTri(rs.getString("ViTri"));
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<SanPham_DTO> getAllSanPhamByMaDM (String maDM){
        ArrayList<SanPham_DTO> listSanPham = new ArrayList<>();
        
        String sql ="SELECT MaSP, TenSP " +
                    "FROM SanPham " +
                    "WHERE MaDM = ? " +
                    "ORDER BY TenSP";
        
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
             
            ps.setString(1, maDM);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham_DTO sanPham = new SanPham_DTO();
                    sanPham.setMaSP(rs.getString("MaSP"));
                    sanPham.setTenSP(rs.getString("TenSP"));
                    listSanPham.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public boolean insertSanPham (SanPham_DTO sp){
        boolean result = false;
        String sql = "insert into SanPham (MaSP, TenSP, MoTa, GiaBan, DonVi, SoLuongTon, MaDM, MaKhuyenMai, ViTri) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGiaBan());
            ps.setDouble(5, sp.getDonVi());
            ps.setInt(6, sp.getSoLuongTon());
            ps.setString(7, sp.getMaDM());
            ps.setString(8, sp.getMaKhuyenMai());
            ps.setString(9, sp.getViTri());

            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isMaSPExists(String maSP) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE MaSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTenSPExists(String tenSP) {
        String sql = "SELECT COUNT(*) FROM SanPham WHERE TenSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSanPham(SanPham_DTO sp) {
        boolean result = false;
        String sql = "UPDATE SanPham SET TenSP = ?, MoTa = ?, GiaBan = ?, DonVi = ?, SoLuongTon = ?, MaDM = ?, MaKhuyenMai = ?, ViTri = ? WHERE MaSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getMoTa());
            ps.setDouble(3, sp.getGiaBan());
            ps.setDouble(4, sp.getDonVi());
            ps.setInt(5, sp.getSoLuongTon());
            ps.setString(6, sp.getMaDM());
            ps.setString(7, sp.getMaKhuyenMai());
            ps.setString(8, sp.getViTri());
            ps.setString(9, sp.getMaSP());

            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean deleteSanPham(String maSP) {
        boolean result = false;
        String sql = "DELETE FROM SanPham WHERE MaSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            if (ps.executeUpdate() > 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}