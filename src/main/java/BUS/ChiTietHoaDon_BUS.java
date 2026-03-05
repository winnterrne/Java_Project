package BUS;
import DAO.ChiTietHoaDon_DAO;
import DTO.ChiTietHoaDon_DTO;
import DTO.SanPham_DTO;

import java.util.ArrayList;

public class ChiTietHoaDon_BUS{
    private ChiTietHoaDon_DAO chiTietHoaDon = new ChiTietHoaDon_DAO();

    public ChiTietHoaDon_BUS() {

    }

    public ArrayList<ChiTietHoaDon_DTO> getChiTietHoaDon(String maHD) {
        return chiTietHoaDon.getChiTietHoaDon(maHD);
    }

    public void capNhatSoLuongTon(ArrayList<ChiTietHoaDon_DTO> dsSanPham) {
        SanPham_BUS spBus =  new SanPham_BUS();
        for (ChiTietHoaDon_DTO d : dsSanPham) {
            SanPham_DTO sp = spBus.getSanPhamByMaSP(d.getMaSP());
            int i = sp.getSoLuongTon();
            sp.setSoLuongTon(i - d.getSoLuongMua());
        }
    }
}