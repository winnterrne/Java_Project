package GUI.Admin;
import javax.swing.*;
import java.awt.*;

public class AdminFrame_GUI extends JFrame {
    public AdminFrame_GUI() {
        setTitle("Quản lý siêu thị Độ KiKi - Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        leftPanel.add(new AdminMenuPanel_GUI());
        add(rightPanel, BorderLayout.CENTER);
        rightPanel.add(new AdminHeaderPanel_GUI(), BorderLayout.NORTH);
        rightPanel.add(new AdminContetnPanel_GUI(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new AdminFrame_GUI();
    }

}