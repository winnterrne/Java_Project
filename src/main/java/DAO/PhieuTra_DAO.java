package DAO;

import DTO.ChiTietPhieuTra_DTO;
import DTO.PhieuNhap_DTO;
import DTO.PhieuTra_DTO;
import Utils.databaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class PhieuTra_DAO {
    public ArrayList<PhieuTra_DTO> getAllPhieuTra() {
        ArrayList<PhieuTra_DTO> list = new ArrayList<>();
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "SELECT * FROM PhieuTra WHERE trangThai = 1";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PhieuTra_DTO pt = new PhieuTra_DTO(
                        rs.getString("maPhieuTra"),
                        rs.getString("lyDo"),
                        rs.getString("maNV"),
                        rs.getString("maNCC"),
                        rs.getString("maPhieuNhap"),
                        rs.getDouble("tongTra"),
                        rs.getDate("ngayTra").toLocalDate()
                );
                list.add(pt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getTenNCCByMaPT(String maPT) {
        String tenNCC = "";
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "SELECT ncc.TenNCC FROM NhaCungCap ncc JOIN PhieuTra pt ON ncc.MaNCC = pt.MaNCC WHERE MaPhieuTra = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tenNCC = rs.getString("TenNCC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public boolean taoPhieuTraVaChiTiet(PhieuTra_DTO pt, ArrayList<ChiTietPhieuTra_DTO> listCT) {
        Connection con = null;
        try {
            con = databaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlPT = "INSERT INTO PhieuTra(maPhieuTra, lyDo, maNV, maNCC, trangThai, maPhieuNhap, tongTra, ngayTra) VALUES(?, ?, ?, ?, 1, ?, ?, ?)";
            PreparedStatement psPT = con.prepareStatement(sqlPT);
            psPT.setString(1, pt.getMaPhieuTra());
            psPT.setString(2, pt.getLyDo());
            psPT.setString(3, pt.getMaNV());
            psPT.setString(4, pt.getMaNCC());
            psPT.setString(5, pt.getMaPhieuNhap());
            psPT.setDouble(6, pt.getTongTra());
            psPT.setDate(7, Date.valueOf(pt.getNgayTra()));

            psPT.executeUpdate();

            String sqlCT = "INSERT INTO ChiTietPhieuTra(maPhieuTra, maSP, ngayTra, soLuongTra, giaNhap) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement psCT = con.prepareStatement(sqlCT);
            for(ChiTietPhieuTra_DTO ct : listCT) {
                psCT.setString(1, ct.getMaPhieuTra());
                psCT.setString(2, ct.getMaSP());
                psCT.setDate(3, Date.valueOf(ct.getNgayTra()));
                psCT.setInt(4, ct.getSoLuongTra());
                psCT.setDouble(5, ct.getGiaNhap());
                psCT.addBatch();
            }
            psCT.executeBatch();
            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if(con != null) con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if(con != null) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getMaPTLonNhat() {
        String lastMaPN = null;
        String sql = "SELECT TOP 1 maPhieuTra " +
                "FROM PhieuTra WHERE trangThai = 1" +
                "ORDER BY CAST(SUBSTRING(maPhieuTra, 3, LEN(maPhieuTra)) AS INT) DESC";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                lastMaPN = rs.getString("maPhieuTra");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lastMaPN;
    }

    public ArrayList<PhieuTra_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, Double giaTu, Double giaDen) {
        ArrayList<PhieuTra_DTO> list = new ArrayList<>();

        try {
            Connection con = databaseConnection.getConnection();
            String sql = """
                        SELECT pt.* FROM PhieuTra pt
                        JOIN NhaCungCap ncc on pt.MaNCC = ncc.MaNCC
                        WHERE pt.trangThai = 1
                        """;

            ArrayList<Object> danhSachThamSo = new ArrayList<>();

            // Tìm theo mã
            if (!keyword.trim().isEmpty()) {
                sql += " AND (pt.MaPhieuTra LIKE ? or ncc.TenNCC LIKE ?)";
                danhSachThamSo.add("%" + keyword + "%");
                danhSachThamSo.add("%" + keyword + "%");
            }

            // Lọc ngày
            if (tuNgay != null && denNgay != null) {
                sql += " AND ngayTra BETWEEN ? AND ?";
                danhSachThamSo.add(new Date(tuNgay.getTime()));
                danhSachThamSo.add(new Date(denNgay.getTime()));
            }

            // Lọc giá
            if (giaTu != null && giaDen != null) {
                sql += " AND tongTra BETWEEN ? AND ?";
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
                PhieuTra_DTO pt = new PhieuTra_DTO(
                        rs.getString("maPhieuTra"),
                        rs.getString("lyDo"),
                        rs.getString("maNV"),
                        rs.getString("maNCC"),
                        rs.getString("maPhieuNhap"),
                        rs.getDouble("tongTra"),
                        rs.getDate("ngayTra").toLocalDate()
                );
                list.add(pt);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deletePhieuTra(String maPT) {
        String sql = "UPDATE PhieuTra SET trangThai = 0 WHERE maPhieuTra = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPT);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuTra_DTO getPhieuTraByMaPT(String maPT) {
        PhieuTra_DTO pt = null;
        String sql = "SELECT * FROM PhieuTra WHERE MaPhieuTra = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pt = new PhieuTra_DTO(
                            rs.getString("maPhieuTra"),
                            rs.getString("lyDo"),
                            rs.getString("maNV"),
                            rs.getString("maNCC"),
                            rs.getString("maPhieuNhap"),
                            rs.getDouble("tongTra"),
                            rs.getDate("ngayTra").toLocalDate()
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pt;
    }
}