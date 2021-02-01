package com.example.mysugardiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class SugarInfoActivity extends Activity {
    TextView nameTv,mSugarTv,nSugarTv,dateTv,infoTv,insulinTv,recSugar,recInsulin;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugar_info);
        mSugarTv=findViewById(R.id.m_sugar_tv);
        nSugarTv=findViewById(R.id.n_sugar_tv);
        dateTv=findViewById(R.id.date_tv);
        infoTv=findViewById(R.id.info_tv);
        insulinTv=findViewById(R.id.insulin_tv);
        imageView=findViewById(R.id.image_iv);
        recSugar=findViewById(R.id.rec_sugar_tv);
        recInsulin=findViewById(R.id.rec_insulin_tv);

        int sugarPosition=getIntent().getIntExtra("sugar",0);
        Sugar sugar=SugarManager.getInstance(this).getSugar(sugarPosition);
        mSugarTv.setText(""+sugar.getSugarM());
        nSugarTv.setText(""+sugar.getSugarN());
        dateTv.setText(sugar.getDate());
        infoTv.setText(sugar.getInfo());
        insulinTv.setText(""+sugar.getInsulin());
        imageView.setImageBitmap(sugar.getPhoto());
        ImageButton backBtn=findViewById(R.id.back_btn);
        if (sugar.getSugarN()+sugar.getSugarM()<200){
            recSugar.setText("Your sugar levels are balanced, so keep up the good work");

            }
        if (sugar.getSugarN()+sugar.getSugarM()>=200&&sugar.getSugarN()+sugar.getSugarM()<500){
            recSugar.setText("Your sugar levels are not at the desired level, be sure to have sugar levels lower than 200," +
                    " you can combine exercise to lower your sugar levels.");
            }

        if (sugar.getSugarN()+sugar.getSugarM()>=500){
            recSugar.setText("Your sugar level is high, make sure you inject insulin according to the carbohydrate you eat." +
                    " High sugar levels can cause future damage");
            }
        if (sugar.getInsulin()<25){
            recInsulin.setText("The amount of carbohydrate units you need is at the desired level and you should make sure that meals are arranged and injected accordingly");
        }else
            {
            recInsulin.setText("The amount of carbohydrate units you need is high, you should eat low carbohydrate meals like eggs, cheese, vegetables, meat, fish.");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SugarInfoActivity.this,ShowSugarActivity.class);
                startActivity(intent);
            }
        });


    }
}
