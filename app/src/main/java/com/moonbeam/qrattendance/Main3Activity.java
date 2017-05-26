package com.moonbeam.qrattendance;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
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

public class Main3Activity extends AppCompatActivity {
    private WebView webv;
    private WebView webadd;
    private EditText channel;
    private Button open;
    private Button add;
    private Button delete;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main3);
        channel=(EditText) findViewById(R.id.channel);
        open=(Button)findViewById(R.id.signup);
        add=(Button)findViewById(R.id.add);
        delete=(Button)findViewById(R.id.delete);
        webv=(WebView)findViewById(R.id.webView1);
        webadd=(WebView) findViewById(R.id.websign);
        Bundle bundle=getIntent().getExtras();
        ID=bundle.getString("ID");
        WebSettings webaddSettings = webadd.getSettings();
        webaddSettings.setJavaScriptEnabled(true);
        WebSettings webSettings = webv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webv.loadUrl("https://demotestsocial.000webhostapp.com/channels.php?show="+ID);
        reload();
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    open();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });
    }
    public void reload() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                webv.loadUrl("https://demotestsocial.000webhostapp.com/channels.php?show="+ID);
                reload();
            }
        }, 5000);
    }
    void add(){
        String chname=channel.getText().toString();
        if(!chname.equals("")) {
            String url = "http://demotestsocial.000webhostapp.com/channels.php?name=" + chname + "&input=" + ID + "&change=0";
            webadd.loadUrl(url);
            channel.setText("");
            Toast.makeText(this, chname + ":Wait for few moments for the channel to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Main3Activity.this,"Channel Field is empty",Toast.LENGTH_LONG).show();
        }
    }
    void delete(){
        String chname=channel.getText().toString();
        if(!chname.equals("")) {
            String url = "http://demotestsocial.000webhostapp.com/channels.php?name=" + chname + "&input=" + ID + "&change=1";
            webadd.loadUrl(url);
            channel.setText("");
            Toast.makeText(this, chname + ":Wait for few moments for the channel to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Main3Activity.this,"Channel Field is empty",Toast.LENGTH_LONG).show();
        }
    }
    void open() throws IOException {
        String name = channel.getText().toString();
        if(!name.equals("")) {
            name = URLEncoder.encode(name, "UTF-8");
            ID = URLEncoder.encode(ID, "UTF-8");
            String response = "0";
            String wsite = "http://demotestsocial.000webhostapp.com/next.php?name=" + name + "&table=" + ID;
            URL url = new URL(wsite);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            response = reader.readLine();
            //Toast.makeText(Main2Activity.this, response, Toast.LENGTH_LONG).show();
            if (!response.equals("0")) {
                Intent i = new Intent(Main3Activity.this, MainActivity.class);
                i.putExtra("ID", ID + response);
                startActivity(i);
            } else {
                Toast.makeText(this, "No Such Channel", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(Main3Activity.this,"Channel Field is empty",Toast.LENGTH_LONG).show();
        }
    }
}
