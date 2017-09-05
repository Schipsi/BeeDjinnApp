package google.com.beedjinn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class JeuAc extends AppCompatActivity {

    public float fx;
    public float fy;

    public final static String HIGHSCORE = "0";
    public final static String ALCOOL="0.0";

    public boolean sonAc;

    public final static String SON="true";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_jeu_ac);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();


        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        fx=((float)size.x)/2392f;
        fy=((float)size.y)/1440f;

        Typeface amar = Typeface.createFromAsset(this.getAssets(), "fonts/Amarillo.otf");
        Typeface cicle = Typeface.createFromAsset(this.getAssets(), "fonts/Cicle Fina.ttf");
        Typeface cicleB = Typeface.createFromAsset(this.getAssets(), "fonts/cicle_bold.ttf");

        TextView titre= (TextView) findViewById(R.id.textView2);
        titre.setTypeface(amar);


        float alcool=Float.parseFloat(preferences.getString(ALCOOL,"0.0"));
        int highscore = Integer.parseInt(preferences.getString(HIGHSCORE, "0"));


        TextView highS=new TextView(this);
        highS.setTypeface(cicleB);
        highS.setText("Highscore: "+Float.toString(highscore)+"m");
        highS.setTextColor(Color.parseColor("#F4B32D"));
        highS.setX(80*fx);
        highS.setY(1100*fy);
        highS.setTextSize(25*fy);

        TextView alcoolS=new TextView(this);
        alcoolS.setTypeface(cicleB);
        alcoolS.setText("Record alcoolémie: "+Float.toString(alcool)+"g/L");
        alcoolS.setTextColor(Color.parseColor("#F4B32D"));
        alcoolS.setX(800*fx);
        alcoolS.setY(1100*fy);
        alcoolS.setTextSize(25*fy);


        Button boutton1= (Button) findViewById(R.id.button);
        boutton1.setTypeface(cicleB);
        Button boutton2= (Button) findViewById(R.id.button_tuto);
        boutton2.setTypeface(cicleB);

        final ImageButton sonOnOff= new ImageButton(this);
        sonOnOff.setScaleX(0.1f*fx/fy);
        sonOnOff.setScaleY(0.1f*fx/fy);
        sonOnOff.setX(1600*fx);
        sonOnOff.setY(550*fy);

        ImageView tete=new ImageView(this);
        tete.setBackgroundResource(R.drawable.head);
        tete.setScaleX(0.3f*fx/fy);
        tete.setScaleY(0.4f*fx/fy);
        tete.setX(-600f*fx);
        tete.setY(50*fy);

        ImageView perchman=new ImageView(this);
        perchman.setBackgroundResource(R.drawable.perch);
        perchman.setScaleX(0.11f*fx/fy);
        perchman.setScaleY(0.4f*fx/fy);
        perchman.setX(700*fx);
        perchman.setY(0f);

        ImageView journaliste=new ImageView(this);
        journaliste.setBackgroundResource(R.drawable.journaliste);
        journaliste.setScaleX(0.1f*fx/fy);
        journaliste.setScaleY(0.3f*fx/fy);
        journaliste.setX(975*fx);
        journaliste.setY(150f*fy);

        LayoutInflater mInflater = LayoutInflater.from(this);
        View contentView = mInflater.inflate(R.layout.activity_jeu_ac, null);
        ViewGroup inclusionViewGroup = (ViewGroup)findViewById(R.id.activity_vue4);
        inclusionViewGroup.addView(sonOnOff);
        inclusionViewGroup.addView(tete);
        inclusionViewGroup.addView(highS);
        inclusionViewGroup.addView(alcoolS);
        inclusionViewGroup.addView(journaliste);
        inclusionViewGroup.addView(perchman);
        setContentView(inclusionViewGroup);

        sonAc=Boolean.parseBoolean(preferences.getString(SON,"true"));
        if (sonAc){
            sonOnOff.setBackgroundResource(R.drawable.son_on);
        }
        else{
            sonOnOff.setBackgroundResource(R.drawable.son_off);
        }

        sonOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                SharedPreferences.Editor editor = preferences.edit();



                if (sonAc){
                    sonAc=false;
                    editor.putString(SON, Boolean.toString(sonAc));
                    editor.commit();
                    sonOnOff.setBackgroundResource(R.drawable.son_off);
                }
                else{
                    sonAc=true;
                    editor.putString(SON, Boolean.toString(sonAc));
                    editor.commit();
                    sonOnOff.setBackgroundResource(R.drawable.son_on);
                }

            }
        });




        boutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(JeuAc.this,JeuMain.class);
                myIntent.putExtra("son",sonAc);

                startActivity(myIntent);
            }
        });
        boutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(JeuAc.this,Tuto.class);


                startActivity(myIntent);
            }
        });
    }
    @Override
    public void onBackPressed(){
        Intent myIntent=new Intent(JeuAc.this,MainActivity.class);
        finish();
        startActivity(myIntent);
    }

    public boolean getSon(){
        return sonAc;
    }
}
