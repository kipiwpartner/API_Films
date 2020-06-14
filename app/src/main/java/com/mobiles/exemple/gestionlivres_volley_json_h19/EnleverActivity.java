package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

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

public class EnleverActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout modif;
    Button bmodif;
    Button btenlever;
    Button btannuler;
    EditText titre;
    EditText auteur;
    EditText annee;
    EditText pages;
    EditText idlivre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enlever);
        ajouterEvents();
    }

    public void ajouterEvents(){
        modif=( RelativeLayout) findViewById(R.id.formenlever);
        modif.setVisibility(View.GONE);
        bmodif = (Button) findViewById(R.id.gobt);
        bmodif.setOnClickListener(this);
        btannuler = (Button) findViewById(R.id.btannuler);
        btannuler.setOnClickListener(this);
        btenlever = (Button) findViewById(R.id.btenlever);
        btenlever.setOnClickListener(this);
        idlivre = (EditText) findViewById(R.id.idlivrem);
        titre = (EditText) findViewById(R.id.titre);
        auteur = (EditText) findViewById(R.id.auteur);
        annee = (EditText) findViewById(R.id.annee);
        pages = (EditText) findViewById(R.id.pages);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gobt:
                ficheLivre();
                break;
            case R.id.btenlever:
                enlever();
                modif.setVisibility(View.GONE);
                break;
            case R.id.btannuler:
                idlivre.setText("");
                modif.setVisibility(View.GONE);
                break;
        }
    }

    public void ficheLivre() {

        String url = "http://10.0.2.2:8888/Exemples_PHP_MYSQL_HTTP_JSON_XML_PDO_MYSQLI/Exemple_Controleur_Android/PHP/livresControleurJSON.php";

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
                                JSONObject unLivre;
                                unLivre=jsonResponse.getJSONObject(1);
                                titre.setText(unLivre.getString("titre"));
                                auteur.setText(unLivre.getString("auteur"));
                                annee.setText(unLivre.getString("annee"));
                                pages.setText(unLivre.getString("pages"));
                                modif.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(EnleverActivity.this, msg, Toast.LENGTH_SHORT).show();
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
                params.put("action", "modifier");
                params.put("idlivre", idlivre.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }

    public void enlever() {

        String url = "http://10.0.2.2:80/Exemples_PHP_MYSQL_HTTP_JSON_XML_PDO_MYSQLI/Exemple_Controleur_Android/PHP/livresControleurJSON.php";

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
                                Toast.makeText(EnleverActivity.this, "Livre enleve", Toast.LENGTH_SHORT).show();
                                idlivre.setText("");
                            }
                            else{
                                Toast.makeText(EnleverActivity.this, "Probleme avec enlever", Toast.LENGTH_SHORT).show();
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
                params.put("idlivre", idlivre.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }
}