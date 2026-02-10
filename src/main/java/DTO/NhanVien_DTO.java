package DTO;

import java.time.LocalDate;

public class NhanVien_DTO {
    private String maNV;
    private String hoTenNV;
    private String chucVu;
    private double luong;
    private LocalDate ngayvaolam;
    private String soDT;
    private String diaChi;
    private String email;

    public NhanVien_DTO() {

    }
    public NhanVien_DTO(String maNV, String hoTenNV, String chucVu, double luong, LocalDate ngayvaolam, String soDT, String diaChi, String email) {
        this.maNV = maNV;
        this.hoTenNV = hoTenNV;
        this.chucVu = chucVu;
        this.luong = luong;
        this.ngayvaolam = ngayvaolam;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public double getLuong() {
        return luong;
    }

    public LocalDate getNgayvaolam() {
        return ngayvaolam;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getEmail() {
        return email;
    }

    public String getSoDT() {
        return soDT;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    public void setLuong(double luong) {
        this.luong = luong;
    }

    public void setNgayvaolam(LocalDate ngayvaolam) {
        this.ngayvaolam = ngayvaolam;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}