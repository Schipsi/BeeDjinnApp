package google.com.beedjinn;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        Typeface amar = Typeface.createFromAsset(this.getAssets(), "fonts/Amarillo.otf");
        Typeface cicle = Typeface.createFromAsset(this.getAssets(), "fonts/Cicle Fina.ttf");
        Typeface cicleB = Typeface.createFromAsset(this.getAssets(), "fonts/cicle_bold.ttf");

        Button button1= (Button) findViewById(R.id.button1);
        button1.setTypeface(cicleB);
        Button button2= (Button) findViewById(R.id.button2);
        button2.setTypeface(cicleB);
        Button button3= (Button) findViewById(R.id.button3);
        button3.setTypeface(cicleB);
        Button button4= (Button) findViewById(R.id.button4);
        button4.setTypeface(cicleB);

        TextView title=(TextView) findViewById(R.id.textViewTitleMain);
        title.setTypeface(amar);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,Presentation.class);
                finish();
                startActivity(myIntent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,JeuAc.class);
                finish();
                startActivity(myIntent);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,Allos.class);
                finish();
                startActivity(myIntent);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(MainActivity.this,Partenariats.class);
                finish();
                startActivity(myIntent);
            }
        });
    }

}
