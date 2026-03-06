package DTO;

public class VaiTro_DTO {
    private String maVT;
    private String tenVT;
    private String hoTenNV;
    private String username;

    public VaiTro_DTO() {

    }
    public VaiTro_DTO(String maVT, String tenVT, String hoTenNV, String username) {
        this.maVT = maVT;
        this.tenVT = tenVT;
        this.hoTenNV = hoTenNV;
        this.username = username;
    }

    public String getMaVT() {
        return maVT;
    }

    public String getTenVT() {
        return tenVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }

    public void setTenVT(String tenVT) {
        this.tenVT = tenVT;
    }

    public String getHoTenNV() {
        return hoTenNV;
    }

    public void setHoTenNV(String hoTenNV) {
        this.hoTenNV = hoTenNV;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

