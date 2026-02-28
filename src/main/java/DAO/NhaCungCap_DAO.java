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
        String sql = "SELECT * FROM NHACUNGCAP";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                NhaCungCap_DTO ncc = new NhaCungCap_DTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email")
                );
                list.add(ncc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addNhaCungCap(NhaCungCap_DTO ncc) {
        String sql = "INSERT INTO NHACUNGCAP (MaNCC, TenNCC, SoDienThoai, DiaChi, Email) VALUES (?, ?, ?, ?, ?)";

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
        String sql = "SELECT COUNT(*) FROM NHACUNGCAP WHERE MaNCC = ?";
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
        String sql = "DELETE FROM NHACUNGCAP WHERE MaNCC = ?";
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
        String sql = "UPDATE NHACUNGCAP SET TenNCC = ?, SoDienThoai = ?, DiaChi = ?, Email = ? WHERE MaNCC = ?";

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
        String sql = "SELECT TenNCC FROM NHACUNGCAP WHERE MaNCC = ?";
        String tenNCC = "";
        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maNCC);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tenNCC = rs.getString("TenNCC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public ArrayList<NhaCungCap_DTO> timKiemNCCTheoTen(String keyword) {
        ArrayList<NhaCungCap_DTO> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM NHACUNGCAP WHERE TenNCC LIKE ?";
            Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,"%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                NhaCungCap_DTO ncc = new NhaCungCap_DTO(
                        rs.getString("MaNCC"),
                        rs.getString("TenNCC"),
                        rs.getString("SoDienThoai"),
                        rs.getString("DiaChi"),
                        rs.getString("Email")
                );
                list.add(ncc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}