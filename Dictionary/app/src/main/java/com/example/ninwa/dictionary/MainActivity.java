package com.example.ninwa.dictionary;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    private ListView listViewDictionary;
    DBHelper mydb;
    private Context mContext;
    EditText txtDict;
    Button btnClear;

    ArrayList arrayWordEng;
    ArrayAdapter arrayAdapterEng;
    ArrayList arrayWordThai;
    ArrayAdapter arrayAdapterThai;

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mydb = new DBHelper(this);
        txtDict = (EditText) findViewById(R.id.txtDict);
        listViewDictionary = (ListView) findViewById(R.id.listDict);


        btnClear = (Button) findViewById(R.id.btnClear);




        arrayWordEng = mydb.getAllWords();
        arrayAdapterEng = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayWordEng);
        listViewDictionary.setAdapter(arrayAdapterEng);

        listViewDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String wordClick = parent.getItemAtPosition(position).toString();


                int id_to_search = position + 1;


                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id_to_search);
                dataBundle.putString("word", wordClick);
                Intent intent = new Intent(getApplicationContext(), DisplayWords.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });







        txtDict.addTextChangedListener(new TextWatcher() {
            ArrayList arrayList = mydb.getAllWords();
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayList);


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                listViewDictionary.setAdapter(null);
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

                if(txtDict.length() != 0){
                    btnClear.setVisibility(View.VISIBLE);
                    btnClear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            txtDict.setText("");
                        }
                    });


                    arrayWordEng = mydb.getWords(txtDict.getText().toString());
                    arrayAdapterEng = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,arrayWordEng);
                    listViewDictionary.setAdapter(arrayAdapterEng);


                    if(arrayAdapterEng.getCount() == 0 || arrayAdapterEng == null){
                        txtDict.setTextColor(Color.RED);
                    }else{
                        txtDict.setTextColor(Color.BLACK);
                    }


                    listViewDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String wordClick = parent.getItemAtPosition(position).toString();


                            int id_to_search = position+1;


                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("id", id_to_search);
                            dataBundle.putString("word",wordClick);
                            Intent intent = new Intent(getApplicationContext(), DisplayWords.class);
                            intent.putExtras(dataBundle);
                            startActivity(intent);
                        }
                    });


                }else{
                    btnClear.setVisibility(View.INVISIBLE);
                    listViewDictionary = (ListView) findViewById(R.id.listDict);
                    listViewDictionary.setAdapter(arrayAdapter);

                    listViewDictionary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            String wordClick = parent.getItemAtPosition(position).toString();


                            int id_to_search = position+1;


                            Bundle dataBundle = new Bundle();
                            dataBundle.putInt("id", id_to_search);
                            dataBundle.putString("word", wordClick);
                            Intent intent = new Intent(getApplicationContext(), DisplayWords.class);
                            intent.putExtras(dataBundle);
                            startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }




        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(MainActivity.this,pongniwat.class);
            startActivity(intent);


        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(MainActivity.this,tum.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(MainActivity.this,toey.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

            Intent intent = new Intent(MainActivity.this,setting.class);
            startActivity(intent);


        } else if (id == R.id.nav_share) {

            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.co.th/maps/@7.5210225,99.5772512,15z"));
            startActivity(intent);

        } else if (id == R.id.nav_send) {

                        Intent intent = new Intent(MainActivity.this,SendEmail.class);
            startActivity(intent);



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




}
