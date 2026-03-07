package GUI.Admin;
import javax.swing.*;
import java.awt.*;

import BUS.SanPham_BUS;
import BUS.ThongKe_BUS;
import DTO.CurrentUser;
import DTO.SanPham_DTO;
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
    public static final String CARD_TRONG = "Panel trong";
    private SanPham_BUS spbus = new SanPham_BUS();
    private ThongKe_BUS tkbus = new ThongKe_BUS();
    private NhapHangGUI nhapHangGUI;
    private PhieuNhapGUI phieuNhapGUI;
    private TraHangGUI traHangGUI;
    private PhieuTraGUI phieuTraGUI;
    private BanHang_GUI  banHangGUI;
    private HoaDon_GUI hoaDonGUI;

    public AdminContentPanel_GUI() {
        card = new CardLayout();
        setLayout(card);
        setBackground(new Color(0xF6F3F3));

        add(new PanelTrong());

        banHangGUI = new BanHang_GUI();
        add(banHangGUI, CARD_BAN_HANG);
        add(new SanPhamMain_GUI(spbus, tkbus),CARD_SAN_PHAM);
        add(new DanhMuc_GUI(), CARD_DANH_MUC_SAN_PHAM);
        hoaDonGUI = new HoaDon_GUI();
        add(hoaDonGUI, CARD_HOA_DON);
        add(new NhaCungCapGUI(), CARD_NHA_CUNG_CAP);
        nhapHangGUI = new NhapHangGUI();
        add(nhapHangGUI, CARD_NHAP_HANG);
        phieuNhapGUI = new PhieuNhapGUI();
        add(phieuNhapGUI, CARD_PHIEU_NHAP);
        traHangGUI = new TraHangGUI();
        add(traHangGUI,CARD_TRA_HANG);
        phieuTraGUI = new PhieuTraGUI();
        add(phieuTraGUI, CARD_PHIEU_TRA);
        add(new TaiKhoan_GUI(), CARD_TAI_KHOAN);

    }

    public NhapHangGUI getNhapHangGUI() {
        return nhapHangGUI;
    }

    public PhieuNhapGUI getPhieuNhapGUI() {
        return phieuNhapGUI;
    }

    public TraHangGUI getTraHangGUI() {
        return traHangGUI;
    }

    public  PhieuTraGUI getPhieuTraGUI() {
        return phieuTraGUI;
    }

    public BanHang_GUI getBanHangGUI() {
        return banHangGUI;
    }

    public HoaDon_GUI getHoaDonGUI() {
        return hoaDonGUI;
    }

    public void showManHinh(String tenmanhinh) {
        card.show(this,tenmanhinh);
    }
}