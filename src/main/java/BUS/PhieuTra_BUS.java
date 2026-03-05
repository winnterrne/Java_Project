package BUS;

import DAO.PhieuTra_DAO;
import DTO.ChiTietPhieuTra_DTO;
import DTO.PhieuNhap_DTO;
import DTO.PhieuTra_DTO;

import java.util.ArrayList;
import java.util.Date;

public class PhieuTra_BUS {
    PhieuTra_DAO ptDAO =  new PhieuTra_DAO();
    public ArrayList<PhieuTra_DTO> getAllPhieuTra(){
        return ptDAO.getAllPhieuTra();
    }

    public boolean taoPhieuTra(PhieuTra_DTO pt, ArrayList<ChiTietPhieuTra_DTO> dsCT) {
        return ptDAO.taoPhieuTra(pt, dsCT);
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
            return "PT001";
        }

        String soStr = lastMa.substring(2);
        int so = Integer.parseInt(soStr);
        so++;

        return String.format("PT%03d", so);
    }
}