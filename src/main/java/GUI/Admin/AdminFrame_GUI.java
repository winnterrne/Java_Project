package GUI.Admin;
import javax.swing.*;
import java.awt.*;

public class AdminFrame_GUI extends JFrame {

    private AdminContentPanel_GUI contentpanel = new AdminContentPanel_GUI();
    private AdminMenuPanel_GUI menupanel = new AdminMenuPanel_GUI(contentpanel);
    private AdminHeaderPanel_GUI headerpanel = new AdminHeaderPanel_GUI();
    public AdminFrame_GUI() {
        setTitle("Quản lý siêu thị Độ KiKi - Admin");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        leftPanel.add(menupanel);
        add(rightPanel, BorderLayout.CENTER);
        rightPanel.add(headerpanel, BorderLayout.NORTH);
        rightPanel.add(contentpanel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

}