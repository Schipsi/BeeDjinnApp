package google.com.beedjinn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class GameView extends RelativeLayout implements SurfaceHolder.Callback {

    public int width; //largeur de l'écran
    public int height; //hauteur de l'écran

    public boolean isGameOver=false;

    public int score=0;
    public TextView scoreD;
    public int mod=1000;
    public float alcoolemie=0;

    public MediaPlayer wasted;
    public MediaPlayer bottle;
    public boolean sonIsOn;

    public ImageView jauge;
    public TextView texteJauge;


    Typeface cicle = Typeface.createFromAsset(this.getContext().getAssets(), "fonts/Cicle Fina.ttf");

    public int indexCurrentCol=0;

    public boolean isCollided=false;

    public position_perso positionPerso=new position_perso();
    public AnimationDrawable animPerso=new AnimationDrawable();
    public AnimationDrawable animFond=new AnimationDrawable();
    public ImageView perso;

    public ImageView bg;

    public float fx;
    public float fy;

    private BoucleJeu game; //pointeur vers la boucle de jeu
    public arrayView grilleDeJeu=new arrayView();//tableau des ennemis

    public ImageView[] sol=new ImageView[20];


    public GameView(Context context, BoucleJeu game, arrayView G) {//creation de l'objet ecran
        super(context);
        this.game = game;
        this.grilleDeJeu=G;

    }
    public void init(){

        this.setBackgroundResource(R.drawable.fond_game);

        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        this.sonIsOn=this.game.sonIsOn;

        width = size.x;
        height = size.y;
        fx=((float)width)/2392f;
        fy=((float)height)/1440f;

        DisplayMetrics metrics = this.getContext().getResources().getDisplayMetrics();
        float dp = 10f;
        float fpixels = metrics.density * dp;
        int textSize = (int) (fpixels + 0.5f);



        wasted=MediaPlayer.create(getContext(), R.raw.wasted);
        bottle= MediaPlayer.create(getContext(), R.raw.bottle);

        if (sonIsOn==false){
            wasted.setVolume(0,0);
            bottle.setVolume(0,0);
        }



        texteJauge=new TextView(this.getContext());
        texteJauge.setX(1500*fx);
        texteJauge.setY(size.y/20);
        texteJauge.setText("Taux d'alcoolémie: 0 g/L ");
        texteJauge.setTextSize(textSize-1.2f*dp);
        texteJauge.setTypeface(cicle);
        texteJauge.setTextColor(Color.parseColor("#EE1616"));
        this.addView(texteJauge);

        positionPerso.setPos(1);//init perso position
        grilleDeJeu.init(this.getContext());//init du tableau
        scoreD=new TextView(this.getContext());
        scoreD.setText("Distance: "+Integer.toString(score)+"m");
        this.addView(scoreD);



        scoreD.setY(size.y/20);
        scoreD.setTextColor(Color.parseColor("#EE1616"));
        scoreD.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        scoreD.setX(100*fx);
        scoreD.setTextSize(textSize-1.2f*dp);
        scoreD.setTypeface(cicle);
        scoreD.bringToFront();
        //mise en place du fond


        initSol();



        ImageView masque=new ImageView(((RelativeLayout)this).getContext());
        masque.setBackgroundResource(R.drawable.masque);
        masque.setY(420);
        //this.addView(masque);


        //affichage du perso
        perso=new ImageView(((RelativeLayout)this).getContext());
        perso.setBackgroundResource(R.drawable.animation_perso);
        this.addView(perso);
        animPerso=(AnimationDrawable) perso.getBackground();
        animPerso.start();
        perso.setX(0);
        perso.setY(0);


        this.game.start();//lancement de la boucle de jeu


        //idem avec background
    }

    public void initSol(){
        for(int i=0;i<20;i++){
            sol[i]=new ImageView(((RelativeLayout)this).getContext());
            if(i%5==0){
                sol[i].setBackgroundResource(R.drawable.sol_1);

            }
            else if(i%5==1){
                sol[i].setBackgroundResource(R.drawable.sol_2);
            }
            else if(i%5==2){
                sol[i].setBackgroundResource(R.drawable.sol_3);
            }
            else if(i%5==3){
                sol[i].setBackgroundResource(R.drawable.sol_4);
            }
            else if(i%5==4){
                sol[i].setBackgroundResource(R.drawable.sol_5);
            }
            sol[i].setX(150*i*fx);
            sol[i].setY(850*fy);
        }
    }




    public void updateSol(){
        for (int i=0;i<20;i++){
            sol[i].setX(sol[i].getX()-10*fx);
        }
        if (sol[0].getX()<=-250*fx){
            ImageView temp=sol[0];
            sol[0]=null;
            for (int i=1;i<20;i++){
                sol[i-1]=sol[i];

            }
            sol[19]=temp;
            sol[19].setX(fx*19*150-fx*100);

        }

    }
    public int getPos(){return positionPerso.get();}

    public boolean getGameOver(){return  isGameOver;}


    public void setPos(int p){positionPerso.setPos(p);}

    public AnimationDrawable getAnimPerso(){return animPerso;}

    public void setAnimPerso(AnimationDrawable anim){animPerso=anim;}

    public ImageView getViewPerso(){return perso;}

    public void displaySol(){
        updateSol();
        for (int i=0;i<20;i++){
            this.removeView(sol[i]);
            this.addView(sol[i]);
        }
    }

    public void display(){
        //update de la grille puis affichage
        score++;
        displaySol();
        perso.bringToFront();
        scoreD.bringToFront();


        if (score>1000 && mod<2000){
            mod=2000;
        }
        else if (score>4000 && mod <5000){
            mod=5000;
        }
        if(score%(mod)==0 && game.getSleep()>2){
            game.setSleep(game.getSleep()-1);
        }
        scoreD.setText("Distance: "+Integer.toString((int)(score/10))+"m");
        perso.setY((675+positionPerso.get()*130)*fy);


        ImageView[] grilleJeu=grilleDeJeu.get();
        for(int i=0;i<15;i++){
            if (grilleJeu[i]!=null){
                this.removeView(grilleJeu[i]);
            }
        }
        grilleDeJeu.update(this.getContext());

        for(int i=0;i<15;i++){
            if (grilleJeu[i]!=null){
                this.addView(grilleJeu[i]);
            }
        }

    }

    public void collision(boolean isJumping,boolean isChangingLine,boolean isApogee){

        for(int i=0;i<15;i++){
            if (grilleDeJeu.get()[i]!=null){
                ImageView img=grilleDeJeu.get()[i];



                if (img.getX()<150*fx && (int)((img.getY()/fy-675)/130)==positionPerso.get()){


                    if (img.getTag()!= null && img.getTag()!="bouteille"){

                        if (isChangingLine==false){

                            gameOver();

                        }
                    }
                    else if (img.getTag()=="bouteille" && isJumping==false ){


                        if (isCollided==false){
                            bottle.setLooping(false);
                            bottle.start();
                            alcoolemie=alcoolemie+0.25f;
                            isCollided=true;
                            indexCurrentCol=i;
                            this.removeView(img);
                            grilleDeJeu.setTable(null,i);
                            texteJauge.setText("Taux d'alcoolémie: "+ Float.toString(alcoolemie)+" g/L ");

                        }



                    }
                    else if (isChangingLine==false && isApogee==false && img.getTag()!="bouteille"){

                        gameOver();



                    }
                    else if (isChangingLine){

                        if (i<14 && i>0 &&(grilleDeJeu.get()[i+1].getX()==img.getX() || grilleDeJeu.get()[i-1].getX()==img.getX())){
                            gameOver();
                        }
                        else if (i==0 && grilleDeJeu.get()[i+1].getX()==img.getX()){
                            gameOver();
                        }
                        else if (i==14 && grilleDeJeu.get()[i-1].getX()==img.getX()){
                            gameOver();
                        }
                    }

                }
                else if(i== indexCurrentCol){
                    isCollided=false;
                }

            }




        }
    }
    public void gameOver(){
        isGameOver=true;
        wasted.setLooping(false);
        if  (sonIsOn){
            wasted.setVolume(500,500);
        }
        wasted.start();

        for (int i=0;i<15;i++){
            this.removeView(grilleDeJeu.get()[i]);
        }


    }

    // Methode suivantes ne font rien mais sont nécessaires à l'implementation de la classe CallBack
    public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {
        this.width = width;
        this.height = height;

        this.game.start();
    }

    public void surfaceCreated(SurfaceHolder holder) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }


}
