package DTO;

public class TaiKhoaDTO {
    private String tenDangNhap;
    private String passWord;
    private String maKH;

    public TaiKhoaDTO() {

    }
    public TaiKhoaDTO(String tenDangNhap, String passWord, String maKH) {
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
}
