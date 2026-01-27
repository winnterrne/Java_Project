package GUI.Admin;
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
        leftPanel.add(new AdminMenuPanel());
        add(rightPanel, BorderLayout.CENTER);
        rightPanel.add(new AdminHeaderPanel(), BorderLayout.NORTH);
        rightPanel.add(new AdminContetnPanel(), BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
}
