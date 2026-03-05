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
            String sql = "SELECT * FROM PhieuTra";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PhieuTra_DTO pt = new PhieuTra_DTO(
                        rs.getString("maPhieuTra"),
                        rs.getString("lyDo"),
                        rs.getString("maNV"),
                        rs.getString("maPhieuNhap"),
                        rs.getString("maNCC"),
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
            String sql = "SELECT ncc.tenNCC FROM NhaCungCap ncc JOIN PhieuTra pt ON ncc.maNCC = pt.maNCC WHERE maPhieuTra = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPT);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                tenNCC = rs.getString("tenNCC");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public boolean taoPhieuTra(PhieuTra_DTO pt, ArrayList<ChiTietPhieuTra_DTO> listCT) {
        Connection con = null;
        try {
            con = databaseConnection.getConnection();
            con.setAutoCommit(false);

            String sqlPT = "INSERT INTO PhieuTra(maPhieuTra, lyDo, maNV, maPhieuNhap, maNCC, tongTra, ngayTra) VALUES(?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psPT = con.prepareStatement(sqlPT);
            psPT.setString(1, pt.getMaPhieuTra());
            psPT.setString(2, pt.getLyDo());
            psPT.setString(3, pt.getMaNV());
            psPT.setString(4, pt.getMaPhieuNhap());
            psPT.setString(5, pt.getMaNCC());
            psPT.setDouble(6, pt.getTongTra());
            psPT.setDate(7, Date.valueOf(pt.getNgayTra()));

            psPT.executeUpdate();

            String sqlCT = "INSERT INTO ChiTietPhieuTra(maPT, maSP, soLuongTra, giaNhap, ngayTra) VALUES(?, ?, ?, ?, ?)";
            PreparedStatement psCT = con.prepareStatement(sqlCT);
            for(ChiTietPhieuTra_DTO ct : listCT) {
                psCT.setString(1, ct.getMaPhieuTra());
                psCT.setString(2, ct.getMaSP());
                psCT.setInt(3, ct.getSoLuongTra());
                psCT.setDouble(4, ct.getGiaNhap());
                psCT.setDate(5, Date.valueOf(ct.getNgayTra()));
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
                if(con != null) {} con.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String getMaPTLonNhat() {
        String LastMaPT = null;
        try {
            Connection con = databaseConnection.getConnection();
            String sql = "SELECT MAX(maPhieuTra) as maPhieuTraMax FROM PhieuTra";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                LastMaPT = rs.getString("maPhieuTraMax");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return LastMaPT;
    }

    public ArrayList<PhieuTra_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, Double giaTu, Double giaDen) {
        ArrayList<PhieuTra_DTO> list = new ArrayList<>();

        try {
            Connection con = databaseConnection.getConnection();
            String sql = """
                        SELECT pt.* FROM PhieuTra pt
                        JOIN NhaCungCap ncc on pt.maNCC = ncc.maNCC
                        WHERE 1 = 1
                        """;

            ArrayList<Object> danhSachThamSo = new ArrayList<>();

            // Tìm theo mã
            if (!keyword.trim().isEmpty()) {
                sql += " AND (pt.maPhieuTra LIKE ? or ncc.tenNCC LIKE ?)";
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
                        rs.getString("maPhieuNhap"),
                        rs.getString("maNCC"),
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
        String sql = "DELETE FROM PhieuTra WHERE maPhieuTra = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPT);
            return ps.executeUpdate() > 0; // true nếu xóa thành công

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuTra_DTO getPhieuTraByMaPT(String maPT) {
        PhieuTra_DTO pt = null;
        String sql = "SELECT * FROM PhieuTra WHERE maPhieuTra = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPT);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pt = new PhieuTra_DTO(
                            rs.getString("maPhieuTra"),
                            rs.getString("lyDo"),
                            rs.getString("maNV"),
                            rs.getString("maPhieuNhap"),
                            rs.getString("maNCC"),
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