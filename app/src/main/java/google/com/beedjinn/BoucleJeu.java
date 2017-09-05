package google.com.beedjinn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class BoucleJeu extends Thread {
    public boolean running;
    public Activity act;
    public long sleepTime = 10;

    public final static String HIGHSCORE = "0";
    public final static String ALCOOL = "0.0";



    /** Ecran de jeu */
    public GameView screen;

    public Point size=new Point();

    public arrayView grilleDeJeu=new arrayView();


    public boolean isJumping=false;
    public boolean isChangingLine=false;
    public boolean isApogee=false;

    public float fx;
    public float fy;

    public MediaPlayer theme ;
    public boolean sonIsOn;







    private Context context;
    /** activer ou désactiver l'animation*/


    public void initGame(final Context context, final Activity act, boolean b) {//init variables
        this.context = context;
        this.sonIsOn=b;
        theme = MediaPlayer.create(context, R.raw.theme);
        if (sonIsOn==false){
            theme.setVolume(0,0);
        }


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getSize(size);
        this.act=act;//recupération de l'activité principale (JeuMain)
        running = true;
        this.screen = new GameView(context, this,grilleDeJeu); //creation ecran
        screen.init();//init ecran
        screen.setOnTouchListener(new OnSwipeTouchListener(screen.getContext()){
            public void onTap() {
                final Animation jumping = AnimationUtils.loadAnimation(context, R.anim.jump);
                double proba=Math.random();
                if (proba<(screen.alcoolemie)*0.05)
                {
                    int choixInc=(int) (3*Math.random());
                    if (choixInc==0){
                        onTap(false);
                        return;

                    }
                    else if (choixInc==1){
                        onSwipeBottom(false);
                        return;
                    }
                    else if (choixInc==2){
                        onSwipeTop(false);
                        return;
                    }
                }

                jumping.setDuration(250-15*(10-(int)sleepTime));
                if (isChangingLine == false && isJumping == false && running) {


                    isJumping=true;
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_saut);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(jumping);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(50-4*(10-(int)sleepTime));
                                isApogee=true;
                                Thread.sleep(450-20*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    screen.getViewPerso().clearAnimation();
                                    if (running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }

                                    isApogee=false;
                                    isJumping = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }
            }

            public void onSwipeTop() throws InterruptedException {
                final Animation changeLineUp = AnimationUtils.loadAnimation(context, R.anim.chgup);
                double proba=Math.random();
                if (proba<(screen.alcoolemie)*0.05)
                {
                    int choixInc=(int) (3*Math.random());
                    if (choixInc==0){
                        onTap(false);
                        return;

                    }
                    else if (choixInc==1){
                        onSwipeBottom(false);
                        return;
                    }
                    else if (choixInc==2){
                        onSwipeTop(false);
                        return;
                    }
                }
                changeLineUp.setDuration(250-15*(10-(int)sleepTime));
                if (screen.getPos() != 0 && isChangingLine == false && isJumping == false && running) {
                    isChangingLine = true;
                    screen.setPos(screen.getPos()-1);
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_back_perso);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(changeLineUp);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(250-15*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                                    screen.getViewPerso().setScaleX(scale);
                                    screen.getViewPerso().setScaleY(scale);
                                    screen.getViewPerso().clearAnimation();
                                    if(running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }



                                    isChangingLine = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }

            }

            public void onSwipeBottom() throws InterruptedException {
                final Animation changeLineDown = AnimationUtils.loadAnimation(context, R.anim.chgdown);

                double proba=Math.random();
                if (proba<(screen.alcoolemie)*0.05)
                {
                    int choixInc=(int) (3*Math.random());
                    if (choixInc==0){
                        onTap(false);
                        return;

                    }
                    else if (choixInc==1){
                        onSwipeBottom(false);
                        return;
                    }
                    else if (choixInc==2){
                        onSwipeTop(false);
                        return;
                    }
                }

                changeLineDown.setDuration(250-15*(10-(int)sleepTime));
                if (screen.getPos() != 2 && isChangingLine == false && isJumping == false && running) {
                    isChangingLine = true;
                    screen.setPos(screen.getPos()+1);
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_face_perso);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(changeLineDown);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(250-15*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                                    screen.getViewPerso().setScaleX(scale);
                                    screen.getViewPerso().setScaleY(scale);
                                    screen.getViewPerso().clearAnimation();
                                    if (running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }



                                    isChangingLine = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }
            }

            public void onTap(boolean b) {
                final Animation jumping = AnimationUtils.loadAnimation(context, R.anim.jump);


                jumping.setDuration(250-15*(10-(int)sleepTime));
                if (isChangingLine == false && isJumping == false && running) {


                    isJumping=true;
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_saut);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(jumping);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(50-4*(10-(int)sleepTime));
                                isApogee=true;
                                Thread.sleep(450-20*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    screen.getViewPerso().clearAnimation();
                                    if (running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }

                                    isApogee=false;
                                    isJumping = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }
            }

            public void onSwipeTop(boolean b)  {
                final Animation changeLineUp = AnimationUtils.loadAnimation(context, R.anim.chgup);
                changeLineUp.setDuration(200-15*(10-(int)sleepTime));
                if (screen.getPos() != 0 && isChangingLine == false && isJumping == false && running) {
                    isChangingLine = true;
                    screen.setPos(screen.getPos()-1);
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_back_perso);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(changeLineUp);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(200-15*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                                    screen.getViewPerso().setScaleX(scale);
                                    screen.getViewPerso().setScaleY(scale);
                                    screen.getViewPerso().clearAnimation();
                                    if(running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }



                                    isChangingLine = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }

            }

            public void onSwipeBottom(boolean b) {
                final Animation changeLineDown = AnimationUtils.loadAnimation(context, R.anim.chgdown);
                changeLineDown.setDuration(200-15*(10-(int)sleepTime));
                if (screen.getPos() != 2 && isChangingLine == false && isJumping == false && running) {
                    isChangingLine = true;
                    screen.setPos(screen.getPos()+1);
                    screen.getViewPerso().setBackgroundResource(R.drawable.animation_face_perso);
                    screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                    screen.getAnimPerso().start();
                    screen.getViewPerso().clearAnimation();
                    screen.getViewPerso().startAnimation(changeLineDown);

                    final Thread thread = new Thread() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(200-15*(10-(int)sleepTime));
                            } catch (InterruptedException ignored) {
                            }
                            act.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                                    screen.getViewPerso().setScaleX(scale);
                                    screen.getViewPerso().setScaleY(scale);
                                    screen.getViewPerso().clearAnimation();
                                    if (running){
                                        screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                        screen.getAnimPerso().start();

                                    }



                                    isChangingLine = false;
                                }
                            });
                        }
                    };
                    thread.start();

                }
            }

        });



        //detection des events (tourne dans une boucle tout seul) et reponse aux event
    }



    public void onTap(boolean b) {
        final Animation jumping = AnimationUtils.loadAnimation(context, R.anim.jump);


        jumping.setDuration(250-15*(10-(int)sleepTime));
        if (isChangingLine == false && isJumping == false && running) {


            isJumping=true;
            screen.getViewPerso().setBackgroundResource(R.drawable.animation_saut);
            screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
            screen.getAnimPerso().start();
            screen.getViewPerso().clearAnimation();
            screen.getViewPerso().startAnimation(jumping);

            final Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(50-4*(10-(int)sleepTime));
                        isApogee=true;
                        Thread.sleep(450-20*(10-(int)sleepTime));
                    } catch (InterruptedException ignored) {
                    }
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            screen.getViewPerso().clearAnimation();
                            if (running){
                                screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                screen.getAnimPerso().start();

                            }

                            isApogee=false;
                            isJumping = false;
                        }
                    });
                }
            };
            thread.start();

        }
    }

    public void onSwipeTop(boolean b)  {
        final Animation changeLineUp = AnimationUtils.loadAnimation(context, R.anim.chgup);
        changeLineUp.setDuration(200-15*(10-(int)sleepTime));
        if (screen.getPos() != 0 && isChangingLine == false && isJumping == false && running) {
            isChangingLine = true;
            screen.setPos(screen.getPos()-1);
            screen.getViewPerso().setBackgroundResource(R.drawable.animation_back_perso);
            screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
            screen.getAnimPerso().start();
            screen.getViewPerso().clearAnimation();
            screen.getViewPerso().startAnimation(changeLineUp);

            final Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(200-15*(10-(int)sleepTime));
                    } catch (InterruptedException ignored) {
                    }
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                            screen.getViewPerso().setScaleX(scale);
                            screen.getViewPerso().setScaleY(scale);
                            screen.getViewPerso().clearAnimation();
                            if(running){
                                screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                screen.getAnimPerso().start();

                            }



                            isChangingLine = false;
                        }
                    });
                }
            };
            thread.start();

        }

    }

    public void onSwipeBottom(boolean b) {
        final Animation changeLineDown = AnimationUtils.loadAnimation(context, R.anim.chgdown);
        changeLineDown.setDuration(200-15*(10-(int)sleepTime));
        if (screen.getPos() != 2 && isChangingLine == false && isJumping == false && running) {
            isChangingLine = true;
            screen.setPos(screen.getPos()+1);
            screen.getViewPerso().setBackgroundResource(R.drawable.animation_face_perso);
            screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
            screen.getAnimPerso().start();
            screen.getViewPerso().clearAnimation();
            screen.getViewPerso().startAnimation(changeLineDown);

            final Thread thread = new Thread() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(200-15*(10-(int)sleepTime));
                    } catch (InterruptedException ignored) {
                    }
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            float scale=(float)(0.9-0.15*((float) (1-screen.getPos())));
                            screen.getViewPerso().setScaleX(scale);
                            screen.getViewPerso().setScaleY(scale);
                            screen.getViewPerso().clearAnimation();
                            if (running){
                                screen.getViewPerso().setBackgroundResource(R.drawable.animation_perso);
                                screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
                                screen.getAnimPerso().start();

                            }



                            isChangingLine = false;
                        }
                    });
                }
            };
            thread.start();

        }
    }



    public long getSleep(){return sleepTime;}

    public void setSleep(long l){this.sleepTime=l;}

    /** la boucle de jeu */
    @Override
    public void run() {
        theme.start();
        while (this.running) {
            act.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    screen.display();//affichage et update de l'ecran + des positions des ennemis
                    double proba=Math.random();
                    if (proba<(screen.alcoolemie)*0.001)
                    {
                        int choixInc=(int) (2*Math.random());
                        if (choixInc==0){
                            onSwipeTop(false);
                            return;

                        }
                        else if (choixInc==1){
                            onSwipeBottom(false);
                            return;
                        }
                    }
                    screen.collision(isJumping,isChangingLine,isApogee);
                    if (screen.getGameOver()){
                        running=false;
                        gameOver();

                    }
                }
            });

            try {
                Thread.sleep(sleepTime);//ralentissement de la boucle de jeu pour avoir du temps
            } catch (InterruptedException e) {
            }
        }
    }

    public void gameOver(){
        theme.stop();
        screen.getViewPerso().setBackgroundResource(R.drawable.animation_chute);
        screen.setAnimPerso((AnimationDrawable) screen.getViewPerso().getBackground());
        screen.getAnimPerso().start();

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        fx=((float)size.x)/2392f;
        fy=((float)size.y)/1440f;

        screen.removeView(screen.scoreD);
        screen.removeView(screen.jauge);
        screen.removeView(screen.texteJauge);

        ImageView overView=new ImageView(context);
        ImageButton restart= new ImageButton(context);
        overView.setBackgroundResource(R.drawable.over);
        restart.setBackgroundResource(R.drawable.restart);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = 10f;
        float fpixels = metrics.density * dp;
        int textSize = (int) (fpixels + 0.5f);


        screen.addView(overView);
        screen.addView(restart);


        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) overView.getLayoutParams();
        lp1.addRule(RelativeLayout.CENTER_IN_PARENT);

        restart.setX(375*fx);
        restart.setY(740*fy);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        int highscore =  Integer.parseInt(preferences.getString(HIGHSCORE, "0"));
        float alcoolMax=Float.parseFloat(preferences.getString(ALCOOL,"0.0"));
        if(screen.score/10>highscore)
        {
            editor.putString(HIGHSCORE, Integer.toString(screen.score/10));

            editor.commit();
        }

        if(screen.alcoolemie>alcoolMax)
        {
            editor.putString(ALCOOL, Float.toString(screen.alcoolemie));

            editor.commit();
        }
        TextView highscoreV=new TextView(context);
        TextView alcoolMaxV=new TextView(context);
        TextView gameOverTitle=new TextView(context);

        Typeface amar = Typeface.createFromAsset(context.getAssets(), "fonts/Amarillo.otf");
        Typeface cicle = Typeface.createFromAsset(context.getAssets(), "fonts/Cicle Fina.ttf");

        gameOverTitle.setText(" Wasted ");
        gameOverTitle.measure(0,0);
        gameOverTitle.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        gameOverTitle.setX(size.x/2-gameOverTitle.getMeasuredWidth()*metrics.density/2);

        gameOverTitle.setY(fy*25f*dp+0.5f);
        gameOverTitle.setTextSize(textSize);
        gameOverTitle.setTextColor(Color.parseColor("#F4B32D"));
        gameOverTitle.setTypeface(amar);
        gameOverTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        screen.addView(gameOverTitle);

        int strId=0;
        int n=1;
        strId=context.getResources().getIdentifier("s"+Integer.toString(1+(int)(n*Math.random())),"string","google.com.beedjinn");
        String quote=context.getResources().getString(strId);
        TextView citation=new TextView(context);
        citation.setText(quote);
        citation.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
        citation.setX(780*fx);
        citation.setY(880*fy);
        citation.setTextColor(Color.parseColor("#F4B32D"));
        citation.setTypeface(cicle);
        screen.addView(citation);

        highscoreV.setTypeface(cicle);
        highscoreV.setText("Highscore: "+Float.toString(highscore)+"m");
        highscoreV.setTextColor(Color.parseColor("#F4B32D"));
        highscoreV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        highscoreV.setX(fx*140f*dp+0.5f);
        highscoreV.setY(fy*56*dp+0.5f);
        screen.addView(highscoreV);

        TextView scoreFin=new TextView(context);
        scoreFin.setText("Distance parcourue: "+Integer.toString(screen.score/10)+"m");
        scoreFin.setTextColor(Color.parseColor("#F4B32D"));
        scoreFin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        scoreFin.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        scoreFin.setX(fx*44f*dp+0.5f);
        scoreFin.setY(fy*56f*dp+0.5f);
        scoreFin.setTypeface(cicle);


        screen.addView(scoreFin);

        alcoolMaxV.setTypeface(cicle);
        alcoolMaxV.setText("Highscore: "+Float.toString(alcoolMax)+"g/L");
        alcoolMaxV.setTextColor(Color.parseColor("#F4B32D"));
        alcoolMaxV.setX(fx*140f*dp+0.5f);
        alcoolMaxV.setY(fy*66*dp+0.5f);
        alcoolMaxV.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        screen.addView(alcoolMaxV);

        TextView alcoolFin=new TextView(context);
        alcoolFin.setText("Taux d'alcoolémie: "+ Float.toString(screen.alcoolemie)+"g/L");
        alcoolFin.setTextColor(Color.parseColor("#F4B32D"));
        alcoolFin.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        alcoolFin.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        alcoolFin.setX(fx*44f*dp+0.5f);
        alcoolFin.setY(fy*66f*dp+0.5f);
        alcoolFin.setTypeface(cicle);

        screen.addView(alcoolFin);



        restart.setScaleX(0.5f);
        restart.setScaleY(0.5f);
        overView.setLayoutParams(lp1);


        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(act,JeuMain.class);
                myIntent.putExtra("son",sonIsOn);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
                act.finish();
                context.startActivity(myIntent);}});







    }


}

