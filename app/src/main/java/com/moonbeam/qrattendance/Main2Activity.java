package com.moonbeam.qrattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.webkit.WebView;
import android.webkit.WebSettings;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ProtocolException;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private Button btn;
    private TextView nameview;
    private TextView passwordview;
    private WebView webv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn=(Button)findViewById(R.id.button);
        nameview=(TextView)findViewById(R.id.username);
        passwordview=(TextView)findViewById(R.id.pass);
        webv=(WebView)findViewById(R.id.login);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
    public void onButtonClick(View v) throws IOException{
        if(v.getId()==R.id.button){
            String name=(String) nameview.getText();
            String pass=(String)passwordview.getText();
            String wsite="demotestsocial.000webhostapp.com/login.php?name="+name+"&pass="+pass;
            URL url = new URL(wsite);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String response=reader.readLine();
            Toast.makeText(Main2Activity.this, response,Toast.LENGTH_LONG).show();
            Intent i=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(i);
        }
    }
}
