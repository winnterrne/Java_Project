package DTO;

import java.time.LocalDate;

public class ChiTietHoaDon_DTO {
    private String maHD;
    private String maSP;
    private int soLuong;
    private double thanhTien;
    private float donGia;
    private String maNV;
    private LocalDate date;

    public ChiTietHoaDon_DTO() {

    }
    public ChiTietHoaDon_DTO(String maHD, String maSP, int soLuong, double thanhTien, float donGia, String maNV, LocalDate date) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.donGia = donGia;
        this.maNV = maNV;
        this.date = date;
    }
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public int getSoLuongMua() {
        return soLuong;
    }

    public void setSoLuongMua(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
