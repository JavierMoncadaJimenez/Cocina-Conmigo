package com.javi.cocinaconmigo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RecetaCompleta extends Activity {

    private ImageView foto;
    private TextView titulo;
    private TextView cuerpo;
    private TextView ingredientes;
    private Receta receta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receta_completa);

        Bundle bundle = getIntent().getExtras();

        receta = (Receta) bundle.getSerializable("receta");

        foto = findViewById(R.id.imagenRecetaCompleta);
        titulo = findViewById(R.id.tvTituloCompleto);
        ingredientes = findViewById(R.id.tvIngredientesCompleto);
        cuerpo = findViewById(R.id.tvCuerpoCompleto);

        Glide.with(this).
                load(receta.getNombreFoto()).into(foto);



        titulo.setText(receta.getTitulo());
        cuerpo.setText(receta.getCuerpo());
        ingredientes.setText(receta.getIngredientes());
    }

    public void mostrarComen (View v) {
        Intent intent = new Intent(this, Listado_Comentarios.class);
        intent.putExtra("receta", receta);
        startActivity(intent);
    }
}
