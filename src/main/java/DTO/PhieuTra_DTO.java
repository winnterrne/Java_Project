package DTO;

public class PhieuTra_DTO {
    private String maPhieuTra;
    private String lyDo;
    private String maNV;
    private String maPhieuNhap;
    private boolean trangThai;

    public PhieuTra_DTO() {

    }
    public PhieuTra_DTO(String maPhieuTra, String lyDo, String maNV, String maPhieuNhap, boolean trangThai) {
        this.maPhieuTra = maPhieuTra;
        this.lyDo = lyDo;
        this.maNV = maNV;
        this.maPhieuNhap = maPhieuNhap;
        this.trangThai = trangThai;
    }

    public String getMaPhieuTra() {
        return maPhieuTra;
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
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

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }
}