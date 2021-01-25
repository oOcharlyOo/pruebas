package com.example.ahorcado;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class letrasAdapter extends BaseAdapter { /*este sera las letras que se veran para poder adivinar la palabra*/
    private String[] letras;
    private LayoutInflater letrasInf;

    public letrasAdapter(Context context){
        letras = new String[26];/*se elije un tamaño de 26 letras*/
        for(int i=0;i<letras.length;i++){
            letras[i]=""+(char)(i+'A'); /*a qui indicamos que seran todas las letras del alfabeto en orden y se guardaran en el arreglo  */
        }
        letrasInf=LayoutInflater.from(context);/*crearemos la vista*/


    }

    @Override
    public int getCount() {
        return letras.length;/*se especifica el tamallo del adaptador*/
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i ) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Button btnletras;
        if(view==null){/*se creara la vista dependiendo si es null de lo contrario solo se guardara en la variable que ya esta definida */
            btnletras=(Button)letrasInf.inflate(R.layout.letters,viewGroup, false);


        }else {
            btnletras=(Button)view;
        }
        btnletras.setText(letras[i]); /* se agrega el texto desde el arreglo */
        return btnletras;
    }
}
