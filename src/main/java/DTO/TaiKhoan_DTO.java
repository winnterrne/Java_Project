package DTO;

public class TaiKhoan_DTO {
    private String tenDangNhap;
    private String passWord;
    private String maKH;

    public TaiKhoan_DTO() {

    }
    public TaiKhoan_DTO(String tenDangNhap, String passWord, String maKH) {
        this.tenDangNhap = tenDangNhap;
        this.passWord = passWord;
        this.maKH = maKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setTenDangNhap(String tenDangNhap) {
        this.tenDangNhap = tenDangNhap;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }
}