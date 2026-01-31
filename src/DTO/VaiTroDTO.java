package DTO;

public class VaiTroDTO {
    private String maVT;
    private String tenVT;

    public VaiTroDTO() {

    }
    public VaiTroDTO(String maVT, String tenVT) {
        this.maVT = maVT;
        this.tenVT = tenVT;
    }

    public String getMaVT() {
        return maVT;
    }

    public String getTenVT() {
        return tenVT;
    }
}

