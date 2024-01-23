package com.javi.cocinaconmigo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final String TAG = "cocina";
    private EditText etUsuario;
    private EditText etCon;
    private EditText etNombreUsuario;
    private EditText etConfirm;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("usuarios");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etUsuario = findViewById(R.id.etCorreo);
        etCon = findViewById(R.id.etContra);
        etConfirm = findViewById(R.id.etConfir);
        etNombreUsuario = findViewById(R.id.etNombreUsuario);
        mAuth = FirebaseAuth.getInstance();
    }

    private void registar () {
        String email = etUsuario.getText().toString().trim();
        String password = etCon.getText().toString().trim();
        String passwordCon = etConfirm.getText().toString().trim();
        final String nombreUsuario =  etNombreUsuario.getText().toString();

        if (email.length() > 0 && password.length()> 0 && passwordCon.length() > 0) {
            if (password.equals(passwordCon)) {

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Usuario usuario = new Usuario();
                                    usuario.setNombre(nombreUsuario);
                                    myRef.child(user.getUid()).setValue(usuario);



                                    updateUI(user);





                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Registro.this, "Error al registrarse",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                            }
                        });
            }
            else {
                Toast.makeText(Registro.this, "Las contraseñas no son iguales",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Registro.this, "Los campos de Usuario y Contraseña son obligatorios",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void updateUI(FirebaseUser user) {

        if (user != null) {
            startActivity(new Intent(Registro.this, MainActivity.class));
            finish();
        } else {

        }
    }

    public void clickRegistrar (View view) {
        registar();
    }
}
