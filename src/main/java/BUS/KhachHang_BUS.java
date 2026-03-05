package BUS;

import DAO.KhachHang_DAO;import DTO.KhachHang_DTO;import java.util.ArrayList;

public class KhachHang_BUS {
    KhachHang_DAO khachHang = new  KhachHang_DAO();

    public KhachHang_BUS() {

    }
    public ArrayList<KhachHang_DTO> layTatCaKH() {
        return  khachHang.layTatCaKH();
    }

    public KhachHang_DTO layKHTheoMaKH(String maKH) {
        return khachHang.layKHTheoMaKH(maKH);
    }

    public void insertKH(KhachHang_DTO kh) {
        khachHang.insertKH(kh);
    }

    public void updateKhachHang(KhachHang_DTO kh) {
        khachHang.updateKhachHang(kh);
    }

    public void  deleteKhachHang(KhachHang_DTO kh) {

    }

    public String layMaKHmoiNhat() {
        return "SP001";
    }
}