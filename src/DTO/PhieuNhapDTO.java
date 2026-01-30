package DTO;

import javax.crypto.Cipher;
import java.time.LocalDate;

public class PhieuNhapDTO {
    private String maPhieuNhap;
    private LocalDate ngayNhapHang;
    private double tongTien;
    private String maNCC;
    private String maNV;

    public PhieuNhapDTO() {

    }
    public PhieuNhapDTO(String maPhieuNhap, LocalDate ngayNhapHang, double tongTien, String maNCC, String maNV) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhapHang = ngayNhapHang;
        this.tongTien = tongTien;
        this.maNCC = maNCC;
        this. maNV = maNV;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public LocalDate getNgayNhapHang() {
        return ngayNhapHang;
    }

    public double getTongTien() {
        return tongTien;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getMaNV() {
        return maNV;
    }
}
