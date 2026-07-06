package GUI;

import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {
    private JTextField txtId, txtNama, txtUsername, txtPassword, txtUidRfid;
    private JComboBox<String> cmbRole;
    private JButton btnSave, btnCancel;
    private boolean saved = false;

    public UserFormDialog(JFrame parent) {
        super(parent, "Tambah / Update User", true);
        initComponents();
    }

    private void initComponents() {
        // Panel utama dengan padding
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255)); // biru muda lembut

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);

        // Baris 1: ID
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblId = new JLabel("ID Karyawan:");
        lblId.setFont(labelFont);
        panel.add(lblId, gbc);
        gbc.gridx = 1;
        txtId = new JTextField(15);
        panel.add(txtId, gbc);

        // Baris 2: Nama
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblNama = new JLabel("Nama Lengkap:");
        lblNama.setFont(labelFont);
        panel.add(lblNama, gbc);
        gbc.gridx = 1;
        txtNama = new JTextField(15);
        panel.add(txtNama, gbc);

        // Baris 3: Role
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblRole = new JLabel("Role:");
        lblRole.setFont(labelFont);
        panel.add(lblRole, gbc);
        gbc.gridx = 1;
        cmbRole = new JComboBox<>(new String[]{"ADMIN", "MANAGER", "STAFF"});
        panel.add(cmbRole, gbc);

        // Baris 4: Username
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setFont(labelFont);
        panel.add(lblUsername, gbc);
        gbc.gridx = 1;
        txtUsername = new JTextField(15);
        panel.add(txtUsername, gbc);

        // Baris 5: Password
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setFont(labelFont);
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        txtPassword = new JTextField(15);
        panel.add(txtPassword, gbc);

        // Baris 6: UID RFID
        gbc.gridx = 0; gbc.gridy++;
        JLabel lblUid = new JLabel("UID RFID:");
        lblUid.setFont(labelFont);
        panel.add(lblUid, gbc);
        gbc.gridx = 1;
        txtUidRfid = new JTextField(15);
        panel.add(txtUidRfid, gbc);

        // Baris 7: Tombol
        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        btnSave = new JButton("💾 Simpan");
        btnCancel = new JButton("❌ Batal");

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

    // Getter untuk ambil data setelah disimpan
    public boolean isSaved() { return saved; }
    public String getId() { return txtId.getText(); }
    public String getNama() { return txtNama.getText(); }
    public String getRole() { return cmbRole.getSelectedItem().toString(); }
    public String getUsername() { return txtUsername.getText(); }
    public String getPassword() { return txtPassword.getText(); }
    public String getUidRfid() { return txtUidRfid.getText(); }
}
