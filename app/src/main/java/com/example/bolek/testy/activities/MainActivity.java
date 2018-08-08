package com.example.bolek.testy.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bolek.testy.R;
import com.example.bolek.testy.fragments.AsyncFragment;
import com.example.bolek.testy.fragments.BluetoothFragment;
import com.example.bolek.testy.fragments.ConverterFragment;
import com.example.bolek.testy.fragments.FilesFragment;
import com.example.bolek.testy.fragments.MainFragment;
import com.example.bolek.testy.fragments.RetrofitFragment;
import com.example.bolek.testy.fragments.SQLiteFragment;
import com.example.bolek.testy.fragments.RotateFragment;
import com.example.bolek.testy.fragments.ServiceFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    FragmentTransaction transaction;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            MainFragment frag = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();
        }

        //TODO usunąć
//        Intent i = new Intent(this, MusicPlayerActivity.class);
//        i.putExtra("file",new File("/storage/60A7-160E/music/op i ed/Akame ga Kill! [opening].mp3"));
//        startActivity(i);
//        Intent i = new Intent(this, TestActivity.class);
//        startActivity(i);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.BLUETOOTH,
                            Manifest.permission.BLUETOOTH_ADMIN}, 1);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_toast:
                Toast.makeText(getApplicationContext(), "Toast", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_snackbar:
                Snackbar.make(findViewById(R.id.fragment_container), "Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Cofnij", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "Cofam", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
                break;
            case R.id.action_test:
                Intent i = new Intent(this, TestActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager manager;
        switch (id) {
            case R.id.nav_main:
                MainFragment main = new MainFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, main).addToBackStack("main");
                transaction.commit();
                break;
            case R.id.nav_rotate:
                RotateFragment rotate = new RotateFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, rotate).addToBackStack("rotate");
                transaction.commit();
                break;
            case R.id.nav_async:
                AsyncFragment async = new AsyncFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, async).addToBackStack("async");
                transaction.commit();
                break;
            case R.id.nav_converter:
                ConverterFragment conv = new ConverterFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, conv).addToBackStack("conv");
                transaction.commit();
                break;
            case R.id.nav_sqlite:
                SQLiteFragment sql = new SQLiteFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, sql).addToBackStack("sqlite");
                transaction.commit();
                break;
            case R.id.nav_files:
                FilesFragment files = new FilesFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, files).addToBackStack("files");
                transaction.commit();
                break;
            case R.id.nav_rest:
                RetrofitFragment retro = new RetrofitFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, retro).addToBackStack("retrofit");
                transaction.commit();
                break;
            case R.id.nav_bluetooth:
                BluetoothFragment blue = new BluetoothFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, blue).addToBackStack("retrofit");
                transaction.commit();
                break;
            case R.id.nav_service:
                ServiceFragment ser = new ServiceFragment();
                manager = getSupportFragmentManager();
                manager.popBackStackImmediate();

                transaction = manager.beginTransaction();
                transaction.replace(R.id.fragment_container, ser).addToBackStack("retrofit");
                transaction.commit();
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (!(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    finish();
                }
                return;
            }
        }
    }
}
