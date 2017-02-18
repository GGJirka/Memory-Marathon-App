package jimmy.gg.flashingnumbers.quicklevel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Html;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.QuickLevelHighScore;
import jimmy.gg.flashingnumbers.highscore.TabbedHighScore;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;
import jimmy.gg.flashingnumbers.settings.QuickLevelSettings;

public class QuickLevel extends AppCompatActivity {
    public ProgressBar progress;
    public TextView onEndView;
    private StringBuilder builder;
    private int level = 1;
    private CountDownTimer firstTimer = null;
    private CountDownTimer secondTimer = null;
    private CountDownTimer thirdTimer = null;
    private int sec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_level);
        setTitle("Quick - Level 1");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progress = (ProgressBar) findViewById(R.id.quick_progressBar);
        onEndView = (TextView) findViewById(R.id.quick_on_end_view);
        if (FlashingNumbers.sharedPreferences.getString(String.valueOf(getText(R.string.CHECKBOX)), "uncheck").equals("uncheck")) {
            rulesExplain();
        } else {
            timerBetweenLevels();
        }
    }

    public void rulesExplain() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.checkbox, null);
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.check);
            new AlertDialog.Builder(QuickLevel.this)
                    .setView(view)
                    .setTitle("Rules")
                    .setMessage("There will be levels in each level the numbers amount increases by one. Starting on" +
                            "one digit. After timer is done you will have to write down this number.")
                    .setPositiveButton(getResources().getText(R.string.quick_button_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (checkbox.isChecked()) {
                                SharedPreferences.Editor editor = FlashingNumbers.sharedPreferences.edit();
                                editor.putString(String.valueOf(getText(R.string.CHECKBOX)), "check");
                                editor.commit();
                            }
                            timerBetweenLevels();
                        }
                    })
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            if (checkbox.isChecked()) {
                                SharedPreferences.Editor editor = FlashingNumbers.sharedPreferences.edit();
                                editor.putString(String.valueOf(getText(R.string.CHECKBOX)), "check");
                                editor.commit();
                            }
                            timerBetweenLevels();
                        }
                    })
                    .show();

    }

    public void timerBetweenLevels(){
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("settings_timer_quick", true)) {
            final TextView countDown = (TextView) findViewById(R.id.quick_count_down);
            countDown.setVisibility(View.VISIBLE);
            firstTimer = new CountDownTimer(2500, 100) {

                @Override
                public void onTick(long millisUntilFinished) {
                    if (Math.round((float) millisUntilFinished / 1000.0f) != sec) {
                        sec = Math.round((float) millisUntilFinished / 1000.0f);
                        countDown.setText("" + (sec + 1));
                    }
                }

                @Override
                public void onFinish() {
                    countDown.setVisibility(View.INVISIBLE);
                    displayNumbers();
                }
            };
            firstTimer.start();
        } else {
            displayNumbers();
        }
    }


    public void displayNumbers(){
        TextView numbers = (TextView) findViewById(R.id.quick_numbers);
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
        numbers.setTextSize(50);
        startTimer();
    }

    public void onPause(){
        super.onPause();
    }

    public void onResume(){
        super.onResume();
        EditText editText = (EditText) findViewById(R.id.quick_edit_text);
        if(editText.getVisibility() == View.INVISIBLE) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        if (FlashingNumbers.sharedPreferences.getString("QUICK_LEVEL_STATE", "2").equals("0")) {
            LinearLayout lay = (LinearLayout) findViewById(R.id.quick_linear_result);
            lay.removeAllViews();
            if (FlashingNumbers.sharedPreferences.getString(String.valueOf(getText(R.string.CHECKBOX)), "uncheck").equals("uncheck")) {
                rulesExplain();
            } else {
                timerBetweenLevels();
            }
            FlashingNumbers.sharedPreferences
                    .edit()
                    .putString("QUICK_LEVEL_STATE", "1")
                    .commit();
        }

    }

    public void onStop(){
        super.onStop();
    }

    public void startTimer(){
        progress.setVisibility(View.VISIBLE);

        secondTimer = new CountDownTimer(level*1000+1000,1){

            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress((int) millisUntilFinished/((level*1000+1000)/100));
            }

            @Override
            public void onFinish() {
                levelRemember();
            }
        };
        secondTimer.start();
    }

    public void levelRemember(){

        EditText editText = (EditText) findViewById(R.id.quick_edit_text);
        findViewById(R.id.quick_button_done).setVisibility(View.VISIBLE);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        editText.getBackground().setColorFilter(getResources().getColor(R.color.numbers_title_bot), PorterDuff.Mode.SRC_ATOP);

        findViewById(R.id.quick_numbers).setVisibility(View.INVISIBLE);
        editText.setVisibility(View.VISIBLE);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(level)});

        onEndView.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        timerOnEnd();
    }

    public void timerOnEnd(){

        thirdTimer = new CountDownTimer(level*2000+2000,1){
            @Override
            public void onTick(long millisUntilFinished) {
                progress.setProgress((int) millisUntilFinished/((level*2000+2000)/100));
            }
            @Override
            public void onFinish() {
                numberFinish();
            }
        };
        thirdTimer.start();
    }
    public void numberFinish() {
        EditText editText = (EditText) findViewById(R.id.quick_edit_text);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        thirdTimer.cancel();

        TextView timeRemain = (TextView) findViewById(R.id.quick_time_remain);

        timeRemain.setVisibility(View.INVISIBLE);
        progress.setVisibility(View.INVISIBLE);
        onEndView.setVisibility(View.INVISIBLE);
        editText.setVisibility(View.INVISIBLE);

        findViewById(R.id.quick_button_done).setVisibility(View.INVISIBLE);

        String numberText = editText.getText().toString();
        ContextThemeWrapper newContext = new ContextThemeWrapper(getApplicationContext(), R.style.MaterialButton);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.quick_linear_result);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 30, 0, 0);

        layout.setGravity(Gravity.CENTER);

        TextView textView1 = new TextView(QuickLevel.this);
        textView1.setText(R.string.text_number);
        textView1.setTextSize(25);
        textView1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textView1);

        final TextView number = new TextView(QuickLevel.this);
        number.setTextSize(34);
        number.setText(builder.toString());
        number.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(number);

        TextView textView2 = new TextView(QuickLevel.this);
        textView2.setText(R.string.text_answer);
        textView2.setTextSize(25);
        textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        layout.addView(textView2);

        TextView answer = new TextView(QuickLevel.this);
        answer.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        answer.setTextSize(34);
        textView2.setLayoutParams(params);
        layout.addView(answer);

        Button next = new Button(newContext);
        next.setLayoutParams(params);
        next.setTextSize(20);

        int passed = 0;
        try {
            for (int i = 0; i < editText.getText().length(); i++) {
                if (numberText.charAt(i) != ' ') {
                    char c = numberText.charAt(i);
                    if (c == builder.charAt(i) && i < builder.length()) {
                        String a = "<font color='#8bc34a'>" + c + "</font>";
                        answer.append(Html.fromHtml(a));
                        passed++;
                    } else {
                        String a = "<font color='#FE5F55'>" + c + "</font>";
                        answer.append(Html.fromHtml(a));
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException exception) {
            exception.printStackTrace();
        }

        if (passed == builder.length()) {
            next.setText(R.string.button_next);
            next.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            layout.addView(next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layout.removeAllViews();
                    level++;
                    timerBetweenLevels();
                    setTitle("Quick - Level " + level);
                }
            });
        } else {
            View v = getLayoutInflater().inflate(R.layout.buttons, null);
            LinearLayout linear = (LinearLayout) v.findViewById(R.id.quick_linear_layout);
            linear.setPadding(0, 30, 0, 0);
            layout.addView(linear);

            v.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = FlashingNumbers.sharedPreferences;
                    level = 1;
                    setTitle("Quick - Level " + level);
                    int numberCount = number.getText().toString().length() - 1;
                    String count = String.valueOf(sharedPreferences.getInt(QuickLevelHighScore.KEY_COUNT,0)+1);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(QuickLevelHighScore.KEY_COUNT,Integer.parseInt(count));
                    editor.commit();
                    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    editor.putString(QuickLevelHighScore.KEY + count, date + " " + "numbers:" + " " + String.valueOf(numberCount));
                    editor.commit();

                    sharedPreferences.edit()
                            .putString("TAB_STATE", "0")
                            .apply();
                    Intent intent = new Intent(QuickLevel.this, TabbedHighScore.class);
                    intent.putExtra(String.valueOf(getText(R.string.EXTRA_PAGE)), "1");
                    QuickLevel.this.startActivity(intent);
                }
            });
            v.findViewById(R.id.retry).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    level = 1;
                    timerBetweenLevels();
                    setTitle("Quick - Level " + level);
                    layout.removeAllViews();
                }
            });
        }
        editText.setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu){
        switch(menu.getItemId()) {
            case R.id.high_score:
                Intent intent = new Intent(this, TabbedHighScore.class);
                intent.putExtra(String.valueOf(getText(R.string.EXTRA_PAGE)),"1");
                startActivity(intent);
                break;
            case R.id.quick_action_settings:
                Intent settings = new Intent(this, QuickLevelSettings.class);
                startActivity(settings);
                return true;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.popup_menu,menu);
        return true;
    }

    public void numbersRemembered(View view){
        if (firstTimer != null) {
            firstTimer.cancel();
        }
        if (secondTimer != null) {
            secondTimer.cancel();
        }
        if (thirdTimer != null) {
            thirdTimer.cancel();
        }
        numberFinish();
    }
    @Override
    public void onDestroy(){
        if(firstTimer != null) {
            firstTimer.cancel();
        }
        if(secondTimer !=null) {
            secondTimer.cancel();
        }
        if(thirdTimer != null) {
            thirdTimer.cancel();
        }
        super.onDestroy();
    }
}