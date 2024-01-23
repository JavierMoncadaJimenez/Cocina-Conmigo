package com.javi.cocinaconmigo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;



/**
 * Created by nuca_ on 04/04/2018.
 */

public class Adaptador extends RecyclerView.Adapter<Adaptador.RecetasViewHolder> implements View.OnClickListener {
    List<Receta> recetas;

    private StorageReference mStorageRef;
    public  Recetas visorReceta;
    public static RecyclerViewEscuchadorClick escuchador;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("usuarios");

    public Adaptador(List<Receta> recetas, Recetas visorReceta) {
        this.recetas = recetas;
        this.visorReceta = visorReceta;
        escuchador = visorReceta;
    }


    @Override
    public RecetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_receta, parent, false);
        RecetasViewHolder holder = new RecetasViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecetasViewHolder holder, int position) {
        final Receta receta = recetas.get(position);

        holder.titulo.setText(receta.getTitulo());



        mStorageRef = FirebaseStorage.getInstance().getReference();

        Glide.with(visorReceta).
        load(receta.getNombreFoto()).into(holder.foto);

        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String uid = snapshot.getKey();

                    if (uid.equals(receta.getUidAtuor())) {
                        Usuario usuario = snapshot.getValue(Usuario.class);
                        holder.nombreAtor.setText("Subida por: " + usuario.getNombre());

                    }

                }
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return recetas.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class RecetasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView titulo;

        TextView nombreAtor;
        ImageView foto;


        public RecetasViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            titulo = itemView.findViewById(R.id.tvTitulo);

            nombreAtor = itemView.findViewById(R.id.nombreAutor);
            foto = itemView.findViewById(R.id.imageView);

        }

        @Override
        public void onClick(View view) {
            escuchador.escuchadorClick(view, this.getLayoutPosition());
        }
    }
}
