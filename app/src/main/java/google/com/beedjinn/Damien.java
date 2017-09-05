package google.com.beedjinn;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Damien extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.damien);

        Button toMenu = (Button) findViewById(R.id.toMenu);
        toMenu.setOnClickListener(this);

        ImageButton toPierre = (ImageButton) findViewById(R.id.toPierre);
        toPierre.setOnClickListener(this);

        ImageButton toThibaut = (ImageButton) findViewById(R.id.toThibaut);
        toThibaut.setOnClickListener(this);

        TextView myTextView = (TextView) findViewById(R.id.titre);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/Amarillo.ttf");
        myTextView.setTypeface(typeface);

        TextView myTextView2 = (TextView) findViewById(R.id.texte);
        Typeface typeface2=Typeface.createFromAsset(getAssets(), "fonts/Cicle Fina.ttf");
        myTextView2.setTypeface(typeface2);

        TextView myTextView3 = (TextView) findViewById(R.id.stitre);
        Typeface typeface3=Typeface.createFromAsset(getAssets(), "fonts/Amarillo.ttf");
        myTextView3.setTypeface(typeface3);

        final RelativeLayout layout=(RelativeLayout) findViewById(R.id.damien);
        layout.setOnTouchListener(new OnSwipeTouchListener(layout.getContext()){
            public void onSwipeLeft(){
                finish();
                Intent toPierre = new Intent(layout.getContext(),Pierre.class);
                startActivity(toPierre);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
            public void onSwipeRight(){
                finish();
                Intent toThibaut = new Intent(layout.getContext(),Thibaut.class);
                startActivity(toThibaut);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.toMenu:
                finish();
                Intent toMenu = new Intent(this,Presentation.class);
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
                break;

            case R.id.toPierre:
                finish();
                Intent toPierre = new Intent(this,Pierre.class);
                startActivity(toPierre);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.toThibaut:
                finish();
                Intent toThibaut = new Intent(this,Thibaut.class);
                startActivity(toThibaut);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;


        }
    }


}