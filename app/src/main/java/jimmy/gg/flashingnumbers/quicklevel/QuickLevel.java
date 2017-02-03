package jimmy.gg.flashingnumbers.quicklevel;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

import jimmy.gg.flashingnumbers.R;

public class QuickLevel extends AppCompatActivity {

    public ProgressBar progress;
    public EditText editText;
    public TextView numbers;
    public TextView onEndView;
    private StringBuilder builder;
    private int level = 1;
    private CountDownTimer firstTimer = null;
    private CountDownTimer secondTimer = null;
    private CountDownTimer thirdTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_level);
        setTitle("Quick Level");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar) findViewById(R.id.quick_progressBar);
        onEndView = (TextView) findViewById(R.id.quick_on_end_view);
        rulesExplain(false);
        numbers = (TextView) findViewById(R.id.quick_numbers);
        editText = (EditText) findViewById(R.id.quick_edit_text);
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
        builder = new StringBuilder();
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
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.getBackground().setColorFilter(getResources().getColor(R.color.numbers_title_bot), PorterDuff.Mode.SRC_ATOP);
        numbers.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        onEndView.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        timerOnEnd(editText);
    }

    public void timerOnEnd(final EditText editText){
        new CountDownTimer(level*4000,1){
            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress((int) millisUntilFinished/(level*4000/100));
            }
            @Override
            public void onFinish() {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                numberFinish();
            }
        }.start();
    }
    public void numberFinish(){
        TextView timeRemain = (TextView) findViewById(R.id.quick_time_remain);
        timeRemain.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        onEndView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);
        String numberText = editText.getText().toString();
        ContextThemeWrapper newContext = new ContextThemeWrapper(getApplicationContext(), R.style.MaterialButton);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.quick_linear_result);
        layout.setGravity(Gravity.CENTER);
        TextView textView1 = new TextView(QuickLevel.this);
        TextView textView2 = new TextView(QuickLevel.this);
        TextView number = new TextView(QuickLevel.this);
        TextView answer = new TextView(QuickLevel.this);
        textView1.setTextSize(20);
        textView2.setTextSize(20);
        number.setTextSize(24);
        answer.setTextSize(24);
        Button next = new Button(newContext);
        textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView1.setText("Number");
        textView2.setText("Your answer");
        next.setText("NEXT");
        number.setText(builder.toString());
        try {
            for (int i = 0; i < builder.length(); i++) {
                if (numberText.charAt(i) != ' ') {
                    char c = numberText.charAt(i);
                    if (c == builder.charAt(i)) {
                        String a = "<font color='#37FF8B'>" + c + "</font>";
                        answer.append(Html.fromHtml(a));
                    } else {
                        String a = "<font color='#FE5F55'>" + c + "</font>";
                        answer.append(Html.fromHtml(a));
                    }
                }
            }
        }catch (ArrayIndexOutOfBoundsException exception){
            exception.printStackTrace();
        }
        layout.addView(textView1);
        layout.addView(number);
        layout.addView(textView2);
        layout.addView(answer);
        layout.addView(next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                layout.removeAllViews();
                level++;
                timerBetweenLevels();
            }
        });
        editText.setText("");
    }
    public void numbersRemembered(View view){
        numberFinish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        this.finish();
        return super.onOptionsItemSelected(menuItem);
    }
}
