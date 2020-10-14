package com.bram.vsgastorage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class InternalStorageActivity extends AppCompatActivity {
    public static final String FILENAME = "namafile.txt";
    Button buat , baca, edit, hapus;
    TextView txt_hasil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_storage);

        buat = findViewById(R.id.btn_buat);
        baca = findViewById(R.id.btn_baca);
        edit = findViewById(R.id.btn_edit);
        hapus = findViewById(R.id.btn_hapus);

        txt_hasil = findViewById(R.id.txt_tmp);

        buat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buatFile();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ubahFile();
            }
        });

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusFile();
            }
        });

        baca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bacaFile();
            }
        });
    }

    void buatFile(){
        String isiFile = "Coba Isi Data File Text";
        File file = new File(getFilesDir(), FILENAME);
        Toast.makeText(this, "getinternal path"+ Environment.getDataDirectory(), Toast.LENGTH_SHORT).show();
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
        String ubah = "Udate isi Data file text";
        File file = new File(getFilesDir(),FILENAME);
        FileOutputStream outputStream = null;

        try {
            file.createNewFile();
            outputStream  = new FileOutputStream(file,false);
            outputStream.write(ubah.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void hapusFile(){
        File file = new File(getFilesDir(), FILENAME);
        if (file.exists()){
            file.delete();
        }
    }

    void bacaFile(){
        File sdcard = getFilesDir();
        File file = new File(sdcard, FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));

                String line = br.readLine();
                while (line != null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            txt_hasil.setText(text.toString());
        }
    }
}