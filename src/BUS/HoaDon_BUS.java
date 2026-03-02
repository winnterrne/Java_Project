package BUS;

import DTO.KhachHang_DTO;

import java.time.LocalDate;
import java.util.Vector;

public class HoaDon_BUS {
    private String maHD;
    private LocalDate ngayLapHD;
    private Vector<ChiTietHoaDon_BUS> dssanPham;
    private KhachHang_DTO khachHang;
    private NhanVien_BUS nhanVien;

    public HoaDon_BUS(LocalDate ngayLapHD, Vector<ChiTietHoaDon_BUS> sanPham, KhachHang_DTO khachHang, NhanVien_BUS nhanVien) {
//        HoaDon_DAO.getMaHDTiepTheo();
        maHD = "HD000";
        this.ngayLapHD = ngayLapHD;
        this.dssanPham = sanPham;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
    }
    public HoaDon_BUS() {
    }

    public String getMaHD() {
        return maHD;
    }

    public Vector<ChiTietHoaDon_BUS> getSanPham() {
        return dssanPham;
    }

    public LocalDate getNgayLapHD() {
        return ngayLapHD;
    }

    public KhachHang_DTO  getKhachHang() {
        return khachHang;
    }

    public NhanVien_BUS getNhanVien() {
        return nhanVien;
    }
}