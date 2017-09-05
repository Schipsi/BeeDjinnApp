package google.com.beedjinn;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Presentation extends Activity implements OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation);

        Button nextActivity = (Button) findViewById(R.id.nextActivity);
        nextActivity.setOnClickListener(this);

        TextView myTextView = (TextView) findViewById(R.id.titre);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/Amarillo.otf");
        myTextView.setTypeface(typeface);

        TextView myTextView2 = (TextView) findViewById(R.id.texte);
        Typeface typeface2=Typeface.createFromAsset(getAssets(), "fonts/Cicle Fina.ttf");
        myTextView2.setTypeface(typeface2);

    }


    @Override
    public void onBackPressed(){
        finish();
        Intent myIntent=new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.nextActivity:
                Intent nextActivity = new Intent(this,Fantine.class);
                startActivity(nextActivity);
                //push from bottom to top
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                //slide from right to left
                //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            // More buttons go here (if any) ...

        }
    }


}
