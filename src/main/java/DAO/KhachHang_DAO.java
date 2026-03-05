package DAO;

import DTO.KhachHang_DTO;
import Utils.databaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_DAO {

//    private String maKH;
//    private String hoTenKH;
//    private String soDT;
//    private String diaChi;
//    private double diemTichLuy;
    public ArrayList<KhachHang_DTO> layTatCaKH() {
        ArrayList<KhachHang_DTO> list = new ArrayList<>();

        String sql = "Select * from khachhang where 1=1";


        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang_DTO dto = new KhachHang_DTO();
                    dto.setMaKH(rs.getString("maHD"));
                    dto.setHoTenKH(rs.getString("hoTenKH"));
                    dto.setSoDT(rs.getString("soDT"));
                    dto.setDiaChi(rs.getString("diaChi"));
                    dto.setDiemTichLuy(rs.getDouble("diemTichLuy"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public KhachHang_DTO layKHTheoMaKH(String maKH) {
        KhachHang_DTO dto = new KhachHang_DTO();
        String sql = "Select * from khachhang where maKH = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1,maKH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.setMaKH(rs.getString("maHD"));
                    dto.setHoTenKH(rs.getString("hoTenKH"));
                    dto.setSoDT(rs.getString("soDT"));
                    dto.setDiaChi(rs.getString("diaChi"));
                    dto.setDiemTichLuy(rs.getDouble("diemTichLuy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }


    public boolean insertKH(KhachHang_DTO dto) {
        String sql = "INSERT INTO HoaDon (maKH, hoTenKH, soDT, diaChi, diemTichLuy) VALUES (?, ?, ?, ?, ?)";
        boolean isSuccess = false;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getMaKH());
            ps.setString(2, dto.getHoTenKH());
            ps.setString(3, dto.getSoDT());
            ps.setString(4, dto.getDiaChi());
            ps.setDouble(5, dto.getDiemTichLuy());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public void deleteKhachHang(String maKH) {

    }

    public boolean updateKhachHang(KhachHang_DTO dto) {
        String sql = "UPDATE HoaDon SET hoTenKH = ?, soDT = ?, diaChi = ?, diemTichLuy = ? WHERE maKH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getHoTenKH());
            ps.setString(2, dto.getSoDT());
            ps.setString(3, dto.getDiaChi());

            ps.setDouble(4, dto.getDiemTichLuy());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String layMaKhachHangMoiNhat() {
        String maMoiNhat = null;

        String sql = "SELECT maKH FROM KhachHang ORDER BY LENGTH(maKH) DESC, maKH DESC LIMIT 1";



        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                maMoiNhat = rs.getString("maKH");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi truy vấn (query) mã khách hàng mới nhất từ Database.");
        }

        return maMoiNhat;
    }
}
