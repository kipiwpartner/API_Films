package edu.android.android_lab2_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    WebView webview;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.lister:
                Intent intent_lister = new Intent(this, ListerActivity.class);
                this.finish();
                startActivity(intent_lister);
                break;
            case R.id.modifier:
                Intent intent_modifier = new Intent(this, ModifierActivity.class);
                this.finish();
                startActivity(intent_modifier);
                break;
            case R.id.enregistrer:
                Intent intent_enregistrer = new Intent(this, EnregistrerActivity.class);
                this.finish();
                startActivity(intent_enregistrer);
                break;
            case R.id.main_activity:
                Intent intent_main = new Intent(this, MainActivity.class);
                this.finish();
                startActivity(intent_main);
                break;
            case R.id.enlever:
                Intent intent_enlever = new Intent(this, EnleverActivity.class);
                this.finish();
                startActivity(intent_enlever);
                break;
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        webview = (WebView) findViewById(R.id.webview);
        //webview.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient());
        webview.loadUrl("https://ivandziuba.site/en/");
    }
}
