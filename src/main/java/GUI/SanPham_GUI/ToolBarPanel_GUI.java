package GUI.SanPham_GUI;

import javax.swing.*;

public class ToolBarPanel_GUI extends JToolBar {
    private JButton btnThem, btnSua, btnXoa, btnChiTiet, btnLamMoi, btnExport, btnThongKe;

    public ToolBarPanel_GUI() {
        setFloatable(false);

        btnThem = new JButton("THÊM");
        btnSua = new JButton("SỬA");
        btnXoa = new JButton("XÓA");
        btnChiTiet = new JButton("CHI TIẾT");
        btnLamMoi = new JButton("Làm mới");
        btnExport = new JButton("XUẤT EXCEL");
        btnThongKe = new JButton("THỐNG KÊ DOANH THU");

        btnThem.setToolTipText("Thêm sản phẩm mới");
        btnSua.setToolTipText("Sửa sản phẩm đã chọn");
        btnXoa.setToolTipText("Xóa sản phẩm đã chọn");
        btnChiTiet.setToolTipText("Xem chi tiết sản phẩm");
        btnLamMoi.setToolTipText("Làm mới danh sách");
        btnExport.setToolTipText("Xuất danh sách ra file Excel");
        btnThongKe.setToolTipText("Thống kê doanh thu theo tháng");

        add(btnThem);
        add(btnSua);
        add(btnXoa);
        addSeparator();
        add(btnChiTiet);
        add(btnLamMoi);
        addSeparator();
        add(btnExport);
        add(btnThongKe);
    }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnChiTiet() { return btnChiTiet; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnExport() { return btnExport; }
    public JButton getBtnThongKe() { return btnThongKe; }

}
