package jimmy.gg.flashingnumbers.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jimmy.gg.flashingnumbers.R;


public class NumbersLevelsSettings extends AppCompatActivity {
    private SettingsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.NumbersStyle);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_settings);
        fragment = new SettingsFragment();
        setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment()).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            trimCache(this);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public class SettingsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        private final String KEY_SETTINGS_GROUP = "levels_test_g";
        private final String KEY_SETTINGS_ROW = "levels_test_r";
        private final String KEY_SETTINGS_TIMER = "levels_test_t";
        private SharedPreferences prefs;
        private Preference pref1, pref2, pref3;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.levels_test);
            prefs = getPreferenceScreen().getSharedPreferences();
            pref1 = findPreference(KEY_SETTINGS_GROUP);
            pref2 = findPreference(KEY_SETTINGS_ROW);
            pref3 = findPreference(KEY_SETTINGS_TIMER);
        }

        @Override
        public void onStart() {
            super.onStart();
            pref1.setSummary(getString(R.string.numbers_size_group)
                    + prefs.getString(KEY_SETTINGS_GROUP, "Actual number size in group: 2"));
            pref2.setSummary(getString(R.string.numbers_size_row)
                    + prefs.getString(KEY_SETTINGS_ROW, "Actual number of numbers in row: 20"));
            pref3.setSummary(getString(R.string.actual_inspection_time)
                    + prefs.getString(KEY_SETTINGS_TIMER, "Actual inspection time: 3s") + "s");
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference pref = findPreference(key);

            switch (key) {
                case KEY_SETTINGS_GROUP:
                    pref.setSummary(getString(R.string.numbers_size_group) + sharedPreferences.getString(key, ""));
                    break;
                case KEY_SETTINGS_ROW:
                    pref.setSummary(getString(R.string.numbers_size_row) + sharedPreferences.getString(key, ""));
                    break;
                case KEY_SETTINGS_TIMER:
                    if (sharedPreferences.getString(key, "").equals("none")) {
                        pref.setSummary(R.string.inspection_time_none);
                    } else {
                        pref.setSummary(getString(R.string.actual_inspection_time) + sharedPreferences.getString(key, "") + "s");
                    }
                    break;
               /* case "settings_timer_quick":
                    CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("settings_timer_quick");
                    if(checkBoxPreference.isChecked())
                        FlashingNumbers.sharedPreferences.edit().putBoolean("settings_timer_quick",true);
                    else
                        FlashingNumbers.sharedPreferences.edit().putBoolean("settings_timer_quick",false);
                    break;*/
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            prefs.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            prefs.unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}