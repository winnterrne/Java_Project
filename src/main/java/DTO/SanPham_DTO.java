package DTO;

public class SanPham_DTO {
    private String maSP;
    private String tenSP;
    private String moTa;
    private double giaBan;
    private double donVi;
    private int soLuongTon;
    private String maDM;

    public SanPham_DTO() {

    }
    public SanPham_DTO(String maSP, String tenSP, String moTa, double giaBan, double donVi, int soLuongTon, String maDM) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.donVi = donVi;
        this.soLuongTon = soLuongTon;
        this.maDM = maDM;
    }

    public String getMaSP() {
        return maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public double getGiaBan() {
        return giaBan;
    }

    public double getDonVi() {
        return donVi;
    }

    public int getSoLuongTon() {
        return soLuongTon;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setDonVi(double donVi) {
        this.donVi = donVi;
    }

    public void setGiaBan(double giaBan) {
        this.giaBan = giaBan;
    }

    public void setSoLuongTon(int soLuongTon) {
        this.soLuongTon = soLuongTon;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

}