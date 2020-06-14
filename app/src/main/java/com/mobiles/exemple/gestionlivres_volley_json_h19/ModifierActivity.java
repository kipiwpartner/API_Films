package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModifierActivity extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout modif;
    Button bmodif;
    Button benreg;
    EditText titre;
    EditText realisateur;
    EditText prix;
    EditText duree;
    EditText id_film_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
        modif=( RelativeLayout) findViewById(R.id.form);
        modif.setVisibility(View.GONE);
        bmodif = (Button) findViewById(R.id.gobt);
        bmodif.setOnClickListener(this);
        benreg = (Button) findViewById(R.id.enregistrer);
        benreg.setOnClickListener(this);
        id_film_edit = (EditText) findViewById(R.id.id_film_edit);
        titre = (EditText) findViewById(R.id.titre);
        realisateur = (EditText) findViewById(R.id.realisateur);
        prix = (EditText) findViewById(R.id.prix);
        duree = (EditText) findViewById(R.id.duree);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gobt:
                ficheLivre();
                break;
            case R.id.enregistrer:
                majLivre();
                break;
        }
    }

    public void ficheLivre() {

        String url = ConfigApp.getUrl_api();
        //"http://10.0.2.2:80/Exemples_PHP_MYSQL_HTTP_JSON_XML_PDO_MYSQLI/Exemple_Controleur_Android/PHP/livresControleurJSON.php";

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
                                modif.setVisibility(View.VISIBLE);
                            }
                            else{
                                Toast.makeText(ModifierActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifierActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "modifier");
                params.put("idFilm", id_film_edit.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }

    public void majLivre() {

        String url = ConfigApp.getUrl_api();

        //"http://10.0.2.2:80/Exemples_PHP_MYSQL_HTTP_JSON_XML_PDO_MYSQLI/Exemple_Controleur_Android/PHP/livresControleurJSON.php";
        //Recuperer les donnees
        final String titre = ((EditText) findViewById(R.id.titre)).getText().toString();
        final String auteur = ((EditText) findViewById(R.id.auteur)).getText().toString();
        final String annee = ((EditText) findViewById(R.id.annee)).getText().toString();
        final String pages = ((EditText) findViewById(R.id.pages)).getText().toString();

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK"))
                                Toast.makeText(ModifierActivity.this, "Livre modifie", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ModifierActivity.this, "Probleme pour modifier", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ModifierActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "edit");
                params.put("idFilm", id_film_edit.getText().toString());
                params.put("titre", titre);
                params.put("auteur", auteur);
                params.put("annee", annee);
                params.put("pages", pages);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }
}

