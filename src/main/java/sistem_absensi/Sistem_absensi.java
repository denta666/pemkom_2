package sistem_absensi;

import javax.swing.JFrame;
import GUI.LoginPage;
import Object.MigrasiEnkripsiUid;
import Object.MongoManager;

public class Sistem_absensi {
    public static void main(String[] args) {

        // Jalankan migrasi sekali saja, lalu hapus/comment baris ini setelah selesai
        MigrasiEnkripsiUid.jalankan(MongoManager.getDatabase());

        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setContentPane(new GUI.LoginPage()); // panel login
            frame.pack();
            frame.setLocationRelativeTo(null); // tampil di tengah layar
            frame.setVisible(true);
        });
    }
}