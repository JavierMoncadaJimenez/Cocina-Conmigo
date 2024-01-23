package com.javi.cocinaconmigo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditarUsuario extends Fragment implements View.OnClickListener {

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference("usuarios");
    private EditText nombre;
    private Button bntEditar;
    private Usuario usuActual;
    private static final int GALERIA_INTEN = 1;
    private static final int PERMISO_ESCRITURA = 2;


    public EditarUsuario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_editar_usuario, container, false);
        nombre = v.findViewById(R.id.etEditarNombreUsuario);

        bntEditar = v.findViewById(R.id.bntEditar);

        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                usuActual = dataSnapshot.getValue(Usuario.class);
                nombre.setText(usuActual.getNombre());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        bntEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> childUpdates = new HashMap<>();

                Usuario usuario = new Usuario();
                usuario.setNombre(nombre.getText().toString());

                childUpdates.put(uid, usuario);



                database.updateChildren(childUpdates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(v.getContext(), "Datos del usuario modificados",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return v;
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bntEditar) {

        }
    }
}
