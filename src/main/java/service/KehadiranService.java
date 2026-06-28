/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Lenovo
 */
import Object.Kehadiran;
import Object.KehadiranDAO;
import Object.MongoManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class KehadiranService {

    private final KehadiranDAO dao;

    public KehadiranService() {
        dao = new KehadiranDAO(MongoManager.getDatabase());
    }

    public void tampilkanSemuaAbsensi(JPanel panelContainer) {
        panelContainer.removeAll();
        panelContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        List<Kehadiran> list = dao.findAll();

        for (Kehadiran a : list) {
            JPanel card = new JPanel();
            card.setBackground(new Color(255, 204, 153));
            card.setPreferredSize(new Dimension(280, 130));
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

            JLabel lblNama = new JLabel("Nama: " + a.getNama());
            JLabel lblTanggal = new JLabel("Tanggal: " + a.getTanggal());
            JLabel lblStatus = new JLabel("Status: " + a.getStatus());
            JLabel lblRole = new JLabel("Role: " + a.getRole());

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            JButton btnUbah = new JButton("Ubah Status");
            JButton btnHapus = new JButton("Hapus");

            btnUbah.addActionListener(e -> {
                String[] opsiStatus = {"HADIR", "IZIN", "ALPHA"};
                String newStatus = (String) JOptionPane.showInputDialog(panelContainer,
                        "Pilih status baru untuk " + a.getNama() + ":",
                        "Ubah Status",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opsiStatus,
                        a.getStatus());

                if (newStatus != null) {
                    a.setStatus(newStatus);
                    dao.update(new org.bson.Document("id", a.getId()), a);
                    JOptionPane.showMessageDialog(panelContainer,
                            "Status berhasil diubah menjadi " + newStatus + "!");
                    tampilkanSemuaAbsensi(panelContainer); // refresh otomatis
                }
            });

            btnHapus.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(panelContainer,
                        "Yakin ingin menghapus data absensi " + a.getNama() + "?",
                        "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    dao.delete(new org.bson.Document("id", a.getId()));
                    JOptionPane.showMessageDialog(panelContainer, "Data absensi berhasil dihapus!");
                    tampilkanSemuaAbsensi(panelContainer); // refresh otomatis
                }
            });

            panelButton.add(btnUbah);
            panelButton.add(btnHapus);

            card.add(lblNama);
            card.add(lblTanggal);
            card.add(lblStatus);
            card.add(lblRole);
            card.add(panelButton);

            panelContainer.add(card);
        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }
}
