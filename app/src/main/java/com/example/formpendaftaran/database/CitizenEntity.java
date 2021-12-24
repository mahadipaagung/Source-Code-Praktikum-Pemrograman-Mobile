package com.example.formpendaftaran.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CitizenEntity {
    @PrimaryKey
    public int idCitizen;

    @ColumnInfo(name = "Nama")
    public String fullname;

    @ColumnInfo(name = "JenisKelamin")
    public String jeniskelamin;

    @ColumnInfo(name = "Alamat")
    public String alamat;

    @ColumnInfo(name = "Iuran")
    public String iuran;

    @ColumnInfo(name = "AnggotaKK")
    public int anggota;
}
