package BUS;

import DTO.ChiTietHoaDon_DTO;
import DTO.SanPham_DTO;

import java.util.Vector;

public class TaoHoaDon {

    // Đã nâng cấp: Xử lý cộng dồn số lượng nếu sản phẩm đã có trong giỏ
    public static void tinhTien(Vector<ChiTietHoaDon_BUS> gioHang, SanPham_DTO sanPham, int soLuong) {
        if (gioHang == null) {
            gioHang = new Vector<>();
        }

        if (sanPham == null || soLuong <= 0) {
            return;
        }

        // 1. Quét xem sản phẩm này đã tồn tại trong giỏ hàng chưa
        for (ChiTietHoaDon_BUS cthd : gioHang) {
            if (cthd.getSanPham().getMaSP().equals(sanPham.getMaSP())) {

                // Nếu ĐÃ CÓ: Cộng dồn số lượng cũ với số lượng mới thêm
                int soLuongMoi = cthd.getSoLuongMua() + soLuong;
                cthd.setSoLuongMua(soLuongMoi);

                // Cập nhật lại thành tiền
                cthd.setThanhTien(0);

                return; // Dừng hàm tại đây, không tạo dòng mới
            }
        }

        // 2. Nếu CHƯA CÓ: Tạo ra một dòng chi tiết hóa đơn mới toanh
        ChiTietHoaDon_BUS dto = new ChiTietHoaDon_BUS(
                sanPham,
                soLuong,
                0,
                0
        );
        gioHang.add(dto);
    }
}