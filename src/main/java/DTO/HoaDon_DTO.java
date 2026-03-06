package DTO;

import java.time.LocalDate;

public class HoaDon_DTO {
    private String maHD;
    private LocalDate ngayLapHD;
    private String maKH;
    private String maNV;
    private double tongTien;

    public HoaDon_DTO() {

    }
    public HoaDon_DTO(String maHD, LocalDate ngayLapHD, String maKH, String maNV, double tongTien) {
        this.maHD = maHD;
        this.ngayLapHD = ngayLapHD;
        this.maKH = maKH;
        this.maNV = maNV;
        this.tongTien = tongTien;
    }

    public String getMaHD() {
        return maHD;
    }

    public LocalDate getNgayLapHD() {
        return ngayLapHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setNgayLapHD(LocalDate ngayLapHD) {
        this.ngayLapHD = ngayLapHD;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
