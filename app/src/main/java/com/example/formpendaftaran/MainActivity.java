package com.example.formpendaftaran;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.formpendaftaran.database.AppDatabase;
import com.example.formpendaftaran.database.CitizenEntity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.formpendaftaran.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText EditNama, EditAlm;
    private Button BtnSave;
    private AppDatabase database;
    private RadioGroup EditJK;
    private RadioButton radiobtnchoose;
    private Integer jumlahanggota;
    private RadioButton choosenradiobtn;
    Boolean stat=false;
    Boolean statRadio=false;
    Boolean statCheckBox=false;
    String checkNama, checkAlmt;
    private RadioButton rbPria;
    private RadioButton rbWanita;
    private CheckBox cbSampahdK;
    private CheckBox cbDesa;
    private CheckBox cbDuka;
    private CheckBox cbPembangunan;

    private int idCitizen = 0;
    private boolean isEdit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditNama = findViewById(R.id.editnama);
        EditJK = findViewById(R.id.rg);
        EditAlm = findViewById(R.id.editalamat);
        BtnSave = findViewById(R.id.btnsubmit);

        rbPria = findViewById(R.id.radioPria);
        rbWanita = findViewById(R.id.radioWanita);
        cbDesa = findViewById(R.id.kategori1);
        cbSampahdK = findViewById(R.id.kategori2);
        cbDuka = findViewById(R.id.kategori3);
        cbPembangunan = findViewById(R.id.kategori4);

        database = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        idCitizen = intent.getIntExtra("idCitizen",0);
        if (idCitizen>0){
            isEdit = true;
            choosenradiobtn = findViewById(EditJK.getCheckedRadioButtonId());
            //edit dengan unique id
            CitizenEntity CitizenEntity = database.CitizenDao().get(idCitizen);
            EditNama.setText(CitizenEntity.fullname);
//            choosenradiobtn.setText(CitizenEntity.jeniskelamin);
            EditAlm.setText(CitizenEntity.alamat);
        }else {
            isEdit = false;
        }

        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radiobtnchoose = findViewById(EditJK.getCheckedRadioButtonId());
                checkNama = EditNama.getText().toString();
                checkAlmt = EditAlm.getText().toString();

                //Validation if empty
                if(EditNama.getText().toString().length()==0){
                    EditNama.setError("Nama Wajib Diisi");
                }else if (EditAlm.getText().toString().length()==0){
                    EditAlm.setError("Alamat Wajib Diisi");
                }
                else {
                    stat = true;
                }
                if (rbPria.isChecked() || rbWanita.isChecked()){
                    statRadio = true;
                }else {
                    statRadio = false;
                    Toast.makeText(getApplicationContext(), "Gender Wajib Diisi", Toast.LENGTH_SHORT).show();
                }
                if (cbDesa.isChecked() || cbSampahdK.isChecked() || cbDuka.isChecked() || cbPembangunan.isChecked()){
                    statCheckBox = true;
                }else{
                    statCheckBox = false;
                    Toast.makeText(getApplicationContext(), "Pilihan Iuran Wajib Diisi",Toast.LENGTH_SHORT).show();
                }
                if (stat==true&&statRadio==true&statCheckBox==true){
                    showDialog();
                    stat=false;
                }
//                showDialog();
            }
        });


        Objects.requireNonNull(getSupportActionBar()).setTitle("Form Iuran Desa");

        setupSeekBar();
    }

    private void showDialog() {
        EditText editTextnama = (EditText) findViewById(R.id.editnama);
        RadioGroup radioGroupGenders = (RadioGroup) findViewById(R.id.rg);
        RadioButton radioButtonChosen = (RadioButton) findViewById(radioGroupGenders.getCheckedRadioButtonId());
        EditText editTextAlm = (EditText) findViewById(R.id.editalamat);
        TextView Jumlahangkel = (TextView) findViewById(R.id.seekBarJumlahAngkelDet);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Konfirmasi Data");

        dialogBuilder.setMessage("Apakah anda sudah yakin dengan data berikut?\n\n" +
                "Nama: " + editTextnama.getText() + "\n" +
                "Jenis Kelamin: " + radioButtonChosen.getText() + "\n" +
                "Jumlah Anggota : " + Jumlahangkel.getText() + "\n" +
                "Alamat : " + editTextAlm.getText() + "\n" +
                "Jenis Iuran: " + getCheckedBoxValue() + "\n")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Data diterima!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"" + editTextnama.getText().toString(), Toast.LENGTH_SHORT).show();

                        Intent pindahAct = new Intent(getApplicationContext(), checkdata.class);
                        pindahAct.putExtra("nama", editTextnama.getText().toString());
                        pindahAct.putExtra("jeniskelamin", radioButtonChosen.getText().toString());
                        pindahAct.putExtra("almt", editTextAlm.getText().toString());
                        pindahAct.putExtra("jumlahkk", Jumlahangkel.getText());
                        pindahAct.putExtra("jenisIuran", getCheckedBoxValue());

                        if (isEdit){
                            database.CitizenDao().update(idCitizen, EditNama.getText().toString(), EditAlm.getText().toString(), radiobtnchoose.getText().toString());
                        }else {
                            database.CitizenDao().insertAll(EditNama.getText().toString(), EditAlm.getText().toString(), radiobtnchoose.getText().toString(),getCheckedBoxValue(),jumlahanggota);
                        }

                        startActivity(pindahAct);

                        dialogInterface.cancel();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setCancelable(true);

        AlertDialog confirmDialog = dialogBuilder.create();

        confirmDialog.show();
    }

    private String getCheckedBoxValue() {
        CheckBox checkBoxdesa = (CheckBox) findViewById(R.id.kategori1);
        CheckBox checkBoxsampah = (CheckBox) findViewById(R.id.kategori2);
        CheckBox checkBoxduka = (CheckBox) findViewById(R.id.kategori3);
        CheckBox checkBoxkeamanan = (CheckBox) findViewById(R.id.kategori4);

        String checkedValue = "";

        if (checkBoxdesa.isChecked()) {
            checkedValue += checkBoxdesa.getText() + ", ";
        }
        if (checkBoxsampah.isChecked()) {
            checkedValue += checkBoxsampah.getText() + ", ";
        }
        if (checkBoxduka.isChecked()) {
            checkedValue += checkBoxduka.getText() + ", ";
        }
        if (checkBoxkeamanan.isChecked()) {
            checkedValue += checkBoxkeamanan.getText() + ".\n";
        }

        return checkedValue;
    }

    private void setupSeekBar() {
        int MIN = 1;
        int MAX = 20;
        int STEP = 1;

        SeekBar seekBarAngkel = (SeekBar) findViewById(R.id.seekBarJumlahAngkel);
        TextView textViewAngkelValue = (TextView) findViewById(R.id.seekBarJumlahAngkelDet);

        seekBarAngkel.setMax((MAX - MIN) / STEP);

        seekBarAngkel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewAngkelValue.setText(String.format(progress + " Orang"));
                jumlahanggota = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}