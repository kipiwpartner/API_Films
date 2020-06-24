package edu.android.android_lab2_2;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BaseActivity extends Activity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.lister:
                Intent intent_lister = new Intent(this, ListerActivity.class);
                startActivity(intent_lister);
                return true;
            case R.id.modifier:
                Intent intent_modifier = new Intent(this, ModifierActivity.class);
                startActivity(intent_modifier);
                return true;
            case R.id.enregistrer:
                Intent intent_enregistrer = new Intent(this, EnregistrerActivity.class);
                startActivity(intent_enregistrer);
                return true;
            case R.id.main_activity:
                Intent intent_main = new Intent(this, MainActivity.class);
                startActivity(intent_main);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
