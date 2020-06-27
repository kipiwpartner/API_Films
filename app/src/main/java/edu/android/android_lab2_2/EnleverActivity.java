package edu.android.android_lab2_2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EnleverActivity extends MainActivity implements View.OnClickListener  {

    LinearLayout progressBar;
    LinearLayout enlev;
    Button annuler;
    Button enlever;
    Button btn_go;
    EditText titre;
    EditText realisateur;
    EditText prix;
    EditText duree;
    EditText id_film_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlever);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        enlev =(LinearLayout) findViewById(R.id.form);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        annuler = (Button) findViewById(R.id.annuler);
        annuler.setOnClickListener(this);
        enlever = (Button) findViewById(R.id.enlever);
        enlever.setOnClickListener(this);
        btn_go = (Button) findViewById(R.id.gobt);
        btn_go.setOnClickListener(this);

        id_film_delete = (EditText) findViewById(R.id.id_film_delete);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gobt:
                ficheFilm();
                break;
            case R.id.annuler:

                //changeFilm();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void ficheFilm() {

        String url = ConfigApp.getUrl_api();

        progressBar.setVisibility(View.VISIBLE);
        enlev.setVisibility(View.INVISIBLE);

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i;
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject Film;
                                Film = jsonResponse.getJSONObject(1);
                                titre.setText(Film.getString("titre"));
                                realisateur.setText(Film.getString("realisateur"));
                                prix.setText(Film.getString("prix"));
                                duree.setText(Film.getString("duree"));
                                enlev.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            else{
                                Toast.makeText(EnleverActivity.this, msg, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnleverActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "enlever");
                params.put("idFilm", id_film_delete.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }
}
