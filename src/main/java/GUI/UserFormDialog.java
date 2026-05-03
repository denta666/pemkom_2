/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

import javax.swing.*;
import java.awt.*;

public class UserFormDialog extends JDialog {
    private JTextField txtId, txtNama, txtUsername, txtPassword, txtUidRfid;
    private JComboBox<String> cmbRole;
    private JButton btnSave, btnCancel;
    private boolean saved = false;

    public UserFormDialog(JFrame parent) {
        super(parent, "Tambah/Update User", true);
        initComponents();
    }

    private void initComponents() {
        setLayout(new GridLayout(7, 2, 5, 5));

        add(new JLabel("ID:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Nama:"));
        txtNama = new JTextField();
        add(txtNama);

        add(new JLabel("Role:"));
        cmbRole = new JComboBox<>(new String[]{"ADMIN", "MANAGER", "STAFF"});
        add(cmbRole);

        add(new JLabel("Username:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Password:"));
        txtPassword = new JTextField();
        add(txtPassword);

        add(new JLabel("UID RFID:"));
        txtUidRfid = new JTextField();
        add(txtUidRfid);

        btnSave = new JButton("Simpan");
        btnCancel = new JButton("Batal");

        add(btnSave);
        add(btnCancel);

        btnSave.addActionListener(e -> {
            saved = true;
            dispose();
        });

        btnCancel.addActionListener(e -> dispose());

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
