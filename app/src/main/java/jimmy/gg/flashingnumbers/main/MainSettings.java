package jimmy.gg.flashingnumbers.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.settings.NumbersSettings;
import jimmy.gg.flashingnumbers.settings.SettingsAdapter;
import jimmy.gg.flashingnumbers.words.words.WordsSettings;

public class MainSettings extends AppCompatActivity {
    public ArrayList<String> settings;
    public ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);
        setTitle("Settings");
        settings = new ArrayList<>();
        list = (ListView) findViewById(R.id.main_settings);
        settings.add(new String("General settings"));
        settings.add(new String("Numbers settings"));
        settings.add(new String("Words settings"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list.setAdapter(new SettingsAdapter(getApplicationContext(), settings));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainSettings.this, GeneralSettings.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent numbersSettings = new Intent(MainSettings.this, NumbersSettings.class);
                        startActivity(numbersSettings);
                        break;
                    case 2:
                        Intent wordsSettings = new Intent(MainSettings.this, WordsSettings.class);
                        startActivity(wordsSettings);
                        break;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
