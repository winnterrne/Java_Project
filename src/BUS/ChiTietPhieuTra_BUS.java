package BUS;

import DAO.ChiTietPhieuTra_DAO;
import DTO.ChiTietPhieuTra_DTO;

import java.util.ArrayList;

public class ChiTietPhieuTra_BUS {
    ChiTietPhieuTra_DAO ctptDAO =  new ChiTietPhieuTra_DAO();

    public ArrayList<ChiTietPhieuTra_DTO> getAllChiTietPhieuTra() {
        return ctptDAO.getAllChiTietPhieuTra();
    }

    public ArrayList<ChiTietPhieuTra_DTO> getChiTietPhieuTraByMaPT(String maPT) {
        return ctptDAO.getChiTietPhieuTraByMaPT(maPT);
    }

    public String getTenSPByMaSP(String maSP) {
        return ctptDAO.getTenSPByMaSP(maSP);
    }
}