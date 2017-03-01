package jimmy.gg.flashingnumbers.LevelManager;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.helpers.IternalMemory;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class NumbersRemember extends AppCompatActivity {
    private EditText number;
    private int numberCount;
    private CountDownTimer countDownTimer = null;
    private String numbers;
    private int timerRemain;
    private RelativeLayout layout;
    private int c=20;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setTitle(getIntent().getStringExtra(EXTRA_LEVEL));
        setContentView(R.layout.activity_numbers_remember);
        Intent intent = getIntent();
        numberCount = intent.getIntExtra(EXTRA_NUMBERS_COUNT,0);
        numbers = intent.getStringExtra(EXTRA_NUMBERS);
        timerRemain = intent.getIntExtra(EXTRA_TIME_REMAIN,0);
        layout = (RelativeLayout) findViewById(R.id.activity_numbers_remember);
        createRow();
        startTimer();

        final TextView textChar = (TextView) findViewById(R.id.numbers_remembered_txtchanged);

        textChar.setText(getString(R.string.number_remember_numlength) + "0/" + numberCount + ")");
        final EditText txt = (EditText) findViewById(R.id.numbers_remember);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChar.setText(getString(R.string.number_remember_numlength) + txt.getText().toString().length() + "/" + numberCount + ")");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    protected void createRow(){
        number = (EditText) findViewById(R.id.numbers_remember);
        number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(numberCount)});
        findViewById(R.id.numbers_remember_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    public void startTimer(){
        final ProgressBar timer = (ProgressBar) findViewById(R.id.remember_timer);
        countDownTimer = new CountDownTimer(180*1000, 1){
            @Override
            public void onTick(long millisUntilFinished){
                timer.setProgress((int) (millisUntilFinished/(180*1000/100)));
            }
            @Override
            public void onFinish() {
                done();
            }
        };
        countDownTimer.start();
    }

    public void done(){
        EditText txt = (EditText) findViewById(R.id.numbers_remember);
        String numbToStr = txt.getText().toString();
        int check = numberCount/21;

        int b = numbToStr.length();//25
        String[] lines = new String[numbToStr.length()/21+1];
        layout.removeAllViews();
        for(int i=0;i<lines.length;i++){
            if(b >= 21) {
                lines[i] = numbToStr.substring(i*21, i*21+21);
                b-=21;
            }else{
                lines[i] = numbToStr.substring(i*21,numbToStr.length());
            }
        }

        int i = 0;
        int count = 0;
        int passed = 0;
        LinearLayout linear = new LinearLayout(NumbersRemember.this);
        linear.setOrientation(LinearLayout.VERTICAL);
        for(String s:lines){
            try {
                TextView numEntered = new TextView(NumbersRemember.this);
                numEntered.setTextSize(27);
                int k = 0;
                for (char c : s.toCharArray()) {
                    if (c == numbers.charAt(k + count)) {
                        String a = "<font color='#8bc34a'>"+c+"</font>";
                        numEntered.append(Html.fromHtml(a));
                        passed++;
                    } else {
                        String a = "<font color='#FE5F55'>"+c+"</font>";
                        numEntered.append(Html.fromHtml(a));
                    }
                    k++;
                }
                TextView numRight = new TextView(NumbersRemember.this);
                numRight.setTextSize(27);
                if (i != check) {
                    numRight.setText(numbers.substring(count, count+21));
                    count += 21;
                } else {
                    numRight.setText(numbers.substring(count, count+(numberCount-count)));
                }

                linear.addView(numEntered);
                linear.addView(numRight);
                i++;
            }catch (Exception e){
            }
        }
        layout.addView(linear);
        //Toast.makeText(getApplicationContext(),"i: "+i,Toast.LENGTH_SHORT).show();
        if(passed == numberCount) {
            dialogPassed();
        }else{
            dialogFailed(passed);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroy(){
        countDownTimer.cancel();
        super.onDestroy();
    }
    public void dialogPassed(){
        SharedPreferences sharedPreferences = FlashingNumbers.sharedPreferences;
        SharedPreferences highScore = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String level= getIntent().getStringExtra(EXTRA_LEVEL);
        String[] data = level.split(" ");
        int acLevel = Integer.parseInt(data[1]);
        final ArrayList<Level> levelList = FlashingNumbers.levelList;

        StringBuilder highestScore = new StringBuilder();
        highestScore.append(getString(R.string.dialog_passed_message1)+" "+numberCount
                + " " + getString(R.string.dialog_passed_message2) + " " + getTimeRemain().toString() + "s\n");

        if(highScore.getString(KEY_HIGH_SCORE+String.valueOf(acLevel-1),"0")!="0") {
            double time = Double.parseDouble(highScore.getString(KEY_HIGH_SCORE + String.valueOf(acLevel - 1), "0"));
            if (time > Double.parseDouble(getTimeRemain().toString())) {
                highestScore.append(getString(R.string.numbers_remembered_highest_score));
                editor.putString(KEY_HIGH_SCORE + String.valueOf(acLevel - 1), getTimeRemain().toString());
                editor.commit();
            }
        }else {
            editor.putString(KEY_HIGH_SCORE + String.valueOf(acLevel - 1), getTimeRemain().toString());
            editor.commit();
        }
        if(sharedPreferences.getInt(String.valueOf(getResources().getText(R.string.LEVEL_KEY)),1)==acLevel){
            acLevel++;
            levelList.get(acLevel).setLocked(false);
            editor.putInt(String.valueOf(getResources().getText(R.string.LEVEL_KEY)), acLevel);
            editor.commit();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(NumbersRemember.this);
            builder.setTitle(getIntent().getStringExtra(EXTRA_LEVEL)+" "+getApplicationContext().getResources().getString(R.string.numbers_remembered_passed));
            builder.setMessage(highestScore.toString());

            builder.setPositiveButton(R.string.button_next, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            List<Level> levels = levelList;
                            String level = getIntent().getStringExtra(EXTRA_LEVEL);
                            String[] data = level.split(" ");
                            int levell = Integer.parseInt(data[1]);
                            Intent intent = new Intent(NumbersRemember.this,Numbers.class);
                            intent.putExtra(EXTRA_NUMBERS,levels.get((levell)).getNumbers());
                            intent.putExtra(EXTRA_LEVEL,levels.get((levell)).getLevel()) ;
                            intent.putExtra(EXTRA_TIME, levels.get((levell)).getTime());
                            NumbersRemember.this.startActivity(intent);
                        }
                    });
                builder.setNegativeButton(R.string.button_menu, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.numbers_title_bot));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.numbers_title_bot));
        }

    public StringBuilder getTimeRemain() {
        String tm = String.valueOf(timerRemain);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tm.length(); i++) {
            char c = tm.charAt(i);
            if (tm.length() == 2) {
                builder.append("0");
            }
            if (i == tm.length() - 2) {
                builder.append(".");
            }
            builder.append(c);
        }
        return builder;
    }

        public void dialogFailed(int numbersRight){
            final ArrayList<Level> levelList = FlashingNumbers.levelList;
               AlertDialog.Builder builder = new AlertDialog.Builder(NumbersRemember.this);
                    builder.setTitle(getIntent().getStringExtra(EXTRA_LEVEL) + getString(R.string.failed));
                    builder.setMessage(getString(R.string.number_remember_remembered) +" "+numbersRight + "/" + numberCount + "\n" + getString(R.string.timer) + " " + getTimeRemain().toString() + "s");
                    builder.setPositiveButton(R.string.button_menu, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                    builder.setNegativeButton(R.string.button_retry, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    List<Level> levels = levelList;
                                    String level = getIntent().getStringExtra(EXTRA_LEVEL);
                                    String[] data = level.split(" ");
                                    int levell = Integer.parseInt(data[1]);
                                    Intent intent = new Intent(NumbersRemember.this,Numbers.class);
                                    intent.putExtra(EXTRA_NUMBERS,levels.get((levell-1)).getNumbers());
                                    intent.putExtra(EXTRA_LEVEL,levels.get((levell-1)).getLevel());
                                    intent.putExtra(EXTRA_TIME, levels.get((levell-1)).getTime());
                                    NumbersRemember.this.startActivity(intent);
                                }
                            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.numbers_title_bot));
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.numbers_title_bot));
        }

    public String KEY_HIGH_SCORE ="KEY_HIGH_SCORE";
    public String EXTRA_NUMBERS = "jimmy.gg.flashingnumbers.NUMBERS";
    public String EXTRA_NUMBERS_COUNT = "jimmy.gg.flashingnumbers.NUMBERS_COUNT";
    public String EXTRA_TIME = "jimmy.gg.flashingnumbers.TIME";
    public String EXTRA_TIME_REMAIN = "jimmy.gg.flashingnumbers.TIME_REMAIN";
    public final String EXTRA_LEVEL = "jimmy.gg.flashingnumbers.LEVEL";
}

