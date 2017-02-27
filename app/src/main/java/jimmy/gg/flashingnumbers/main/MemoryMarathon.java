package jimmy.gg.flashingnumbers.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ShareActionProvider;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;
import jimmy.gg.flashingnumbers.techniques.MemoryTechniques;
import jimmy.gg.flashingnumbers.techniques.NumberMajorSystem;
import jimmy.gg.flashingnumbers.words.words.WordsMain;

public class MemoryMarathon extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_test, menu);
        return true;
    }

    /*public void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null) {
            shareActionProvider.setShareIntent(shareIntent);
        }
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.drawer_settings:
                Intent intent = new Intent(this, MainSettings.class);
                startActivity(intent);
                break;

            case R.id.drawe_techniques:
                Intent techniques = new Intent(this, MemoryTechniques.class);
                startActivity(techniques);
                break;

            case R.id.drawer_about:
                Intent about = new Intent(this, MainAboutApp.class);
                startActivity(about);
                break;

            case R.id.drawer_share:
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.action_share_message));
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                break;

            case R.id.drawer_send_feedback:
                startActivity(new Intent(this, MainSendFeedback.class));
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
