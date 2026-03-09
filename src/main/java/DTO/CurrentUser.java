package DTO;
import DTO.VaiTro_DTO;

import java.util.Locale;

public class CurrentUser {
    private static final CurrentUser instance = new CurrentUser();
    private TaiKhoan_DTO tkhientai;
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
    public boolean isAdmin() {
        String ma = getMaQuyen();
        return "ADMIN".equals(ma);
    }

    public String getMaNV() {
        if(tkhientai == null) {
            return "";
        }
        return tkhientai.getMaNV();
    }

    public String getHoTenNV() {
        if(tkhientai == null) {
            return "";
        }
        return tkhientai.getHoTen();
    }
    public boolean isNhanVienBanHang() {
        String ma = getMaQuyen();
        return "NHANVIENBANHANG".equals(ma);
    }
    public boolean isKho() {
        String ma = getMaQuyen();
        return "KHO".equals(ma);
    }
}

