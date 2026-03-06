package GUI.Account;

import BUS.TaiKhoan_BUS;
import DTO.CurrentUser;
import DTO.TaiKhoan_DTO;

import javax.swing.*;
import java.awt.*;

public class ThayDoiPass_GUI extends JDialog {
    JLabel lbtitle;
    JTextField tfTenDangNhap, tfHoVaTen, tfEmail;
    JPasswordField tfPassCu, tfPassMoi;
    JPanel panel1;
    JButton btnLuu;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    public ThayDoiPass_GUI(Frame parent) {
        super(parent, "Thay đổi mật khẩu",true);
        init();
        xemAccount();
    }
    public void init() {
        setSize(550,700);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        panel1 = new JPanel();
        panel1.setBackground(Color.white);
        panel1.setLayout(new BoxLayout(panel1,BoxLayout.Y_AXIS));
        panel1.add(Box.createVerticalStrut(80));

        lbtitle = new JLabel("THAY ĐỔI MẬT KHẨU");
        lbtitle.setFont(new Font("Arial",Font.BOLD,36));
        lbtitle.setForeground(new Color(0,102,204));
        lbtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel1.add(lbtitle);
        panel1.add(Box.createVerticalStrut(50));

        tfHoVaTen = new JTextField();
        panel1.add(creatField("Họ và Tên",tfHoVaTen));
        panel1.add(Box.createVerticalStrut(8));

        tfEmail = new JTextField();
        panel1.add(creatField("Email",tfEmail));
        panel1.add(Box.createVerticalStrut(8));

        tfTenDangNhap = new JTextField();
        panel1.add(creatField("Tên đăng nhập ", tfTenDangNhap));
        panel1.add(Box.createVerticalStrut(8));

        tfPassCu = new JPasswordField();
        panel1.add(creatField("Mật khẩu cũ",tfPassCu));
        panel1.add(Box.createVerticalStrut(8));

        tfPassMoi = new JPasswordField();
        panel1.add(creatField("Mật khẩu mới",tfPassMoi));
        panel1.add(Box.createVerticalStrut(20));


        btnLuu = new JButton("Lưu");
        btnLuu.setMaximumSize(new Dimension(180,40));
        btnLuu.setFont(new Font("Arial",Font.BOLD,14));
        btnLuu.setForeground(Color.white);
        btnLuu.setBackground(new Color(0,102,204));
        btnLuu.setFocusPainted(false);
        btnLuu.setAlignmentX(Component.LEFT_ALIGNMENT);
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
            JOptionPane.showMessageDialog(this,"Điền đầy đủ thông tin");
            return;
        }
        boolean kq = tkbus.updatePassword(taikhoan,matkhaucu,matkhaumoi);
        if(kq) {
            JOptionPane.showMessageDialog(this,"Đã đổi mật khẩu");
        }else {
            JOptionPane.showMessageDialog(this,"Mật khẩu không trùng hoặc tên sai");
        }
    }
    public void xemAccount() {
        TaiKhoan_DTO tkdto = CurrentUser.getInstance().getTaiKhoan();

        tfHoVaTen.setText(tkdto.getHoTen());
        tfEmail.setText(tkdto.getEmail());

        tfHoVaTen.setEnabled(false);
        tfEmail.setEnabled(false);

    }



}
