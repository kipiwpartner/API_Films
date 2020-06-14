package com.mobiles.exemple.gestionlivres_volley_json_h19;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//https://futurestud.io/tutorials/how-to-run-an-android-app-against-a-localhost-api
public class GestionLivresActivity extends AppCompatActivity implements View.OnClickListener{
    Button benreg,blister,bmodifier,benlever;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_livres);
        ajouterEvents();
    }

    public void ajouterEvents(){
        benreg = (Button) findViewById(R.id.benreg);
        benreg.setOnClickListener(this);
        blister = (Button) findViewById(R.id.blister);
        blister.setOnClickListener(this);
        bmodifier = (Button) findViewById(R.id.bmodifier);
        bmodifier.setOnClickListener(this);
        benlever = (Button) findViewById(R.id.benlever);
        benlever.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.benreg:
                enregistrer();
                break;
            case R.id.blister:
                lister();
                break;
            case R.id.bmodifier:
                modifier();
                break;
            case R.id.benlever:
                enlever();
                break;
        }
    }
    public void enregistrer(){
        Intent IntEnreg = new Intent(GestionLivresActivity.this, EnregistrerActivity.class);
        startActivity(IntEnreg);
    }
    public void lister(){
        Intent IntLister = new Intent(GestionLivresActivity.this, ListerActivity.class);
        startActivity(IntLister);
    }
    public void modifier(){
        Intent IntModif = new Intent(GestionLivresActivity.this, ModifierActivity.class);
        startActivity(IntModif);
    }
    public void enlever(){
        Intent IntEnlever = new Intent(GestionLivresActivity.this, EnleverActivity.class);
        startActivity(IntEnlever);
    }

}
