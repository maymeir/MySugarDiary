package com.example.mysugardiary;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

public class NewSugarActivity extends AppCompatActivity {

    EditText mSugar,nSugar,insulin,info;
    int CAMERA=1;
    Bitmap bitmap=null;
    ImageView imageView;
    TextView dateTv;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_sugar);


        mSugar=findViewById(R.id.m_sugar);
        nSugar=findViewById(R.id.n_sugar);
        insulin=findViewById(R.id.insulin_sugar);
        info=findViewById(R.id.details_sugar);
        imageView=findViewById(R.id.photo_output);
        dateTv=findViewById(R.id.date_output);
        ImageButton backBtn=findViewById(R.id.return_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewSugarActivity.this,ShowSugarActivity.class);
                startActivity(intent);
            }
        });
        ImageButton dateBtn=findViewById(R.id.date_btn);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year  = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(NewSugarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        dateTv.setText(i2 + " / " + (i1+1) + " / " + i);
                    }
                },year,month,day);
                dpd.show();

            }
        });
        ImageButton photoBtn=findViewById(R.id.photo_btn);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 23) {

                    int has = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (has == PackageManager.PERMISSION_GRANTED) {


                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,CAMERA);


                    } else {

                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA);
                    }

                } else {


                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,CAMERA);
                }

            }
        });

        ImageButton saveBtn=findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSugar.length()==0) {
                    Toast.makeText(NewSugarActivity.this, "enter morning sugar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(nSugar.length()==0) {
                    Toast.makeText(NewSugarActivity.this, "enter night sugar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(insulin.length()==0) {
                    Toast.makeText(NewSugarActivity.this, "enter insulin unit sugar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(dateTv.length()==0) {
                    Toast.makeText(NewSugarActivity.this, "enter date sugar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(info.length()==0) {
                    Toast.makeText(NewSugarActivity.this, "enter meal information sugar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(bitmap==null) {
                    Toast.makeText(NewSugarActivity.this, "add photo", Toast.LENGTH_LONG).show();
                    return;
                }

                Sugar sugar=new Sugar(Integer.parseInt(mSugar.getText().toString()),Integer.parseInt(nSugar.getText().toString())
                        ,Integer.parseInt(insulin.getText().toString()),info.getText().toString(),dateTv.getText().toString(),bitmap);
                SugarManager manager=SugarManager.getInstance(NewSugarActivity.this);
                manager.addSugar(sugar);
                Intent intent=new Intent(NewSugarActivity.this,ShowSugarActivity.class);
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA && resultCode==RESULT_OK) {


            bitmap =(Bitmap)data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/saved_images");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            Random generator = new Random();
            int n = 10000;
            n = generator.nextInt(n);
            String fname = "Image-"+ n +".jpg";
            File file = new File (myDir, fname);
            if (file.exists ())
                file.delete ();
            try {
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            MediaScannerConnection.scanFile(this, new String[] { file.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

        }
    }


}
