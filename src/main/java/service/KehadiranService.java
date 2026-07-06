package service;

import Object.Kehadiran;
import Object.KehadiranDAO;
import Object.MongoManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.bson.Document;
import utility.WrapLayout;

public class KehadiranService {

    private final KehadiranDAO dao;

    public KehadiranService() {
        dao = new KehadiranDAO(MongoManager.getDatabase());
    }

    // ==========================
    // Tampilkan semua absensi
    // ==========================
    public void tampilkanSemuaAbsensi(JPanel panelContainer) {
        List<Kehadiran> list = dao.findAll();
        tampilkanCard(panelContainer, list);
    }

    // ==========================
    // Tampilkan hasil pencarian
    // ==========================
    public void tampilkanHasilPencarian(JPanel panelContainer, String keyword) {

        List<Kehadiran> list = dao.findByKeyword(keyword);

        if (list.isEmpty()) {
            panelContainer.removeAll();
            panelContainer.add(new JLabel("Data tidak ditemukan."));
            panelContainer.revalidate();
            panelContainer.repaint();
            return;
        }

        tampilkanCard(panelContainer, list);
    }

    // ==========================
    // Membuat Card
    // ==========================
    private void tampilkanCard(JPanel panelContainer, List<Kehadiran> list) {

        panelContainer.removeAll();
        panelContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        for (Kehadiran a : list) {

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(300, 170));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            card.setLayout(new BorderLayout());

            // ==========================
            // Panel Informasi
            // ==========================
            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));

            JLabel lblNama = new JLabel(a.getNama());
            lblNama.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JLabel lblTanggal = new JLabel("Tanggal : " + a.getTanggal());
            lblTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            JLabel lblRole = new JLabel("Role : " + a.getRole());
            lblRole.setFont(new Font("Segoe UI", Font.PLAIN, 13));

            JLabel lblStatus = new JLabel("Status : " + a.getStatus());
            lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));

            switch (a.getStatus().toUpperCase()) {
                case "HADIR":
                    lblStatus.setForeground(new Color(25,135,84)); // hijau
                    break;

                case "IZIN":
                    lblStatus.setForeground(new Color(255,193,7)); // kuning
                    break;

                case "ALPHA":
                    lblStatus.setForeground(Color.RED);
                    break;

                default:
                    lblStatus.setForeground(Color.BLACK);
            }

            info.add(lblNama);
            info.add(Box.createVerticalStrut(5));
            info.add(lblTanggal);
            info.add(lblRole);
            info.add(lblStatus);

            // ==========================
            // Tombol
            // ==========================
            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
            panelButton.setOpaque(false);

            JButton btnUbah = new JButton("Ubah");
            JButton btnHapus = new JButton("Hapus");

            Dimension ukuran = new Dimension(100,30);
            btnUbah.setPreferredSize(ukuran);
            btnHapus.setPreferredSize(ukuran);

            // ==========================
            // Ubah Status
            // ==========================
            btnUbah.addActionListener(e -> {

                String[] opsi = {"HADIR","IZIN","ALPHA"};

                String statusBaru = (String) JOptionPane.showInputDialog(
                        panelContainer,
                        "Pilih Status",
                        "Ubah Status",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        opsi,
                        a.getStatus());

                if(statusBaru != null){

                    a.setStatus(statusBaru);

                    dao.update(
                            new Document("id", a.getId()),
                            a);

                    JOptionPane.showMessageDialog(panelContainer,
                            "Status berhasil diubah.");

                    tampilkanSemuaAbsensi(panelContainer);
                }

            });

            // ==========================
            // Hapus
            // ==========================
            btnHapus.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        panelContainer,
                        "Hapus data absensi " + a.getNama() + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){

                    dao.delete(new Document("id", a.getId()));

                    JOptionPane.showMessageDialog(panelContainer,
                            "Data berhasil dihapus.");

                    tampilkanSemuaAbsensi(panelContainer);
                }

            });

            panelButton.add(btnUbah);
            panelButton.add(btnHapus);

            card.add(info, BorderLayout.CENTER);
            card.add(panelButton, BorderLayout.SOUTH);

            panelContainer.add(card);

        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }

}