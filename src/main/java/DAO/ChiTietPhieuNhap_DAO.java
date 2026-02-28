package DAO;


import DTO.ChiTietPhieuNhap_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietPhieuNhap_DAO {
    public ArrayList<ChiTietPhieuNhap_DTO> getAllChiTietPhieuNhap(){
        ArrayList<ChiTietPhieuNhap_DTO> list = new ArrayList<>();
        String sql = "select * from CHITIETPHIEUNHAP";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                ChiTietPhieuNhap_DTO ctpn = new ChiTietPhieuNhap_DTO(
                        rs.getString("MaPN"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaNhap"),
                        rs.getDate("NgayNhap").toLocalDate(),
                        rs.getDate("HanSuDung").toLocalDate(),
                        rs.getDate("NgaySanXuat").toLocalDate()
                );
                list.add(ctpn);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<ChiTietPhieuNhap_DTO> getChiTietPhieuNhapByMaPN(String maPN){
        ArrayList<ChiTietPhieuNhap_DTO> list = new ArrayList<>();
        String sql = "select * from CHITIETPHIEUNHAP where MaPN = ?";

        try (Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPN);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ChiTietPhieuNhap_DTO ctpn = new ChiTietPhieuNhap_DTO(
                        rs.getString("MaPN"),
                        rs.getString("MaSP"),
                        rs.getInt("SoLuong"),
                        rs.getDouble("GiaNhap"),
                        rs.getDate("NgayNhap").toLocalDate(),
                        rs.getDate("HanSuDung").toLocalDate(),
                        rs.getDate("NgaySanXuat").toLocalDate()
                );
                list.add(ctpn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getTenSPByMaSP(String maSP) {
        String tenSP = "";
        String sql = "Select sp.TenSP from CHITIETPHIEUNHAP ct JOIN SANPHAM sp ON ct.MaSP = sp.MaSP where ct.MaSP = ?";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    tenSP = rs.getString("TenSP");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  tenSP;
    }
}