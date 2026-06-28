/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import Object.Karyawan;

public class SessionManager {
    // Menyimpan user yang sedang login
    private static Karyawan currentUser;

    // Set user saat login berhasil
    public static void setCurrentUser(Karyawan user) {
        currentUser = user;
    }

    // Ambil user yang sedang login
    public static Karyawan getCurrentUser() {
        return currentUser;
    }

    // Hapus session (misalnya saat logout)
    public static void clearSession() {
        currentUser = null;
    }

    // Cek apakah ada user yang sedang login
    public static boolean isLoggedIn() {
        return currentUser != null;
    }
}

