package DTO;

public class PhieuTraDTO {
    private String maPhieuTra;
    private String lyDo;
    private String maNV;
    private String maPhieuNhap;
    private boolean trangThai;

    public PhieuTraDTO() {

    }
    public PhieuTraDTO(String maPhieuTra, String lyDo, String maNV, String maPhieuNhap, boolean trangThai) {
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
}
