package com.example.formpendaftaran.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CitizenDao {
    @Query("SELECT * FROM CitizenEntity")
    List<CitizenEntity> getAll();

    @Query("INSERT INTO CitizenEntity (Nama,JenisKelamin,Alamat,Iuran,AnggotaKK) VALUES(:Nama,:JenisKelamin,:Alamat,:Iuran,:AnggotaKK)")
    void insertAll(String Nama, String JenisKelamin, String Alamat, String Iuran, Integer AnggotaKK);

    //edit
    @Query("UPDATE CitizenEntity SET Nama=:Nama, JenisKelamin=:JenisKelamin, Alamat=:Alamat WHERE idCitizen=:idCitizen")
    void update(int idCitizen, String Nama, String JenisKelamin, String Alamat);

    @Query("SELECT * FROM CitizenEntity WHERE idCitizen=:idCitizen")
    CitizenEntity get(int idCitizen);

    @Delete
    void delete(CitizenEntity CitizenEntity);

}
