package BUS;

import DAO.PhieuNhap_DAO;
import DAO.SanPham_DAO;
import DTO.ChiTietPhieuNhap_DTO;
import DTO.PhieuNhap_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;


public class PhieuNhap_BUS {
    PhieuNhap_DAO pnDAO = new PhieuNhap_DAO();
    SanPham_DAO spDAO = new SanPham_DAO();
    public ArrayList<PhieuNhap_DTO> getAllPhieuNhap() {
        return pnDAO.getAllPhieuNhap();
    }

    public boolean deletePhieuNhap(String maPN) {
        return pnDAO.deletePhieuNhap(maPN);

    }

    public ArrayList<PhieuNhap_DTO> timPhieuNhapTheoTenNCCHoacMaPN(String keyword) {
        return pnDAO.timPhieuNhapTheoTenNCCHoacMaPN(keyword);
    }

    public ArrayList<PhieuNhap_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, String giaTuStr, String giaDenStr) {
        Double giaTu = null, giaDen = null;
        try {
            if (giaTuStr != null && !giaTuStr.isEmpty()) {
                giaTu = Double.parseDouble(giaTuStr);
            }
            if (giaDenStr != null && !giaDenStr.isEmpty()) {
                giaDen = Double.parseDouble(giaDenStr);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ");
            return new ArrayList<>();
        }

        if (tuNgay != null && denNgay != null && tuNgay.after(denNgay)) {
            JOptionPane.showMessageDialog(null,"Ngày bắt đầu không được sau ngày kết thúc!");
            return new ArrayList<>();
        }

        java.sql.Date tuNgaySql = null;
        java.sql.Date denNgaySql = null;
        if(tuNgay != null) {
            tuNgaySql = new java.sql.Date(tuNgay.getTime());
        }
        if(denNgay != null) {
            denNgaySql = new java.sql.Date(denNgay.getTime());
        }

        if ((keyword == null || keyword.isEmpty()) &&
                tuNgay == null && denNgay == null &&
                (giaTuStr == null || giaTuStr.isEmpty()) &&
                (giaDenStr == null || giaDenStr.isEmpty())) {
            JOptionPane.showMessageDialog(null, "Phải nhập ít nhất một điều kiện tìm kiếm");
        }

        return pnDAO.timKiemNangCao(keyword, tuNgaySql, denNgaySql, giaTu, giaDen);
    }

    public String taoMaPN() {
        String maPN = pnDAO.getMaPNLonNhat();
        if(maPN == null) {
            return "PN01";
        }
        String so = maPN.substring(2);
        int soMoi = Integer.parseInt(so) + 1;
        return String.format("PN%02d", soMoi);
    }


    public boolean taoPhieuNhapVaChiTiet(PhieuNhap_DTO pn, ArrayList<ChiTietPhieuNhap_DTO> ct) {
        boolean result = pnDAO.themPhieuNhapVaChiTiet(pn, ct);
        if(result) {
            for(ChiTietPhieuNhap_DTO c : ct) {
                SanPham_DTO sp = spDAO.getSanPhamByMaSP(c.getMaSP());
                int soLuongTonMoi = sp.getSoLuongTon() + c.getSoLuong();
                sp.setSoLuongTon(soLuongTonMoi);
                spDAO.updateSoLuongTonSP(sp);
            }
        }
        return result;
    }

    public String getTenNCCByMaPN(String maPN) {
        return pnDAO.getTenNCCByMaPN(maPN);
    }

    public int getSoLuongNhap(String maPN, String maSP) {
        return pnDAO.getSoLuongNhap(maPN, maSP);
    }

}