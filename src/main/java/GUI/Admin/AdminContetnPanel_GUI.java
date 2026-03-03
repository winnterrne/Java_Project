package GUI.Admin;
import javax.swing.*;
import java.awt.*;
import GUI.Account.*;
import GUI.Product.*;
import GUI.Inbound_Returns.*;

public class AdminContetnPanel_GUI extends JPanel {
    private CardLayout card;
    public static final String CARD_BAN_HANG = "Quan ly ban hang";
    public static final String CARD_SAN_PHAM = "Quan ly san pham";
    public static final String CARD_HOA_DON = "Quan ly hoa don";
    public static final String CARD_DANH_MUC_SAN_PHAM = "Danh muc san pham";
    public static final String CARD_TAI_KHOAN = "Quan ly tai khoan";
    public static final String CARD_NHAP_HANG = "Quan ly nhap hang";
    public static final String CARD_THONG_KE = "Thong ke va bao cao";
    public static final String CARD_NHA_CUNG_CAP = "Quan ly nha cung cap";
    public static final String CARD_PHIEU_NHAP = "Quan ly phieu nhap";
    public static final String CARD_PHIEU_TRA = "Quan ly phieu tra";
    public static final String CARD_TRA_HANG = "Quan ly tra hang";

    public AdminContetnPanel_GUI() {
        card = new CardLayout();
        setLayout(card);
        setBackground(new Color(0xF6F3F3));

        add(new BanHang_GUI(), CARD_BAN_HANG);
        add(new HoaDon_GUI(), CARD_HOA_DON);
        add(new NhaCungCapGUI(), CARD_NHA_CUNG_CAP);
        add(new TaiKhoan_GUI(), CARD_TAI_KHOAN);
        add(new NhapHangGUI(), CARD_NHAP_HANG);
        add(new PhieuNhapGUI(), CARD_PHIEU_NHAP);
        add(new DanhMuc_GUI(), CARD_DANH_MUC_SAN_PHAM);
    }
    public void showManHinh(String tenmanhinh) {
        card.show(this,tenmanhinh);
    }
}