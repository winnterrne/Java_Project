package DTO;

public class DanhMuc_DTO {
    private String maDM;
    private String tenDM;
    private int soLuongSP;
    private byte trangThai;

    public DanhMuc_DTO() {

    }
    public DanhMuc_DTO(String maDM, String tenDM, byte trangThai) {
        this.maDM = maDM;
        this.tenDM = tenDM;
        this.trangThai = trangThai;
    }

    public String getMaDM() {
        return maDM;
    }

    public void setMaDM(String maDM) {
        this.maDM = maDM;
    }

    public String getTenDM() {
        return tenDM;
    }

    public void setTenDM(String tenDM) {
        this.tenDM = tenDM;
    }

    public int getSoLuongSP() {
        return soLuongSP;
    }

    public void setSoLuongSP(int soLuongSP) {
        this.soLuongSP = soLuongSP;
    }

    public byte getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(byte trangThai) {
        this.trangThai = trangThai;
    }
}