package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import DTO.ThongKeDoanhThu_DTO;
import Utils.databaseConnection;

public class ThongKeDoanhThu_DAO {
    public List<ThongKeDoanhThu_DTO> getThongKeDoanhThu(int thang, int nam) {
        List<ThongKeDoanhThu_DTO> list = new ArrayList<>();

        String sql ="SELECT sp.MaSP, sp.TenSP, " +
                    "       SUM(ct.soLuong) AS soLuong, " +
                    "       SUM(ct.ThanhTien) AS tongTien " +
                    "FROM HoaDon hd " +
                    "JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD " +
                    "JOIN SanPham sp ON ct.MaSP = sp.MaSP " +
                    "WHERE MONTH(hd.NgayLapHD) = ? AND YEAR(hd.NgayLapHD) = ? " +
                    "GROUP BY sp.MaSP, sp.TenSP " +
                    "ORDER BY tongTien DESC";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            
            ps.setInt(1, thang);
            ps.setInt(2, nam);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ThongKeDoanhThu_DTO(
                        rs.getString("MaSP"),
                        rs.getString("TenSP"),
                        rs.getInt("soLuong"),
                        rs.getDouble("tongTien")
                    ));
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public double tongDoanhThuTheoThang (int thang, int nam){
        String sql ="SELECT SUM(ct.ThanhTien) AS tongDoanhThu " +
                    "FROM HoaDon hd " +
                    "JOIN ChiTietHoaDon ct ON hd.MaHD = ct.MaHD " +
                    "WHERE MONTH(hd.NgayLapHD) = ? AND YEAR(hd.NgayLapHD) = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, thang);
            ps.setInt(2, nam);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tongDoanhThu");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }
}
