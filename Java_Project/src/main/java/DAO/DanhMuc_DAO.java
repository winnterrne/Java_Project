package DAO;
import java.util.ArrayList;
import java.sql.*;

import DTO.DanhMuc_DTO;
import Utils.databaseConnection;

public class DanhMuc_DAO {

    /*public static Connection getConnectionSQL() {
        Connection con = null;
        try {
            String url = "jdbc:sqlserver:
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

    public ArrayList<DanhMuc_DTO> getAllDanhMucAvailable() {
        ArrayList<DanhMuc_DTO> list = new ArrayList<>();
        String sql = """
            SELECT dm.maDM, dm.tenDM, 
                   COUNT(CASE WHEN sp.trangThai = 1 THEN sp.maSP END) AS soLuongSP
            FROM DanhMuc dm
            LEFT JOIN SanPham sp ON dm.maDM = sp.maDM
            WHERE dm.trangThai = 1
            GROUP BY dm.maDM, dm.tenDM
            ORDER BY dm.maDM
            """;
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("maDM"));
                dm.setTenDM(rs.getString("tenDM"));
                dm.setSoLuongSP(rs.getInt("soLuongSP"));
                list.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<DanhMuc_DTO> getAllDanhMuc() {
        ArrayList<DanhMuc_DTO> list = new ArrayList<>();
        String sql ="SELECT dm.maDM, " +
                "       dm.tenDM, " +
                "       COUNT(sp.maSP) AS soLuongSP " +
                "FROM DanhMuc dm " +
                "LEFT JOIN SanPham sp ON dm.maDM = sp.maDM " +
                "GROUP BY dm.maDM, dm.tenDM " +
                "ORDER BY dm.maDM";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("maDM"));
                dm.setTenDM(rs.getString("tenDM"));
                dm.setSoLuongSP(rs.getInt("soLuongSP"));
                list.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<DanhMuc_DTO> getAllDanhMucDaXoa() {
        ArrayList<DanhMuc_DTO> list = new ArrayList<>();
        String sql = """
            SELECT dm.maDM, dm.tenDM, 
                   COUNT(CASE WHEN sp.trangThai = 1 THEN sp.maSP END) AS soLuongSP
            FROM DanhMuc dm
            LEFT JOIN SanPham sp ON dm.maDM = sp.maDM
            WHERE dm.trangThai = 0
            GROUP BY dm.maDM, dm.tenDM
            ORDER BY dm.maDM
            """;
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("maDM"));
                dm.setTenDM(rs.getString("tenDM"));
                dm.setSoLuongSP(rs.getInt("soLuongSP"));
                list.add(dm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DanhMuc_DTO getDanhMucByMaDM(String maDM) {
        String sql = "SELECT maDM, tenDM FROM DanhMuc WHERE maDM = ? and trangThai = 1";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDM);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DanhMuc_DTO dm = new DanhMuc_DTO();
                dm.setMaDM(rs.getString("maDM"));
                dm.setTenDM(rs.getString("tenDM"));
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

        String sql = "INSERT INTO DanhMuc (maDM, tenDM, trangThai) VALUES (?, ?, 1)";
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
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE tenDM = ? and trangThai = 1";
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
        String sql = "UPDATE DanhMuc SET tenDM = ? WHERE maDM = ? and trangThai = 1";
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

    
    
    

    
    
    

    
    
    
    
    
    

    
    

    
    
    
    
    

    public boolean isMaDMExists(String maDM) {
        String sql = "SELECT COUNT(*) FROM DanhMuc WHERE maDM = ? and trangThai = 1";
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

    public String getMaxMADM (){
        String sql = "Select max(MaDM) as maxMaDM from DanhMuc where trangThai = 1";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){

            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return rs.getString("maxMaDM");
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean updateTrangThaiDanhMuc(String maDM, byte trangThai) {
        String sql = "UPDATE DanhMuc SET trangThai = ? WHERE maDM = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setByte(1, trangThai);
            ps.setString(2, maDM);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDanhMuc(String maDM) {
        String checkSQL = """
            SELECT COUNT(*) 
            FROM SanPham 
            WHERE maDM = ? AND trangThai = 1
            """;

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement check = con.prepareStatement(checkSQL)) {

            check.setString(1, maDM);
            try (ResultSet rs = check.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("Không thể xóa: Danh mục còn sản phẩm đang hoạt động!");
                    return false;
                }
            }

            return updateTrangThaiDanhMuc(maDM, (byte) 0);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean restoreDanhMuc(String maDM) {
        return updateTrangThaiDanhMuc(maDM, (byte) 1);
    }
}