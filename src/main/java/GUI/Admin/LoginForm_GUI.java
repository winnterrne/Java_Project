package GUI.Admin;
import GUI.Account.ForgotPass_GUI;
import javax.swing.*;
import java.awt.*;
import DAO.*;
import DTO.*;
import BUS.*;
import GUI.Product.BanHang_GUI;


public class LoginForm_GUI extends JFrame{
    JLabel lbtitle, lbImage, lbkhoiPhuc, lbnhapEmail;
    JTextField txtaiKhoan, txmatKhau, txnhapEmail;
    JPanel panelForm, panelImage;
    JButton btnLogin, btnSignUp, btnForgotPass, btnguiOTP;
    TaiKhoan_BUS tkbus = new TaiKhoan_BUS();
    public LoginForm_GUI() {
        intGui();
    }
    public void intGui() {
        setTitle("Dang Nhap");
        setSize(1000,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        panelForm = new JPanel();
        panelForm.setBackground(Color.white);
        panelForm.setPreferredSize(new Dimension(500, 0));
        panelForm.setLayout(new BoxLayout(panelForm,BoxLayout.Y_AXIS));
        panelForm.add(Box.createVerticalStrut(80));

        // SIGN IN / LOGIN
        lbtitle = new JLabel("Welcom to my shop");
        lbtitle.setFont(new Font("Arial",Font.BOLD,36));
        lbtitle.setForeground(new Color(0,102,204));
        lbtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelForm.add(lbtitle);
        panelForm.add(Box.createVerticalStrut(50));

        txtaiKhoan = new JTextField();
        panelForm.add(createFiled("Username",txtaiKhoan));
        panelForm.add(Box.createVerticalStrut(25));

        txmatKhau = new JPasswordField();
        panelForm.add(createFiled("Password",txmatKhau));
        panelForm.add(Box.createVerticalStrut(8));

        btnForgotPass = new JButton("Forgot password ?");
        btnForgotPass.setMaximumSize(new Dimension(160, 30));
        btnForgotPass.setForeground(new Color(0,102,204));
        btnForgotPass.setFont(new Font("Arial",Font.PLAIN,13));
        btnForgotPass.setBackground(Color.white);
        btnForgotPass.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelForm.add(btnForgotPass);

        panelForm.add(Box.createVerticalStrut(60));

        btnLogin = new JButton("LOGIN");
        btnLogin.setMaximumSize(new Dimension(180,40));
        btnLogin.setFont(new Font("Arial",Font.BOLD,14));
        btnLogin.setForeground(Color.white);
        btnLogin.setBackground(new Color(0,102,204));
        btnLogin.setFocusPainted(false);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelForm.add(btnLogin);

        panelForm.add(Box.createVerticalStrut(20));

        btnSignUp = new JButton("SIGN IN");
        btnSignUp.setMaximumSize(new Dimension(180,40));
        btnSignUp.setFont(new Font("Arial",Font.BOLD,14));
        btnSignUp.setForeground(Color.white);
        btnSignUp.setBackground(new Color(0,153,80));
        btnSignUp.setFocusPainted(false);
        btnSignUp.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelForm.add(btnSignUp);

        // IMAGE
        panelImage = new JPanel(new BorderLayout());
        panelImage.setBackground(Color.white);

        ImageIcon icon = new ImageIcon(LoginForm_GUI.class.getResource("/Image/z7442838284200_8d1012d310e331c9aff8b0764d4a914b.jpg"));
        lbImage = new JLabel(icon);
        lbImage.setHorizontalAlignment(JLabel.CENTER);
        panelImage.add(lbImage,BorderLayout.CENTER);

        add(panelForm,BorderLayout.WEST);
        add(panelImage,BorderLayout.CENTER);
        btnLogin.addActionListener(e -> login());
        btnForgotPass.addActionListener(e -> guiMaOTP());
        
        setVisible(true);

    }
    private JPanel createFiled(String text,JComponent input) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setBackground(Color.white);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial",Font.PLAIN,14));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        input.setMaximumSize(new Dimension(320,38));
        input.setPreferredSize(new Dimension(320,38));
        input.setMinimumSize(new Dimension(320,80));
        input.setAlignmentX(Component.CENTER_ALIGNMENT);
        input.setFont(new Font("Arial",Font.PLAIN,14));

        panel.add(label);
        panel.add(Box.createVerticalStrut(6));
        panel.add(input);
        return panel;
    }
    public void login() {
        String username = txtaiKhoan.getText();
        String password = new String(((JPasswordField)txmatKhau).getPassword());
        if(username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Vui long dien day du thong tin");
            return;
        }
        TaiKhoan_DTO tk = tkbus.login(username,password);
        if(tk != null) {
            if(password.equals("123456")) {
                JOptionPane.showMessageDialog(this,"Vui long doi mat khau lan dau");
            }
            JOptionPane.showMessageDialog(this,"Chao mung mo kim chi ");
            AdminFrame_GUI admin = new AdminFrame_GUI();
            admin.getMenupanel().applyPermission();
            this.dispose();
        }else {
            JOptionPane.showMessageDialog(this,"Sai ten hoac sai mat khau");
        }
    }
    public void guiMaOTP() {
        new ForgotPass_GUI(this).setVisible(true);
    }



    public static void main(String[] args) {
        new LoginForm_GUI();
    }

}