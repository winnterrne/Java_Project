package DTO;

public class KhachHang_DTO {
    private String maKH;
    private String hoTenKH;
    private String soDT;
    private String diaChi;
    private String email;
    private double diemTichLuy;

    public KhachHang_DTO() {

    }
    public KhachHang_DTO(String maKH, String hoTenKH, String soDT, String diaChi, String email, double diemTichLuy) {
        this.maKH = maKH;
        this.hoTenKH = hoTenKH;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.email = email;
        this.diemTichLuy = diemTichLuy;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getHoTenNV() {
        return hoTenKH;
    }

    public String getSoDT() {
        return soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getEmail() {
        return email;
    }

    public double getDiemTichLuy() {
        return diemTichLuy;
    }
}