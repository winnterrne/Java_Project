package DAO;

import DTO.NhaCungCap_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class NhaCungCap_DAO {
    public ArrayList<NhaCungCap_DTO> getAllNhaCungCap() {
        ArrayList<NhaCungCap_DTO> list = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap WHERE trangThai = 1";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                NhaCungCap_DTO ncc = new NhaCungCap_DTO(
                        rs.getString("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("soDT"),
                        rs.getString("diaChi"),
                        rs.getString("email")
                );
                list.add(ncc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNhaCungCap(NhaCungCap_DTO ncc) {
        String sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, soDT, diaChi, email, trangThai) VALUES (?, ?, ?, ?, ?, 1)";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ncc.getMaNCC());
            ps.setString(2, ncc.getTenNCC());
            ps.setString(3, ncc.getSoDT());
            ps.setString(4, ncc.getDiaChi());
            ps.setString(5, ncc.getEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isDuplicateMaNCC(String maNCC) {
        String sql = "SELECT COUNT(*) FROM NhaCungCap WHERE maNCC = ? AND trangThai = 1";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNCC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int count = rs.getInt(1);
                return count>0;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteNhaCungCap(String maNCC) {
        String sql = "UPDATE NhaCungCap SET trangThai = 0 WHERE maNCC = ?";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNCC);
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNhaCungCap(NhaCungCap_DTO ncc) {
        String sql = "UPDATE NhaCungCap SET tenNCC = ?, soDT = ?, diaChi = ?, email = ? WHERE maNCC = ? and trangThai = 1";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ncc.getTenNCC());
            ps.setString(2, ncc.getSoDT());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getEmail());
            ps.setString(5, ncc.getMaNCC());
            return ps.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getTenNhaCungCapByMaNCC(String maNCC) {
        String sql = "SELECT tenNCC FROM NhaCungCap WHERE maNCC = ? AND trangThai = 1";
        String tenNCC = "";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNCC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tenNCC = rs.getString("tenNCC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public ArrayList<NhaCungCap_DTO> timKiemNCCTheoTen(String keyword) {
        ArrayList<NhaCungCap_DTO> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM NhaCungCap WHERE tenNCC LIKE ? AND trangThai = 1";
            Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,"%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                NhaCungCap_DTO ncc = new NhaCungCap_DTO(
                        rs.getString("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("soDT"),
                        rs.getString("diaChi"),
                        rs.getString("email")
                );
                list.add(ncc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getMaNCCLonNhat() {
        String lastMaNCC = null;
        String sql = "SELECT TOP 1 maNCC " +
                "FROM NhaCungCap WHERE trangThai = 1" +
                "ORDER BY CAST(SUBSTRING(maNCC, 4, LEN(maNCC)) AS INT) DESC";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lastMaNCC = rs.getString("maNCC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastMaNCC;
    }

}