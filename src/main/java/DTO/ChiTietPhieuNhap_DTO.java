package DTO;

import java.sql.Date;
import java.time.LocalDate;

public class ChiTietPhieuNhap_DTO {
    private String maPhieuNhap;
    private String maSP;
    private int soLuong;
    private double giaNhap;
    private LocalDate ngayNhap;
    private LocalDate hanSuDung;
    private LocalDate ngaySanXuat;

    public ChiTietPhieuNhap_DTO() {

    }
    public ChiTietPhieuNhap_DTO(String maPhieuNhap, String maSP, int soLuong, double giaNhap, LocalDate ngayNhap, LocalDate hanSuDung, LocalDate ngaySanXuat) {
        this.maPhieuNhap = maPhieuNhap;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.ngayNhap = ngayNhap;
        this.hanSuDung = hanSuDung;
        this.ngaySanXuat = ngaySanXuat;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public String getMaSP() {
        return maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public Date getNgayNhapToDate() {
        Date ngayNhapDate = Date.valueOf(ngayNhap);
        return  ngayNhapDate;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public Date getHanSuDungToDate() {
        Date HSD =  Date.valueOf(ngayNhap);
        return HSD;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }

    public Date getNgaySanXuatToDate() {
        Date ngaySanXuatDate = Date.valueOf(ngaySanXuat);
        return ngaySanXuatDate;
    }
}