package com.javi.cocinaconmigo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listado_Comentarios extends AppCompatActivity implements RecyclerViewEscuchadorClick {
    private RecyclerView recyclerView;
    public  List<Comentario> comentarios;
    private AdaptadorComentarios adaptador;
    private Receta receta;

    private Usuario usuActual;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("recetas");
    private DatabaseReference myRefUsu = database.getReference("usuarios");

    private EditText mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado__comentarios);
        mensaje = findViewById(R.id.etNewComent);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        myRefUsu.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuActual = dataSnapshot.getValue(Usuario.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Bundle bundle = getIntent().getExtras();
        receta = (Receta) bundle.getSerializable("receta");

        comentarios = new ArrayList<>();
        adaptador = new AdaptadorComentarios(comentarios, this);
        recyclerView = findViewById(R.id.listaComentarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adaptador);

        myRef.child(receta.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comentarios.clear();
                Receta recComen = dataSnapshot.getValue(Receta.class);

                if (recComen.getComentarios() != null) {
                    comentarios.addAll(recComen.getComentarios());
                }

                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));


    }

    @Override
    public void escuchadorClick(View view, int posicion) {

    }

    public void aniadirComen (View v) {
        Comentario c = new Comentario();
        c.setMensaje(mensaje.getText().toString());
        c.setAutor(usuActual.getNombre());
        comentarios.add(c);
        receta.setComentarios((ArrayList<Comentario>) comentarios);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(receta.getKey(), receta);
        myRef.updateChildren(childUpdates);
        mensaje.setText("");
    }
}
