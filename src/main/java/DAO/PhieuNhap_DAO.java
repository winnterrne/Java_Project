package DAO;


import DTO.ChiTietPhieuNhap_DTO;
import DTO.PhieuNhap_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class PhieuNhap_DAO {
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        ArrayList<PhieuNhap_DTO> list = new ArrayList<>();
        String sql =  "SELECT * FROM PhieuNhap WHERE trangThai = 1";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                PhieuNhap_DTO pn = new PhieuNhap_DTO(
                        rs.getString("maPhieuNhap"),
                        rs.getDate("ngayNhapHang").toLocalDate(),
                        rs.getDouble("tongTien"),
                        rs.getString("maNCC"),
                        rs.getString("maNV")
                );
                list.add(pn);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public String getTenNCCByMaPN(String maPN) {
        String tenNCC = "";
        String sql = "SELECT ncc.tenNCC " +
                "FROM PhieuNhap pn " +
                "JOIN NhaCungCap ncc ON pn.maNCC = ncc.maNCC " +
                "WHERE pn.maPhieuNhap = ? AND pn.trangThai = 1";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tenNCC = rs.getString("tenNCC");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public boolean deletePhieuNhap(String maPN) {
        String sql = "UPDATE PhieuNhap SET trangThai = 0 WHERE maPhieuNhap = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuNhap_DTO getPhieuNhapbyMaPN(String maPN) {
        PhieuNhap_DTO pn = null;
        String sql = "SELECT * FROM PhieuNhap WHERE maPhieuNhap = ? AND trangThai = 1";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pn = new PhieuNhap_DTO(
                            rs.getString("maPhieuNhap"),
                            rs.getDate("ngayNhapHang").toLocalDate(),
                            rs.getDouble("tongTien"),
                            rs.getString("maNCC"),
                            rs.getString("maNV")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pn;
    }

    public ArrayList<PhieuNhap_DTO> timPhieuNhapTheoTenNCCHoacMaPN(String keyword) {
        ArrayList<PhieuNhap_DTO> list = new ArrayList<>();
        try {
            Connection con = databaseConnection.getConnection();
            String sql = """
                    SELECT pn.*
                    FROM PhieuNhap pn
                    JOIN NhaCungCap ncc on pn.maNCC = ncc.maNCC
                    WHERE pn.trangThai = 1
                    AND   (pn.maPhieuNhap LIKE ? OR ncc.tenNCC LIKE ?)
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PhieuNhap_DTO pn = new PhieuNhap_DTO(
                        rs.getString("maPhieuNhap"),
                        rs.getDate("ngayNhapHang").toLocalDate(),
                        rs.getDouble("tongTien"),
                        rs.getString("maNCC"),
                        rs.getString("maNV")
                );
                list.add(pn);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhieuNhap_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay,  Double giaTu, Double giaDen) {
        ArrayList<PhieuNhap_DTO> list = new ArrayList<>();

        try {
            Connection con = databaseConnection.getConnection();
            String sql = """
                        SELECT pn.* FROM PhieuNhap pn
                        JOIN NhaCungCap ncc on pn.maNCC = ncc.maNCC
                        WHERE pn.trangThai = 1
                        """;

            ArrayList<Object> danhSachThamSo = new ArrayList<>();

            // Tìm theo mã
            if (!keyword.trim().isEmpty()) {
                sql += " AND (pn.maPhieuNhap LIKE ? or ncc.tenNCC LIKE ?)";
                danhSachThamSo.add("%" + keyword + "%");
                danhSachThamSo.add("%" + keyword + "%");
            }

            // Lọc ngày
            if (tuNgay != null && denNgay != null) {
                sql += " AND ngayNhapHang BETWEEN ? AND ?";
                danhSachThamSo.add(new Date(tuNgay.getTime()));
                danhSachThamSo.add(new Date(denNgay.getTime()));
            }

            // Lọc giá
            if (giaTu != null && giaDen != null) {
                sql += " AND tongTien BETWEEN ? AND ?";
                danhSachThamSo.add(giaTu);
                danhSachThamSo.add(giaDen);
            }

            PreparedStatement ps = con.prepareStatement(sql);

            // Gán tham số
            for (int i = 0; i < danhSachThamSo.size(); i++) {
                ps.setObject(i + 1, danhSachThamSo.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhieuNhap_DTO pn = new PhieuNhap_DTO(
                        rs.getString("maPhieuNhap"),
                        rs.getDate("ngayNhapHang").toLocalDate(),
                        rs.getDouble("tongTien"),
                        rs.getString("maNCC"),
                        rs.getString("maNV")

                );
                list.add(pn);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getMaPNLonNhat() {
        String lastMaPN = null;
        String sql = "SELECT TOP 1 maPhieuNhap " +
                "FROM PhieuNhap WHERE trangThai = 1" +
                "ORDER BY CAST(SUBSTRING(maPhieuNhap, 3, LEN(maPhieuNhap)) AS INT) DESC";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lastMaPN = rs.getString("maPhieuNhap");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastMaPN;
    }

    public boolean themPhieuNhapVaChiTiet(PhieuNhap_DTO pn, ArrayList<ChiTietPhieuNhap_DTO> listCT) {
        String sqlPN = "INSERT INTO PhieuNhap(maPhieuNhap, ngayNhapHang, tongTien, maNCC, maNV, trangThai) VALUES (?, ?, ?, ?, ?, 1)";
        Connection con = null;
        try {
            con = databaseConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement psPN = con.prepareStatement(sqlPN);
            psPN.setString(1, pn.getMaPhieuNhap());
            LocalDate ngayNhap = LocalDate.now();
            Date sqlDate = Date.valueOf(ngayNhap);
            psPN.setDate(2, sqlDate);
            psPN.setDouble(3, pn.getTongTien());
            psPN.setString(4, pn.getMaNCC());
            psPN.setString(5, pn.getMaNV());

            psPN.executeUpdate();

            String sqlCT = "INSERT INTO ChiTietPhieuNhap(maPhieuNhap, maSP, soLuong, giaNhap, ngayNhap, hanSuDung, ngaySanXuat) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psCT = con.prepareStatement(sqlCT);

            for(ChiTietPhieuNhap_DTO ct : listCT) {
                psCT.setString(1, ct.getMaPhieuNhap());
                psCT.setString(2, ct.getMaSP());
                psCT.setInt(3, ct.getSoLuong());
                psCT.setDouble(4, ct.getGiaNhap());
                psCT.setDate(5, ct.getNgayNhapToDate());
                psCT.setDate(6, ct.getHanSuDungToDate());
                psCT.setDate(7, ct.getNgaySanXuatToDate());
                psCT.addBatch();
            }
            psCT.executeBatch();
            con.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int getSoLuongNhap(String maPhieuNhap, String maSP) {
        int soLuong = 0;
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "SELECT ct.soLuong FROM PhieuNhap pn JOIN ChiTietPhieuNhap ct ON pn.maPhieuNhap = ct.maPhieuNhap Where ct.maPhieuNhap = ? AND ct.maSP = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPhieuNhap);
            ps.setString(2, maSP);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                soLuong = rs.getInt("soLuong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }

}