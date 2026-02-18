package DTO;

public class PhanQuyen_DTO {
    private String maQuyen;
    private String maVT;

    public PhanQuyen_DTO() {

    }
    public PhanQuyen_DTO(String maQuyen, String maVT) {
        this.maQuyen = maQuyen;
        this.maVT = maVT;
    }

    public String getMaQuyen() {
        return maQuyen;
    }

    public void setMaQuyen(String maQuyen) {
        this.maQuyen = maQuyen;
    }

    public String getMaVT() {
        return maVT;
    }

    public void setMaVT(String maVT) {
        this.maVT = maVT;
    }
}
