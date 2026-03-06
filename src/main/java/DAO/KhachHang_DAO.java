package DAO;

import DTO.KhachHang_DTO;
import Utils.GeneratingID;
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

        String sql = "Select * from khachhang where trangThai = 1";


        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang_DTO dto = new KhachHang_DTO();
                    dto.setMaKH(rs.getString("maKH"));
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
        String sql = "Select * from khachhang where maKH = ? and trangThai = 1";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1,maKH);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.setMaKH(rs.getString("maKH"));
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
        String sql = "INSERT INTO khachhang (maKH, hoTenKH, soDT, diaChi, email, diemTichLuy, trangThai) VALUES (?, ?, ?, ?, ?, ?, 1)";
        boolean isSuccess = false;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getMaKH());
            ps.setString(2, dto.getHoTenKH());
            ps.setString(3, dto.getSoDT());
            ps.setString(4, dto.getDiaChi());
            ps.setString(5, dto.getEmail());
            ps.setDouble(6, dto.getDiemTichLuy());

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
        String sql = "update khachhang set trangThai = ? where maKH = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,0);
            ps.setString(2,maKH);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateKhachHang(KhachHang_DTO dto) {
        String sql = "UPDATE KhachHang SET hoTenKH = ?, soDT = ?, diaChi = ?,email= ?, diemTichLuy = ? WHERE maKH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, dto.getHoTenKH());
            ps.setString(2, dto.getSoDT());
            ps.setString(3, dto.getDiaChi());
            ps.setString(4, dto.getEmail());
            ps.setDouble(5, dto.getDiemTichLuy());
            ps.setString(6, dto.getMaKH());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public String layMaKhachHangMoiNhat() {
        String maMoiNhat = null;

        String sql = "Select top 1 maKH from KhachHang order by maKH desc";




        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                maMoiNhat = GeneratingID.generatingID(rs.getString("maKH"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi truy vấn (query) mã khách hàng mới nhất từ Database.");
        }

        return maMoiNhat;
    }
}