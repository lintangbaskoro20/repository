package com.bram.vsgastorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class ExternalStorageActivity extends AppCompatActivity implements View.OnClickListener{

    public static final  String FILENAME = "namafile.txt";
    public static String TAG = BuildConfig.APPLICATION_ID;
    public static final int REQUEST_CODE_STORAGE = 100;
    public int selectEvent = 0;

    Button buat, ubah, baca, hapus;
    TextView txt_baca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_storage);

        buat = findViewById(R.id.btn_buat_ext);
        ubah = findViewById(R.id.btn_edit_ext);
        baca = findViewById(R.id.btn_baca_ext);
        hapus = findViewById(R.id.btn_hapus_ext);
        txt_baca = findViewById(R.id.txt_tmp_ext);

        buat.setOnClickListener(this);
        ubah.setOnClickListener(this);
        baca.setOnClickListener(this);
        hapus.setOnClickListener(this);


    }
    void buatFile(){
        String isiFile = "Coba Isi Data File Text";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void ubahFile(){
        String ubah = "Update Isi Data File Text";
        String state = Environment.getExternalStorageState();

        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);

        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, false);
            outputStream.write(ubah.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bacaFile(){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, FILENAME);

        if(file.exists()) {

            StringBuilder text = new StringBuilder();

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();

                while (line != null) {
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            } catch (IOException e) {
                System.out.println("Error " + e.getMessage());
            }
            txt_baca.setText(text.toString());
        }
    }
    void hapusFile(){
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        if (file.exists()) {
            file.delete();
        }
    }

    public boolean periksaIzinPenyimpanan(){
        if (Build.VERSION.SDK_INT >=23){
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED){
                return true;
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    jalankanPerintah(selectEvent);
            }
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case  R.id.btn_baca_ext:
            case R.id.btn_buat:
            case R.id.btn_edit:
            case R.id.btn_hapus_ext:
                if (periksaIzinPenyimpanan()){
                    selectEvent = view.getId();
                    jalankanPerintah(view.getId());
                }
        }
    }

    public void jalankanPerintah(int id){
        switch (id){
            case R.id.btn_buat_ext:
                buatFile();
                break;
            case R.id.btn_baca_ext:
                bacaFile();
                break;
            case R.id.btn_edit_ext:
                ubahFile();
                break;
            case R.id.btn_hapus_ext:
                hapusFile();
                break;
        }
    }
}