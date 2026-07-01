package service;

import com.fazecast.jSerialComm.SerialPort;
import Object.KaryawanDAO;
import Object.Karyawan;
import Object.Kehadiran;
import Object.KehadiranDAO;
import Object.MongoManager;
import java.time.LocalDate;
import java.time.LocalTime;

public class RFIDAbsensiService {

    private SerialPort comPort;
    private String mode;

    // ✅ Tambahkan listener agar GUI bisa menerima data scan
    public interface ScanListener {
        void onScanDetected(Karyawan karyawan, Kehadiran absensi);
    }

    private ScanListener listener;

    public void setScanListener(ScanListener listener) {
        this.listener = listener;
    }

    public RFIDAbsensiService(String modeOrPort) {
        this.mode = "HARDWARE";
        comPort = SerialPort.getCommPort(modeOrPort);
        comPort.setBaudRate(9600);

        if (comPort.openPort()) {
            System.out.println("RFID Reader connected on " + comPort.getSystemPortName());
        } else {
            System.out.println("Gagal membuka port " + comPort.getSystemPortName());
        }
    }

    public void startScan() {
        new Thread(() -> {
            try {
                while (true) {
                    if (comPort.bytesAvailable() > 0) {
                        byte[] buffer = new byte[comPort.bytesAvailable()];
                        int numRead = comPort.readBytes(buffer, buffer.length);
                        String uid = new String(buffer, 0, numRead).trim();
                        if (!uid.isEmpty()) {
                            System.out.println("UID terbaca: " + uid);
                            prosesAbsensi(uid);
                        }
                    }
                    Thread.sleep(200);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                comPort.closePort();
            }
        }).start();
    }

    public void prosesAbsensi(String uid) {
        KaryawanDAO karyawanDAO = new KaryawanDAO(MongoManager.getDatabase());
        Karyawan k = karyawanDAO.findByUid(uid);

        if (k != null) {
            KehadiranDAO kehadiranDAO = new KehadiranDAO(MongoManager.getDatabase());
            Kehadiran absensiHariIni = kehadiranDAO.findByUidAndTanggal(uid, LocalDate.now().toString());

            Kehadiran absensi;
            if (absensiHariIni == null) {
                // Belum absen → catat jam masuk
                absensi = new Kehadiran(
                        "AUTO",
                        uid,
                        k.getNamaLengkap(),
                        LocalDate.now().toString(),
                        LocalTime.now().toString(),
                        null,
                        "HADIR",
                        k.getRole()
                );
                kehadiranDAO.save(absensi);
                System.out.println("Jam masuk dicatat untuk: " + k.getNamaLengkap());
            } else if (absensiHariIni.getJamKeluar() == null) {
                // Sudah absen masuk → catat jam keluar
                absensiHariIni.setJamKeluar(LocalTime.now().toString());
                kehadiranDAO.update(absensiHariIni);
                absensi = absensiHariIni;
                System.out.println("Jam keluar dicatat untuk: " + k.getNamaLengkap());
            } else {
                absensi = absensiHariIni;
                System.out.println("Karyawan sudah absen masuk dan keluar hari ini.");
            }

            // ✅ Kirim data ke GUI (AttendancePage)
            if (listener != null) {
                listener.onScanDetected(k, absensi);
            }

        } else {
            System.out.println("UID tidak dikenali di database!");
        }
    }
}
