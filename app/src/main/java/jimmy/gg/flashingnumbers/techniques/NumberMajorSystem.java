package jimmy.gg.flashingnumbers.techniques;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import jimmy.gg.flashingnumbers.R;

public class NumberMajorSystem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_major_system);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Number memory system");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
