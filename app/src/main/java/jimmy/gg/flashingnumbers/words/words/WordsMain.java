package jimmy.gg.flashingnumbers.words.words;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.Score;

public class WordsMain extends AppCompatActivity {
    /**
     * TODO: BETTER DESIGN, FIX WORD LIST (words with less than 3 length, vulcaric words)
     * TODO: FIX WORDS THAT ARE MORE THAN ONCE SHOWED.
     */
    public static SharedPreferences sharedPreferences;
    private WordsStats words;
    private BufferedReader bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_main2);
        setTitle(getText(R.string.words_activity_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        words = new WordsStats(3, 0);
        findViewById(R.id.words_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        TextSwitcher switcher = (TextSwitcher) findViewById(R.id.words_word);
        TextSwitcher liveSwitcher = (TextSwitcher) findViewById(R.id.words_lives);

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView wordText = new TextView(WordsMain.this);
                wordText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                wordText.setTextSize(30);
                return wordText;
            }
        });
        liveSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView liveText = new TextView(WordsMain.this);
                liveText.setTextSize(24);
                return liveText;
            }
        });

        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("words_settings_anim", true)) {
            Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_top);
            Animation live_in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            Animation live_out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
            liveSwitcher.setInAnimation(live_in);
            liveSwitcher.setOutAnimation(live_out);
            switcher.setInAnimation(in);
        }
        if (sharedPreferences.getString(readText(R.string.words_activity_dialog), "0").equals("0")) {
            init();
        }
        initWordList();
    }
    public void init() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.checkbox, null);
        final CheckBox checkbox = (CheckBox) view.findViewById(R.id.check);
        new AlertDialog.Builder(WordsMain.this)
                .setView(view)
                .setTitle("How to play")
                .setMessage("There will be always one word displayed.\n" +
                        "press seen if you already seen that word " +
                        "or press new if it is new.")
                .setPositiveButton(getText(R.string.quick_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkbox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(readText(R.string.words_activity_dialog), "check");
                            editor.apply();
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if (checkbox.isChecked()) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(readText(R.string.words_activity_dialog), "check");
                            editor.apply();
                        }
                    }
                })
                .show();
    }

    public void initWordList() {
        try {
            bf = new BufferedReader(new InputStreamReader(getResources().getAssets().open("wordlist.txt")));
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    String line = "";
                    while (line != null) {
                        try {
                            line = bf.readLine();
                            words.addWord(line);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    TextSwitcher word = (TextSwitcher) findViewById(R.id.words_word);
                    TextSwitcher lives = (TextSwitcher) findViewById(R.id.words_lives);
                    int r = new Random().nextInt(words.getWordList().size());
                    lives.setText("3");
                    word.setText(words.getWords(r));
                    words.setRandom(5);
                }
            };
            runOnUiThread(run);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newWord(View view) {
        TextSwitcher word = (TextSwitcher) findViewById(R.id.words_word);
        TextView wordText = (TextView) word.getCurrentView();
        int r = new Random().nextInt(words.getWordList().size());
        if (words.isInUsed(wordText.getText().toString())) {
            TextSwitcher liveSwitcher = (TextSwitcher) findViewById(R.id.words_lives);
            words.setLives(words.getLives() - 1);
            liveSwitcher.setText("" + words.getLives());
            if (words.getLives() == 0) {
                gameEnds();
                TextView scored = (TextView) findViewById(R.id.words_end_scored);
                scored.setText("Score: " + words.getScore());
                return;
            }
        } else {
            TextView score = (TextView) findViewById(R.id.words_score);
            words.setScore(words.getScore() + 1);
            score.setText("score: " + words.getScore());
        }
        usedAndUnusedCycle(word, r, view);
    }

    public void saveData() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("WORDS_COUNT_SCORE", String.valueOf
                (Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")) + 1));

        editor.putString("WORDS_SCORE" + sharedPreferences.getString("WORDS_COUNT_SCORE", "0")
                , date + " | score: " + words.getScore());
        editor.commit();
        startActivity(new Intent(this, WordsScore.class));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        runOnUiThread(runnable);
    }

    public void seenWord(View v) {
        TextSwitcher word = (TextSwitcher) findViewById(R.id.words_word);
        TextView wordText = (TextView) word.getCurrentView();
        int r = new Random().nextInt(words.getWordList().size());
        if (!words.isInUsed(wordText.getText().toString())) {
            TextSwitcher liveSwitcher = (TextSwitcher) findViewById(R.id.words_lives);
            words.setLives(words.getLives() - 1);
            liveSwitcher.setText("" + words.getLives());
            if (words.getLives() == 0) {
                gameEnds();
                TextView scored = (TextView) findViewById(R.id.words_end_scored);
                scored.setText("Score: " + words.getScore());
                return;
            }
        } else {
            TextView score = (TextView) findViewById(R.id.words_score);
            words.setScore(words.getScore() + 1);
            score.setText("score: " + words.getScore());
        }
        usedAndUnusedCycle(word, r, v);
    }

    public void usedAndUnusedCycle(TextSwitcher word, int r, View v) {
        TextView wordText = (TextView) word.getCurrentView();
        if (words.isUsed()) {
            if (words.count != words.getRandom() + 1) {
                if (!words.isInUsed(wordText.getText().toString())) {
                    for (int i = 0; i < 5; i++) {
                        words.addNewWord(wordText.getText().toString());
                    }
                }
                word.setText(words.getWords(r));
                words.getWordList().remove(r);
                words.count++;
            } else {
                if (!words.isInUsed(wordText.getText().toString())) {
                    for (int i = 0; i < 5; i++) {
                        words.addNewWord(wordText.getText().toString());
                    }
                }
                int rand = new Random().nextInt(words.getUsedWords().size());
                word.setText(words.getUsedWords().get(rand));
                words.getUsedWords().remove(rand);
                words.count = 0;
                if (words.getUsedWords().size() <= 15) {
                    words.random = new Random().nextInt(3);
                } else {
                    words.random = new Random().nextInt(4);
                }
                words.count++;
                words.setUsed(false);
            }
        } else {
            if (words.count != words.getRandom() + 1) {
                Random random = new Random();
                int rand = random.nextInt(words.getUsedWords().size() - 1);
                if (!words.isInUsed(wordText.getText().toString())) {
                    for (int i = 0; i < 5; i++) {
                        words.addNewWord(wordText.getText().toString());
                    }
                }
                word.setText(words.getUsedWords().get(rand));
                words.getUsedWords().remove(rand);
                words.count++;
            } else {
                if (!words.isInUsed(wordText.getText().toString())) {
                    for (int i = 0; i < 5; i++) {
                        words.addNewWord(wordText.getText().toString());
                    }
                }
                word.setText(words.getWords(r));
                words.getWordList().remove(r);
                words.count = 0;
                /*if (words.getUsedWords().size() <= 15) {
                    words.random = new Random().nextInt(3);
                } else {
                    words.random = new Random().nextInt(4);
                }*/
                words.random = new Random().nextInt(5) + 1;
                words.count++;
                words.setUsed(true);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.words_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearView();
    }
    public void gameEnds() {
        findViewById(R.id.words_new).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_seen).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_save).setVisibility(View.VISIBLE);
        findViewById(R.id.words_lives).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_score).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_word).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_live).setVisibility(View.INVISIBLE);
        TextView text = (TextView) findViewById(R.id.words_end_high_score);
        if (getHighestScore() != 0) {
            text.setText("Highest score: " + getHighestScore());
            text.setVisibility(View.VISIBLE);
        }
    }

    public String readText(int string) {
        return String.valueOf(getText(string));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedPreferences.getString("WORDS_STATE", "2").equals("0")) {
            clearView();
            sharedPreferences.edit().putString("WORDS_STATE", "2").apply();
        }
        TextSwitcher switcher = (TextSwitcher) findViewById(R.id.words_word);
        TextSwitcher liveSwitcher = (TextSwitcher) findViewById(R.id.words_lives);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (pref.getBoolean("words_settings_anim", true)) {
            Animation in = AnimationUtils.loadAnimation(this, R.anim.anim_top);
            switcher.setInAnimation(in);
            Animation live_in = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
            Animation live_out = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
            liveSwitcher.setInAnimation(live_in);
            liveSwitcher.setOutAnimation(live_out);
        } else {
            Animation in = AnimationUtils.loadAnimation(this, R.anim.no_anim);
            Animation live_in = AnimationUtils.loadAnimation(this, R.anim.no_anim);
            Animation live_out = AnimationUtils.loadAnimation(this, R.anim.no_anim);
            liveSwitcher.setInAnimation(live_in);
            liveSwitcher.setOutAnimation(live_out);
            switcher.setInAnimation(in);
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    public void clearView() {
        words.count = 1;
        words.setLives(3);
        words.setScore(0);
        TextView score = (TextView) findViewById(R.id.words_score);
        TextSwitcher liveSwitcher = (TextSwitcher) findViewById(R.id.words_lives);
        TextSwitcher word = (TextSwitcher) findViewById(R.id.words_word);
        word.setVisibility(View.VISIBLE);
        score.setText("score: " + words.getScore());
        liveSwitcher.setText("" + words.getLives());
        int r = new Random().nextInt(words.getWordList().size());
        word.setText(words.getWords(r));
        words.setUsed(true);
        words.setRandom(5);
        words.getUsedWords().removeAll(words.getUsedWords());
        TextView scored = (TextView) findViewById(R.id.words_end_scored);
        scored.setText("");
        findViewById(R.id.words_new).setVisibility(View.VISIBLE);
        findViewById(R.id.words_seen).setVisibility(View.VISIBLE);
        findViewById(R.id.words_save).setVisibility(View.INVISIBLE);
        findViewById(R.id.words_lives).setVisibility(View.VISIBLE);
        findViewById(R.id.words_live).setVisibility(View.VISIBLE);
        findViewById(R.id.words_score).setVisibility(View.VISIBLE);
        findViewById(R.id.words_end_high_score).setVisibility(View.INVISIBLE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.retry_icon:
                clearView();
                break;
            case R.id.high_score:
                startActivity(new Intent(this, WordsScore.class));
                break;
            case R.id.words_settings:
                startActivity(new Intent(this, WordsSettings.class));
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public int getHighestScore() {
        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
            Score row = new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), ""));
            String[] data = row.getText().split(" ");
            int score = Integer.parseInt(data[3]);
            for (int j = 0; j < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); j++) {
                Score row2 = new Score(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(j), ""));
                String[] data2 = row2.getText().split(" ");
                int score2 = Integer.parseInt(data2[3]);
                if (score < score2) {
                    score = score2;
                }
            }
            return score;
        }
        return 0;
    }
}
