package DTO;

import java.time.LocalDate;

public class PhieuNhap_DTO {
    private String maPhieuNhap;
    private LocalDate ngayNhapHang;
    private double tongTien;
    private String maNCC;
    private String maNV;

    public PhieuNhap_DTO() {

    }
    public PhieuNhap_DTO(String maPhieuNhap, LocalDate ngayNhapHang, double tongTien, String maNCC, String maNV) {
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

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setNgayNhapHang(LocalDate ngayNhapHang) {
        this.ngayNhapHang = ngayNhapHang;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}