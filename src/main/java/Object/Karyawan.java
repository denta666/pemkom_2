/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

public class Karyawan {

    private String uidRfid;
    private String idKaryawan;
    private String namaLengkap;
    private String role;      // "KARYAWAN" atau "MANAGER" atau "ADMIN"
    private String username;  // untuk login
    private String password;  // untuk login

    public Karyawan() {
    }

    // Constructor untuk CRUD biasa
    public Karyawan(String uidRfid, String idKaryawan, String namaLengkap, String role) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.role = role;
    }

    // Constructor untuk login (lengkap dengan username & password)
    public Karyawan(String uidRfid, String idKaryawan, String namaLengkap,
            String role, String username, String password) {
        this.uidRfid = uidRfid;
        this.idKaryawan = idKaryawan;
        this.namaLengkap = namaLengkap;
        this.role = role;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Karyawan{"
                + "uidRfid=" + uidRfid
                + ", idKaryawan=" + idKaryawan
                + ", namaLengkap=" + namaLengkap
                + ", role=" + role
                + ", username=" + username
                + '}';
    }

    // Getter & Setter
    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getIdKaryawan() {
        return idKaryawan;
    }

    public void setIdKaryawan(String idKaryawan) {
        this.idKaryawan = idKaryawan;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
