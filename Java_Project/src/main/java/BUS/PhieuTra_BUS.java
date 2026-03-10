package BUS;

import DAO.ChiTietPhieuNhap_DAO;
import DAO.PhieuNhap_DAO;
import DAO.PhieuTra_DAO;
import DAO.SanPham_DAO;
import DTO.ChiTietPhieuTra_DTO;
import DTO.PhieuNhap_DTO;
import DTO.PhieuTra_DTO;
import DTO.SanPham_DTO;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;

public class PhieuTra_BUS {
    PhieuTra_DAO ptDAO =  new PhieuTra_DAO();
    SanPham_DAO spDAO =  new SanPham_DAO();
    ChiTietPhieuNhap_DAO ctpnDAO = new ChiTietPhieuNhap_DAO();
    public ArrayList<PhieuTra_DTO> getAllPhieuTra(){
        return ptDAO.getAllPhieuTra();
    }

    public boolean taoPhieuTraVaChiTiet(PhieuTra_DTO pt, ArrayList<ChiTietPhieuTra_DTO> dsCT) {
        for (ChiTietPhieuTra_DTO ctpt : dsCT) {
            SanPham_DTO sp = spDAO.getSanPhamByMaSP(ctpt.getMaSP());
            int soLuongTon = sp.getSoLuongTon();
            int soLuongNhap = ctpnDAO.getSoLuongNhap(pt.getMaPhieuNhap(), ctpt.getMaSP());
            int soLuongTra = ctpt.getSoLuongTra();

            if (soLuongTra > soLuongTon) {
                JOptionPane.showMessageDialog(null, "Số lượng trả không được lớn hơn số lượng tồn");
                return false;
            }
            if (soLuongTra > soLuongNhap) {
                JOptionPane.showMessageDialog(null, "Số lượng trả không được lớn hơn số lượng nhập");
                return false;
            }
        }

        boolean result = ptDAO.taoPhieuTraVaChiTiet(pt, dsCT);
        if (result) {
            for (ChiTietPhieuTra_DTO ctpt : dsCT) {
                SanPham_DTO sp = spDAO.getSanPhamByMaSP(ctpt.getMaSP());
                sp.setSoLuongTon(sp.getSoLuongTon() - ctpt.getSoLuongTra());
                spDAO.updateSoLuongTonSP(sp);
            }
        }
        return result;
    }

    public String getTenNCCByMaPT(String maPT){
        return ptDAO.getTenNCCByMaPT(maPT);
    }

    public PhieuTra_DTO getPhieuTraByMaPT(String maPT){
        return ptDAO.getPhieuTraByMaPT(maPT);
    }

    public boolean deletePhieuTra(String maPT){
        return ptDAO.deletePhieuTra(maPT);
    }

    public ArrayList<PhieuTra_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, String giaTuStr, String giaDenStr) {
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

        return ptDAO.timKiemNangCao(keyword, tuNgaySql, denNgaySql, giaTu, giaDen);
    }

    public String taoMaPhieuTraTuDong() {

        String lastMa = ptDAO.getMaPTLonNhat();

        if (lastMa == null) {
            return "PT01";
        }

        String soStr = lastMa.substring(2);
        int so = Integer.parseInt(soStr);
        so++;

        return String.format("PT%02d", so);
    }
}