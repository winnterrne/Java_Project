package BUS;
import DTO.SanPham_DTO;

public class ChiTietHoaDon_BUS{
    private SanPham_DTO sanPham;
    private int soLuongMua;
    private float donGia;
    private double thanhTien;

    public ChiTietHoaDon_BUS() {

    }
    public ChiTietHoaDon_BUS(SanPham_DTO SP, int soLuongMua,float donGia, double thanhTien) {
        this.sanPham = SP;
        this.soLuongMua = soLuongMua;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public SanPham_DTO getSanPham() {
        return sanPham;
    }

    public int getSoLuongMua() {
        return soLuongMua;
    }

    public float getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public  void setSanPham(SanPham_DTO sanPham) {
        this.sanPham = sanPham;
    }
    public void setSoLuongMua(int soLuongMua) {
        this.soLuongMua = soLuongMua;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }
}