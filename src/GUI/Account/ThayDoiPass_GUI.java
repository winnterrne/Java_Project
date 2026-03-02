package GUI.Account;

import BUS.TaiKhoan_BUS;

import javax.swing.*;
import java.awt.*;

public class ThayDoiPass_GUI extends JDialog {
    JLabel lbtitle;
    JTextField tfTenDangNhap;
    JPasswordField tfPassCu, tfPassMoi;
    JPanel panel1, panel2;
    JButton btnLuu, btnHuy;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    public ThayDoiPass_GUI(Frame parent) {
        super(parent, "Thay doi mat khau",true);
        init();
    }
    public void init() {
        setSize(400,600);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        panel1 = new JPanel();
        panel1.setBackground(Color.white);
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.add(Box.createVerticalStrut(80));

        lbtitle = new JLabel("Thay doi mat khau");
        lbtitle.setFont(new Font("Arial",Font.BOLD,36));
        lbtitle.setForeground(new Color(0,102,204));
        panel1.add(lbtitle);
        panel1.add(Box.createVerticalStrut(50));

        tfTenDangNhap = new JTextField();
        panel1.add(creatField("Ten tai khoan", tfTenDangNhap));
        panel1.add(Box.createVerticalStrut(25));

        tfPassCu = new JPasswordField();
        panel1.add(creatField("Mat khau cu",tfPassCu));
        panel1.add(Box.createVerticalStrut(8));

        tfPassMoi = new JPasswordField();
        panel1.add(creatField("Mat khau moi",tfPassMoi));
        panel1.add(Box.createVerticalStrut(8));

        btnLuu = new JButton("Luu");
        btnLuu.setMaximumSize(new Dimension(180,40));
        btnLuu.setFont(new Font("Arial",Font.BOLD,14));
        btnLuu.setForeground(Color.white);
        btnLuu.setBackground(new Color(0,102,204));
        btnLuu.setFocusPainted(false);
        btnLuu.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.add(btnLuu);

        panel1.add(Box.createVerticalStrut(20));



        add(panel1,BorderLayout.CENTER);
        btnLuu.addActionListener(e -> luuTrangThai());
    }
    private JPanel creatField(String text, JComponent com) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.white);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        com.setMaximumSize(new Dimension(320, 38));
        com.setPreferredSize(new Dimension(320, 38));
        com.setMinimumSize(new Dimension(320, 80));
        com.setAlignmentX(Component.CENTER_ALIGNMENT);
        com.setFont(new Font("Arial", Font.PLAIN, 14));

        panel.add(label);
        panel.add(Box.createVerticalStrut(8));
        panel.add(com);
        return panel;
    }
    public void luuTrangThai() {
        String taikhoan = tfTenDangNhap.getText().trim();
        String matkhaucu = tfPassCu.getText().trim();
        String matkhaumoi = tfPassMoi.getText().trim();

        if(taikhoan.isEmpty() || matkhaucu.isEmpty() || matkhaumoi.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Dien day du thong tin");
            return;
        }
        boolean kq = tkbus.updatePassword(taikhoan,matkhaucu,matkhaumoi);
        if(kq) {
            JOptionPane.showMessageDialog(this,"Da doi mat khau");
        }else {
            JOptionPane.showMessageDialog(this,"Khong the thay doi mat khau");
        }
    }

}
