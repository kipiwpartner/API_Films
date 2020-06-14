package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView;

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

public class ListerActivity extends AppCompatActivity { //implements View.OnClickListener {
    ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lister);
        liste=(ListView) findViewById(R.id.liste);
        lister();
    }

    public void lister() {

        // set
        //((MyApplication) this.getApplication()).setSomeVariable("foo");

        // get
        //String s = ((MyApplication) this.getApplication()).getSomeVariable();

        final ArrayList<HashMap<String, Object>> tabLivres = new ArrayList<HashMap<String, Object>>();
        String url = ConfigApp.getUrl_api();

        StringRequest requete = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("RESULTAT", response);
                            int i,j;
                            JSONArray jsonResponse = new JSONArray(response);
                            HashMap<String, Object> map;
                            String msg = jsonResponse.getString(0);
                            if(msg.equals("OK")){
                                JSONObject unLivre;
                                for(i=1;i<jsonResponse.length();i++){
                                    unLivre=jsonResponse.getJSONObject(i);
                                    map= new HashMap<String, Object>();
                                    j=(i%7);//m0.jpg, ...,m6.jpg round robin
                                    String nomImage = "m"+j;
                                   /* byte[] decodedString = Base64.decode(unLivre.getString("image"), Base64.DEFAULT);
                                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                    Drawable d = new BitmapDrawable(getResources(),decodedByte);
                                    map.put("Image", d);
                                    if (i==3)
                                        map.put("img", d);
                                    else*/
                                    //map.put("img", String.valueOf(getResources().getIdentifier(nomImage, "drawable", getPackageName())));
                                    map.put("img", "dadsasd");
                                    map.put("idl", unLivre.getString("idFilm"));
                                    map.put("mtitre", unLivre.getString("titre"));
                                    map.put("mauteur", unLivre.getString("realisateur"));
                                    map.put("mannee", unLivre.getString("prix"));
                                    map.put("mpages", unLivre.getString("duree"));
                                    tabLivres.add(map);
                                }

                                SimpleAdapter monAdapter = new SimpleAdapter (ListerActivity.this, tabLivres, R.layout.lister_livres_map,
                                        new String[] {"img", "idl", "mtitre", "mauteur", "mannee", "mpages"},
                                        new int[] {R.id.img, R.id.idl, R.id.mtitre, R.id.mauteur, R.id.mannee, R.id.mpages});
                                liste.setAdapter(monAdapter);
                            }
                            else{}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ListerActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                // Les parametres pour POST
                params.put("action", "lister");
                return params;
            }
        };
        Volley.newRequestQueue(this).add(requete);//Si Volley rouge clique Volley et choisir add dependency on module volley
    }
}
