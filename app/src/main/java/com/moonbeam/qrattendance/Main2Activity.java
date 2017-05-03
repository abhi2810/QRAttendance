package com.moonbeam.qrattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }
    public void onButtonClick(View v){
        if(v.getId()==R.id.button){
            Intent i=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(i);
        }
    }
}
