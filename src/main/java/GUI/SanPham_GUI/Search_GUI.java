package GUI.SanPham_GUI;

import javax.swing.*;
import java.awt.*;

public class Search_GUI extends JPanel {
    private JTextField txtSearch;
    private JButton btnSearch;

    public Search_GUI() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

        txtSearch = new JTextField(30);
        txtSearch.setText("Nhập tên hoặc mã sản phẩm...");
        btnSearch = new JButton("Tìm");

        add(new JLabel("Tìm kiếm: "));
        add(txtSearch);
        add(btnSearch);
    }

    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
}