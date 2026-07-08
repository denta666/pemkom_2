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
    // Cari berdasarkan nama / id
    // ==========================
    public void tampilkanHasilPencarian(JPanel panelContainer, String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            tampilkanSemuaAbsensi(panelContainer);
            return;
        }

        List<Kehadiran> list = dao.findByKeyword(keyword.trim());

        tampilkanCard(panelContainer, list);
    }

    // ==========================
    // Membuat Card
    // ==========================
    private void tampilkanCard(JPanel panelContainer, List<Kehadiran> list) {

        panelContainer.removeAll();
        panelContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

        if (list.isEmpty()) {
            JLabel kosong = new JLabel("Data tidak ditemukan.");
            kosong.setFont(new Font("Segoe UI", Font.BOLD, 16));
            kosong.setForeground(Color.RED);

            panelContainer.add(kosong);

            panelContainer.revalidate();
            panelContainer.repaint();
            return;
        }

        for (Kehadiran a : list) {

            JPanel card = new JPanel();
            card.setPreferredSize(new Dimension(300,170));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createLineBorder(new Color(220,220,220)));
            card.setLayout(new BorderLayout());

            JPanel info = new JPanel();
            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBorder(BorderFactory.createEmptyBorder(10,10,5,10));

            JLabel lblNama = new JLabel(a.getNama());
            lblNama.setFont(new Font("Segoe UI", Font.BOLD,18));

            JLabel lblTanggal = new JLabel("Tanggal : " + a.getTanggal());
            JLabel lblRole = new JLabel("Role : " + a.getRole());
            JLabel lblStatus = new JLabel("Status : " + a.getStatus());

            info.add(lblNama);
            info.add(Box.createVerticalStrut(5));
            info.add(lblTanggal);
            info.add(lblRole);
            info.add(lblStatus);

            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
            panelButton.setOpaque(false);

            JButton btnUbah = new JButton("Ubah");
            JButton btnHapus = new JButton("Hapus");

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

                    dao.update(new Document("id",a.getId()),a);

                    JOptionPane.showMessageDialog(panelContainer,
                            "Status berhasil diubah.");

                    tampilkanSemuaAbsensi(panelContainer);
                }

            });

            btnHapus.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        panelContainer,
                        "Hapus data absensi " + a.getNama() + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);

                if(confirm == JOptionPane.YES_OPTION){

                    dao.delete(new Document("id",a.getId()));

                    JOptionPane.showMessageDialog(panelContainer,
                            "Data berhasil dihapus.");

                    tampilkanSemuaAbsensi(panelContainer);
                }

            });

            panelButton.add(btnUbah);
            panelButton.add(btnHapus);

            card.add(info,BorderLayout.CENTER);
            card.add(panelButton,BorderLayout.SOUTH);

            panelContainer.add(card);
        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }

}