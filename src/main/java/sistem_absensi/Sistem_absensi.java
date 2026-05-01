/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sistem_absensi;


/**
 *
 * @author Lenovo
 */

import javax.swing.JFrame;
import GUI.LoginPage;

public class Sistem_absensi {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new GUI.LoginPage()); // panel login
            frame.pack();
            frame.setLocationRelativeTo(null); // tampil di tengah layar
            frame.setVisible(true);
        });
    }
}