package GUI.NhapGUI;

import BUS.NhaCungCap_BUS;
import DTO.NhaCungCap_DTO;
import DTO.PhieuNhap_DTO;
import Utils.EportExcel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class NhaCungCapGUI extends JFrame {
    JPanel topPanel, centerPanel, pTimKiem, pChucNang;
    JTextField tfTimKiem;
    JButton btnTimKiem, btnLamMoi, btnThem, btnXoa, btnSua, btnXemCTPN, btnXuatExcel;
    JTable tbNCC;
    DefaultTableModel dtmNCC;
    JScrollPane spNCC;
    NhaCungCap_BUS nccBUS = new NhaCungCap_BUS();
    EportExcel export = new EportExcel();
    public NhaCungCapGUI() {
        initGUI();
        loadTableNCC();
        addEvents();
    }

    public void initGUI() {
        Font font = new Font("Times New Roman", Font.BOLD, 12);
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("TableHeader.font", font);
        UIManager.put("ComboBox.font", font);

        setLayout(new BorderLayout(10, 10));
        topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
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
        btnThem = new JButton("Thêm");
        pChucNang.add(btnThem);

        btnXoa = new JButton("Xóa");
        pChucNang.add(btnXoa);

        btnSua = new JButton("Sửa");
        pChucNang.add(btnSua);

        btnXuatExcel = new JButton("Xuất Excel");
        pChucNang.add(btnXuatExcel);
        pChucNang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Chức Năng"));

        topPanel.add(pTimKiem);
        topPanel.add(pChucNang);

        centerPanel = new JPanel(new GridLayout(1, 1, 10, 10));
        String cols[] = {"Mã NCC", "Tên nhà cung cấp", "Số điện thoại", "Địa chỉ", "Email"};
        dtmNCC = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tbNCC = new JTable(dtmNCC);
        tbNCC.setRowHeight(20);
        tbNCC.getTableHeader().setReorderingAllowed(false);
        TableColumn colMaNCC = tbNCC.getColumnModel().getColumn(0);
        colMaNCC.setMaxWidth(100);
        TableColumn colSDT =  tbNCC.getColumnModel().getColumn(2);
        colSDT.setMaxWidth(100);
        spNCC = new JScrollPane(tbNCC);
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 20, 10, 20);
        spNCC.setBorder(new CompoundBorder(line, margin));
        centerPanel.add(spNCC);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void loadTableNCC() {
        ArrayList<NhaCungCap_DTO> listNCC = nccBUS.getAllNhaCungCap();
        dtmNCC.setRowCount(0);
        for(NhaCungCap_DTO ncc : listNCC) {
            dtmNCC.addRow(new Object[]{
                    ncc.getMaNCC(),
                    ncc.getTenNCC(),
                    ncc.getSoDT(),
                    ncc.getDiaChi(),
                    ncc.getEmail()
            });
        }
    }

    public void loadTableNCC(String keyword) {
        ArrayList<NhaCungCap_DTO> listNCC = nccBUS.timNCCTheoTenNCC(keyword);
        dtmNCC.setRowCount(0);
        for(NhaCungCap_DTO ncc : listNCC) {
            dtmNCC.addRow(new Object[]{
                    ncc.getMaNCC(),
                    ncc.getTenNCC(),
                    ncc.getSoDT(),
                    ncc.getDiaChi(),
                    ncc.getEmail()
            });
        }
    }

    public void addEvents() {
        btnTimKiem.addActionListener(e -> {
            String keyword = tfTimKiem.getText().trim();
            if(keyword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhà cung cấp cần tìm!");
                return;
            } else {
                loadTableNCC(keyword);
            }
        });

        btnLamMoi.addActionListener(e -> {
            tfTimKiem.setText("");
            tfTimKiem.requestFocus();
            loadTableNCC();
        });

        btnThem.addActionListener(e -> {
            new ThemNhaCungCapGUI(this).setVisible(true);
        });

        btnXoa.addActionListener(e -> {
            int selectedRow = tbNCC.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần xóa!");
                return;
            }
            String maNCC = tbNCC.getValueAt(selectedRow, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn xóa nhà cung cấp " + tbNCC.getValueAt(selectedRow, 1).toString(),
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION
            );
            if(confirm == JOptionPane.YES_OPTION){
                if(nccBUS.deleteNCC(maNCC)) {
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
                    loadTableNCC();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa không thành công!");
                }
            }
        });

        btnSua.addActionListener(e -> {
            int row = tbNCC.getSelectedRow();
            if(row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhà cung cấp cần sửa!");
                return;
            }
            String maNCC =  tbNCC.getValueAt(row, 0).toString();
            String tenNCC =  tbNCC.getValueAt(row, 1).toString();
            String soDT = tbNCC.getValueAt(row, 2).toString();
            String diaChi =  tbNCC.getValueAt(row, 3).toString();
            String email =  tbNCC.getValueAt(row, 4).toString();
            NhaCungCap_DTO nccDTO  = new NhaCungCap_DTO(maNCC, tenNCC, soDT, diaChi, email);
            new SuaNhaCungCapGUI(nccDTO, this).setVisible(true);
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

                ArrayList<NhaCungCap_DTO> list = nccBUS.getAllNhaCungCap();
                export.exportTableNCCToExcel(tbNCC, filePath);
            }
        });
    }

    public static void main(String[] args) {
        new NhaCungCapGUI();
    }
}
