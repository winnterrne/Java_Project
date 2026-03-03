package DTO;

public class ChiTietHoaDon_DTO {
    private String maHD;
    private String maSP;
    private int soLuongMua;
    private double thanhTien;
    private float donGia;

    public ChiTietHoaDon_DTO() {

    }
    public ChiTietHoaDon_DTO(String maHD, String maSP, int soLuong, float donGia) {
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuongMua = soLuong;
        this.donGia = donGia;
        this.thanhTien = soLuongMua*donGia;
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
        return soLuongMua;
    }

    public void setSoLuongMua(int soLuong) {
        this.soLuongMua = soLuong;
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
}
