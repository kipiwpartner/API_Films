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

public class ModifierActivity extends MainActivity  implements View.OnClickListener {

    LinearLayout modif;
    Button bmodif;
    Button benreg;
    EditText titre;
    EditText realisateur;
    EditText prix;
    EditText duree;
    EditText id_film_edit;
    LinearLayout progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        modif=( LinearLayout) findViewById(R.id.form);
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
                ficheFilm();
                break;
            case R.id.enregistrer:
                changeFilm();
                break;
        }
    }

    public void ficheFilm() {

        String url = ConfigApp.getUrl_api();

        progressBar.setVisibility(View.VISIBLE);
        modif.setVisibility(View.INVISIBLE);

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
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                            else{
                                Toast.makeText(ModifierActivity.this, msg, Toast.LENGTH_SHORT).show();
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

    public void changeFilm() {

        String url = ConfigApp.getUrl_api();

        //Recuperer les donnees
        final String titre = ((EditText) findViewById(R.id.titre)).getText().toString();
        final String realisateur = ((EditText) findViewById(R.id.realisateur)).getText().toString();
        final String prix = ((EditText) findViewById(R.id.prix)).getText().toString();
        final String duree = ((EditText) findViewById(R.id.duree)).getText().toString();

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK"))
                                Toast.makeText(ModifierActivity.this, "Film modifie", Toast.LENGTH_SHORT).show();
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
                params.put("realisateur", realisateur);
                params.put("prix", prix);
                params.put("duree", duree);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }


}
