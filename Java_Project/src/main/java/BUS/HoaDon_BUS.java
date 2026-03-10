package BUS;

import DAO.HoaDon_DAO;
import DTO.ChiTietHoaDon_DTO;
import DTO.HoaDon_DTO;
import DTO.SanPham_DTO;


import DTO.KhachHang_DTO;

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
        String maHDMoi = hoaDon.layMaHoaDonMoiNhat();
        if (maHDMoi == null || maHDMoi.equals("HD000")) {
            maHDMoi = "HD001";
        }
        dto.setMaHD(maHDMoi);
        return dto;
    }

    public ArrayList<HoaDon_DTO> layTatCaHD() { return hoaDon.layTatCaHD(); }
    public void insertHD(HoaDon_DTO dto) { hoaDon.insertHoaDon(dto); }
    public void updateHD(HoaDon_DTO dto) { hoaDon.updateHoaDon(dto); }
    public void deleteHD(HoaDon_DTO dto) { hoaDon.deleteHoaDon(dto.getMaHD()); }
    public HoaDon_DTO layHDTheoMaHD(String maHD) { return hoaDon.layHDTheoMaHD(maHD); }
    public ArrayList<HoaDon_DTO> layHDTheoMaKH(String maKH) { return hoaDon.layHDTheoMaKH(maKH); }

    public ArrayList<HoaDon_DTO> layHDTheoNgay(LocalDate ngay1, LocalDate ngay2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");
        if (ngay1 == null && ngay2 == null) return null;
        if (ngay1 == null) ngay1 = LocalDate.parse("2026/1/1", formatter);
        if (ngay2 == null) ngay2 = LocalDate.now();
        return hoaDon.layHDTheoNgay(ngay1, ngay2);
    }





    public void themVaoGioHang(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham, int soLuongThem) {
        if (sanPham == null || gioHang == null || soLuongThem <= 0) return;

        ChiTietHoaDon_DTO cthdTonTai = kiemTra(gioHang, sanPham);

        if (cthdTonTai != null) {
            int soLuongMoi = cthdTonTai.getSoLuongMua() + soLuongThem;


            if (soLuongMoi > sanPham.getSoLuongTon()) {
                throw new IllegalArgumentException("Số lượng mua vượt quá số lượng tồn kho (" + sanPham.getSoLuongTon() + ")!");
            }

            cthdTonTai.setSoLuongMua(soLuongMoi);
            cthdTonTai.setThanhTien(soLuongMoi * sanPham.getGiaBan());
        } else {

            if (soLuongThem > sanPham.getSoLuongTon()) {
                throw new IllegalArgumentException("Số lượng mua vượt quá số lượng tồn kho (" + sanPham.getSoLuongTon() + ")!");
            }

            ChiTietHoaDon_DTO cthdMoi = new ChiTietHoaDon_DTO();
            cthdMoi.setMaSP(sanPham.getMaSP());
            cthdMoi.setSoLuongMua(soLuongThem);
            cthdMoi.setDonGia((float) sanPham.getGiaBan());
            cthdMoi.setThanhTien(soLuongThem * sanPham.getGiaBan());
            gioHang.add(cthdMoi);
        }
    }

    public void capNhatSoLuongMoi(ArrayList<ChiTietHoaDon_DTO> gioHang, SanPham_DTO sanPham, int soLuongMoi) {
        if (sanPham == null || gioHang == null) return;


        if (soLuongMoi > sanPham.getSoLuongTon()) {
            throw new IllegalArgumentException("Số lượng nhập vào vượt quá tồn kho (" + sanPham.getSoLuongTon() + ")!");
        }

        Iterator<ChiTietHoaDon_DTO> iterator = gioHang.iterator();
        while (iterator.hasNext()) {
            ChiTietHoaDon_DTO cthd = iterator.next();
            if (cthd.getMaSP().equals(sanPham.getMaSP())) {
                if (soLuongMoi <= 0) {
                    iterator.remove();
                } else {
                    cthd.setSoLuongMua(soLuongMoi);
                    cthd.setThanhTien(soLuongMoi * sanPham.getGiaBan());
                }
                break;
            }
        }
    }

    public void xoaKhoiGioHang(ArrayList<ChiTietHoaDon_DTO> gioHang, String maSP) {
        if (gioHang != null && maSP != null) {
            gioHang.removeIf(cthd -> cthd.getMaSP().equals(maSP));
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
        if (sanPham == null || gioHang == null) return null;
        for (ChiTietHoaDon_DTO cthd : gioHang) {
            if (cthd.getMaSP().equals(sanPham.getMaSP())) {
                return cthd;
            }
        }
        return null;
    }

    public ArrayList<HoaDon_DTO> boLocTimKiemHD(String maHD, String maKH, LocalDate ngay1, LocalDate ngay2) {
        ArrayList<HoaDon_DTO> listKetQua = new ArrayList<>();
        ArrayList<HoaDon_DTO> danhSachGoc = layTatCaHD();
        if (danhSachGoc == null || danhSachGoc.isEmpty()) return listKetQua;

        for (HoaDon_DTO hd : danhSachGoc) {
            boolean thoaMan = true;
            if (maHD != null && !maHD.trim().isEmpty()) {
                if (hd.getMaHD() == null || !hd.getMaHD().toLowerCase().contains(maHD.toLowerCase())) thoaMan = false;
            }
            if (thoaMan && maKH != null && !maKH.trim().isEmpty()) {
                if (hd.getMaKH() == null || !hd.getMaKH().toLowerCase().contains(maKH.toLowerCase())) thoaMan = false;
            }
            if (thoaMan && (ngay1 != null || ngay2 != null)) {
                LocalDate ngayLap = hd.getNgayLapHD();
                if (ngayLap != null) {
                    if (ngay1 != null && ngayLap.isBefore(ngay1)) thoaMan = false;
                    if (ngay2 != null && ngayLap.isAfter(ngay2)) thoaMan = false;
                } else {
                    thoaMan = false;
                }
            }
            if (thoaMan) listKetQua.add(hd);
        }
        return listKetQua;
    }




    public boolean thanhToanGiaoDich(HoaDon_DTO hoaDon, KhachHang_DTO kh, ArrayList<ChiTietHoaDon_DTO> dsCTHD, String maNV) {
        try {

            if (kh != null) {
                KhachHang_BUS khBus = new KhachHang_BUS();
                khBus.insertKH(kh);
                hoaDon.setMaKH(kh.getMaKH());
            } else {
                hoaDon.setMaKH("KH000");
            }


            hoaDon.setMaNV(maNV);


            insertHD(hoaDon);


            ChiTietHoaDon_BUS cthdBus = new ChiTietHoaDon_BUS();
            cthdBus.capNhatSoLuongTon(dsCTHD);
            cthdBus.insertChiTietHoaDon(dsCTHD, hoaDon.getMaHD());




            return true;
        } catch (Exception e) {
            System.out.println("Lỗi quá trình thanh toán: " + e.getMessage());
            return false;
        }
    }
}