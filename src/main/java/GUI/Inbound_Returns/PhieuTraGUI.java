package GUI.Inbound_Returns;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import BUS.PhieuNhap_BUS;
import BUS.PhieuTra_BUS;
import DAO.PhieuNhap_DAO;
import DTO.PhieuNhap_DTO;
import DTO.PhieuTra_DTO;
import Utils.EportExcel;
import com.toedter.calendar.JDateChooser;

public class PhieuTraGUI extends JPanel {
    JPanel topPanel, centerPanel, pTimKiem, pChucNang, pLocNgay, pLocGia;
    JTextField tfTimKiem, tfTuGia, tfDenGia;
    JButton btnXoa, btnXemCTPT, btnXuatExcel, btnTimKiem, btnLamMoi;
    JDateChooser dcTuNgay, dcDenNgay;
    JTable tbPhieuTra;
    DefaultTableModel dtmPhieuTra;
    JScrollPane spPhieuTra;

    PhieuTra_BUS ptBUS =  new PhieuTra_BUS();

    EportExcel ep = new EportExcel();
    public PhieuTraGUI() {
        initGUI();
        loadPhieuTra();
        addEvents();
    }

    public void initGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);

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

        btnXemCTPT = new JButton("Xem Chi Tiết");
        pChucNang.add(btnXemCTPT);
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
        String cols[] = {"STT", "Mã Phiếu Trả", "Nhà cung cấp", "Người tạo", "Lý do", "Thời gian tạo", "Tổng tiền"};
        dtmPhieuTra = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbPhieuTra = new JTable(dtmPhieuTra);
        tbPhieuTra.setRowHeight(20);
        tbPhieuTra.getTableHeader().setReorderingAllowed(false);
        TableColumn col1 = tbPhieuTra.getColumnModel().getColumn(0);
        col1.setMaxWidth(50);

        spPhieuTra = new JScrollPane(tbPhieuTra);
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 20, 10, 20);
        spPhieuTra.setBorder(new CompoundBorder(line, margin));
        centerPanel.add(spPhieuTra);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setSize(1000, 700);
        setVisible(true);

    }

    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }

    public void loadPhieuTra() {
        ArrayList<PhieuTra_DTO> listPT = ptBUS.getAllPhieuTra();
        int stt = 1;
        dtmPhieuTra.setRowCount(0);

        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(PhieuTra_DTO pt : listPT) {
            String tenNCC = ptBUS.getTenNCCByMaPT(pt.getMaPhieuTra());
            String tongTienFormatted = nf.format(pt.getTongTra());
            dtmPhieuTra.addRow(new Object[]{
                    stt++,
                    pt.getMaPhieuTra(),
                    tenNCC,
                    "admin",
                    pt.getLyDo(),
                    pt.getNgayTra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    tongTienFormatted
            });
        }
    }

    public void loadPhieuTra(ArrayList<PhieuTra_DTO> listPT) {
        int stt = 1;
        dtmPhieuTra.setRowCount(0);
        NumberFormat nf =  NumberFormat.getInstance(new Locale("vi", "VN"));
        for(PhieuTra_DTO pt : listPT) {
            String tenNCC = ptBUS.getTenNCCByMaPT(pt.getMaPhieuTra());
            String tongTienFormatted = nf.format(pt.getTongTra());
            dtmPhieuTra.addRow(new Object[]{
                    stt++,
                    pt.getMaPhieuTra(),
                    tenNCC,
                    "admin",
                    pt.getNgayTra(),
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

        ArrayList<PhieuTra_DTO> list = ptBUS.timKiemNangCao(keyword, tuNgay, denNgay, giaTuStr, giaDenStr);

        loadPhieuTra(list);
    }

    public void addEvents() {
        btnTimKiem.addActionListener(this::btnTimKiemActionPerformed);

        btnLamMoi.addActionListener(e -> {
            tfTimKiem.setText("");
            tfTimKiem.requestFocus();
            dcTuNgay.setDate(null);
            dcDenNgay.setDate(null);
            tfTuGia.setText("");
            tfDenGia.setText("");
            loadPhieuTra();
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tbPhieuTra.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu trả cần xóa");
                return;
            }
            String maPN = tbPhieuTra.getValueAt(selectedRow, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn xóa phiếu trả " + maPN,
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if(confirm == JOptionPane.YES_OPTION) {
                if(ptBUS.deletePhieuTra(maPN)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    loadPhieuTra();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa không thành công");
                }
            }
        });

        btnXemCTPT.addActionListener(e -> {
            int selectedRow = tbPhieuTra.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu nhập");
                return;
            }
            String maPT = tbPhieuTra.getValueAt(selectedRow, 1).toString();
            new ChiTietPhieuTraGUI(maPT).setVisible(true);
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

                ep.exportTablePNToExcel(tbPhieuTra, filePath);
            }
        });

    }
}