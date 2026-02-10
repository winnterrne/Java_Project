package DTO;

public class VaiTro_DTO {
    private String maVT;
    private String tenVT;

    public VaiTro_DTO() {

    }
    public VaiTro_DTO(String maVT, String tenVT) {
        this.maVT = maVT;
        this.tenVT = tenVT;
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
}