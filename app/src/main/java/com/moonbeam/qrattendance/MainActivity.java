package com.moonbeam.qrattendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.os.Handler;
import java.util.Date;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //View Objects
    private Button buttonScan;
    private TextView textViewName;
    private WebView mWebView;
    private WebView webview;
    String table;
    //qr code scanner object
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewName = (TextView) findViewById(R.id.textViewName);
        qrScan = new IntentIntegrator(this);
        mWebView = (WebView) findViewById(R.id.activity_main_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        Bundle bundle=getIntent().getExtras();
        table=bundle.getString("ID");
        mWebView.loadUrl("https://demotestsocial.000webhostapp.com/?show=0&table="+table);
        reload();
        buttonScan.setOnClickListener(this);
    }
    public void reload() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                mWebView.loadUrl("https://demotestsocial.000webhostapp.com/?show=0&table="+table);
                reload();
            }
        }, 5000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                textViewName.setText(result.getContents());
                webview=(WebView) findViewById(R.id.webview);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String t = sdf.format(new Date());
                String time=""+t.substring(9,11)+"H"+t.substring(11,13)+"M, "+t.substring(6,8)+"/"+t.substring(4,6)+"/"+t.substring(0,4);
                String url="https://demotestsocial.000webhostapp.com/?name="+result.getContents()+"&time="+time+"&table="+table;
                webview.loadUrl(url);
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
//test code