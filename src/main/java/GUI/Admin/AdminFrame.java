package GUI.Admin;
import Utils.main;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    /**
	 * 
	 */

	public AdminFrame() {
        setTitle("Quản lý siêu thị Độ KiKi - Admin");
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        leftPanel.add(new GUI.Admin.AdminMenuPanel());
        add(rightPanel, BorderLayout.CENTER);
        rightPanel.add(new GUI.Admin.AdminHeaderPanel(), BorderLayout.NORTH);
        rightPanel.add(new GUI.Admin.AdminContentPanel(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    public static void main(String[] args) {
        new AdminFrame();
    }
}
