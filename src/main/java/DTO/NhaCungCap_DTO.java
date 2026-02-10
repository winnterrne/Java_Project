package DTO;

public class NhaCungCap_DTO {
    private String maNCC;
    private String tenNCC;
    private String soDT;
    private String diaChi;
    private String email;

    public NhaCungCap_DTO() {

    }
    public NhaCungCap_DTO(String maNCC, String tenNCC, String soDT, String diaChi, String email) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.email = email;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getTenNCC() {
        return tenNCC;
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
}