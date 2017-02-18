package jimmy.gg.flashingnumbers.settings;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

public class NumbersSettings extends AppCompatActivity {
    public ArrayList<String> settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_settings);
        setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        settings = new ArrayList<>();
        ListView listView = (ListView) findViewById(R.id.settings_navigate);
        settings.add(new String("Numbers settings"));
        settings.add(new String("Quick level settings"));
        listView.setAdapter(new SettingsAdapter(getApplicationContext(), settings));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(NumbersSettings.this, NumbersLevelsSettings.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(NumbersSettings.this, QuickLevelSettings.class);
                    startActivity(intent);
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
