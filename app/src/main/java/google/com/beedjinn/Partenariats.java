package google.com.beedjinn;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class Partenariats extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_partenariats);

        Typeface amar = Typeface.createFromAsset(this.getAssets(), "fonts/Amarillo.otf");
        Typeface cicle = Typeface.createFromAsset(this.getAssets(), "fonts/Cicle Fina.ttf");

        TextView title=(TextView) findViewById(R.id.Partenariats);
        title.setTypeface(amar);

    }
    @Override
    public void onBackPressed(){
        finish();
        Intent myIntent=new Intent(Partenariats.this,MainActivity.class);
        startActivity(myIntent);
    }
}