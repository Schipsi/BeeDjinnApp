package google.com.beedjinn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Pierre on 10/01/2017.
 */

public class Allos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_allos);
    }
    @Override
    public void onBackPressed(){
        finish();
        Intent myIntent=new Intent(Allos.this,MainActivity.class);
        startActivity(myIntent);
    }
}
