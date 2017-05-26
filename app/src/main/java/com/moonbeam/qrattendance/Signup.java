package com.moonbeam.qrattendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.os.Build.ID;

public class Signup extends AppCompatActivity {
    private EditText uname;
    private EditText pass;
    private EditText upass;
    private EditText email;
    private Button sign;
    private WebView web;
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        uname=(EditText)findViewById(R.id.username);
        pass=(EditText)findViewById(R.id.pass);
        upass=(EditText)findViewById(R.id.cnfPass);
        email=(EditText)findViewById(R.id.email);
        sign=(Button)findViewById(R.id.signup);
        web=(WebView)findViewById(R.id.websign);
        response="0";
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signup();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void signup()throws IOException{
        String user=uname.getText().toString();
        String password=pass.getText().toString();
        String cpass=upass.getText().toString();
        String mail=email.getText().toString();
        if(!user.equals("")||!password.equals("")||!cpass.equals("")||!mail.equals("")){
            if (password.equals(cpass)){
                user = URLEncoder.encode(user, "UTF-8");
                password=URLEncoder.encode(password,"UTF-8");
                mail=URLEncoder.encode(mail,"UTF-8");
                response = "0";
                String wsite = "http://demotestsocial.000webhostapp.com/signup.php?read=1&name=" + user;
                URL url = new URL(wsite);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoOutput(true);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                response = reader.readLine();
                if (response.equals("0")){
                    web.loadUrl("http://demotestsocial.000webhostapp.com/signup.php?read=0&name=" + user+"&pass="+password+"&email="+mail);
                    Intent i=new Intent(Signup.this,Main2Activity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(Signup.this,"Username already in use",Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
                pass.setText("");
                upass.setText("");
            }
        }else{
            Toast.makeText(Signup.this,"field/s is/are still empty",Toast.LENGTH_LONG).show();
        }
    }
}
