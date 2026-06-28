/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Lenovo
 */
import com.fazecast.jSerialComm.SerialPort;
import Object.KaryawanDAO;
import Object.Karyawan;
import Object.Kehadiran;
import Object.KehadiranDAO;
import Object.MongoManager;
import java.time.LocalDateTime;

public class RFIDAbsensiService {

    private SerialPort comPort;

    public RFIDAbsensiService(String portName) {
        comPort = SerialPort.getCommPort(portName); // contoh "COM3"
        comPort.setBaudRate(9600);
    }

    public void startScan() {
        if (comPort.openPort()) {
            System.out.println("RFID Reader connected on " + comPort.getSystemPortName());

            new Thread(() -> {
                try {
                    while (true) {
                        if (comPort.bytesAvailable() > 0) {
                            byte[] buffer = new byte[comPort.bytesAvailable()];
                            int numRead = comPort.readBytes(buffer, buffer.length);
                            String uid = new String(buffer).trim();
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

        } else {
            System.out.println("Gagal membuka port " + comPort.getSystemPortName());
        }
    }

    public void prosesAbsensi(String uid) {
        KaryawanDAO karyawanDAO = new KaryawanDAO(MongoManager.getDatabase());
        Karyawan k = karyawanDAO.findByUid(uid);

        if (k != null) {
            KehadiranDAO kehadiranDAO = new KehadiranDAO(MongoManager.getDatabase());
            Kehadiran absensi = new Kehadiran(
                    "AUTO",
                    uid,
                    k.getNamaLengkap(),
                    LocalDateTime.now().toLocalDate().toString(), // tanggal
                    LocalDateTime.now().toLocalTime().toString(), // jamMasuk
                    "HADIR",
                    k.getRole()
            );

            kehadiranDAO.save(absensi);
            System.out.println("Absensi tersimpan untuk: " + k.getNamaLengkap());
        } else {
            System.out.println("UID tidak dikenali di database!");
        }
    }
}
