package BUS;

import DTO.SanPham_DTO;

import javax.swing.*;
import java.util.ArrayList;

public class SanPham_BUS {
    private final DAO.SanPham_DAO spDAO = new DAO.SanPham_DAO();


    public ArrayList<SanPham_DTO> getAllSanPhamAvavilable(){
        return spDAO.getAllSanPhamAvailable();
    }

    public ArrayList<SanPham_DTO> getAllSanPham() {
        return spDAO.getAllSanPham();
    }


    public ArrayList<SanPham_DTO> getAllSanPhamDaXoa() {
        return spDAO.getAllSanPhamDaXoa();
    }

    public ArrayList<SanPham_DTO> getAllSanPhamByMaDM(String maDM) {
        if (maDM == null || maDM.isEmpty()) {
            return new ArrayList<>();
        }
        return spDAO.getAllSanPhamByMaDM(maDM);
    }

    public ArrayList<SanPham_DTO> getAllTenSP() {
        return spDAO.getAllTenSP();
    }

    public ArrayList<SanPham_DTO> layDsSanPhamConTon() {
        ArrayList<SanPham_DTO> list = new ArrayList<>();
        for (SanPham_DTO sp : getAllSanPhamAvavilable()) {
            if (sp.getSoLuongTon() > 0) {
                list.add(sp);
            }
        }
        return list;
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

    public boolean nhapHang(String maSP, int soLuongNhap){

        if(soLuongNhap <= 0){
            JOptionPane.showMessageDialog(null,"Số lượng phải > 0");
            return false;
        }

        SanPham_DTO sp = spDAO.getSanPhamByMaSP(maSP);

        int tonMoi = sp.getSoLuongTon() + soLuongNhap;

        sp.setSoLuongTon(tonMoi);

        spDAO.updateSoLuongTonSP(sp);

        return true;
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

    public String taoMaSPTuDong (){
        String maxMaSP = spDAO.getMaxMaSP();
        int soThuTu = 1;
        if (maxMaSP != null && maxMaSP.startsWith("SP")) {
            String soThuTuStr = maxMaSP.substring(2);
            try {
                soThuTu = Integer.parseInt(soThuTuStr) + 1;
            } catch (NumberFormatException e) {
                soThuTu = 1;
            }
        }
        String dinhDangSo = String.format ("%02d", soThuTu);
        String maSPMoi = "SP" + dinhDangSo;

        
        while (spDAO.isMaSPExists(maSPMoi)) {
            soThuTu++;
            dinhDangSo = String.format("%02d", soThuTu);
            maSPMoi = "SP" + dinhDangSo;
        }

        if (soThuTu > 999) {
            throw new IllegalStateException("Đã hết mã SP cho danh mục này (vượt 999)!");
        }

        return maSPMoi;
    }

    public boolean restoreSanPham(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return false;
        }
        return spDAO.restoreSanPham(maSP);
    }

    public int getSoLuongTon(String maSP) {
        if (maSP == null || maSP.trim().isEmpty()) {
            return 0;
        }
        return spDAO.getSoLuongTon(maSP);
    }

    public ArrayList<SanPham_DTO> timKiemChung(String tuKhoa) {
        ArrayList<SanPham_DTO> ketQua = new ArrayList<>();
        SanPham_DTO spTheoMa = getSanPhamByMaSP(tuKhoa);

        if (spTheoMa != null) {
            ketQua.add(spTheoMa); 
        } else {
            ArrayList<SanPham_DTO> dsTheoTen = timSanPhamTheoTen(tuKhoa); 
            if (dsTheoTen != null && !dsTheoTen.isEmpty()) {
                ketQua.addAll(dsTheoTen);
            }
        }
        return ketQua;
    }

    public boolean luuSanPham(SanPham_DTO sp){
        if(sp == null) return false;

        if(sp.getTenSP() == null || sp.getTenSP().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"Tên sản phẩm không được rỗng");
            return false;
        }

        if(sp.getGiaBan() <= 0){
            JOptionPane.showMessageDialog(null,"Giá bán phải > 0");
            return false;
        }

        if(spDAO.isMaSPExists(sp.getMaSP())){
            JOptionPane.showMessageDialog(null,"Mã sản phẩm đã tồn tại");
            return false;
        }

        return spDAO.luuSanPham(sp);
    }
}