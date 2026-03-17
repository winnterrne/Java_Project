package DAO;

import DTO.KhachHang_DTO;
import Utils.GeneratingID;
import Utils.databaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_DAO {

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
                    dto.setEmail(rs.getString("email"));
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
        KhachHang_DTO dto = null;
        String sql = "SELECT * FROM khachhang WHERE maKH = ? AND trangThai = 1";

        // Đã bỏ dấu ';' thừa ở cuối
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                // Thay while bằng if và CHỈ khởi tạo dto khi thực sự có dữ liệu
                if (rs.next()) {
                    dto = new KhachHang_DTO();
                    dto.setMaKH(rs.getString("maKH"));
                    dto.setHoTenKH(rs.getString("hoTenKH"));
                    dto.setSoDT(rs.getString("soDT"));
                    dto.setDiaChi(rs.getString("diaChi"));
                    dto.setEmail(rs.getString("email")); // Đã bổ sung email
                    dto.setDiemTichLuy(rs.getDouble("diemTichLuy"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dto; // Trả về null nếu không tìm thấy, ngược lại trả về object chứa dữ liệu
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

    public boolean isMaKhachHang(String maKH) {
        String sql = "SELECT * FROM KhachHang WHERE maKH = ?";

        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateKhachHang(KhachHang_DTO dto) {
        String sql = "UPDATE KhachHang SET hoTenKH = ?, soDT = ?, diaChi = ?,email= ?, diemTichLuy = ? WHERE maKH = ? and trangThai = 1";

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

    public void deleteKH(String maKH) {
        String sql = "update khachhang set trangThai = 0 where maKH = ?";
        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maKH);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public KhachHang_DTO timKH(String sdt) {
        String sql = "select * from khachhang where soDT = ? and trangThai = 1";
        KhachHang_DTO dto = null;
        try (Connection conn = databaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);){
            pst.setString(1, sdt);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                dto = new KhachHang_DTO();
                dto.setMaKH(rs.getString("maKH"));
                dto.setHoTenKH(rs.getString("hoTenKH"));
                dto.setSoDT(rs.getString("soDT"));
                dto.setDiaChi(rs.getString("diaChi"));
                dto.setEmail(rs.getString("email"));
                dto.setDiemTichLuy(rs.getDouble("diemTichLuy"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dto;
    }

}