/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

/**
 *
 * @author Lenovo
 */
public class Kehadiran {
    private String id;
    private String nama;
    private String tanggal;
    private String status;

    public Kehadiran(String id, String nama, String tanggal, String status) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.status = status;
    }

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}