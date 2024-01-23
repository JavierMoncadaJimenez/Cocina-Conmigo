package com.javi.cocinaconmigo;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recetas extends Fragment implements RecyclerViewEscuchadorClick {

    private RecyclerView recyclerView;
    public static List<Receta> recetas;
    private Adaptador adaptador;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("recetas");

    public Recetas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_activity_recetas, container, false);
        recyclerView = view.findViewById(R.id.lista);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        recetas = new ArrayList<>();
        adaptador = new Adaptador(recetas, this);

        recyclerView.setAdapter(adaptador);

        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(),DividerItemDecoration.VERTICAL));



        final FloatingActionButton fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), AniadirRecetaActivity.class);

                startActivity(i);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if (dy > 0 || dy < 0 && fab.isShown())
                {
                    fab.hide();
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                {
                    fab.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        myRef.orderByChild("fechaSubida").limitToFirst(100).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recetas.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Receta receta = snapshot.getValue(Receta.class);
                    recetas.add(0 ,receta);
                }

                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void escuchadorClick(View view, int posicion) {
        Intent intent = new Intent(getView().getContext(), RecetaCompleta.class);
        intent.putExtra("receta", recetas.get(posicion));
        startActivity(intent);
    }

}
