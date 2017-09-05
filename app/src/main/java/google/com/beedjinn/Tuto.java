package google.com.beedjinn;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Pierre on 15/01/2017.
 */

public class Tuto extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_tuto);

        Typeface amar = Typeface.createFromAsset(this.getAssets(), "fonts/Amarillo.otf");
        Typeface cicle = Typeface.createFromAsset(this.getAssets(), "fonts/Cicle Fina.ttf");

        TextView title=(TextView) findViewById(R.id.textTuto);
        title.setTypeface(amar);

        TextView t1=(TextView) findViewById(R.id.textT1);
        t1.setTypeface(cicle);
        TextView t2=(TextView) findViewById(R.id.textT2);
        t2.setTypeface(cicle);
        TextView t3=(TextView) findViewById(R.id.textT3);
        t3.setTypeface(cicle);
        TextView t4=(TextView) findViewById(R.id.textT4);
        t4.setTypeface(cicle);

    }
    @Override
    public void onBackPressed(){
        finish();
        Intent myIntent=new Intent(Tuto.this,JeuAc.class);
        startActivity(myIntent);
    }
}
