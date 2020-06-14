package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class EnregistrerActivity extends AppCompatActivity implements View.OnClickListener {
    Button benreg, btchoisir;
    ImageView image;
    int PICK_IMAGE_REQUEST = 111;
    Bitmap bitmap;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enregistrer);
        ajouterEvents();
    }

    public void ajouterEvents(){
        benreg = (Button) findViewById(R.id.enregistrer);
        benreg.setOnClickListener(this);
        image = (ImageView)findViewById(R.id.upimg);
        image.setOnClickListener(this);
        btchoisir = (Button)findViewById(R.id.btchoisir);
        btchoisir.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.enregistrer:
                requeteEnregistrer();
                break;
            case R.id.btchoisir:
                //http://www.frandroid.com/comment-faire/lemultimedia/234269_apercudelapplicationgalerie
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                break;
        }
    }

    public void requeteEnregistrer() {

        String url = "http://10.0.2.2:80/Exemples_PHP_MYSQL_HTTP_JSON_XML_PDO_MYSQLI/Exemple_Controleur_Android/PHP/livresControleurJSON.php";
        //Recuperer les donnees
        final String titre = ((EditText) findViewById(R.id.titre)).getText().toString();
        final String auteur = ((EditText) findViewById(R.id.auteur)).getText().toString();
        final String annee = ((EditText) findViewById(R.id.annee)).getText().toString();
        final String pages = ((EditText) findViewById(R.id.pages)).getText().toString();
        progressDialog = new ProgressDialog(EnregistrerActivity.this);
        progressDialog.setMessage("Uploading, attendre SVP...");
        progressDialog.show();

        //convertir image en base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //Envoyer données au serveur

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            Log.d("RESULTAT", response);
                            JSONArray jsonResponse = new JSONArray(response);
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK"))
                                Toast.makeText(EnregistrerActivity.this, "Livre bien enregistre", Toast.LENGTH_SHORT).show();
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
                params.put("image", imageString);
                params.put("action", "enregistrer");
                params.put("titre", titre);
                params.put("auteur", auteur);
                params.put("annee", annee);
                params.put("pages", pages);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }//fin Enregistrer

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();

            try {
                //Obtenir l'image de la gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Placer image dans ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

