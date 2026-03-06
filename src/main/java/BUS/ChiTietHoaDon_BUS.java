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


}