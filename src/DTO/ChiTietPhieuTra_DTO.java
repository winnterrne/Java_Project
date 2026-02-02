package DTO;

import java.time.LocalDate;

public class ChiTietPhieuTra_DTO {
    private String maPhieuTra;
    private String maSP;
    private LocalDate ngayTra;
    private int soLuongTra;

    public ChiTietPhieuTra_DTO() {

    }
    public ChiTietPhieuTra_DTO(String maPhieuTra, String maSP, LocalDate ngayTra, int soLuongTra) {
        this.maPhieuTra = maPhieuTra;
        this.maSP = maSP;
        this.ngayTra = ngayTra;
        this.soLuongTra = soLuongTra;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public String getMaSP() {
        return maSP;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public int getSoLuongTra() {
        return soLuongTra;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }

    public void setSoLuongTra(int soLuongTra) {
        this.soLuongTra = soLuongTra;
    }
}
