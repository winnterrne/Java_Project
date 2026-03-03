package BUS;

import java.util.ArrayList;

import DAO.DanhMuc_DAO;
import DAO.SanPham_DAO;
import DTO.DanhMuc_DTO;
import DTO.SanPham_DTO;

public class DanhMuc_BUS {
    private final DanhMuc_DAO dmDAO = new DanhMuc_DAO();


    public ArrayList<DanhMuc_DTO> getAllDanhMuc() {
        return dmDAO.getAllDanhMuc();
    }

    public DanhMuc_DTO getDanhMucByMaDM(String maDM) {
        if (maDM == null || maDM.isEmpty()) {
            return null;
        }
        return dmDAO.getDanhMucByMaDM(maDM);
    }

    public boolean insertDanhMuc(DanhMuc_DTO dm) {
        if (dm == null || dm.getMaDM() == null || dm.getMaDM().isEmpty()) {
            return false;
        }
        return dmDAO.insertDanhMuc(dm);
    }

    public boolean updateDanhMuc(DanhMuc_DTO dm) {
        if (dm == null || dm.getMaDM() == null || dm.getMaDM().isEmpty()) {
            return false;
        }
        return dmDAO.updateDanhMuc(dm);
    }

    public boolean deleteDanhMuc(String maDM) {
        if (maDM == null || maDM.isEmpty()) {
            return false;
        }
        return dmDAO.deleteDanhMuc(maDM);
    }

    public ArrayList<SanPham_DTO> getSanPhamByMaDM(String maDM) {
        SanPham_DAO spDAO = new SanPham_DAO();
        return spDAO.getAllSanPhamByMaDM(maDM);
    }
}