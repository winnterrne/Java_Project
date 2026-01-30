package DTO;

import javax.swing.*;

public class ChiTietHoaDonDTO {
    private String maHD;
    private String maSP;
    private int soLuong;
    private double thanhTien;

    public ChiTietHoaDonDTO() {

    }
    public ChiTietHoaDonDTO(String maHD, String maSP, int soLuong, double thanhTien) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }
    public String getMaHD() {
        return maHD;
    }

    public String getMaSP() {
        return maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getThanhTien() {
        return thanhTien;
    }
}
