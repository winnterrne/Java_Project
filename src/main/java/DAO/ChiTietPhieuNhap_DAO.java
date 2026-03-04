package DAO;


import DTO.ChiTietPhieuNhap_DTO;
import Utils.databaseConnection;
import org.apache.poi.ss.formula.functions.PPMT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ChiTietPhieuNhap_DAO {
    public ArrayList<ChiTietPhieuNhap_DTO> getAllChiTietPhieuNhap(){
        ArrayList<ChiTietPhieuNhap_DTO> list = new ArrayList<>();
        String sql = "select * from ChiTietPhieuNhap";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                ChiTietPhieuNhap_DTO ctpn = new ChiTietPhieuNhap_DTO(
                        rs.getString("maPhieuNhap"),
                        rs.getString("maSP"),
                        rs.getInt("soLuong"),
                        rs.getDouble("giaNhap"),
                        rs.getDate("ngayNhap").toLocalDate(),
                        rs.getDate("hanSuDung").toLocalDate(),
                        rs.getDate("ngaySanXuat").toLocalDate()
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
        String sql = "select * from ChiTietPhieuNhap where maPhieuNhap = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPN);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ChiTietPhieuNhap_DTO ctpn = new ChiTietPhieuNhap_DTO(
                        rs.getString("maPhieuNhap"),
                        rs.getString("maSP"),
                        rs.getInt("soLuong"),
                        rs.getDouble("giaNhap"),
                        rs.getDate("ngayNhap").toLocalDate(),
                        rs.getDate("hanSuDung").toLocalDate(),
                        rs.getDate("ngaySanXuat").toLocalDate()
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
        String sql = "Select sp.tenSP from ChiTietPhieuNhap ct JOIN SanPham sp ON ct.maSP = sp.maSP where ct.maSP = ?";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maSP);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    tenSP = rs.getString("tenSP");
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return  tenSP;
    }

    public int getSoLuongTonByMaSP(String maSP) {
        int soLuongTon = 0;
        String sql = "SELECT sp.soLuongTon FROM ChiTietPhieuNhap ct JOIN SanPham sp ON ct.maSP = sp.maSP where ct.maSP = ?";

        try {
            Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,  maSP);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                soLuongTon = rs.getInt("soLuongTon");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuongTon;
    }
}