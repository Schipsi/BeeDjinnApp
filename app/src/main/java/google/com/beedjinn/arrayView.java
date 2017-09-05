package google.com.beedjinn;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Pierre on 10/01/2017.
 */

public class arrayView {
    ImageView[] table =new ImageView[15]; //creation du tableau, valeur fixée pour le nombre d'ennemis max
    int compteur=0;//compteur permettant d'avoir un espace fixe entre les lignes d'ennemis

    public float fy;
    public float fx;

    public void init(Context ctx ){
        //initialisation, on place un ennemi dans le tableau pour lancer l'algo
        for (int i=0;i<15;i++){
            table[i]=null;
        }

        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        fx=((float)size.x)/2392;
        fy=((float)size.y)/1440;
        ImageView v1 = new ImageView(ctx);
        v1.setX(2500*fx);
        v1.setY(700*fy);
        v1.setBackgroundResource(R.drawable.jour1);
        v1.setScaleX(0.75f);
        v1.setScaleY(0.75f);
        table[14]=v1;
        compact();
    }

    public ImageView[] get(){return table;}

    public void compact(){
        // "range" le tableau de sorte que tous les elements non nuls soient en premier

        int minNul=0;
        for(int i=0;i<15;i++){
            if (table[i]!=null){
                if (i!=minNul){
                    table[minNul]=table[i];
                    table[i]=null;
                }
                minNul++;
            }


        }
    }

    public void update(Context ctx){
        compteur++;// chaque tour de la boucle de jeu on incremente le compteur car on decale tout les ennemis

        //decalage des ennemis et detection si certains sont à moins de 150px du bord pour les faire disparaitre
        for(int i=0;i<15;i++){
            if(table[i]!=null){
                table[i].setX(table[i].getX()-10*fx);
                if (table[i].getX()<=-50*fx){
                    table[i]=null;


                }
            }
        }
        for(int i=0;i<14;i++){
            if(table[i]!=null && table[i+1]!=null && table[i].getY()>table[i+1].getY()){
                ImageView temp=table[i];
                table[i]=table[i+1];
                table[i+1]=temp;
                i++;
            }
        }
        compact();//on range le tableau




        if (compteur>=100){//generation de nouveau ennemis
            if (table[12]==null){
                generateNew(0.5,ctx,true,true);
            }
            else if (table[13]==null){//si table[13]==null alors table[14] aussi --> on peut placer new 2 ennemis
                generateNew(0.5,ctx,true,false);

            }
            else {// sinon on en place 1 seul
                generateNew(0.5,ctx,false,false);
            }
            compteur=0;
        }




    }

    public void setTable(ImageView v, int i){
        table[i]=v;
        compact();

    }


    public void generateNew(double p,Context ctx,boolean b,boolean c){
        //generation des differentes coord du(es) nouveaux ennemis
        if (c && Math.random()>0.9){
            ImageView u1= new ImageView(ctx);
            ImageView u2= new ImageView(ctx);
            ImageView u3= new ImageView(ctx);
            u1.setX(2500*fx);
            u1.setY(700*fy);
            u2.setX(2500*fx);
            u2.setY(870*fy);
            u3.setX(2500*fx);
            u3.setY(1030*fy);
            u1.setBackgroundResource(R.drawable.bouteille);
            u2.setBackgroundResource(R.drawable.bouteille);
            u3.setBackgroundResource(R.drawable.bouteille);
            scale(u1,0,0);
            scale(u2,1,0);
            scale(u3,2,0);
            u1.setTag("bouteille");
            u2.setTag("bouteille");
            u3.setTag("bouteille");
            table[12]=u1;
            table[13]=u2;
            table[14]=u3;


        }
        else{
            int poshasard=(int) (3*Math.random());

            int poshas2=-1;

            double hasard=Math.random();
            if(hasard<p && b){//si la condition est verifié on placera deux ennemis
                poshas2=(poshasard+1+(int)(2*Math.random()))%3;

            }
            int pers1=(int)(2*Math.random());
            int pers2=(int)(2*Math.random());
            ImageView v1 = new ImageView(ctx);

            v1.setX(2500*fx);
            v1.setY(700*fy+fy*130*poshasard);
            if (pers1==0){
                v1.setBackgroundResource(R.drawable.perch1);
                v1.setTag("ingeson");
            }
            else {
                v1.setBackgroundResource(R.drawable.jour1);
                v1.setTag(null);
            }
            scale(v1,poshasard,pers1);
            table[14]=v1;
            if (poshas2!=-1){
                ImageView v2 = new ImageView(ctx);
                v2.setX(2500*fx);
                v2.setY(700*fy+fy*130*poshas2);
                if (pers2==0){
                    v2.setBackgroundResource(R.drawable.perch1);
                    v2.setTag("ingeson");
                }
                else {
                    v2.setBackgroundResource(R.drawable.jour1);
                    v2.setTag(null);
                }
                scale(v2,poshas2,pers2);
                table[13]=v2;
            }

        }


    }
    public void scale(ImageView v,int p,int perso){
        if (perso==0){
            if (p==0){
                v.setScaleY(1f);
                v.setScaleX(1f);

            }
            else if (p==1){
                v.setScaleY(1.1f);
                v.setScaleX(1.1f);
            }
            else if(p==2){
                v.setScaleY(1.2f);
                v.setScaleX(1.2f);
            }

        }
        else {
            if (p==0){
                v.setScaleY(0.80f);
                v.setScaleX(0.80f);

            }
            else if (p==1){
                v.setScaleY(0.90f);
                v.setScaleX(0.90f);
            }
            else if(p==2){
                v.setScaleY(1f);
                v.setScaleX(1f);
            }

        }



    }


}
