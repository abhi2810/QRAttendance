package com.moonbeam.qrattendance;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Main3Activity extends AppCompatActivity {
    private WebView webv;
    private EditText channel;
    private Button open;
    private Button add;
    private Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main3);
        channel=(EditText) findViewById(R.id.channel);
        open=(Button)findViewById(R.id.open);
        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        webv=(WebView)findViewById(R.id.webView1);
        Bundle bundle=getIntent().getExtras();
        final String ID=bundle.getString("ID");
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.loadUrl("https://demotestsocial.000webhostapp.com/channels.php?show="+ID);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    add(ID);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    void add(String ID)throws IOException{
        String chname=channel.getText().toString();
        chname = URLEncoder.encode(chname, "UTF-8");
        String wsite = "http://demotestsocial.000webhostapp.com/login.php?name=" + chname + "&input=" + ID;
        URL url = new URL(wsite);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
    }
}
