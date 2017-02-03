package jimmy.gg.flashingnumbers.quicklevel;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import jimmy.gg.flashingnumbers.R;

public class QuickLevel extends AppCompatActivity {
    public ProgressBar progress;
    public TextView numbers;
    private int level = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_level);
        setTitle("Quick Level");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar) findViewById(R.id.quick_progressBar);
        rulesExplain(false);
        numbers = (TextView) findViewById(R.id.quick_numbers);

    }

    public void rulesExplain(boolean userDenied){
        if(!userDenied){
            new AlertDialog.Builder(QuickLevel.this)
                    .setTitle("Rules")
                    .setMessage("There will be levels in each level the numbers amount increases by one. Starting on" +
                            "one digit. After timer is done you will have to write down this number.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            timerBetweenLevels();
                        }
                    })
                    .show();
        }
    }
    public void timerBetweenLevels(){
        final TextView countDown = (TextView) findViewById(R.id.quick_count_down);
        countDown.setVisibility(View.VISIBLE);
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                countDown.setText(""+millisUntilFinished/1000);
            }

            @Override
            public void onFinish() {
                countDown.setVisibility(View.INVISIBLE);
                displayNumbers();
            }
        }.start();
    }


    public void displayNumbers(){
        TextView timeRemain = (TextView) findViewById(R.id.quick_time_remain);
        timeRemain.setVisibility(View.VISIBLE);
        numbers.setVisibility(View.VISIBLE);
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        numbers.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        for(int i=0;i<level;i++){
            builder.append(random.nextInt(10));
        }
        numbers.setText(builder.toString());

        startTimer();
    }

    public void startTimer(){
        progress.setVisibility(View.VISIBLE);

        new CountDownTimer(level*1000+1000,1){

            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress((int) millisUntilFinished/((level*1000+1000)/100));
            }

            @Override
            public void onFinish() {
                levelRemember();

            }
        }.start();
    }

    public void levelRemember(){
        EditText editText = (EditText) findViewById(R.id.quick_edit_text);
        editText.getBackground().setColorFilter(getResources().getColor(R.color.numbers_title_bot), PorterDuff.Mode.SRC_ATOP);
        numbers.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        timerOnEnd();
    }

    public void timerOnEnd(){
        new CountDownTimer(level*5000,1){

            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress((int) millisUntilFinished/(5000/100));
            }

            @Override
            public void onFinish() {
                levelRemember();

            }
        }.start();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        this.finish();
        return super.onOptionsItemSelected(menuItem);
    }
}
