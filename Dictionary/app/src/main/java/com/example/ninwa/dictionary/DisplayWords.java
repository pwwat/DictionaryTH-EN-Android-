package com.example.ninwa.dictionary;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.util.Log;

import android.view.MotionEvent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;

public class DisplayWords extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listDisplay;
    ArrayAdapter arrayAdapter;

    DBHelper mydb;
    int id_update = 0;
    ArrayList arrayList;
    public TextToSpeech tts;
    Button btnListen;
    AlertDialog.Builder builder1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_words);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnListen = (Button) findViewById(R.id.btnListen);

        listDisplay = (ListView) findViewById(R.id.listDisplay);

        try {






            mydb = new DBHelper(this);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                int Value = extras.getInt("id");
                String word = extras.getString("word");
                arrayList = mydb.getDisplaylist(word);
                arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayList);
                listDisplay.setAdapter(arrayAdapter);

                listDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String word = (String) parent.getItemAtPosition(position);
                        SpeakOut(getApplicationContext(), word);
                    }
                });


                Toast.makeText(getApplicationContext(), "" + arrayList.get(0), Toast.LENGTH_SHORT).show();


                SpeakOut(getApplicationContext(), arrayList.get(0).toString());

                btnListen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        tts.speak(arrayList.get(0).toString(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                });


                // Cursor rs = mydb.getData(Value);


                // id_update = Value;

                /*
                if(rs != null && rs.moveToFirst()) {
                    rs.moveToFirst();
                    Toast.makeText(getApplicationContext(), "You Click" + Value, Toast.LENGTH_SHORT).show();

                    String wordeng = rs.getString(rs.getColumnIndex(DBHelper.DICTIONARY_COLUMN_WORDENG));
                    String wordthai = rs.getString(rs.getColumnIndex(DBHelper.DICTIONARY_COLUMN_ETHAI));
                    String wordcat = rs.getString(rs.getColumnIndex(DBHelper.DICTIONARY_COLUMN_CATEGORY));
                    String wordsyn = rs.getString(rs.getColumnIndex(DBHelper.DICTIONARY_COLUMN_ESYN));
                    String wordayn = rs.getString(rs.getColumnIndex(DBHelper.DICTIONARY_COLUMN_EANT));


                    if (!rs.isClosed()) {
                        rs.close();
                    }
                    txtwordeng.setText("คำศัพท์ " + wordeng);
                    txtwordthai.setText("แปลไทย " + wordthai);
                    txtwordcat.setText("ประเภทคำศัพท์ " + wordcat);
                    txtwordsyn.setText("คำที่คล้ายกัน " + wordsyn);
                    txtwordayn.setText("คำตรงกันข้าม " + wordayn);


                } */

            } else {
                Toast.makeText(getApplicationContext(), "Null Bundle", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.i("Error DisplayWord", "ERROR" + ex.getMessage());
            Intent intent = new Intent(DisplayWords.this, MainActivity.class);
            if (tts != null) {

                tts.stop();
                tts.shutdown();
                Log.i("DestroyTTS", "TTS Destroyed");
            }
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void SpeakOut(Context myContext, final String word) {

        try {

            if (tts == null) {

                tts = new TextToSpeech(myContext, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {



                        // TODO Auto-generated method stub
                        if (status == TextToSpeech.SUCCESS) {

                            int result = tts.setLanguage(Locale.US);
                            tts.setLanguage(new Locale("th"));
                            if (result == TextToSpeech.LANG_MISSING_DATA
                                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                Toast.makeText(getApplicationContext(), "Language is not supported", Toast.LENGTH_SHORT).show();
                            } else {

                                tts.speak(word, TextToSpeech.QUEUE_ADD, null);
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Initilization Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

            } else {
                tts.speak(word, TextToSpeech.QUEUE_ADD, null);

            }
        } catch (Exception ex) {
            Toast.makeText(myContext, "Error+" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(myContext, MainActivity.class);
            startActivity(intent);
        }
    }




    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //Close the Text to Speech Library
        if (tts != null) {

            tts.stop();
            tts.shutdown();
            Log.i("DestroyTTS", "TTS Destroyed");
        }

    }


    private void ConvertTextToSpeech(String word) {
        // TODO Auto-generated method stub

        if (word == null || "".equals(word)) {
            word = "Content not available";
            tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);
        } else
            tts.speak(word, TextToSpeech.QUEUE_FLUSH, null);
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
        getMenuInflater().inflate(R.menu.display_words, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent intent = new Intent(DisplayWords.this, pongniwat.class);
            startActivity(intent);


        } else if (id == R.id.nav_gallery) {

            Intent intent = new Intent(DisplayWords.this, tum.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {

            Intent intent = new Intent(DisplayWords.this, toey.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(DisplayWords.this,setting.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("https://www.google.co.th/maps/@7.5210225,99.5772512,15z"));
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(DisplayWords.this, SendEmail.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*
    public class EfficientAdapter extends BaseAdapter {

        public Context mContext;
        public LayoutInflater mInflater;


        public EfficientAdapter(Context context){
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){

                convertView = mInflater.inflate(R.layout.custom_listview,null);



            }else{

            }


            return convertView;
        }
    }

*/


}
