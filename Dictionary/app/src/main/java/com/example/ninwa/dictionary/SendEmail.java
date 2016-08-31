package com.example.ninwa.dictionary;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmail extends AppCompatActivity {

    EditText subject;
    EditText message;
    Button btnSend;
    Button btnCancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        final String sendto = "ninwatza@gmail.com";

        subject = (EditText) findViewById(R.id.subject);
        message = (EditText) findViewById(R.id.message);
        btnSend = (Button) findViewById(R.id.sendemail);
        btnCancle = (Button) findViewById(R.id.cancle);




        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);

                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{sendto});
                i.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                i.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

                try {
                    i.setType("message/rfc822");
                    startActivity(Intent.createChooser(i, "Choose an Email client :"));
                    subject.setText("");
                    message.setText("");
                    Toast.makeText(getApplicationContext(),"Send Success",Toast.LENGTH_SHORT).show();
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    finish();;
                }


            }
        });



    }
}
