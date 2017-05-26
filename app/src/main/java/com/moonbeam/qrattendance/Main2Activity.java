package com.moonbeam.qrattendance;

import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;

import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private Button btn;
    private Button sign;
    private EditText nameview;
    private EditText passwordview;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main2);
        btn = (Button) findViewById(R.id.button);
        sign=(Button)findViewById(R.id.signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()){
                    try {
                        login();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(Main2Activity.this,"Internet not Connected",Toast.LENGTH_LONG).show();
                }
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Main2Activity.this,Signup.class);
                startActivity(i);
            }
        });
    }
    void login()throws IOException{
        nameview = (EditText) findViewById(R.id.username);
        passwordview = (EditText) findViewById(R.id.pass);
        String name = nameview.getText().toString();
        name = URLEncoder.encode(name, "UTF-8");
        String pass = passwordview.getText().toString();
        pass = URLEncoder.encode(pass, "UTF-8");
        if(!name.equals("")||!pass.equals("")) {
            String response = "0";
            String wsite = "http://demotestsocial.000webhostapp.com/login.php?name=" + name + "&pass=" + pass;
            URL url = new URL(wsite);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            response = reader.readLine();
            //Toast.makeText(Main2Activity.this, response, Toast.LENGTH_LONG).show();
            if (!response.equals("0")) {
                Intent i = new Intent(Main2Activity.this, Main3Activity.class);
                i.putExtra("ID", response + name);
                startActivity(i);
            }
        }else{
            Toast.makeText(Main2Activity.this,"Username or Password field is empty",Toast.LENGTH_LONG).show();
        }
    }
    public boolean isInternetAvailable() {
        try {
            final InetAddress address = InetAddress.getByName("www.google.com");
            return !address.equals("");
        } catch (UnknownHostException e) {}
        return false;
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
