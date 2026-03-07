package GUI.Account;
import BUS.TaiKhoan_BUS;
import DAO.TaiKhoan_DAO;
import DTO.TaiKhoan_DTO;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class TaiKhoan_GUI extends JPanel {
    JPanel topPanel, centerPanel, pTimKiem, pChucNang;
    JTextField tfTimKiem;
    JButton btnXoa, btnSua, btnXemCTPN, btnXuatExcel, btnTimKiem, btnThem;
    JTable tbTaiKhoan;
    DefaultTableModel dtmTaiKhoan;
    JScrollPane spTaiKhoan;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    TaiKhoan_DAO tkdao = new TaiKhoan_DAO();
    private String str = "";
    public TaiKhoan_GUI() {
        initGUI();
        loadTable();
    }

    public void initGUI() {
        setLayout(new BorderLayout(10, 10 ));
        topPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        pTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        tfTimKiem = new JTextField(20);
        pTimKiem.add(tfTimKiem);
        btnTimKiem = createButton("Tim kiem");
        pTimKiem.add(btnTimKiem);
        pTimKiem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Tìm Kiếm"));

        pChucNang = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));

        btnXoa = createButton("Xoa");
        pChucNang.add(btnXoa);
        btnSua = createButton("Sua");
        pChucNang.add(btnSua);
        btnThem = createButton("Them");
        pChucNang.add(btnThem);
        btnXemCTPN = createButton("Chi tiet");
        pChucNang.add(btnXemCTPN);
        btnXuatExcel = createButton("Xuat excel");
        pChucNang.add(btnXuatExcel);
        pChucNang.setBorder(BorderFactory.createTitledBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED)
                ,"Chức Năng"));


        topPanel.add(pTimKiem); topPanel.add(pChucNang);

        centerPanel =  new JPanel(new GridLayout(1, 1, 20, 20));
        String cols[] = {"Mã tai khoan","Ho va ten ","Ten dang nhap", "Email", "Vai tro", "Trang thai"};
        dtmTaiKhoan = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tbTaiKhoan = new JTable(dtmTaiKhoan);
        tbTaiKhoan.getTableHeader().setReorderingAllowed(false);
        tbTaiKhoan.setRowHeight(25);
        tbTaiKhoan.setFont(new Font("Arial", Font.PLAIN,18));
        spTaiKhoan = new JScrollPane(tbTaiKhoan);
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        Border margin = new EmptyBorder(10, 20, 10, 20);
        spTaiKhoan.setBorder(new CompoundBorder(line, margin));
        centerPanel.add(spTaiKhoan);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        btnThem.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(this);
            ThemTaiKhoan_GUI themtk = new ThemTaiKhoan_GUI((Frame) window);
            themtk.setLocationRelativeTo(null);
            themtk.setVisible(true);
            if(themtk.isSaved()) {
                loadTable();
            }
        });
        btnXemCTPN.addActionListener(e -> {
            System.out.println("Da chay");
            Window window = SwingUtilities.getWindowAncestor(this);
            ThayDoiPass_GUI thaydoipass = new ThayDoiPass_GUI((Frame) window);
            thaydoipass.setLocationRelativeTo(null);
            thaydoipass.setVisible(true);
        });
        btnXuatExcel.addActionListener(e -> xuatExcel());
        btnSua.addActionListener(e -> SuaTaiKhoan());
        btnXoa.addActionListener(e -> xoaTaiKhoan());

        tfTimKiem.addCaretListener(e -> {
            str = tfTimKiem.getText();
            fillTable();
        });
        btnTimKiem.addActionListener(e -> timKiemTheoTen());
    }

    public JButton createButton(String nameButton) {
        JButton button = new JButton(nameButton);
        return button;
    }

    public JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        return label;
    }
    public void loadTable() {
        dtmTaiKhoan.setRowCount(0);
        ArrayList<TaiKhoan_DTO> list = tkbus.getToShowTable();
        for(TaiKhoan_DTO tkdto : list) {
            dtmTaiKhoan.addRow(new Object[]{
                    tkdto.getMaTK(),
                    tkdto.getHoTen(),
                    tkdto.getTenDangNhap(),
                    tkdto.getEmail(),
                    tkdto.getMaVaiTro(),
                    tkdto.isTrangThai() ? "Hoat dong" : "Khong hoat dong"
            });
        }
    }
    public void fillTable() {
        dtmTaiKhoan.setRowCount(0);
        for (TaiKhoan_DTO tkdto : tkdao.sortName(str)) {
            dtmTaiKhoan.addRow(new Object[]{
                    tkdto.getMaTK(),
                    tkdto.getHoTen(),
                    tkdto.getTenDangNhap(),
                    tkdto.getEmail(),
                    tkdto.getMaVaiTro(),
                    tkdto.isTrangThai() ? "Hoat dong" : "Khong hoat dong"
            });
        }
    }
    public void xoaTaiKhoan() {
        try {
            int i = tbTaiKhoan.getSelectedRow();
            if(i == -1) {
                JOptionPane.showMessageDialog(this,"Vui long chon sinh vien can xoa");
                return;
            }
            String mataikhoan = tbTaiKhoan.getValueAt(i,1).toString();
            int confirm = JOptionPane.showConfirmDialog(this,"Ban co muon xoa tai khoan nay k","Xac nhanh",JOptionPane.YES_NO_OPTION);
            if(confirm == JOptionPane.YES_NO_OPTION) {
                boolean result = tkbus.deleteTaiKhoan(mataikhoan);
                if(result) {
                    JOptionPane.showMessageDialog(this,"Xoa thanh cong");
                    loadTable();
                }else {
                    JOptionPane.showMessageDialog(this,"Xoa that bai");
                }

            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void timKiemTheoTen() {
        str = tfTimKiem.getText();
        fillTable();
    }
    public void SuaTaiKhoan() {
        int i = tbTaiKhoan.getSelectedRow();
        if(i == -1) {
            JOptionPane.showMessageDialog(this,"Vui long chon tai khoan ");
            return;
        }
        String mataikhoan = dtmTaiKhoan.getValueAt(i,0).toString();
        String tendangnhap = dtmTaiKhoan.getValueAt(i,2).toString();
        String email = dtmTaiKhoan.getValueAt(i,3).toString();
        String vaitro = dtmTaiKhoan.getValueAt(i,4).toString();
        String trangthai = dtmTaiKhoan.getValueAt(i,5).toString();

        Window window = SwingUtilities.getWindowAncestor(this);
        SuaTaiKhoan_GUI suatk = new SuaTaiKhoan_GUI((Frame) window, dtmTaiKhoan, i, mataikhoan, tendangnhap, email, vaitro, trangthai);
        suatk.setVisible(true);
    }
    public void xuatExcel() {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Danh sach tai khoan");
            XSSFRow row = null;
            Cell cell = null;

            row = sheet.createRow(0);
            for (int i = 0; i < tbTaiKhoan.getColumnCount(); i++) {
                cell = row.createCell(i,CellType.STRING);
                cell.setCellValue(tbTaiKhoan.getColumnName(i));
            }
            for (int i = 0; i < tbTaiKhoan.getRowCount(); i++) {
                row = sheet.createRow(i + 1);
                for (int j = 0; j < tbTaiKhoan.getColumnCount(); j++) {
                    cell = row.createCell(j,CellType.STRING);
                    Object values = tbTaiKhoan.getValueAt(i,j);
                    if(values != null) {
                        cell.setCellValue(values.toString());
                    }
                }
            }
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chon noi luu file");
            int user = fileChooser.showSaveDialog(this);
            if (user == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                FileOutputStream fileoutput = new FileOutputStream(file + ".xlsx");
                workbook.write(fileoutput);
                fileoutput.close();
                workbook.close();

                JOptionPane.showMessageDialog(this,"Xuat file pdf thanh cong");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
