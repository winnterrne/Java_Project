package DTO;

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

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public LocalDate getNgaySanXuat() {
        return ngaySanXuat;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    public void setNgaySanXuat(LocalDate ngaySanXuat) {
        this.ngaySanXuat = ngaySanXuat;
    }
}
