package Object;

public class Kehadiran {

    private String id;
    private String uidRfid;
    private String nama;
    private String tanggal;
    private String jamMasuk;
    private String jamKeluar;
    private String status;
    private String role;
    private String password;

    // Constructor lengkap
    public Kehadiran(String id, String uidRfid, String nama, String tanggal,
                     String jamMasuk, String jamKeluar, String status, String role) {
        this.id = id;
        this.uidRfid = uidRfid;
        this.nama = nama;
        this.tanggal = tanggal;
        this.jamMasuk = jamMasuk;
        this.jamKeluar = jamKeluar;
        this.status = status;
        this.role = role;
    }

    // Constructor kosong
    public Kehadiran() {}

    // Getter & Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUidRfid() { return uidRfid; }
    public void setUidRfid(String uidRfid) { this.uidRfid = uidRfid; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getTanggal() { return tanggal; }
    public void setTanggal(String tanggal) { this.tanggal = tanggal; }

    public String getJamMasuk() { return jamMasuk; }
    public void setJamMasuk(String jamMasuk) { this.jamMasuk = jamMasuk; }

    public String getJamKeluar() { return jamKeluar; } // ✅ getter baru
    public void setJamKeluar(String jamKeluar) { this.jamKeluar = jamKeluar; } // ✅ setter baru

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
