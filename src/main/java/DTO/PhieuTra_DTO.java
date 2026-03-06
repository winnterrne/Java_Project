package DTO;

import java.time.LocalDate;

public class PhieuTra_DTO {
    private String maPhieuTra;
    private String lyDo;
    private String maNV;
    private String maNCC;
    private boolean trangThai;
    private String maPhieuNhap;
    private double tongTra;
    private LocalDate ngayTra;

    public PhieuTra_DTO() {

    }
    public PhieuTra_DTO(String maPhieuTra, String lyDo, String maNV, String maNCC, String maPhieuNhap, double tongTra, LocalDate ngayTra) {
        this.maPhieuTra = maPhieuTra;
        this.lyDo = lyDo;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.maPhieuNhap = maPhieuNhap;
        this.tongTra = tongTra;
        this.ngayTra = ngayTra;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getMaNV() {
        return maNV;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setMaPhieuTra(String maPhieuTra) {
        this.maPhieuTra = maPhieuTra;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public double getTongTra() {
        return tongTra;
    }

    public void setTongTra(double tongTra) {
        this.tongTra = tongTra;
    }

    public LocalDate getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(LocalDate ngayTra) {
        this.ngayTra = ngayTra;
    }
}
