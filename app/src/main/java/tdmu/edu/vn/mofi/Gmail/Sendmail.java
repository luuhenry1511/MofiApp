package tdmu.edu.vn.mofi.Gmail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Arrays;
import java.util.List;

import tdmu.edu.vn.mofi.R;

public class Sendmail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmail);
        final Button send = (Button) this.findViewById(R.id.button11);
        final Button quaylai=findViewById(R.id.button111);
        EditText editemail=findViewById(R.id.editText31);
        String email=getIntent().getStringExtra("emaillienhe");
        editemail.setText(email);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SendMailActivity", "Send Button Clicked.");

                String fromEmail = ((TextView) findViewById(R.id.editText12))
                        .getText().toString();
                String fromPassword = ((TextView) findViewById(R.id.editText21))
                        .getText().toString();
                String toEmails = ((TextView) findViewById(R.id.editText31))
                        .getText().toString();
                List<String> toEmailList = Arrays.asList(toEmails
                        .split("\\s*,\\s*"));
                Log.i("SendMailActivity", "To List: " + toEmailList);
                String emailSubject = ((TextView) findViewById(R.id.editText41))
                        .getText().toString();
                String emailBody = ((TextView) findViewById(R.id.editText51))
                        .getText().toString();
                new SendMailTask(Sendmail.this).execute(fromEmail,
                        fromPassword, toEmailList, emailSubject, emailBody);
                Toast.makeText(Sendmail.this,"Gửi thành công",Toast.LENGTH_SHORT).show();
            }
        });
        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}