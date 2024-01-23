package com.javi.cocinaconmigo;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AniadirRecetaActivity extends AppCompatActivity {
    private static final int GALERIA_INTEN = 1;
    private static final int PERMISO_ESCRITURA = 2;

    private Uri foto;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("recetas");
    private StorageReference mStorageRef;
    private EditText titulo;
    private EditText cuerpo;
    private EditText ingrediente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aniadir_receta);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        titulo = findViewById(R.id.etTituloReceta);
        cuerpo = findViewById(R.id.etCuerpoReceta);
        ingrediente = findViewById(R.id.etIngredientesRecetaAniadir);

    }

    public void selecionarFoto (View view) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(intent, GALERIA_INTEN);
        }
        else {
            solicitarPermiso(Manifest.permission.READ_EXTERNAL_STORAGE, "Sin el permiso de lectura no se podra selecionar fotos .",
                    PERMISO_ESCRITURA, this);
        }




    }

    protected void onActivityResult (int recuestCode, int resultcode, Intent data) {
        if (data != null) {
            foto = data.getData();
            Glide.with(this).load(foto).into((ImageView) findViewById(R.id.ivAniadirReceta));

        }
    }

    public void subirDatos (View view) {

        final EditText titulo = findViewById(R.id.etTituloReceta);
        final EditText cuerpo = findViewById(R.id.etCuerpoReceta);
        final EditText ingrediente = findViewById(R.id.etIngredientesRecetaAniadir);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //final Receta receta = new Receta();

        if (foto == null) {
            Receta receta = new Receta();
            receta.setNombreFoto("https://firebasestorage.googleapis.com/v0/b/cocinaconmigo-86fa3.appspot.com/o/FotosRecetas%2Fcutlery-786745_960_720.png?alt=media&token=b9893069-4368-4214-8ddb-154ec43998c3");
            subirReceta(receta);
        } else {

            StorageReference riversRef = mStorageRef.child("FotosRecetas/" + foto.getLastPathSegment());
            riversRef.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Receta receta = new Receta();
                    receta.setNombreFoto(taskSnapshot.getDownloadUrl().toString());
                    subirReceta(receta);
                }
            });

        }



        finish();

    }

    private void subirReceta (Receta receta) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        receta.setTitulo(titulo.getText().toString());
        receta.setCuerpo(cuerpo.getText().toString());
        receta.setIngredientes(ingrediente.getText().toString());
        receta.setUidAtuor(user.getUid());
        receta.setFechaSubida (System.currentTimeMillis());
        ArrayList<Comentario> comentarios = new ArrayList<>();
        receta.setComentarios(comentarios);

        DatabaseReference recetaSubida = myRef.push();

        receta.setKey (recetaSubida.getKey()); //le ponemos la refencia
        recetaSubida.setValue(receta);
    }

    public static void solicitarPermiso(final String permiso, String justificacion, final int requestCode, final Activity actividad) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(actividad, permiso)){
            new AlertDialog.Builder(actividad)
                    .setTitle("Solicitud de permiso")
                    .setMessage(justificacion)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ActivityCompat.requestPermissions(actividad,new String[]{permiso}, requestCode);
                        }})
                    .show();
        } else {
            ActivityCompat.requestPermissions(actividad,
                    new String[]{permiso}, requestCode);
        }
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     String[] permissions, int[] grantResults) {
        if (requestCode == PERMISO_ESCRITURA) {
            if (grantResults.length== 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selecionarFoto (null);
            } else {
                Toast.makeText(this, "Sin el permiso, no puedo realizar la " +
                        "acción", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
