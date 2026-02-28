package BUS;

import DAO.PhieuNhap_DAO;
import DTO.ChiTietPhieuNhap_DTO;
import DTO.PhieuNhap_DTO;

import java.util.ArrayList;
import java.util.Date;


public class PhieuNhap_BUS {
    PhieuNhap_DAO pnDAO = new PhieuNhap_DAO();
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        return pnDAO.getAllPhieuNhap();
    }

    public boolean deletePhieuNhap(String maPN) {
        return pnDAO.deletePhieuNhap(maPN);
        // Co the them logic cap nhat ton kho...
    }

    public ArrayList<PhieuNhap_DTO> timPhieuNhapTheoTenNCCHoacMaPN(String keyword) {
        return pnDAO.timPhieuNhapTheoTenNCCHoacMaPN(keyword);
    }

    public ArrayList<PhieuNhap_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, Double giaTu, Double giaDen) {
        return pnDAO.timKiemNangCao(keyword, (java.sql.Date) tuNgay, (java.sql.Date) denNgay, giaTu, giaDen);
    }

    public String taoMaPN() {
        String maPN = pnDAO.getMaPNLonNhat();
        if(maPN == null) {
            return "PN001";
        }
        String so = maPN.substring(2);
        int soMoi = Integer.parseInt(so) + 1;
        return String.format("PN%03d", soMoi);
    }

    public boolean themPhieuNhapVaChiTiet(PhieuNhap_DTO pn, ArrayList<ChiTietPhieuNhap_DTO> ct) {
        return pnDAO.themPhieuNhapVaChiTiet(pn, ct);
    }

    public boolean suaPhieuNhap(PhieuNhap_DTO pn,  ArrayList<ChiTietPhieuNhap_DTO> ct) {
        return pnDAO.suaPhieuNhapVaChiTiet(pn, ct);
    }

}
