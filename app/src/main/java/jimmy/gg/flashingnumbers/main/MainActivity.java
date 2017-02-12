package jimmy.gg.flashingnumbers.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;
import jimmy.gg.flashingnumbers.words.words.WordsMain;

public class MainActivity extends AppCompatActivity {
    /*TODO: BUG IN NUMBER REMEMBER ALL NUMBERS IN ONE ROW.
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void numbersGameStart(View v) {
        Intent intent = new Intent(this, FlashingNumbers.class);
        startActivity(intent);
    }

    public void wordsGameStart(View v) {
        Intent intent = new Intent(this, WordsMain.class);
        startActivity(intent);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
