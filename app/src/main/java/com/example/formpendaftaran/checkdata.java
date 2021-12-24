package com.example.formpendaftaran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class checkdata extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_registrasi);

        TextView namakk = findViewById(R.id.shownama);
        TextView jenkel = findViewById(R.id.showjk);
        TextView anggotakk = findViewById(R.id.showangkel);
        TextView jenisiuran = findViewById(R.id.showkategori);
        TextView alamat = findViewById(R.id.showalamat);
        Button backbutton = findViewById(R.id.btn_kembali);
//        Button exitbutton = findViewById(R.id.exitbtn);

        Intent getiuran = getIntent();
        String nama = getiuran.getStringExtra("nama");
        namakk.setText(nama);
        jenkel.setText(getiuran.getStringExtra("jeniskelamin"));
        alamat.setText(getiuran.getStringExtra("almt"));
        anggotakk.setText(getiuran.getStringExtra("jumlahkk"));
        jenisiuran.setText(getiuran.getStringExtra("jenisIuran"));
        Toast.makeText(this,"Selamat datang" + getiuran.getStringExtra("nama"),Toast.LENGTH_SHORT).show();

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahActivity = new Intent(checkdata.this, home.class);
                startActivity(pindahActivity);
            }
        });

//        exitbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finishAffinity();
//            }
//        });

    }
}
