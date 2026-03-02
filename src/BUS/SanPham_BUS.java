package BUS;

import DTO.SanPham_DTO;

import java.util.ArrayList;

public class SanPham_BUS {
    private final DAO.SanPham_DAO spDAO = new DAO.SanPham_DAO();

    public ArrayList<SanPham_DTO> getAllSanPham() {
        return spDAO.getAllSanPham();
    }

    public ArrayList<SanPham_DTO> getAllSanPhamByMaDM(String maDM) {
        if (maDM == null || maDM.isEmpty()) {
            return new ArrayList<>();
        }
        return spDAO.getAllSanPhamByMaDM(maDM);
    }

    public boolean themSanPham(SanPham_DTO sp) {
        if (sp == null || sp.getMaSP() == null || sp.getMaSP().trim().isEmpty()) {
            return false;
        }
        if (sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()) {
            return false;
        }
        if (sp.getGiaBan() <= 0) {
            return false;
        }
        if (spDAO.isMaSPExists(sp.getMaSP())) {
            return false;
        }
        return spDAO.insertSanPham(sp);
    }

    public boolean updateSanPham(SanPham_DTO sp) {
        if (sp == null || sp.getMaSP() == null || sp.getMaSP().trim().isEmpty()) {
            return false;
        }
        return spDAO.updateSanPham(sp);
    }

    public boolean deleteSanPham(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return false;
        }
        return spDAO.deleteSanPham(maSP);
    }

    public SanPham_DTO getSanPhamByMaSP(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return null;
        }
        return spDAO.getSanPhamByMaSP(maSP);
    }

    public ArrayList<SanPham_DTO> timSanPhamTheoTen(String ten) {
        return spDAO.timSanPhamTheoTen(ten);
    }
}
