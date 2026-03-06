package GUI.Admin;
import javax.swing.*;
import java.awt.*;

import BUS.SanPham_BUS;
import BUS.ThongKe_BUS;
import GUI.Account.*;
import GUI.Product.*;
import GUI.Inbound_Returns.*;

public class AdminContentPanel_GUI extends JPanel {
    private CardLayout card;
    public static final String CARD_BAN_HANG = "Bán Hàng";
    public static final String CARD_SAN_PHAM = "Quản Lí Sản Phẩm";
    public static final String CARD_HOA_DON = "Danh Sách Hóa Đơn";
    public static final String CARD_DANH_MUC_SAN_PHAM = "Danh Mục Sản Phẩm";
    public static final String CARD_TAI_KHOAN = "Quản Lí Tài Khoản ";
    public static final String CARD_NHAP_HANG = "Nhập Hàng";
    public static final String CARD_THONG_KE = "Thống Kê và Báo Cáo";
    public static final String CARD_NHA_CUNG_CAP = "Quản Lí Nhà Cung Cấp";
    public static final String CARD_PHIEU_NHAP = "Phiếu Nhập";
    public static final String CARD_PHIEU_TRA = "Phiếu Trả";
    public static final String CARD_TRA_HANG = "Trả Hàng";
    private SanPham_BUS spbus = new SanPham_BUS();
    private ThongKe_BUS tkbus = new ThongKe_BUS();

    public AdminContentPanel_GUI() {
        card = new CardLayout();
        setLayout(card);
        setBackground(new Color(0xF6F3F3));

        add(new SanPhamMain_GUI(spbus, tkbus),CARD_SAN_PHAM);
        add(new DanhMuc_GUI(), CARD_DANH_MUC_SAN_PHAM);
        add(new HoaDon_GUI(), CARD_HOA_DON);
        add(new NhaCungCapGUI(), CARD_NHA_CUNG_CAP);
        add(new NhapHangGUI(), CARD_NHAP_HANG);
        add(new PhieuNhapGUI(), CARD_PHIEU_NHAP);
        add(new TraHangGUI(),CARD_TRA_HANG);
        add(new PhieuTraGUI(), CARD_PHIEU_TRA);
        add(new TaiKhoan_GUI(), CARD_TAI_KHOAN);
        add(new BanHang_GUI(), CARD_BAN_HANG);


    }
    public void showManHinh(String tenmanhinh) {
        card.show(this,tenmanhinh);
    }
}