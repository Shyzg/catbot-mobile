package com.programmingtechniques.uas.Users;

public class RegisterHelper {
    String NamaPengguna, Surel, KataSandi, NomorHandphone;

    public RegisterHelper() {
    }

    public RegisterHelper(String namaPengguna, String surel, String kataSandi, String nomorHandphone) {
        NamaPengguna = namaPengguna;
        Surel = surel;
        KataSandi = kataSandi;
        NomorHandphone = nomorHandphone;
    }

    public String getNamaPengguna() {
        return NamaPengguna;
    }

    public void setNamaPengguna(String namaPengguna) {
        NamaPengguna = namaPengguna;
    }

    public String getSurel() {
        return Surel;
    }

    public void setSurel(String surel) {
        Surel = surel;
    }

    public String getKataSandi() {
        return KataSandi;
    }

    public void setKataSandi(String kataSandi) {
        KataSandi = kataSandi;
    }

    public String getNomorHandphone() {
        return NomorHandphone;
    }

    public void setNomorHandphone(String nomorHandphone) {
        NomorHandphone = nomorHandphone;
    }
}

