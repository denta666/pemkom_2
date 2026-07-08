package service;

import Object.Karyawan;
import Object.KaryawanDAO;
import Object.MongoManager;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import org.bson.Document;
import utility.WrapLayout;
import utility.SecurityUtility;

public class KaryawanService {

    private final KaryawanDAO dao;

    public KaryawanService() {
        dao = new KaryawanDAO(MongoManager.getDatabase());
    }

    public void tampilkanSemuaKaryawan(JPanel panelContainer) {

        panelContainer.removeAll();
        panelContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));

        List<Karyawan> list = dao.findAll();

        for (Karyawan k : list) {

            // ================= CARD ===================
            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setPreferredSize(new Dimension(310, 190));
            card.setBackground(Color.WHITE);

            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // ================= HEADER =================
            JLabel lblNama = new JLabel(k.getNamaLengkap());
            lblNama.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JLabel lblRole = new JLabel(k.getRole());
            lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));

            switch (k.getRole().toUpperCase()) {
                case "ADMIN":
                    lblRole.setForeground(new Color(220, 53, 69));
                    break;

                case "MANAGER":
                    lblRole.setForeground(new Color(13, 110, 253));
                    break;

                default:
                    lblRole.setForeground(new Color(25, 135, 84));
                    break;
            }

            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);

            header.add(lblNama, BorderLayout.WEST);
            header.add(lblRole, BorderLayout.EAST);

            // ================= BODY =================
            JLabel lblUser = new JLabel("Username : " + k.getUsername());
            lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            JLabel lblPass = new JLabel("Password : ********");
            lblPass.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            String uidTampil;
            try {
                uidTampil = SecurityUtility.decrypt(k.getUidRfid());
            } catch (Exception e) {
                uidTampil = k.getUidRfid();
            }

            JLabel lblUid = new JLabel("UID RFID : " + (uidTampil != null ? uidTampil : "-"));
            lblUid.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            JPanel body = new JPanel();
            body.setOpaque(false);
            body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

            body.add(Box.createVerticalStrut(8));
            body.add(lblUser);
            body.add(Box.createVerticalStrut(5));
            body.add(lblPass);
            body.add(Box.createVerticalStrut(5));
            body.add(lblUid);

            // ================= BUTTON =================
            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            panelButton.setOpaque(false);

            JButton btnDetail = new JButton("Detail");
            JButton btnUpdate = new JButton("Update");
            JButton btnDelete = new JButton("Delete");

            Dimension size = new Dimension(85, 32);

            btnDetail.setPreferredSize(size);
            btnUpdate.setPreferredSize(size);
            btnDelete.setPreferredSize(size);

            btnDetail.setFocusPainted(false);
            btnUpdate.setFocusPainted(false);
            btnDelete.setFocusPainted(false);

            // ================= DETAIL =================
            btnDetail.addActionListener(e -> {

                String uidDetail;
                try {
                    uidDetail = SecurityUtility.decrypt(k.getUidRfid());
                } catch (Exception ex) {
                    uidDetail = k.getUidRfid();
                }

                JOptionPane.showMessageDialog(
                        panelContainer,
                        "ID : " + k.getIdKaryawan()
                        + "\nNama : " + k.getNamaLengkap()
                        + "\nRole : " + k.getRole()
                        + "\nUsername : " + k.getUsername()
                        + "\nUID RFID : " + (uidDetail != null ? uidDetail : "-"),
                        "Detail Karyawan",
                        JOptionPane.INFORMATION_MESSAGE
                );

            });

            // ================= UPDATE =================
            btnUpdate.addActionListener(e -> {

                String newUsername = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Username",
                        k.getUsername());

                String newPassword = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Password",
                        k.getPassword());

                String newRole = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Role",
                        k.getRole());

                if (newUsername != null
                        && newPassword != null
                        && newRole != null) {

                    k.setUsername(newUsername);
                    k.setPassword(newPassword);
                    k.setRole(newRole);

                    dao.update(
                            new Document("idKaryawan", k.getIdKaryawan()),
                            k
                    );

                    JOptionPane.showMessageDialog(
                            panelContainer,
                            "Data berhasil diperbarui!"
                    );

                    tampilkanSemuaKaryawan(panelContainer);
                }

            });

            // ================= DELETE =================
            btnDelete.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        panelContainer,
                        "Yakin ingin menghapus "
                        + k.getNamaLengkap() + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    dao.delete(
                            new Document("idKaryawan",
                                    k.getIdKaryawan()));

                    JOptionPane.showMessageDialog(
                            panelContainer,
                            "User berhasil dihapus!");

                    tampilkanSemuaKaryawan(panelContainer);
                }

            });

            panelButton.add(btnDetail);
            panelButton.add(btnUpdate);
            panelButton.add(btnDelete);

            // ================= MASUKKAN KE CARD =================
            card.add(header, BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            card.add(panelButton, BorderLayout.SOUTH);

            panelContainer.add(card);

        }

        panelContainer.revalidate();
        panelContainer.repaint();

    }

    private void tampilkanCard(JPanel panelContainer, List<Karyawan> list) {

        panelContainer.removeAll();
        panelContainer.setLayout(new WrapLayout(FlowLayout.LEFT, 15, 15));

        if (list.isEmpty()) {
            JLabel lblKosong = new JLabel("Data tidak ditemukan.");
            lblKosong.setFont(new Font("Segoe UI", Font.BOLD, 14));
            lblKosong.setForeground(Color.RED);
            panelContainer.add(lblKosong);

            panelContainer.revalidate();
            panelContainer.repaint();
            return;
        }

        for (Karyawan k : list) {

            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setPreferredSize(new Dimension(310, 190));
            card.setBackground(Color.WHITE);

            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            // HEADER
            JLabel lblNama = new JLabel(k.getNamaLengkap());
            lblNama.setFont(new Font("Segoe UI", Font.BOLD, 18));

            JLabel lblRole = new JLabel(k.getRole());
            lblRole.setFont(new Font("Segoe UI", Font.BOLD, 12));

            switch (k.getRole().toUpperCase()) {
                case "ADMIN":
                    lblRole.setForeground(new Color(220, 53, 69));
                    break;
                case "MANAGER":
                    lblRole.setForeground(new Color(13, 110, 253));
                    break;
                default:
                    lblRole.setForeground(new Color(25, 135, 84));
                    break;
            }

            JPanel header = new JPanel(new BorderLayout());
            header.setOpaque(false);
            header.add(lblNama, BorderLayout.WEST);
            header.add(lblRole, BorderLayout.EAST);

            // BODY
            JLabel lblUser = new JLabel("Username : " + k.getUsername());
            JLabel lblPass = new JLabel("Password : ********");

            String uidTampil;
            try {
                uidTampil = SecurityUtility.decrypt(k.getUidRfid());
            } catch (Exception e) {
                uidTampil = k.getUidRfid();
            }
            JLabel lblUid = new JLabel("UID RFID : " + (uidTampil != null ? uidTampil : "-"));

            JPanel body = new JPanel();
            body.setOpaque(false);
            body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
            body.add(lblUser);
            body.add(Box.createVerticalStrut(5));
            body.add(lblPass);
            body.add(Box.createVerticalStrut(5));
            body.add(lblUid);

            // BUTTON
            JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
            panelButton.setOpaque(false);

            JButton btnDetail = new JButton("Detail");
            JButton btnUpdate = new JButton("Update");
            JButton btnDelete = new JButton("Delete");

            panelButton.add(btnDetail);
            panelButton.add(btnUpdate);
            panelButton.add(btnDelete);

            // DETAIL
            btnDetail.addActionListener(e -> {

                String uidDetail;
                try {
                    uidDetail = SecurityUtility.decrypt(k.getUidRfid());
                } catch (Exception ex) {
                    uidDetail = k.getUidRfid();
                }

                JOptionPane.showMessageDialog(panelContainer,
                        "ID : " + k.getIdKaryawan()
                        + "\nNama : " + k.getNamaLengkap()
                        + "\nRole : " + k.getRole()
                        + "\nUsername : " + k.getUsername()
                        + "\nUID RFID : " + (uidDetail != null ? uidDetail : "-"),
                        "Detail Karyawan",
                        JOptionPane.INFORMATION_MESSAGE);
            });

            // UPDATE
            btnUpdate.addActionListener(e -> {

                String newUsername = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Username",
                        k.getUsername());

                String newPassword = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Password",
                        k.getPassword());

                String newRole = JOptionPane.showInputDialog(
                        panelContainer,
                        "Masukkan Role",
                        k.getRole());

                if (newUsername != null && newPassword != null && newRole != null) {

                    k.setUsername(newUsername);
                    k.setPassword(newPassword);
                    k.setRole(newRole);

                    dao.update(new Document("idKaryawan", k.getIdKaryawan()), k);

                    JOptionPane.showMessageDialog(panelContainer,
                            "Data berhasil diperbarui!");

                    tampilkanSemuaKaryawan(panelContainer);
                }
            });

            // DELETE
            btnDelete.addActionListener(e -> {

                int confirm = JOptionPane.showConfirmDialog(
                        panelContainer,
                        "Yakin ingin menghapus " + k.getNamaLengkap() + "?",
                        "Konfirmasi",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    dao.delete(new Document("idKaryawan", k.getIdKaryawan()));

                    JOptionPane.showMessageDialog(panelContainer,
                            "Data berhasil dihapus!");

                    tampilkanSemuaKaryawan(panelContainer);
                }
            });

            card.add(header, BorderLayout.NORTH);
            card.add(body, BorderLayout.CENTER);
            card.add(panelButton, BorderLayout.SOUTH);

            panelContainer.add(card);
        }

        panelContainer.revalidate();
        panelContainer.repaint();
    }

    public void tampilkanHasilPencarian(JPanel panelContainer, String keyword) {
        List<Karyawan> list = dao.findByKeyword(keyword);
        tampilkanCard(panelContainer, list);
    }

}