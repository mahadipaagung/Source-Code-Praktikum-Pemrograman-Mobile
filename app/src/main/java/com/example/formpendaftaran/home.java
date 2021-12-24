package com.example.formpendaftaran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TextView addlist = findViewById(R.id.btndaftar);
        TextView ceklist = findViewById(R.id.btnlist);

        addlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahactivity = new Intent(home.this,MainActivity.class);
                startActivity(pindahactivity);
            }
        });

        ceklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindahAct = new Intent(home.this,listmenu.class);
                startActivity(pindahAct);
            }
        });


    }
}
