package GUI.Product;

import javax.swing.*;

public class ToolBarPanel_GUI extends JToolBar {
    private JButton btnThem, btnSua, btnXoa, btnChiTiet, btnLamMoi, btnExportExcel, btnThongKe, btnXemSPXoa, btnExportPDF;

    public ToolBarPanel_GUI() {
        setFloatable(false);

        btnThem = new JButton("THÊM");
        btnSua = new JButton("SỬA");
        btnXoa = new JButton("XÓA");
        btnChiTiet = new JButton("CHI TIẾT");
        btnLamMoi = new JButton("Làm mới");
        btnExportExcel = new JButton("XUẤT EXCEL");
        btnExportPDF = new JButton("XUẤT PDF");
        btnThongKe = new JButton("THỐNG KÊ DOANH THU");
        btnXemSPXoa = new JButton("XEM SẢN PHẨM ĐÃ XÓA");

        btnThem.setToolTipText("Thêm sản phẩm mới");
        btnSua.setToolTipText("Sửa sản phẩm đã chọn");
        btnXoa.setToolTipText("Xóa sản phẩm đã chọn");
        btnChiTiet.setToolTipText("Xem chi tiết sản phẩm");
        btnLamMoi.setToolTipText("Làm mới danh sách");
        btnExportExcel.setToolTipText("Xuất danh sách ra file Excel");
        btnExportPDF.setToolTipText("Xuất danh sách ra file PDF");
        btnThongKe.setToolTipText("Thống kê doanh thu theo tháng");
        btnXemSPXoa.setToolTipText("Xem sản phẩm đã xóa");

        add(btnThem);
        add(btnSua);
        add(btnXoa);
        addSeparator();
        add(btnChiTiet);
        add(btnLamMoi);
        addSeparator();
        add(btnExportExcel);
        add(btnExportPDF);
        addSeparator();
        add(btnXemSPXoa);
        add(btnThongKe);
    }

    public JButton getBtnThem() { return btnThem; }
    public JButton getBtnSua() { return btnSua; }
    public JButton getBtnXoa() { return btnXoa; }
    public JButton getBtnChiTiet() { return btnChiTiet; }
    public JButton getBtnLamMoi() { return btnLamMoi; }
    public JButton getBtnExportExcel() { return btnExportExcel; }
    public JButton getBtnExportPDF() { return btnExportPDF; }
    public JButton getBtnThongKe() { return btnThongKe; }
    public JButton getBtnXemSPXoa() { return btnXemSPXoa; }

}