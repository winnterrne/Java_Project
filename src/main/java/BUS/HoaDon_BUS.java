package BUS;

import DAO.HoaDon_DAO;
import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import DTO.SanPham_DTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

public class HoaDon_BUS {
    private HoaDon_DAO hoaDon = new HoaDon_DAO();

    public HoaDon_BUS() {
    }

    public HoaDon_DTO taoHoaDon() {
        HoaDon_DTO dto = new HoaDon_DTO();

        // 1. Gọi hàm lấy mã mới (Hàm này đã xử lý sẵn logic tăng mã + định dạng)
        String maHDMoi = hoaDon.layMaHoaDonMoiNhat();

        // 2. Bảo vệ an toàn: Nếu CSDL trống không có bảng cấp mã, tự động fallback về HD001
        if (maHDMoi == null || maHDMoi.equals("HD000")) {
            maHDMoi = "HD001";
        }

        // 3. Gán mã vào đối tượng
        dto.setMaHD(maHDMoi);

        return dto;
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

    // --- CÁC HÀM XỬ LÝ GIỎ HÀNG TẠI BỘ NHỚ TẠM (RAM) ---

    public void themVaoGioHang(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham, int soLuongThem) {
        if (sanPham == null || gioHang == null || soLuongThem <= 0) {
            return;
        }

        ChiTietHoaDon_DTO cthdTonTai = kiemTra(gioHang, sanPham);

        if (cthdTonTai != null) {
            // Đã có trong giỏ -> Cộng dồn số lượng
            capNhatGioHang(gioHang, sanPham, soLuongThem);
        } else {
            // Chưa có trong giỏ -> Tạo mới
            ChiTietHoaDon_DTO cthdMoi = new ChiTietHoaDon_DTO();
            cthdMoi.setMaSP(sanPham.getMaSP());
            cthdMoi.setSoLuongMua(soLuongThem);
            cthdMoi.setDonGia((float) sanPham.getGiaBan());
            cthdMoi.setThanhTien(soLuongThem * sanPham.getGiaBan());
            gioHang.add(cthdMoi);
        }
    }

    public void capNhatGioHang(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham, int soLuongThayDoi) {
        if (sanPham == null || gioHang == null || gioHang.isEmpty()) {
            return;
        }

        Iterator<ChiTietHoaDon_DTO> iterator = gioHang.iterator();
        while (iterator.hasNext()) {
            ChiTietHoaDon_DTO cthd = iterator.next();

            if (cthd.getMaSP().equals(sanPham.getMaSP())) {
                int soLuongMoi = cthd.getSoLuongMua() + soLuongThayDoi;

                if (soLuongMoi <= 0) {
                    iterator.remove(); // Xóa an toàn
                } else {
                    cthd.setSoLuongMua(soLuongMoi);
                    cthd.setThanhTien(soLuongMoi * sanPham.getGiaBan());
                }
                break;
            }
        }
    }

    public double tinhTien(ArrayList<ChiTietHoaDon_DTO> gioHang) {
        double thanhTien = 0;
        for (ChiTietHoaDon_DTO cthd : gioHang) {
            thanhTien += cthd.getThanhTien();
        }
        return thanhTien;
    }

    public ChiTietHoaDon_DTO kiemTra(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham) {
        if (sanPham == null || gioHang == null) {
            return null;
        }
        for (ChiTietHoaDon_DTO cthd : gioHang) {
            if (cthd.getMaSP().equals(sanPham.getMaSP())) {
                return cthd;
            }
        }
        return null;
    }
}