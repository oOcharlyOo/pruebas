package com.example.ahorcado;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private String[] words; /*arreglo para guardar las palabras del juego*/
    private Random random; /*genera un numero aleatorio*/
    private String anterior;
    private TextView[] charView; /* arreglo para las letras en pantalla */
    private LinearLayout wordLayout;/*para las lineas de la palabra a adivinar*/
    private letrasAdapter adapter; /*creamos el adaptador*/
    private GridView gridView;/*para que agregemos el adaptador al gridview*/
    private int numCorr;
    private int numChars;
    private ImageView[]partes;
    private int Tapartes=6;
    private int currpart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        words=getResources().getStringArray(R.array.words);
        wordLayout=findViewById(R.id.words);
        gridView=findViewById(R.id.letters);
        random=new Random();

        partes=new ImageView[Tapartes];
        partes[0]= findViewById(R.id.head);
        partes[1]= findViewById(R.id.body);
        partes[2]= findViewById(R.id.armLeft);
        partes[3]= findViewById(R.id.armRight);
        partes[4]= findViewById(R.id.legLeft);
        partes[5]= findViewById(R.id.legRight);


    }
    private void playGame(){ /*aqui sera la palabra que tendra que adivinar el usuario*/
        String newWord=words[random.nextInt(words.length)]; /*sirve para que la palbra que se vaya a divinar sea diferente con el random */

        while (newWord.equals(anterior))newWord=words[random.nextInt(words.length)]; /* ciclo while para verificar que no se repita la misma palabra*/

        anterior=newWord;

        charView= new  TextView[anterior.length()];

        for(int i=0; i<anterior.length(); i++)/*para modificar cada textview donde se guardara cada letra de la palabra*/{
            charView[i]=new TextView(this);
        charView[i].setText(""+anterior.charAt(i));
         charView[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
         charView[i].setGravity(Gravity.CENTER);
        charView[i].setTextColor(Color.WHITE);
        charView[i].setBackgroundResource(R.drawable.letter_bg);
        wordLayout.addView(charView[i]);


        }
        adapter=new letrasAdapter(this);/* instanciamos la clase agregando el contexto que se especifico en el metodo constructor */
        gridView.setAdapter(adapter);/*se agrega el adptador al gridview*/
        numCorr=0;
        currpart=0;
        numChars=anterior.length();


    }
    public void Presionar(View view){ /* metodo cuando se presiona el botton*/
        String letter=((TextView)view).getText().toString(); /*se toma el texto del boton que se acaba de dar click y para eso hay que convertirlo a textview*/
        char letterChar=letter.charAt(0);

        view.setEnabled(false);/*se tiene que desabilitar el boton que lla se ingreso para validar el formulario*/
        boolean correct=false;
        for(int i=0;i<anterior.length();i++){/*recorrera la palabra para verificar si la letra presionada es correcta*/
            if (anterior.charAt(i)==letterChar){/*con el if comparamos cada una de las letras*/
                correct=true;
                numCorr++;
                charView[i].setTextColor(Color.BLACK);
            }
        }
        if(correct){
            if (numCorr==numChars){
                quitarbuttons();
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Ganaste");
                builder.setMessage("Felicidades \n\n la respuesta era \n\n" + anterior );
                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.playGame();
                    }
                });
                builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameActivity.this.finish();
                    }
                });
                builder.show();


            }

        }else if (currpart<Tapartes){
            partes[currpart].setVisibility(View.VISIBLE);
            currpart++;

        }else{
            quitarbuttons();
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Perdiste");
            builder.setMessage("Ya perdiste \n\n la respuesta era \n\n" + anterior );
            builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.playGame();
                }
            });
            builder.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.finish();
                }
            });
            builder.show();
        }

    }
    public void quitarbuttons(){
        for(int i=0; i<gridView.getChildCount();i++){
            gridView.getChildAt(i).setEnabled(false);
        }
    }
}