package DAO;

import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChiTietHoaDon_DAO {

//    private String maSP;
//    private int soLuong;
//    private double thanhTien;
//    private float donGia;

    public ArrayList<ChiTietHoaDon_DTO> getChiTietHoaDon(String maHD) {
        ArrayList<ChiTietHoaDon_DTO> list = new ArrayList<>();
        String sql = "select * from chitiethoadon where maHD = ?";
        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon_DTO dto = new ChiTietHoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));
                    dto.setDonGia(rs.getFloat("donGia"));
                    dto.setMaSP(rs.getString("maSP"));
                    dto.setSoLuongMua(rs.getInt("soLuong"));
                    dto.setThanhTien(rs.getFloat("thanhTien"));
                    list.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertChiTietHoaDon(ChiTietHoaDon_DTO chiTietHoaDonDto) {
        String sql = "INSERT INTO chitiethoadon (maHD, maSP, donGia, soLuong, thanhTien) VALUES (?, ?, ?, ?, ?)";
        boolean isSuccess = false;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chiTietHoaDonDto.getMaHD());
            ps.setObject(2, chiTietHoaDonDto.getMaSP());
            ps.setString(3, Float.toString(chiTietHoaDonDto.getDonGia()));
            ps.setString(4, Integer.toString(chiTietHoaDonDto.getSoLuongMua()));
            ps.setString(5, Double.toString(chiTietHoaDonDto.getThanhTien()));
            int i = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public ArrayList<ChiTietHoaDon_DTO> updateCTHD(String maHD, ChiTietHoaDon_DTO chiTietHoaDon) {
        ArrayList<ChiTietHoaDon_DTO> list = new ArrayList<>();
        String sql = "UPDATE chitiethoadon SET maSP = ?, donGia = ?, soLuong = ?, thanhTien= ? WHERE maHD = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon_DTO dto = new ChiTietHoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));
                    dto.setDonGia(rs.getFloat("donGia"));
                    dto.setMaSP(rs.getString("maSP"));
                    dto.setSoLuongMua(rs.getInt("soLuong"));
                    dto.setThanhTien(rs.getFloat("ThanhTien"));
                    list.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateChiTietHoaDon(ChiTietHoaDon_DTO chiTietHoaDonDto) {
        String sql = "insert into chitiethoadon(maSP, donGia, soLuong, thanhTien) values (?,?,?,?) chitiethoadon where maHD = ?";
        int row = 0;
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, chiTietHoaDonDto.getMaSP());
            ps.setFloat(2,chiTietHoaDonDto.getDonGia());
            ps.setInt(3, chiTietHoaDonDto.getSoLuongMua());
            ps.setDouble(4, chiTietHoaDonDto.getThanhTien());
            ps.setString(5, chiTietHoaDonDto.getMaHD());
            row = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
        if (row == 0) {
            return false;
        }
        return true;
    }

    public boolean deleteChiTietHoaDon(String maHD) {
        String sql = "delete from chitiethoadon where maHD = ?";
        int row = 0;
        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, maHD);
            row = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (row == 0) {
            return false;
        }
        return true;
    }
}
