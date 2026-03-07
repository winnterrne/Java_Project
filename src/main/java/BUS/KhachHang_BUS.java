package BUS;

import DAO.KhachHang_DAO;import DTO.KhachHang_DTO;import java.util.ArrayList;

public class KhachHang_BUS {
    KhachHang_DAO khachHang = new  KhachHang_DAO();

    public KhachHang_BUS() {

    }
    public ArrayList<KhachHang_DTO> layTatCaKH() {
        return  khachHang.layTatCaKH();
    }

    public KhachHang_DTO layKHTheoMaKH(String maKH) {
        return khachHang.layKHTheoMaKH(maKH);
    }

    public void insertKH(KhachHang_DTO kh) {
        khachHang.insertKH(kh);
    }

    public void updateKhachHang(KhachHang_DTO kh) {
        khachHang.updateKhachHang(kh);
    }

    public void  deleteKhachHang(KhachHang_DTO kh) {

    }

    public String layMaKHmoiNhat() {
        return khachHang.layMaKhachHangMoiNhat();
    }

    public KhachHang_DTO themKhachHang(String tenKh, String sdtKH, String diaChiKH) {
        // 1. Kiểm tra an toàn: Cắt khoảng trắng (trim) và xem có rỗng không
        boolean isTenRong = (tenKh == null || tenKh.trim().isEmpty());
        boolean isSdtRong = (sdtKH == null || sdtKH.trim().isEmpty());
        boolean isDiaChiRong = (diaChiKH == null || diaChiKH.trim().isEmpty());

        // 2. Nếu TẤT CẢ đều rỗng -> Đây là khách vãng lai (Khách lẻ)
        if (isTenRong && isSdtRong && isDiaChiRong) {
            return null; // Trả về null để bên giao diện tự động gán mã 'KH000'
        }

        // 3. Nếu có nhập thông tin -> Tạo khách hàng mới
        KhachHang_DTO kh = new KhachHang_DTO();
        kh.setMaKH(layMaKHmoiNhat()); // Sinh mã tự động

        // Gán dữ liệu (nếu rỗng thì lưu là "Không có" để CSDL không bị null)
        kh.setHoTenKH(isTenRong ? "Khách hàng mới" : tenKh);
        kh.setSoDT(isSdtRong ? "Không có" : sdtKH);
        kh.setDiaChi(isDiaChiRong ? "Không có" : diaChiKH);

        return kh;
    }
}