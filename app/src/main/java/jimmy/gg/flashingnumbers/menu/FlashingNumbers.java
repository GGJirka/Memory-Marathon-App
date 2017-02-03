package jimmy.gg.flashingnumbers.menu;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;

import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.main.MainActivity;
import jimmy.gg.flashingnumbers.quicklevel.QuickLevel;
import jimmy.gg.flashingnumbers.settings.NumbersSettings;

public class FlashingNumbers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashing_numbers);
        String title = "Menu";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void levelsClicked(View view){
        Intent intent = new Intent(this,Levels.class);
        startActivity(intent);
    }
    public void settingsClicked(View view){
        Intent intent = new Intent(this, NumbersSettings.class);
        startActivity(intent);
    }
    public void quickClicked(View view){
        Intent intent = new Intent(this,QuickLevel.class);
        startActivity(intent);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
