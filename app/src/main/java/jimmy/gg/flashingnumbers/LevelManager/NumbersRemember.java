package jimmy.gg.flashingnumbers.LevelManager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ObbInfo;
import android.graphics.PorterDuff;
import android.os.CountDownTimer;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.HighScore;

public class NumbersRemember extends AppCompatActivity {
    private ArrayList<EditText> texts = new ArrayList<>();
    private LinearLayout layout;
    private int numberCount;
    private CountDownTimer countDownTimer = null;
    private String numbers;
    private int timerRemain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setTitle("Numbers remember");
        setContentView(R.layout.activity_numbers_remember);
        layout = (LinearLayout) findViewById(R.id.linear_layout);
        Intent intent = getIntent();
        numberCount = intent.getIntExtra(Numbers.EXTRA_NUMBERS_COUNT,0);
        numbers = intent.getStringExtra(Numbers.EXTRA_NUMBERS);
        timerRemain = intent.getIntExtra(Numbers.EXTRA_TIME_REMAIN,0);
        createRow();
        startTimer();
    }

    protected void createRow(){
        int numberLength = numberCount;
        for(int i=0;i<numberLength;i++) {
            if (numberLength > 20) {
                if(i == 0)
                    append(20,true);
                else
                    append(20,false);

                numberLength-=20;
            }else{
                append(numberLength,false);
                numberLength=0;
            }
        }
        ContextThemeWrapper newContext = new ContextThemeWrapper(getApplicationContext(), R.style.MaterialButton);
        Button button = new Button(newContext);
        button.setText(R.string.numbers_remember_done);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(600,40,0,0);
        button.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        button.setLayoutParams(params);
        layout.addView(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    public void append(int size, boolean isUpper){
        EditText number = new EditText(NumbersRemember.this);
        number.setInputType(InputType.TYPE_CLASS_NUMBER);
        number.setFocusable(true);
        number.setFocusableInTouchMode(true);
        number.getBackground().setColorFilter(getResources().getColor(R.color.numbers_title_bot), PorterDuff.Mode.SRC_ATOP);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams((27 * size) * 2 - 27, ViewGroup.LayoutParams.WRAP_CONTENT);
        number.setLayoutParams(par);
        number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(size)});
        number.setTextSize(27);
        if(isUpper) {
            par.setMargins(0,20,0,0);
        }
        texts.add(number);
        layout.addView(number);
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
        StringBuilder build = new StringBuilder();
        for(EditText text:this.texts){
            build.append(text.getText().toString()+" ");
        }
        layout.removeAllViews();
        int i = 0;
        int count = 0;
        int check = numberCount/20;
        int passed = 0;
        for(EditText text:this.texts) {
            try {
                TextView numEntered = new TextView(NumbersRemember.this);
                numEntered.setTextSize(27);
                int k = 0;
                for (char c : text.getText().toString().toCharArray()) {
                    if (c == numbers.charAt(k+count)) {
                        String a = "<font color='#37FF8B'>"+c+"</font>";
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
                    numRight.setText(numbers.substring(count, count+20));
                    count += 20;
                } else {
                    numRight.setText(numbers.substring(count, count+(numberCount-count)));
                }
                layout.addView(numEntered);
                layout.addView(numRight);
                i++;
            }catch (Exception e){

            }
        }
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

    public void dialogPassed(){
        SharedPreferences sharedPreferences = Levels.sharedPreferences;
        SharedPreferences highScore = Levels.highScore;
        SharedPreferences.Editor highScoreEditor = highScore.edit();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String level= getIntent().getStringExtra(Numbers.EXTRA_LEVEL);
        String[] data = level.split(" ");
        int acLevel = Integer.parseInt(data[1]);
        String tm = String.valueOf(timerRemain);
        StringBuilder builder = new StringBuilder();

        for(int i=0;i<tm.length();i++) {
            char c = tm.charAt(i);
            if (i == tm.length() - 2) {
                builder.append(".");
            }
            builder.append(c);
        }

        //SharedPreferences.Editor edit = highScore.edit();
        if(Levels.highScore.getInt(HighScore.KEY_HIGH_SCORE+data[1],0)!=0){
            if(timerRemain>Integer.parseInt(Levels.highScore.getString(HighScore.KEY_HIGH_SCORE+String.valueOf(acLevel),"0"))){
                SharedPreferences.Editor edit = Levels.highScore.edit();
                edit.putString(HighScore.KEY_HIGH_SCORE+String.valueOf(acLevel),builder.toString());
                edit.commit();
                Toast.makeText(getApplicationContext(),timerRemain,Toast.LENGTH_SHORT).show();
            }
        }/*else{
            highScoreEditor.putString(HighScore.KEY_HIGH_SCORE,builder.toString());
            highScoreEditor.commit();
        }*/

        if(sharedPreferences.getInt(Levels.LEVEL_KEY,1)==acLevel){
            acLevel++;
            if(acLevel>=6 && acLevel<=10){
                Levels.levelList.get(acLevel).setDifficult("medium");
            }else if(acLevel>=11 && acLevel<=14){
                Levels.levelList.get(acLevel).setDifficult("hard");
            }else{
                Levels.levelList.get(acLevel).setDifficult("easy");
            }
            editor.putInt(Levels.LEVEL_KEY, acLevel);
            editor.commit();
        }

        new AlertDialog.Builder(NumbersRemember.this)
                .setTitle(getIntent().getStringExtra(Numbers.EXTRA_LEVEL)+" passed")
                .setMessage("You have successfuly remembered " + numberCount + " numbers in time: "+builder.toString()+"s")
                .setPositiveButton("NEXT", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        List<Level> levels = Levels.levelList;
                        String level = getIntent().getStringExtra(Numbers.EXTRA_LEVEL);
                        String[] data = level.split(" ");
                        int levell = Integer.parseInt(data[1]);
                        Intent intent = new Intent(NumbersRemember.this,Numbers.class);
                        intent.putExtra(EXTRA_NUMBERS,levels.get((levell)).getNumbers());
                        intent.putExtra(EXTRA_LEVEL,levels.get((levell)).getLevel()) ;
                        intent.putExtra(EXTRA_TIME, levels.get((levell)).getTime());
                        NumbersRemember.this.startActivity(intent);
                    }
                })
                .setNegativeButton("MENU", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        }

        public void dialogFailed(int numbersRight){
            new AlertDialog.Builder(NumbersRemember.this)
                    .setTitle(getIntent().getStringExtra(Numbers.EXTRA_LEVEL)+" failed")
                    .setMessage("Remembered "+numbersRight+"/"+numberCount)
                    .setPositiveButton("MENU", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("RETRY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<Level> levels = Levels.levelList;
                            String level = getIntent().getStringExtra(Numbers.EXTRA_LEVEL);
                            String[] data = level.split(" ");
                            int levell = Integer.parseInt(data[1]);
                            Intent intent = new Intent(NumbersRemember.this,Numbers.class);
                            intent.putExtra(EXTRA_NUMBERS,levels.get((levell-1)).getNumbers());
                            intent.putExtra(EXTRA_LEVEL,levels.get((levell-1)).getLevel()) ;
                            intent.putExtra(EXTRA_TIME, levels.get((levell-1)).getTime());
                            NumbersRemember.this.startActivity(intent);
                        }
                    })
                    .show();
        }

    public final static String EXTRA_LEVEL = "jimmy.gg.flashingnumbers.LEVEL";
    public final static String EXTRA_NUMBERS = "jimmy.gg.flashingnumbers.NUMBERS";
    public final static String EXTRA_TIME = "jimmy.gg.flashingnumbers.TIME";
}

