package BUS;

import java.util.List;
import DTO.ThongKeDoanhThu_DTO;

import DAO.ThongKeDoanhThu_DAO;

public class ThongKe_BUS {
    private ThongKeDoanhThu_DAO dao = new ThongKeDoanhThu_DAO();

    public List<ThongKeDoanhThu_DTO> getThongKeDoanhThu(int thang, int nam) {
        return dao.getThongKeDoanhThu(thang, nam);
    }

    public double tongDoanhThuTheoThang (int thang, int nam){
        return dao.tongDoanhThuTheoThang(thang, nam);
    }
}

