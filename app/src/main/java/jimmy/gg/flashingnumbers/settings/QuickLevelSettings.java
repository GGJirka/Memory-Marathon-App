package jimmy.gg.flashingnumbers.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jimmy.gg.flashingnumbers.R;

public class QuickLevelSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_level_settings);
        setTitle("Quick level settings");
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LevelsSettings()).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public class LevelsSettings extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.quick_level_settings);
        }
    }
}
