package GUI.NhapGUI;

import BUS.NhaCungCap_BUS;
import DTO.NhaCungCap_DTO;

import javax.swing.*;
import java.awt.*;

public class ThemNhaCungCapGUI extends JFrame {
        JPanel topPanel, centerPanel, buttonPanel;
        JLabel lbTitle;
        JTextField tfMaNCC, tfTenNCC, tfSDT, tfDiaChi, tfEmail;
        JButton btnThem, btnHuy;
        NhaCungCap_BUS nccBUS = new  NhaCungCap_BUS();
        private NhaCungCapGUI parent;
        public ThemNhaCungCapGUI(NhaCungCapGUI parent) {
            this.parent = parent;
            initGui();
        }

        public void initGui() {
            setLayout(new GridBagLayout());
            setTitle("Thêm nhà cung cấp");
            GridBagConstraints gbc = new GridBagConstraints();

            // Panel tiêu đề
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2; // chiếm 2 cột
            gbc.weightx = 1.0;
            gbc.weighty = 0.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(0, 0, 10, 0);

            topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            lbTitle = new JLabel("THÊM NHÀ CUNG CẤP");
            lbTitle.setFont(new Font("Times New Roman", Font.BOLD, 32));
            topPanel.setBackground(Color.BLUE);
            lbTitle.setForeground(Color.white);
            topPanel.add(lbTitle);
            add(topPanel, gbc);

            // Reset lại cho các label + textfield
            gbc.gridwidth = 1;
            gbc.weightx = 0;
            gbc.weighty = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST; // label bám trái
            gbc.insets = new Insets(5, 30, 5, 10);

            // Mã NCC
            gbc.gridx = 0; gbc.gridy = 1;
            add(createLabel("Mã nhà cung cấp"), gbc);

            gbc.gridx = 1; gbc.gridy = 1;
            tfMaNCC = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL; // textfield giãn ngang
            add(tfMaNCC, gbc);

            // Tên NCC
            gbc.gridx = 0; gbc.gridy = 2;
            gbc.fill = GridBagConstraints.NONE;
            add(createLabel("Tên nhà cung cấp"), gbc);

            gbc.gridx = 1; gbc.gridy = 2;
            tfTenNCC = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(tfTenNCC, gbc);

            // Số điện thoại
            gbc.gridx = 0; gbc.gridy = 3;
            gbc.fill = GridBagConstraints.NONE;
            add(createLabel("Số điện thoại"), gbc);

            gbc.gridx = 1; gbc.gridy = 3;
            tfSDT = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(tfSDT, gbc);

            // Địa chỉ
            gbc.gridx = 0; gbc.gridy = 4;
            gbc.fill = GridBagConstraints.NONE;
            add(createLabel("Địa chỉ"), gbc);

            gbc.gridx = 1; gbc.gridy = 4;
            tfDiaChi = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(tfDiaChi, gbc);

            //Email
            gbc.gridx = 0; gbc.gridy = 5;
            gbc.fill = GridBagConstraints.NONE;
            add(createLabel("Email"), gbc);

            gbc.gridx = 1; gbc.gridy = 5;
            tfEmail = new JTextField(20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            add(tfEmail, gbc);

            // Panel nút bấm
            gbc.gridx = 0; gbc.gridy = 6;
            gbc.gridwidth = 2; // chiếm cả 2 cột
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(0, 0, 15, 0);

            buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            btnThem = new JButton("Thêm");
            btnThem.addActionListener(e -> {
                NhaCungCap_DTO ncc = new NhaCungCap_DTO(tfMaNCC.getText().trim(),
                        tfTenNCC.getText(), tfSDT.getText(), tfDiaChi.getText(), tfEmail.getText());
                String result = nccBUS.addNCC(ncc);
                if(result.equalsIgnoreCase("Success")) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    parent.loadTableNCC();
                    tfMaNCC.setText(""); tfTenNCC.setText(""); tfSDT.setText("");
                    tfDiaChi.setText(""); tfEmail.setText("");
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, result);
                }

            });
            buttonPanel.add(btnThem);
            btnHuy = new JButton("Hủy");
            btnHuy.addActionListener(e -> {
                dispose();
            });
            buttonPanel.add(btnHuy);
            add(buttonPanel, gbc);
            setSize(500, 300);
            setLocationRelativeTo(null);
            setVisible(true);

        }

        public JLabel createLabel(String text) {
            JLabel label = new JLabel(text);
            return label;
        }

}
