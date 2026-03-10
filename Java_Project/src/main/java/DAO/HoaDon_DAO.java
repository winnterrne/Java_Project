package DAO;

import DTO.HoaDon_DTO;
import Utils.GeneratingID;
import Utils.databaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class HoaDon_DAO {
    public ArrayList<HoaDon_DTO> layTatCaHD() {
        ArrayList<HoaDon_DTO> list = new ArrayList<>();

        String sql = "Select * from hoadon where trangThai = 1";


        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon_DTO dto = new HoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));
                    dto.setNgayLapHD(LocalDate.parse(rs.getDate("ngayLapHD").toString()));
                    dto.setMaKH(rs.getString("maKH"));
                    dto.setMaNV(rs.getString("maNV"));
                    dto.setTongTien(rs.getDouble("tongTien"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<HoaDon_DTO> layHDTheoNgay(LocalDate ngay1, LocalDate ngay2) {
        ArrayList<HoaDon_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM hoadon WHERE ngayLapHD BETWEEN ? AND ? and trangThai = 1";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(ngay1));
            ps.setDate(2, Date.valueOf(ngay2));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon_DTO dto = new HoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));

                    LocalDate ngayLap = rs.getObject("ngayLapHD", LocalDate.class);
                    dto.setNgayLapHD(ngayLap);

                    dto.setMaKH(rs.getString("maKH"));
                    dto.setMaNV(rs.getString("maNV"));
                    dto.setTongTien(rs.getDouble("tongTien"));
                    list.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<HoaDon_DTO> layHDTheoMaKH(String maKH) {
        String value = maKH;
        ArrayList<HoaDon_DTO> list = new ArrayList<>();
        String sql = "Select * from hoadon where maKH = ? and trangThai = 1";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1,value);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon_DTO dto = new HoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));
                    dto.setNgayLapHD(LocalDate.parse(rs.getDate("ngayLapHD").toString()));
                    dto.setMaKH(rs.getString("maKH"));
                    dto.setMaNV(rs.getString("maNV"));
                    dto.setTongTien(rs.getDouble("tongTien"));
                    list.add(dto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public HoaDon_DTO layHDTheoMaHD(String maHD) {
        String value = maHD;
        HoaDon_DTO dto = new HoaDon_DTO();
        String sql = "Select * from hoadon where maHD = ? and trangThai = 1";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,value);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dto.setMaHD(rs.getString("maHD"));
                    LocalDate ngayLap = rs.getObject("ngayLapHD", LocalDate.class);
                    dto.setNgayLapHD(ngayLap);
                    dto.setMaKH(rs.getString("maKH"));
                    dto.setMaNV(rs.getString("maNV"));
                    dto.setTongTien(rs.getDouble("tongTien"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public boolean insertHoaDon(HoaDon_DTO dto) {
        String sql = "INSERT INTO HoaDon (maHD, ngayLapHD, maKH, maNV, tongTien, trangThai) VALUES (?, ?, ?, ?, ?, 1)";
        boolean isSuccess = false;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {


            ps.setString(1, dto.getMaHD());
            ps.setObject(2, dto.getNgayLapHD());
            ps.setString(3, dto.getMaKH());
            ps.setString(4, dto.getMaNV());
            ps.setDouble(5, dto.getTongTien());



            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                isSuccess = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isSuccess;
    }

    public void deleteHoaDon(String maHD) {
        String sql = "update HoaDon set trangThai = ? where maHD = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,0);
            ps.setString(2,maHD);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateHoaDon(HoaDon_DTO dto) {
        String sql = "UPDATE HoaDon SET ngayLapHD = ?, maKH = ?, maNV = ?, tongTien = ? WHERE maHD = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, dto.getNgayLapHD());
            ps.setString(2, dto.getMaKH());
            ps.setString(3, dto.getMaNV());
            ps.setDouble(4, dto.getTongTien());

            ps.setString(5, dto.getMaHD());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaHoaDonMoiNhat() {
        String maHoaDon = null;
        String sql = "Select top 1 maHD from HoaDon order by maHD desc";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                maHoaDon = GeneratingID.generatingID(rs.getString("maHD"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy mã hóa đơn mới nhất từ Database.");
        }

        return maHoaDon;
    }

}