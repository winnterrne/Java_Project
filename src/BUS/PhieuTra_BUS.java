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
            int soLuongNhap = ctpnDAO.getSoLuongNhap(pt.getMaPhieuNhap(), ctpt.getMaSP()); // chú ý: phải dùng maSP, không phải maPhieuTra
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

    public ArrayList<PhieuTra_DTO> timKiemNangCao(String keyword, Date tuNgay, Date denNgay, Double giaTu, Double giaDen) {
        return ptDAO.timKiemNangCao(keyword, (java.sql.Date) tuNgay, (java.sql.Date) denNgay, giaTu, giaDen);
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