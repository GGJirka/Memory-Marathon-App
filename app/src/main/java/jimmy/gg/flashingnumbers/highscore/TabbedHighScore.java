package jimmy.gg.flashingnumbers.highscore;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class TabbedHighScore extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private HighScore highScore;
    private QuickLevelHighScore quickHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_high_score);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        highScore = new HighScore();
        quickHighScore = new QuickLevelHighScore();
        mViewPager = (ViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        String actualPage = getIntent().getStringExtra(String.valueOf(getText(R.string.EXTRA_PAGE)));

        if(actualPage.equals("0")){
            mViewPager.setCurrentItem(0);
        }else{
            mViewPager.setCurrentItem(1);
        }
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.high_score_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.words_score_menu, menu);
        if (FlashingNumbers.sharedPreferences.getString(HIGH_SCORE_SORT, "0").equals("0")) {
            menu.findItem(R.id.sort_date).setChecked(true);
        } else {
            menu.findItem(R.id.sort_score).setChecked(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_date:
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(1);
                } else {
                    if (item.isChecked())
                        item.setChecked(false);
                    else
                        item.setChecked(true);

                    quickHighScore.sortByDate();
                    SharedPreferences.Editor editor = FlashingNumbers.sharedPreferences.edit();
                    editor.putString(HIGH_SCORE_SORT, "0");
                    editor.apply();
                }
                break;
            case R.id.sort_score:
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(1);
                } else {
                    if (item.isChecked())
                        item.setChecked(false);
                    else
                        item.setChecked(true);

                    quickHighScore.sortByScore();
                    SharedPreferences.Editor editor = FlashingNumbers.sharedPreferences.edit();
                    editor.putString(HIGH_SCORE_SORT, "1");
                    editor.apply();
                }
                break;
            case R.id.words_delete:
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(1);
                } else {
                    showDialog();
                }
                break;
            case R.id.graph:
                if (mViewPager.getCurrentItem() == 0) {
                    mViewPager.setCurrentItem(1);
                } else {
                    if(FlashingNumbers.sharedPreferences.getInt("HIGHEST_SCORE_QUICK_COUNT", 0)!=0) {
                        Intent graphIntent = new Intent(this, NumbersGraph.class);
                        startActivity(graphIntent);
                    }else{
                        Toast.makeText(getApplicationContext(), R.string.graph_no_score, Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                if (FlashingNumbers.sharedPreferences.getString("TAB_STATE", "2").equals("0")) {
                    FlashingNumbers.sharedPreferences.edit().putString("QUICK_LEVEL_STATE", "0").apply();
                    FlashingNumbers.sharedPreferences.edit().putString("TAB_STATE", "2").apply();
                }
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void showDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.delete_all_score)
                .setMessage(R.string.delete_all_score_summary)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quickHighScore.removeList();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_high_score, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    @Override
    public void onBackPressed() {
        if (FlashingNumbers.sharedPreferences.getString("TAB_STATE", "2").equals("0")) {
            FlashingNumbers.sharedPreferences.edit().putString("QUICK_LEVEL_STATE", "0").apply();
            FlashingNumbers.sharedPreferences.edit().putString("TAB_STATE", "2").apply();
        }
        this.finish();
    }
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    highScore = new HighScore();
                    return highScore;
                case 1:
                    quickHighScore = new QuickLevelHighScore();
                    return quickHighScore;
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.levels_title);
                case 1:
                    return getString(R.string.quick_level_title);
            }
            return null;
        }
    }

    public final String HIGH_SCORE_SORT = "HIGH_SCORE_SORT";
}
