package com.javi.cocinaconmigo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.List;


/**
 * Created by nuca_ on 04/04/2018.
 */

public class AdaptadorComentarios extends RecyclerView.Adapter<AdaptadorComentarios.RecetasViewHolder> implements View.OnClickListener {
    List<Comentario> comentarios;

    private StorageReference mStorageRef;
    public  Listado_Comentarios listado_comentarios;
    public static RecyclerViewEscuchadorClick escuchador;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("usuarios");

    public AdaptadorComentarios(List<Comentario> comentarios, Listado_Comentarios listado_comentarios) {
        this.comentarios = comentarios;
        this.listado_comentarios = listado_comentarios;
        escuchador = listado_comentarios;

    }


    @Override
    public RecetasViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.elemento_comentario, parent, false);
        RecetasViewHolder holder = new RecetasViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecetasViewHolder holder, int position) {
        final Comentario comentario = comentarios.get(position);

        holder.nombreAtor.setText(comentario.getAutor());
        holder.mensaje.setText(comentario.getMensaje());


    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class RecetasViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mensaje;
        TextView nombreAtor;



        public RecetasViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mensaje = itemView.findViewById(R.id.tvComenMesaje);

            nombreAtor = itemView.findViewById(R.id.tvAutorComen);


        }

        @Override
        public void onClick(View view) {
            //escuchador.escuchadorClick(view, this.getLayoutPosition());
        }
    }
}
