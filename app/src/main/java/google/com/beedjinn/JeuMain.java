package google.com.beedjinn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Pierre on 10/01/2017.
 */

public class JeuMain extends AppCompatActivity {
    private BoucleJeu game;
    public boolean sonIsOn;


    /** A la création de l'activité
     * 1. on initialise le jeu et affecter la vue à l'activité
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //cacher action bar
        sonIsOn=getIntent().getExtras().getBoolean("son");
        game = new BoucleJeu();//creation de la boucle de jeu
        game.initGame(this.getBaseContext(),this,sonIsOn);// + initialisation
        setContentView(game.screen); //affichage de l'ecran
    }

    /** Lorsque l'application s'arrête,
     il faut arrêter proprement la boucle de jeu*/
    @Override
    protected void onDestroy() {
        game.running = false;
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        game.theme.stop();
        game.running=false;
        finish();
        Intent myIntent=new Intent(JeuMain.this,JeuAc.class);
        startActivity(myIntent);
    }
}
