package utility;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FrameUtils {

    /**
     * Ganti isi frame ke halaman baru, sekaligus pastikan frame full screen (maximize).
     * @param sourceComponent komponen asal (biasanya "this" dari panel yang lagi aktif)
     * @param newPage panel tujuan yang mau ditampilkan
     */
    public static void switchPage(java.awt.Component sourceComponent, JPanel newPage) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sourceComponent);

        frame.setContentPane(newPage);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // pastikan tetap full setiap pindah halaman
        frame.revalidate();
        frame.repaint();
    }
}