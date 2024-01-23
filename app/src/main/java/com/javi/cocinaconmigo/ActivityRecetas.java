package com.javi.cocinaconmigo;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRecetas extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView nombreUsuario;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("recetas");
    private DatabaseReference myRefUsuarios = FirebaseDatabase.getInstance().getReference("usuarios");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //nombreUsuario = findViewById(R.id.navNombreUsuario);

        navigationView.getMenu().getItem(0).setChecked(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmetCambiar, new Recetas())
                .commit();
        getSupportActionBar().setTitle("Recetas");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        /*myRefUsuarios.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);


                nombreUsuario.setText(usuario.getNombre());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        int id = menuItem.getItemId();

        if (id == R.id.nav_perfil) {
            fragment = new EditarUsuario();
            fragmentTransaction = true;

        } else if (id == R.id.nav_recetas){
            fragment = new Recetas();
            fragmentTransaction = true;
        }

        if(fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmetCambiar, fragment)
                    .commit();

            menuItem.setChecked(true);
            getSupportActionBar().setTitle(menuItem.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void cerrarSesion (View v) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        finish();
    }





}
