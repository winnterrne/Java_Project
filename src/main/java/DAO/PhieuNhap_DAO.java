package DAO;


import DTO.ChiTietPhieuNhap_DTO;
import DTO.NhaCungCap_DTO;
import DTO.PhieuNhap_DTO;
import Utils.databaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class PhieuNhap_DAO {
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        ArrayList<PhieuNhap_DTO> list = new ArrayList<>();
        String sql =  "SELECT * FROM PHIEUNHAP";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {
                PhieuNhap_DTO pn = new PhieuNhap_DTO(
                        rs.getString("MaPN"),
                        rs.getDate("NgayNhapHang").toLocalDate(),
                        rs.getDouble("TongTien"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV")
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
        String sql = "SELECT ncc.TenNCC " +
                "FROM PHIEUNHAP pn " +
                "JOIN NHACUNGCAP ncc ON pn.MaNCC = ncc.MaNCC " +
                "WHERE pn.MaPN = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tenNCC = rs.getString("TenNCC");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tenNCC;
    }

    public boolean delete(String maPN) {
        String sql = "DELETE FROM PHIEUNHAP WHERE MaPhieuNhap = ?";

        try(Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPN);
            return ps.executeUpdate()>0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePhieuNhap(String maPN) {
        String sql = "DELETE FROM PHIEUNHAP WHERE MaPN = ?";

        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            return ps.executeUpdate() > 0; // true nếu xóa thành công

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public PhieuNhap_DTO getPhieuNhapbyMaPN(String maPN) {
        PhieuNhap_DTO pn = null;
        String sql = "SELECT * FROM PHIEUNHAP WHERE MaPN = ?";
        try (Connection con = databaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pn = new PhieuNhap_DTO(
                            rs.getString("MaPN"),
                            rs.getDate("NgayNhapHang").toLocalDate(),
                            rs.getDouble("TongTien"),
                            rs.getString("MaNCC"),
                            rs.getString("MaNV")
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
                    FROM PHIEUNHAP pn
                    JOIN NHACUNGCAP ncc on pn.MaNCC = ncc.MaNCC
                    WHERE pn.MaPN LIKE ? OR ncc.TenNCC LIKE ?
                    """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                PhieuNhap_DTO pn = new PhieuNhap_DTO(
                        rs.getString("MaPN"),
                        rs.getDate("NgayNhapHang").toLocalDate(),
                        rs.getDouble("TongTien"),
                        rs.getString("MaNCC"),
                        rs.getString("MaNV")
                );
                list.add(pn);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<PhieuNhap_DTO> timPhieuNhapLocTheoNgay(){
        ArrayList<PhieuNhap_DTO> list = new ArrayList<>();
        return list;
    }

    public ArrayList<PhieuNhap_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay,  Double giaTu, Double giaDen) {
            ArrayList<PhieuNhap_DTO> list = new ArrayList<>();

            try {
                Connection con = databaseConnection.getConnection();
                String sql = """
                        SELECT pn.* FROM PHIEUNHAP pn
                        JOIN NHACUNGCAP ncc on pn.MaNCC = ncc.MaNCC
                        WHERE 1 = 1
                        """;

                ArrayList<Object> danhSachThamSo = new ArrayList<>();

                // Tìm theo mã
                if (!keyword.trim().isEmpty()) {
                    sql += " AND (pn.MaPN LIKE ? or ncc.TenNCC LIKE ?)";
                    danhSachThamSo.add("%" + keyword + "%");
                    danhSachThamSo.add("%" + keyword + "%");
                }

                // Lọc ngày
                if (tuNgay != null && denNgay != null) {
                    sql += " AND NgayNhapHang BETWEEN ? AND ?";
                    danhSachThamSo.add(new Date(tuNgay.getTime()));
                    danhSachThamSo.add(new Date(denNgay.getTime()));
                }

                // Lọc giá
                if (giaTu != null && giaDen != null) {
                    sql += " AND TongTien BETWEEN ? AND ?";
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
                            rs.getString("MaPN"),
                            rs.getDate("NgayNhapHang").toLocalDate(),
                            rs.getDouble("TongTien"),
                            rs.getString("MaNCC"),
                            rs.getString("MaNV")

                    );
                    list.add(pn);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return list;
    }

    public String getMaPNLonNhat() {
        String sql = "SELECT Max(MaPN) FROM PHIEUNHAP";

        try {
            Connection con = databaseConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getString(1);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean themPhieuNhapVaChiTiet(PhieuNhap_DTO pn, ArrayList<ChiTietPhieuNhap_DTO> listCT) {
        String sqlPN = "INSERT INTO PHIEUNHAP(MaPN, NgayNhapHang, TongTien, MaNCC, MaNV) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = databaseConnection.getConnection();
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

            String sqlCT = "INSERT INTO CHITIETPHIEUNHAP(MaPN, MaSP, SoLuong, GiaNhap, NgayNhap, HanSuDung, NgaySanXuat) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
                Connection con = databaseConnection.getConnection();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean suaPhieuNhapVaChiTiet(PhieuNhap_DTO pn, ArrayList<ChiTietPhieuNhap_DTO> listCT) {
        String sqlPN = "UPDATE PHIEUNHAP SET NgayNhapHang=?, TongTien = ?,  MaNCC = ?, MaNV = ? WHERE MaPN = ?";

        try {
            Connection con = databaseConnection.getConnection();
            con.setAutoCommit(false);
            PreparedStatement psPN = con.prepareStatement(sqlPN);
            psPN.setDate(1, Date.valueOf(pn.getNgayNhapHang()));
            psPN.setDouble(2, pn.getTongTien());
            psPN.setString(3, pn.getMaNCC());
            psPN.setString(4, pn.getMaNV());
            psPN.setString(5, pn.getMaPhieuNhap());

            psPN.executeUpdate();

            String sqlDelete = "DELETE FROM CHITIETPHIEUNHAP WHERE MaPN=?";
            PreparedStatement psDel = con.prepareStatement(sqlDelete);
            psDel.setString(1, pn.getMaPhieuNhap());
            psDel.executeUpdate();

            String sqlInsert = "INSERT INTO CHITIETPHIEUNHAP(MaPN, MaSP, SoLuong, GiaNhap, NgayNhap, HanSuDung, NgaySanXuat) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psCT = con.prepareStatement(sqlInsert);
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
                Connection con = databaseConnection.getConnection();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}