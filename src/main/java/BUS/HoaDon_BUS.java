package BUS;

import DAO.HoaDon_DAO;
import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import DTO.SanPham_DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HoaDon_BUS {
    private HoaDon_DAO hoaDon = new HoaDon_DAO();

    public HoaDon_DTO taoHoaDon() {
        HoaDon_DTO dto = new HoaDon_DTO();
        String maHD =  hoaDon.layMaHoaDonMoiNhat();

        // 1. Trường hợp chưa có hóa đơn nào trong Database
        if (maHD == null || maHD.isEmpty()) {
            maHD = "HD001";
        }
        try {
            String phanSoChuoi = maHD.substring(2);
            int so = Integer.parseInt(phanSoChuoi);
            so = so + 1;
            maHD = String.format("HD%03d", so);

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Định dạng mã hóa đơn cũ không hợp lệ.");
        }
        dto.setMaHD(maHD);
        return dto;
    }


    public HoaDon_BUS() {

    }

    public ArrayList<HoaDon_DTO> layTatCaHD() {
        return hoaDon.layTatCaHD();
    }

    public void insertHD(HoaDon_DTO dto) {
        hoaDon.insertHoaDon(dto);
    }

    public void updateHD(HoaDon_DTO dto) {
        hoaDon.updateHoaDon(dto);
    }

    public void deleteHD(HoaDon_DTO dto) {
        hoaDon.deleteHoaDon(dto.getMaHD());
    }

    public HoaDon_DTO layHDTheoMaHD(String maHD) {
        return hoaDon.layHDTheoMaHD(maHD);
    }

    public ArrayList<HoaDon_DTO> layHDTheoMaKH(String maKH) {
        return hoaDon.layHDTheoMaKH(maKH);
    }

    public ArrayList<HoaDon_DTO> layHDTheoNgay(LocalDate ngay1, LocalDate ngay2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        if (ngay1 == null) {
            ngay1 = LocalDate.parse("2026/1/1", formatter);
        }

        if (ngay2 == null) {
            ngay2 = LocalDate.now();
        }
        return hoaDon.layHDTheoNgay(ngay1, ngay2);
    }

    public double tinhTien(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham, int soLuong) {
        if (sanPham == null || soLuong <= 0) {
            return 0.0f;
        }

        for (ChiTietHoaDon_DTO cthd : gioHang) {
            if (cthd.getMaSP().equals(sanPham.getMaSP())) {
                int soLuongMoi = cthd.getSoLuongMua() + soLuong;
                cthd.setSoLuongMua(soLuongMoi);
                double thanhTienMoi = soLuongMoi * sanPham.getGiaBan();
                cthd.setThanhTien(thanhTienMoi);
                return thanhTienMoi;
            }
        }

        return 0.0f;
    }

}