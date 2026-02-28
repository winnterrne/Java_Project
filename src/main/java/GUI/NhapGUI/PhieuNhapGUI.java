package GUI.NhapGUI;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.rmi.server.ExportException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import BUS.PhieuNhap_BUS;
import DAO.PhieuNhap_DAO;
import DTO.PhieuNhap_DTO;
import Utils.EportExcel;
import com.toedter.calendar.JDateChooser;

public class PhieuNhapGUI extends JFrame {
    JPanel topPanel, centerPanel, pTimKiem, pChucNang, pLocNgay, pLocGia;
    JTextField tfTimKiem, tfTuGia, tfDenGia;
    JButton btnXoa, btnSua, btnXemCTPN, btnXuatExcel, btnTimKiem, btnLamMoi;
    JDateChooser dcTuNgay, dcDenNgay;
    JTable tbPhieuNhap;
    DefaultTableModel dtmPhieuNhap;
    JScrollPane spPhieuNhap;

    PhieuNhap_BUS pnBUS = new PhieuNhap_BUS();
    PhieuNhap_DAO pnDAO = new PhieuNhap_DAO();

    EportExcel ep = new EportExcel();
    public PhieuNhapGUI() {
        initGUI();
        loadPhieuNhap();
        addEvents();
    }

    public void initGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);

        setTitle("Quản lý phiếu nhập");
        setLayout(new BorderLayout(10, 10 ));
        topPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        pTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        tfTimKiem = new JTextField(20);
        pTimKiem.add(tfTimKiem);
        btnTimKiem = new JButton("Tìm kiếm");
        pTimKiem.add(btnTimKiem);

        btnLamMoi = new JButton("Làm mới");
        pTimKiem.add(btnLamMoi);
        pTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Tìm Kiếm"));

        pChucNang = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        btnXoa = new JButton("Xóa");
        pChucNang.add(btnXoa);

        btnSua = new JButton("Sửa");
        pChucNang.add(btnSua);

        btnXemCTPN = new JButton("Xem Chi Tiết");
        pChucNang.add(btnXemCTPN);
        btnXuatExcel = new JButton("Xuất Excel");
        pChucNang.add(btnXuatExcel);
        pChucNang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Chức Năng"));

        pLocNgay = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pLocNgay.add(createLabel("Từ ngày: "));
        dcTuNgay = new JDateChooser();
        dcTuNgay.setDateFormatString("dd/MM/yyyy");
        pLocNgay.add(dcTuNgay);
        pLocNgay.add(createLabel("Đến ngày: "));
        dcDenNgay = new JDateChooser();
        dcDenNgay.setDateFormatString("dd/MM/yyyy");
        pLocNgay.add(dcDenNgay);
        pLocNgay.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Lọc theo ngày"));

        pLocGia = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        pLocGia.add(createLabel("Từ: "));
        tfTuGia = new JTextField(15);
        pLocGia.add(tfTuGia);
        pLocGia.add(createLabel("Đến: "));
        tfDenGia = new JTextField(15);
        pLocGia.add(tfDenGia);
        pLocGia.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Lọc theo giá"));

        topPanel.add(pTimKiem); topPanel.add(pChucNang); topPanel.add(pLocNgay); topPanel.add(pLocGia);

        centerPanel =  new JPanel(new GridLayout(1, 1, 20, 20));
        String cols[] = {"STT", "Mã Phiếu Nhập", "Nhà cung cấp", "Người tạo", "Thời gian tạo", "Tổng tiền"};
        dtmPhieuNhap = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbPhieuNhap = new JTable(dtmPhieuNhap);
        tbPhieuNhap.setRowHeight(20);
        tbPhieuNhap.getTableHeader().setReorderingAllowed(false);
        TableColumn col1 = tbPhieuNhap.getColumnModel().getColumn(0);
        col1.setMaxWidth(50);

        spPhieuNhap = new JScrollPane(tbPhieuNhap);
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 20, 10, 20);
        spPhieuNhap.setBorder(new CompoundBorder(line, margin));
        centerPanel.add(spPhieuNhap);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    public void loadPhieuNhap() {
        ArrayList<PhieuNhap_DTO> listPN = pnBUS.getAllPhieuNhap();
        int stt = 1;
        dtmPhieuNhap.setRowCount(0);

        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(PhieuNhap_DTO pn : listPN) {
            String tenNCC = pnDAO.getTenNCCByMaPN(pn.getMaPhieuNhap());
            String tongTienFormatted = nf.format(pn.getTongTien());
            dtmPhieuNhap.addRow(new Object[]{
                    stt++,
                    pn.getMaPhieuNhap(),
                    tenNCC,
                    "admin", //sau này lấy manv->chức vụ....
                    pn.getNgayNhapHang(),
                    tongTienFormatted
            });
        }
    }

    public void loadPhieuNhap(ArrayList<PhieuNhap_DTO> listPN) {
        int stt = 1;
        dtmPhieuNhap.setRowCount(0);
        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(PhieuNhap_DTO pn : listPN) {
            String tenNCC = pnDAO.getTenNCCByMaPN(pn.getMaPhieuNhap());
            String tongTienFormatted = nf.format(pn.getTongTien());

            dtmPhieuNhap.addRow(new Object[]{
                    stt++,
                    pn.getMaPhieuNhap(),
                    tenNCC,
                    "admin",
                    pn.getNgayNhapHang(),
                    tongTienFormatted
            });
        }
    }

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {

        String keyword = tfTimKiem.getText().trim();
        Date tuNgay = dcTuNgay.getDate();
        Date denNgay = dcDenNgay.getDate();
        String giaTuStr = tfTuGia.getText().trim();
        String giaDenStr = tfDenGia.getText().trim();

        // Nếu tất cả đều trống
        if (keyword.isEmpty() && tuNgay == null && denNgay == null
                && giaTuStr.isEmpty() && giaDenStr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập ít nhất một điều kiện tìm kiếm!");
            return;
        }

        Double giaTu = giaTuStr.isEmpty() ? null : Double.parseDouble(giaTuStr);
        Double giaDen = giaDenStr.isEmpty() ? null : Double.parseDouble(giaDenStr);

        ArrayList<PhieuNhap_DTO> list =
                pnBUS.timKiemNangCao(keyword, tuNgay, denNgay, giaTu, giaDen);

        loadPhieuNhap(list);
    }

    public void addEvents() {
        btnTimKiem.addActionListener(e -> {
            btnTimKiemActionPerformed(e);
        });

        btnLamMoi.addActionListener(e -> {
            tfTimKiem.setText("");
            tfTimKiem.requestFocus();
            dcTuNgay.setDate(null);
            dcDenNgay.setDate(null);
            tfTuGia.setText("");
            tfDenGia.setText("");
            loadPhieuNhap();
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tbPhieuNhap.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần xóa");
                return;
            }
            String maPN = tbPhieuNhap.getValueAt(selectedRow, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn xóa phiếu nhập " + maPN,
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if(confirm == JOptionPane.YES_OPTION) {
                if(pnBUS.deletePhieuNhap(maPN)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    loadPhieuNhap();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa không thành công");
                }
            }
        });

        btnSua.addActionListener(e -> {
            int selectedRow =  tbPhieuNhap.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập cần sửa!");
                return;
            }
            String maPN =  tbPhieuNhap.getValueAt(selectedRow, 1).toString();
            new SuaPhieuNhapGUI(maPN).setVisible(true);
            loadPhieuNhap();
        });

        btnXemCTPN.addActionListener(e -> {
            int selectedRow = tbPhieuNhap.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
                return;
            }
            String maPN = tbPhieuNhap.getValueAt(selectedRow, 1).toString();
            new ChiTietPhieuNhapGUI(maPN).setVisible(true);
        });

        btnXuatExcel.addActionListener(e -> {
           JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu file");

            int userSelection = fileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {

                String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }

                ArrayList<PhieuNhap_DTO> list = pnBUS.getAllPhieuNhap();
                ep.exportTablePNToExcel(tbPhieuNhap, filePath);
            }
        });

    }

    public static void main(String[] args) {
        new PhieuNhapGUI();
    }
}
