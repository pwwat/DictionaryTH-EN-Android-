package com.example.ninwa.dictionary;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class setting extends AppCompatActivity {


    SeekBar seekSpeed;
    SeekBar seekPitch;
    TextView txtSpeed;
    TextView txtPitch;
    Button btnSave;
    Intent intent;
    float progressSpeed;
    float progressPitch;
    Context mContext;
    DisplayWords speak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        txtSpeed = (TextView) findViewById(R.id.txtSpeed);
        txtPitch = (TextView) findViewById(R.id.txtPitch);
        seekSpeed = (SeekBar) findViewById(R.id.seekBarSpeed);
        seekPitch = (SeekBar) findViewById(R.id.seekBarPitch);




        seekPitch.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressPitch = progress;
                txtPitch.setText(progress+"/"+seekPitch.getMax());


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        seekSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                progressSpeed = progress;
                txtSpeed.setText(progress+"/"+seekSpeed.getMax());


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnSave = (Button) findViewById(R.id.save);

        try {
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

/*
                    if(speak.tts == null){

                    }else{
                        speak.tts.setSpeechRate((float) 1.0);
                        speak.tts.setPitch((float) 1.0);
                    }

                   มันบัคครับอาจารย์ ลองหลายวิธีมากๆ 55555+

*/

                    Toast.makeText(getApplicationContext(),"Saved Success",Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception ex){

            Toast.makeText(getApplicationContext(),"Error:"+ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
