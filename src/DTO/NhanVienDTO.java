package DTO;

import java.time.LocalDate;

public class NhanVienDTO {
    private String maNV;
    private String hoTenNV;
    private String chucVu;
    private double luong;
    private LocalDate ngayvaolam;
    private String soDT;
    private String diaChi;
    private String email;

    public NhanVienDTO() {

    }
    public NhanVienDTO(String maNV, String hoTenNV, String chucVu, double luong, LocalDate ngayvaolam, String soDT, String diaChi, String email) {
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
}
