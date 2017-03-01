package jimmy.gg.flashingnumbers.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import jimmy.gg.flashingnumbers.R;

public class MainSendFeedback extends AppCompatActivity {
    public EditText subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.send_feedback_title));
        setContentView(R.layout.activity_main_send_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        subject = (EditText) findViewById(R.id.sf_title);
        message = (EditText) findViewById(R.id.sf_message_send);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.send_feedback_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch(item.getItemId()){
            case R.id.send:
                    if(subject.getText().length()==0){
                        subject.setFocusable(true);
                        subject.requestFocus();
                        Toast.makeText(getApplicationContext(), R.string.enter_title,Toast.LENGTH_SHORT).show();
                    }else if(message.getText().length()==0){
                        message.setFocusable(true);
                        message.requestFocus();
                        Toast.makeText(getApplicationContext(), R.string.enter_message,Toast.LENGTH_SHORT).show();
                    }else {
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Intent email = new Intent(Intent.ACTION_SEND);
                                    email.setData(Uri.parse("mailto:"));
                                    email.setType("message/rfc822");
                                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"GGJimmy@gmail.com"});
                                    email.putExtra(Intent.EXTRA_SUBJECT, subject.getText().toString());
                                    email.putExtra(Intent.EXTRA_TEXT, message.getText().toString());

                                    startActivity(Intent.createChooser(email, getString(R.string.sf_sending_title)));
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Something went wrong, check your internet connection.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                        runOnUiThread(runnable);
                    }
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
