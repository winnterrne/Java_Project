package DAO;

import DTO.ChiTietPhieuNhap_DTO;
import DTO.ChiTietPhieuTra_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class ChiTietPhieuTra_DAO {

    public ArrayList<ChiTietPhieuTra_DTO> getAllChiTietPhieuTra(){
        ArrayList<ChiTietPhieuTra_DTO> listCTPT = new ArrayList<>();
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "SELECT * FROM ChiTieuPhieuNhap";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ChiTietPhieuTra_DTO ctpt = new ChiTietPhieuTra_DTO(
                        rs.getString("maPhieuTra"),
                        rs.getString("maSP"),
                        rs.getInt("soLuongTra"),
                        rs.getDouble("giaNhap"),
                        rs.getDate("ngayTra").toLocalDate()
                );
                listCTPT.add(ctpt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTPT;
    }

    public ArrayList<ChiTietPhieuTra_DTO> getChiTietPhieuTraByMaPT(String maPT){
        ArrayList<ChiTietPhieuTra_DTO> listCTPT = new ArrayList<>();
        String sql = "select * from ChiTietPhieuTra where maPT = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPT);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ChiTietPhieuTra_DTO ctpt = new ChiTietPhieuTra_DTO(
                        rs.getString("maPhieuTra"),
                        rs.getString("maSP"),
                        rs.getInt("soLuongTra"),
                        rs.getDouble("giaNhap"),
                        rs.getDate("ngayTra").toLocalDate()
                );
                listCTPT.add(ctpt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCTPT;
    }

    public String getTenSPByMaSP(String maSP) {
        String tenSP = "";
        String sql = "Select sp.TenSP from ChiTietPhieuTra ct JOIN SanPham sp ON ct.maSP = sp.maSP where ct.maSP = ?";

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


}