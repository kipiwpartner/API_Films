package edu.android.android_lab2_2;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class EnregistrerActivity extends MainActivity implements View.OnClickListener{

    Button benreg;
    Spinner spinner1;
    String category = "1";

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(
                this,
                R.array.table_category,
                R.layout.my_spinner);

        //link the adapter to the spinner
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setAdapter(adapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ajouterEvents();

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){

                    case 0:
                        category = "1";
                        break;
                    case 1:
                        category = "2";
                        break;
                    case 2:
                        category = "3";
                        break;
                    case 3:
                        category = "4";
                        break;
                    case 4:
                        category = "5";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enregistrer:
                requeteEnregistrer();
                break;
        }
    }

    public void ajouterEvents(){
        benreg = (Button) findViewById(R.id.enregistrer);
        benreg.setOnClickListener(this);
    }

    public void requeteEnregistrer() {

        String url = ConfigApp.getUrl_api();
        //Recuperer les donnees
        final String titre = ((EditText) findViewById(R.id.titre)).getText().toString();
        final String realisateur = ((EditText) findViewById(R.id.realisateur)).getText().toString();
        final String youtube = ((EditText) findViewById(R.id.youtube)).getText().toString();
        final String prix = ((EditText) findViewById(R.id.prix)).getText().toString();
        final String duree = ((EditText) findViewById(R.id.duree)).getText().toString();


        //Envoyer données au serveur

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK"))
                                Toast.makeText(EnregistrerActivity.this, "Film bien enregistre", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(EnregistrerActivity.this, "Probleme pour enregistrer", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EnregistrerActivity.this, "ERREUR", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "enregistrer");
                params.put("titre", titre);
                params.put("realisateur", realisateur);
                params.put("youtube", youtube);
                params.put("prix", prix);
                params.put("duree", duree);
                params.put("id_category", category);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }//fin Enregistrer
}
