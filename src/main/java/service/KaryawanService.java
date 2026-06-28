/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Object.Karyawan;
import Object.KaryawanDAO;
import Object.MongoManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.bson.Document;

public class KaryawanService {

    private final KaryawanDAO dao;

    public KaryawanService() {
        dao = new KaryawanDAO(MongoManager.getDatabase());
    }

    public void tampilkanSemuaKaryawan(JPanel panelContainer) {
        panelContainer.removeAll();
        panelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        List<Karyawan> list = dao.findAll();

        for (Karyawan k : list) {
            JPanel card = new JPanel();
            card.setBackground(new Color(255, 204, 153));
            card.setPreferredSize(new Dimension(280, 130));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel lblNama = new JLabel("Nama: " + k.getNamaLengkap());
            JLabel lblRole = new JLabel("Role: " + k.getRole());
            JLabel lblUser = new JLabel("Username: " + k.getUsername());
            JLabel lblPass = new JLabel("Password: " + k.getPassword());

            // Panel tombol di bawah
            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

            JButton btnDetail = new JButton("Detail");
            JButton btnUpdate = new JButton("Update");
            JButton btnDelete = new JButton("Delete");

            // Aksi tombol Detail
            btnDetail.addActionListener(e -> {
                JOptionPane.showMessageDialog(null,
                        "ID: " + k.getIdKaryawan() + "\n"
                        + "Nama: " + k.getNamaLengkap() + "\n"
                        + "Role: " + k.getRole() + "\n"
                        + "Username: " + k.getUsername() + "\n"
                        + "Password : " + k.getPassword(),
                        "Detail Karyawan", JOptionPane.INFORMATION_MESSAGE);
            });

            // 🔹 Update
            btnUpdate.addActionListener(e -> {
                String newUsername = JOptionPane.showInputDialog(panelContainer,
                        "Masukkan username baru:", k.getUsername());
                String newPassword = JOptionPane.showInputDialog(panelContainer,
                        "Masukkan password baru:", k.getPassword());
                String newRole = JOptionPane.showInputDialog(panelContainer,
                        "Masukkan role baru:", k.getRole());

                if (newUsername != null && newPassword != null && newRole != null) {
                    k.setUsername(newUsername);
                    k.setPassword(newPassword);
                    k.setRole(newRole);
                    dao.update(new Document("idKaryawan", k.getIdKaryawan()), k);
                    JOptionPane.showMessageDialog(panelContainer, "Data berhasil diperbarui!");
                    tampilkanSemuaKaryawan(panelContainer); // refresh otomatis
                }
            });

            // 🔹 Delete
            btnDelete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panelContainer,
                        "Yakin ingin menghapus user " + k.getNamaLengkap() + "?",
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(new Document("idKaryawan", k.getIdKaryawan()));
                    JOptionPane.showMessageDialog(panelContainer, "User berhasil dihapus!");
                    tampilkanSemuaKaryawan(panelContainer); // refresh otomatis
                }
            });

            panelButton.add(btnDetail);
            panelButton.add(btnUpdate);
            panelButton.add(btnDelete);

            card.add(lblNama);
            card.add(lblRole);
            card.add(lblUser);
            card.add(lblPass);
            card.add(panelButton);

            panelContainer.add(card);
        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }
}
