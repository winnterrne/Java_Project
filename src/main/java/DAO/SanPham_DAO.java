package DAO;

import DTO.SanPham_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SanPham_DAO {
    public ArrayList<SanPham_DTO> getAllSanPhamAvailable() {
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM SanPham
            where trangThai = 1
            ORDER BY maSP ASC
            """;
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham_DTO sp = new SanPham_DTO();
                sp.setMaSP(rs.getString("maSp"));
                sp.setTenSP(rs.getString("tenSP"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGiaBan(rs.getDouble("giaBan"));
                sp.setDonVi(rs.getString("donVi"));
                sp.setSoLuongTon(rs.getInt("soLuongTon"));
                sp.setMaDM(rs.getString("maDM"));
                sp.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                sp.setViTri(rs.getString("viTri"));
                sp.setTrangThai(rs.getByte("trangThai"));
                sp.setPath(rs.getString("Path"));
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ArrayList<SanPham_DTO> getAllSanPham() {
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        String sql = """
            SELECT maSP, tenSP, moTa, giaBan, donVi, soLuongTon, maDM, maKhuyenMai, viTri, Path
            FROM SanPham
            ORDER BY maSP ASC
            """;
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                SanPham_DTO sp = new SanPham_DTO();
                sp.setMaSP(rs.getString("maSp"));
                sp.setTenSP(rs.getString("tenSP"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGiaBan(rs.getDouble("giaBan"));
                sp.setDonVi(rs.getString("donVi"));
                sp.setSoLuongTon(rs.getInt("soLuongTon"));
                sp.setMaDM(rs.getString("maDM"));
                sp.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                sp.setViTri(rs.getString("viTri"));
                sp.setPath(rs.getString("Path"));
                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public SanPham_DTO getSanPhamByMaSP (String maSP) {
        String sql = "SELECT maSP, tenSP, moTa, giaBan, donVi, soLuongTon, maDM, maKhuyenMai, viTri, Path FROM SanPham WHERE maSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    SanPham_DTO sp = new SanPham_DTO();
                    sp.setMaSP(rs.getString("maSP"));
                    sp.setTenSP(rs.getString("tenSP"));
                    sp.setMoTa(rs.getString("moTa"));
                    sp.setGiaBan(rs.getDouble("giaBan"));
                    sp.setDonVi(rs.getString("donVi"));
                    sp.setSoLuongTon(rs.getInt("soLuongTon"));
                    sp.setMaDM(rs.getString("maDM"));
                    sp.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                    sp.setViTri(rs.getString("viTri"));
                    sp.setPath(rs.getString("Path"));
                    return sp;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<SanPham_DTO> getAllSanPhamByMaDM (String maDM){
        ArrayList<SanPham_DTO> listSanPham = new ArrayList<>();

        String sql = """
                Select maSP, tenSP
                From SanPham
                Where maDM = ?
                Order By tenSP
                """;

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maDM);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SanPham_DTO sanPham = new SanPham_DTO();
                    sanPham.setMaSP(rs.getString("maSP"));
                    sanPham.setTenSP(rs.getString("tenSP"));
                    listSanPham.add(sanPham);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listSanPham;
    }

    public ArrayList<SanPham_DTO> getAllTenSP (){
        ArrayList<SanPham_DTO> list = new ArrayList<>();

        String sql = """
                SELECT sp.TenSP, SUM(ct.soLuong) AS tongSoLuong
                FROM HoaDon hd
                JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD
                JOIN SanPham sp ON sp.MaSP = ct.MaSP
                WHERE MONTH(hd.NgayLapHD) = ? AND YEAR(hd.NgayLapHD) = ?
                GROUP BY sp.TenSP
                """;;

        try (Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SanPham_DTO sp = new SanPham_DTO();
                sp.setTenSP(rs.getString("tenSP"));
                list.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean insertSanPham (SanPham_DTO sp){
        boolean result = false;
        String sql = "insert into SanPham (maSP, tenSP, moTa, giaBan, donVi, soLuongTon, maDM, Path, viTri, trangThai) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1)";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGiaBan());
            ps.setString(5, sp.getDonVi());
            ps.setInt(6, 0);
            ps.setString(7, sp.getMaDM());
            ps.setString(8, sp.getPath());
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
        String sql = "SELECT COUNT(*) FROM SanPham WHERE maSP = ?";
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
        String sql = "SELECT COUNT(*) FROM SanPham WHERE tenSP = ?";
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
        String sql = "UPDATE SanPham SET tenSP = ?, moTa = ?, giaBan = ?, donVi = ?, soLuongTon = ?, maDM = ?, maKhuyenMai = ?, viTri = ? WHERE maSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, sp.getTenSP());
            ps.setString(2, sp.getMoTa());
            ps.setDouble(3, sp.getGiaBan());
            ps.setString(4, sp.getDonVi());
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

    public void updateSoLuongTonSP(SanPham_DTO sp){

        try(Connection con = databaseConnection.getConnection()) {
            String sql = "UPDATE SanPham SET soLuongTon=? WHERE maSP=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, sp.getSoLuongTon());
            ps.setString(2, sp.getMaSP());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<SanPham_DTO> timSanPhamTheoTen(String tenSP) {
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM SanPham WHERE tenSP LIKE ?";
        try {
            Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + tenSP + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SanPham_DTO sp = new SanPham_DTO();
                sp.setMaSP(rs.getString("maSP"));
                sp.setTenSP(rs.getString("tenSP"));
                sp.setMoTa(rs.getString("moTa"));
                sp.setGiaBan(rs.getDouble("giaBan"));
                sp.setDonVi(rs.getString("donVi"));
                sp.setSoLuongTon(rs.getInt("soLuongTon"));
                sp.setMaDM(rs.getString("maDM"));
                sp.setMaKhuyenMai(rs.getString("maKhuyenMai"));
                sp.setViTri(rs.getString("viTri"));
                sp.setPath(rs.getString("Path"));
                list.add(sp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // public String getMaxMaSPByMaDM (String maDM){
    //     String sql = "Select max (maSP) as maxMaSP from SanPham where maDM = ?";
    //     try (Connection con = databaseConnection.getConnection();
    //          PreparedStatement ps = con.prepareStatement(sql)) {

    //         ps.setString(1, maDM);
    //         try (ResultSet rs = ps.executeQuery()) {
    //             if (rs.next()) {
    //                 return rs.getString("maxMaSP");
    //             }
    //         }
    //     }catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

    public String getMaxMaSP() {
        String sql = "SELECT TOP 1 maSP FROM SanPham ORDER BY maSP DESC";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("maSP");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private boolean updateTrangThai(String maSP, byte trangThai) {
        boolean result = false;
        String sql = "UPDATE SanPham SET trangThai = ? WHERE maSP = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setByte(1, trangThai);
            ps.setString(2, maSP);
            result = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    private SanPham_DTO mapRowToDTO(ResultSet rs) throws SQLException {
        SanPham_DTO sp = new SanPham_DTO();
        sp.setMaSP(rs.getString("maSP"));
        sp.setTenSP(rs.getString("tenSP"));
        sp.setMoTa(rs.getString("moTa"));
        sp.setGiaBan(rs.getDouble("giaBan"));
        sp.setDonVi(rs.getString("donVi"));
        sp.setSoLuongTon(rs.getInt("soLuongTon"));
        sp.setMaDM(rs.getString("maDM"));
        sp.setMaKhuyenMai(rs.getString("maKhuyenMai"));
        sp.setViTri(rs.getString("viTri"));
        sp.setTrangThai(rs.getByte("trangThai"));
        return sp;
    }

    public ArrayList<SanPham_DTO> getAllSanPhamDaXoa (){
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        String sql = """
                Select *
                from SanPham
                where trangThai = 0
                order by maDM ASC, maSP ASC
                """;

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRowToDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean restoreSanPham(String maSP) {
        return updateTrangThai(maSP, (byte) 1);
    }

    public boolean deleteSanPham(String maSP) {
        return updateTrangThai(maSP, (byte) 0);
    }

    public int getSoLuongTon(String maSP) {
        int soLuongTon = 0;
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "Select soLuongTon from SanPham where maSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maSP);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                soLuongTon = rs.getInt("soLuongTon");
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return  soLuongTon;
    }

    public boolean luuSanPham(SanPham_DTO sp) {
        // Đảm bảo SQL có đúng 11 dấu chấm hỏi nếu bạn muốn set trạng thái từ Java
        String sql = "INSERT INTO SanPham (maSP, tenSP, moTa, giaBan, donVi, soLuongTon, maDM, maKhuyenMai, viTri, Path, trangThai) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getTenSP());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGiaBan());
            ps.setString(5, sp.getDonVi());
            ps.setInt(6, sp.getSoLuongTon());
            ps.setString(7, sp.getMaDM());
            ps.setString(8, sp.getMaKhuyenMai());
            ps.setString(9, sp.getViTri());
            
            // KIỂM TRA KỸ DÒNG 10 NÀY:
            // Nếu Path null, ta gán chuỗi rỗng hoặc một đường dẫn mặc định
            String path = (sp.getPath() == null) ? "" : sp.getPath();
            ps.setString(10, path); 

            // Dòng 11: Trạng thái
            ps.setInt(11, 1); 

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}