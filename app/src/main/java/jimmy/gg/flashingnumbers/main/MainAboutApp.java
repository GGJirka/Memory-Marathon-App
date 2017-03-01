package jimmy.gg.flashingnumbers.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.widget.TextView;

import jimmy.gg.flashingnumbers.R;

public class MainAboutApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_about_app);
        setTitle(getString(R.string.about_app_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*TextView githubLink = (TextView) findViewById(R.id.github_link);
        githubLink.setMovementMethod(LinkMovementMethod.getInstance());*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
