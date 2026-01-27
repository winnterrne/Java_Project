package GUI.Admin;
import javax.swing.*;
import java.awt.*;

public class AdminHeaderPanel extends JPanel {
    public AdminHeaderPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(0, 80));
        setBackground(Color.WHITE);

        int fontHeader = 0x235EF8;
        JLabel lTitle = new JLabel("Quản lý siêu thị Độ KiKi");
        lTitle.setForeground(new Color(fontHeader));
        lTitle.setFont(new Font("Tahoma", Font.BOLD, 28));
        lTitle.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        JPanel pAccount = new JPanel();
        pAccount.setLayout(new BoxLayout(pAccount, BoxLayout.X_AXIS));
        pAccount.setMaximumSize(new Dimension(120, 40));
        pAccount.setBackground(Color.WHITE);
        pAccount.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY),
                        BorderFactory.createEmptyBorder(10, 12, 10, 20)
                )
        );


        ImageIcon adminIcon = new ImageIcon(getClass().getResource("/GUI/Image/admin-icon.png"));
        Image adminImg = adminIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon adminScaled = new ImageIcon(adminImg);
        JButton btnAccount = new JButton(adminScaled);
        btnAccount.setForeground(new Color(fontHeader));
        btnAccount.setBorderPainted(false);
        btnAccount.setContentAreaFilled(false);
        btnAccount.setFocusPainted(false);

        JLabel lAccount = new JLabel("Admin");
        lAccount.setForeground(new Color(fontHeader));
        lAccount.setFont(new Font("Segoe UI", Font.BOLD, 14));

        pAccount.add(btnAccount);
        pAccount.add(Box.createHorizontalStrut(6));
        pAccount.add(lAccount);

        add(lTitle);
        add(Box.createHorizontalGlue());
        add(pAccount);

    }
}
