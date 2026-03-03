package DAO;

import DTO.HoaDon_DTO;
import Utils.databaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HoaDon_DAO {
    public ArrayList<HoaDon_DTO> layTatCaHD() {
        ArrayList<HoaDon_DTO> list = new ArrayList<>();

        String sql = "Select * from hoadon where 1=1";


        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon_DTO dto = new HoaDon_DTO();
                    dto.setMaHD(rs.getString("maHD"));
                    dto.setNgayLapHD(LocalDate.parse(rs.getDate("ngayLapHD").toString()));
                    dto.setMaKH(rs.getString("maKH"));
                    dto.setMaNV(rs.getString("maNV"));
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
        String sql = "SELECT * FROM hoadon WHERE ngayLapHD BETWEEN ? AND ?";

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
        String sql = "Select * from hoadon where maKH = ?";
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
        String sql = "Select * from hoadon where maHD = ?";
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
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

    public boolean insertHoaDon(HoaDon_DTO dto) {
        String sql = "INSERT INTO HoaDon (maHD, ngayLapHD, maKH, maNV) VALUES (?, ?, ?, ?)";
        boolean isSuccess = false;

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Truyền dữ liệu từ DTO vào các tham số (?) trong câu lệnh SQL
            ps.setString(1, dto.getMaHD());
            ps.setObject(2, dto.getNgayLapHD()); // Ghi chú: JDBC phiên bản 4.2 trở lên hỗ trợ trực tiếp java.time.LocalDate
            ps.setString(3, dto.getMaKH());
            ps.setString(4, dto.getMaNV());

            // Thực thi câu lệnh INSERT
            // executeUpdate() trả về số dòng (rows) bị tác động trong Database
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

    }

    public boolean updateHoaDon(HoaDon_DTO dto) {
        String sql = "UPDATE HoaDon SET ngayLapHD = ?, maKH = ?, maNV = ? WHERE maHD = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setObject(1, dto.getNgayLapHD());
            ps.setString(2, dto.getMaKH());
            ps.setString(3, dto.getMaNV());

            ps.setString(4, dto.getMaHD());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String layMaHoaDonMoiNhat() {
        String maHoaDon = null;
        String sql = "SELECT MaHoaDon FROM HoaDon ORDER BY MaHoaDon DESC LIMIT 1";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (rs.next()) {
                maHoaDon = rs.getString("MaHoaDon");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy mã hóa đơn mới nhất từ Database.");
        }

        return maHoaDon;
    }

}
