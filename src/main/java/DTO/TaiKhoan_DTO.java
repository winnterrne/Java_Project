package DTO;

public class TaiKhoan_DTO {
    private String tenDangNhap;
    private String passWord;
    private String maTK;
    private String email;
    private String maVaiTro;
    private boolean trangThai;
    private String maNV;
    private String maKH;
    private String otp;
    private String hoTen;


    public TaiKhoan_DTO() {

    }

    public TaiKhoan_DTO(String tenDangNhap, String passWord, String maTK, String email, String maVaiTro, boolean trangThai, String maNV, String maKH, String hoTen) {
        this.tenDangNhap = tenDangNhap;
        this.passWord = passWord;
        this.maTK = maTK;
        this.email = email;
        this.maVaiTro = maVaiTro;
        this.trangThai = trangThai;
        this.maNV = maNV;
        this.maKH = maKH;
        this.hoTen = hoTen;
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

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        this.maTK = maTK;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMaVaiTro() {
        return maVaiTro;
    }

    public void setMaVaiTro(String maVaiTro) {
        this.maVaiTro = maVaiTro;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }
}