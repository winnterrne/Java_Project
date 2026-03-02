package DTO;

public class SanPham_DTO {
    private String maSP;
    private String tenSP;
    private String moTa;
    private double giaBan;
    private String donVi;
    private int soLuongTon;
    private String maKM;
    private String viTri;
    private String maDM;
    private String Path;
    public SanPham_DTO() {

    }
    public SanPham_DTO(String maSP, String tenSP, String moTa, double giaBan, String donVi, int soLuongTon,String Path ,String maDM) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.giaBan = giaBan;
        this.donVi = donVi;
        this.soLuongTon = soLuongTon;
        this.Path = Path;
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

    public double getGiaBan() {
        return this.giaBan;
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

    public String getMaKhuyenMai() {
        return maKM;
    }

    public void setMaKhuyenMai(String maKM) {
        this.maKM = maKM;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public String getMaKM() {
        return maKM;
    }

    public void setMaKM(String maKM) {
        this.maKM = maKM;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }
}