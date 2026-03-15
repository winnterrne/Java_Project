package BUS;

import DAO.KhachHang_DAO;
import DAO.TaiKhoan_DAO;
import DTO.KhachHang_DTO;import java.util.ArrayList;

public class KhachHang_BUS {
    KhachHang_DAO khachHang = new  KhachHang_DAO();
    TaiKhoan_DAO taikhoan = new TaiKhoan_DAO();

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
        
        boolean isTenRong = (tenKh == null || tenKh.trim().isEmpty());
        boolean isSdtRong = (sdtKH == null || sdtKH.trim().isEmpty());
        boolean isDiaChiRong = (diaChiKH == null || diaChiKH.trim().isEmpty());

        
        if (isTenRong && isSdtRong && isDiaChiRong) {
            return null; 
        }

        
        KhachHang_DTO kh = new KhachHang_DTO();
        kh.setMaKH(layMaKHmoiNhat()); 

        
        kh.setHoTenKH(isTenRong ? "Khách hàng mới" : tenKh);
        kh.setSoDT(isSdtRong ? "Không có" : sdtKH);
        kh.setDiaChi(isDiaChiRong ? "Không có" : diaChiKH);

        return kh;

    }
    public boolean isMaKH(String maKH) {
        return khachHang.isMaKhachHang(maKH);
    }

    public ArrayList<KhachHang_DTO> boLocTimKiemKH(String maKH, String tenKH, String sdt) {
        ArrayList<KhachHang_DTO> danhSachGoc = layTatCaKH();
        ArrayList<KhachHang_DTO> ketQua = new ArrayList<>();

        // Nếu danh sách rỗng hoặc bị lỗi thì trả về danh sách rỗng luôn
        if (danhSachGoc == null || danhSachGoc.isEmpty()) {
            return ketQua;
        }

        for (KhachHang_DTO kh : danhSachGoc) {
            boolean thongManMaKH = true;
            boolean thongManTenKH = true;
            boolean thongManSDT = true;

            // Kiểm tra khớp Mã KH (Tìm kiếm tương đối - chứa chuỗi)
            if (maKH != null && !maKH.trim().isEmpty()) {
                thongManMaKH = kh.getMaKH().toLowerCase().contains(maKH.toLowerCase().trim());
            }

            // Kiểm tra khớp Tên KH (Không phân biệt hoa thường)
            if (tenKH != null && !tenKH.trim().isEmpty()) {
                if (kh.getHoTenKH() != null) {
                    thongManTenKH = kh.getHoTenKH().toLowerCase().contains(tenKH.toLowerCase().trim());
                } else {
                    thongManTenKH = false;
                }
            }

            // Kiểm tra khớp Số điện thoại
            if (sdt != null && !sdt.trim().isEmpty()) {
                if (kh.getSoDT() != null) {
                    thongManSDT = kh.getSoDT().contains(sdt.trim());
                } else {
                    thongManSDT = false;
                }
            }

            // Nếu khách hàng thỏa mãn TẤT CẢ các điều kiện đã nhập thì đưa vào kết quả
            if (thongManMaKH && thongManTenKH && thongManSDT) {
                ketQua.add(kh);
            }
        }

        return ketQua;
    }
}