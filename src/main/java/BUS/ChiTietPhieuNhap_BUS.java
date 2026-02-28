package BUS;

import DAO.ChiTietPhieuNhap_DAO;
import DTO.ChiTietPhieuNhap_DTO;

import java.util.ArrayList;

public class ChiTietPhieuNhap_BUS {
    ChiTietPhieuNhap_DAO ctpnDAO = new ChiTietPhieuNhap_DAO();
    public ArrayList<ChiTietPhieuNhap_DTO> getAllChiTietPhieuNhap() {
        return ctpnDAO.getAllChiTietPhieuNhap();
    }
    public ArrayList<ChiTietPhieuNhap_DTO> getChiTietPhieuNhapByMaPN(String maPN) {
        return ctpnDAO.getChiTietPhieuNhapByMaPN(maPN);
    }
}
