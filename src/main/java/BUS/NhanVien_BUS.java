package BUS;

import DAO.NhanVien_DAO;
import DTO.NhanVien_DTO;

import java.util.ArrayList;

public class NhanVien_BUS {
    public NhanVien_DAO nvdao;
    public NhanVien_BUS() {
        nvdao = new NhanVien_DAO();
    }
    public ArrayList<NhanVien_DTO > getALL() {
        return nvdao.getALL();
    }
    public boolean isNhanVienExist(String manhanvien) {
        return nvdao.isNhanVienExist(manhanvien);
    }
    public NhanVien_DTO getNhanVienByMa(String manhanvien) {
        return nvdao.getNhanVienByMa(manhanvien);
    }
}
