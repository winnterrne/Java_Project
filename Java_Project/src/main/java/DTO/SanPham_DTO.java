package DTO;

import java.sql.Date;

public class SanPham_DTO {
    private String maSP;
    private String tenSP;
    private String moTa;
    private double giaBan;
    private String donVi;
    private int soLuongTon;
    private String maDM;
    private int khuyenMai;
    private String viTri;
    private String Path;
    private byte trangThai;
    private Date ngaySX;
    private Date hanSD;

    public SanPham_DTO() {

    }
    public SanPham_DTO(String maSP, String tenSP, String moTa, double giaBan, String donVi, int soLuongTon, String maDM, int khuyenMai, byte trangThai, Date ngaySX, Date hanSD) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.donVi = donVi;
        this.trangThai = trangThai;
        this.soLuongTon = soLuongTon;
        this.maDM = maDM;
        this.khuyenMai = khuyenMai;
        this.ngaySX = ngaySX;
        this.hanSD = hanSD;
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

    public String getDonVi() {
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

    public void setDonVi(String donVi) {
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

    public int getKhuyenMai() {
        return khuyenMai;
    }

    public void setKhuyenMai(int khuyenMai) {
        this.khuyenMai = khuyenMai;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public byte getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(byte trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgaySX (){
        return ngaySX;
    }
    
    public void setNgaySX (Date ngaySX){
        this.ngaySX = ngaySX;
    }

    public Date getHanSD (){
        return hanSD;
    }

    public void setHanSD (Date hanSD){
        this.hanSD = hanSD;
    }
}