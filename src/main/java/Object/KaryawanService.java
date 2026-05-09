/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import java.awt.*;
import java.util.List;
import javax.swing.*;

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
                JOptionPane.showMessageDialog(panelContainer,
                        "ID: " + k.getIdKaryawan() + "\n"
                        + "Nama: " + k.getNamaLengkap() + "\n"
                        + "Role: " + k.getRole() + "\n"
                        + "Username: " + k.getUsername(),
                        "Detail Karyawan", JOptionPane.INFORMATION_MESSAGE);
            });

            // Aksi tombol Update
            btnUpdate.addActionListener(e -> {
                JOptionPane.showMessageDialog(panelContainer,
                        "Fitur update user untuk " + k.getNamaLengkap() + " belum diimplementasikan.",
                        "Update User", JOptionPane.INFORMATION_MESSAGE);
                // Di sini nanti bisa kamu sambungkan ke form edit user
            });

            // Aksi tombol Delete
            btnDelete.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panelContainer,
                        "Yakin ingin menghapus user " + k.getNamaLengkap() + "?",
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(new org.bson.Document("idKaryawan", k.getIdKaryawan()));
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
