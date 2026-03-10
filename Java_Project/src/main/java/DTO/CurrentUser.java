package DTO;
import BUS.TaiKhoan_BUS;
import DTO.VaiTro_DTO;

import java.util.Locale;

public class CurrentUser {
    private static final CurrentUser instance = new CurrentUser();
    private TaiKhoan_DTO tkhientai;
    private TaiKhoan_BUS tkbus;
    public CurrentUser() {

    }
    public static CurrentUser getInstance() {
        return instance;
    }
    public void login(TaiKhoan_DTO tk) {
        this.tkhientai = tk;
    }
    public void logout() {
        this.tkhientai = null;
    }
    public TaiKhoan_DTO getTaiKhoan() {
        return tkhientai;
    }
    public String getMaQuyen() {
        if(tkhientai == null) {
            return " ";
        }
        String ma = tkhientai.getMaVaiTro().trim().toUpperCase();
        return ma;
    }
    public String getTenNV() {
        if(tkhientai == null) {
            return " ";
        }
        String tennv = tkhientai.getHoTen().trim().toUpperCase();
        return tennv;
    }
    public String getmaNV() {
        if(tkhientai == null) {
            return " ";
        }
        String manv = tkhientai.getMaNV();
        return manv;
    }

    public boolean isAdmin() {
        String ma = getMaQuyen();
        return "ADMIN".equals(ma);
    }
    public boolean isNhanVienBanHang() {
        String ma = getMaQuyen();
        return "NHANVIENBANHANG".equals(ma);
    }
    public boolean isKho() {
        String ma = getMaQuyen();
        return "KHO".equals(ma);
    }
    public boolean isKhachHang() {
        String ma = getMaQuyen();
        return "KHACHHANG".equals(ma);
    }
}

