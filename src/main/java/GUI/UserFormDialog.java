package GUI;

import utility.LanguageManager;
import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {

    private JTextField txtId, txtNama, txtUsername, txtPassword, txtUidRfid;
    private JComboBox<String> cmbRole;
    private JButton btnSave, btnCancel;

    private JLabel lblId, lblNama, lblRole, lblUsername, lblPassword, lblUid;

    private boolean saved = false;

    public UserFormDialog(JFrame parent) {
        super(parent, "", true);
        initComponents();
        reloadTexts();
    }

    private void initComponents() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        // ==========================
        // ID
        // ==========================
        gbc.gridx = 0;
        gbc.gridy = 0;

        lblId = new JLabel();
        lblId.setFont(labelFont);
        panel.add(lblId, gbc);

        gbc.gridx = 1;

        txtId = new JTextField(15);
        panel.add(txtId, gbc);

        // ==========================
        // Nama
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;

        lblNama = new JLabel();
        lblNama.setFont(labelFont);
        panel.add(lblNama, gbc);

        gbc.gridx = 1;

        txtNama = new JTextField(15);
        panel.add(txtNama, gbc);

        // ==========================
        // Role
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;

        lblRole = new JLabel();
        lblRole.setFont(labelFont);
        panel.add(lblRole, gbc);

        gbc.gridx = 1;

        cmbRole = new JComboBox<>(new String[]{
            "ADMIN",
            "MANAGER",
            "STAFF"
        });

        panel.add(cmbRole, gbc);

        // ==========================
        // Username
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;

        lblUsername = new JLabel();
        lblUsername.setFont(labelFont);
        panel.add(lblUsername, gbc);

        gbc.gridx = 1;

        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // ==========================
        // Password
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;

        lblPassword = new JLabel();
        lblPassword.setFont(labelFont);
        panel.add(lblPassword, gbc);

        gbc.gridx = 1;

        txtPassword = new JTextField(15);
        panel.add(txtPassword, gbc);

        // ==========================
        // UID RFID
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;

        lblUid = new JLabel();
        lblUid.setFont(labelFont);
        panel.add(lblUid, gbc);

        gbc.gridx = 1;

        txtUidRfid = new JTextField(15);
        panel.add(txtUidRfid, gbc);

        // ==========================
        // Button
        // ==========================
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        btnSave = new JButton();
        btnCancel = new JButton();

        btnSave.setBackground(new Color(100, 149, 237));
        btnSave.setForeground(Color.WHITE);

        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(panel.getBackground());

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        panel.add(buttonPanel, gbc);

        btnSave.addActionListener(e -> {
            saved = true;
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

        add(panel);

        pack();
        setLocationRelativeTo(getParent());
    }

    // ==========================
    // Multi Bahasa
    // ==========================
    private void reloadTexts() {

        setTitle(LanguageManager.get("user.title"));

        lblId.setText(LanguageManager.get("user.employeeId"));
        lblNama.setText(LanguageManager.get("user.fullName"));
        lblRole.setText(LanguageManager.get("user.role"));
        lblUsername.setText(LanguageManager.get("user.username"));
        lblPassword.setText(LanguageManager.get("user.password"));
        lblUid.setText(LanguageManager.get("user.uid"));

        btnSave.setText("💾 " + LanguageManager.get("button.save"));
        btnCancel.setText("❌ " + LanguageManager.get("button.cancel"));
    }

    // ==========================
    // Getter
    // ==========================
    public boolean isSaved() {
        return saved;
    }

    public String getId() {
        return txtId.getText();
    }

    public String getNama() {
        return txtNama.getText();
    }

    public String getRole() {
        return cmbRole.getSelectedItem().toString();
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return txtPassword.getText();
    }

    public String getUidRfid() {
        return txtUidRfid.getText();
    }
}
