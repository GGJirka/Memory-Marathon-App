package jimmy.gg.flashingnumbers.techniques;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.mainAdapter.OptionAdapter;
import jimmy.gg.flashingnumbers.mainAdapter.OptionUnit;

public class MemoryTechniques extends AppCompatActivity {
    public ArrayList<OptionUnit> activities;
    public ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_tech);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Memory techniques");
        activities = new ArrayList<>();
        list = (ListView) findViewById(R.id.main_techniques);
        activities.add(new OptionUnit("numbers", "Numbers Major System"));
        activities.add(new OptionUnit("words", "Words System"));
        list.setAdapter(new OptionAdapter(getApplicationContext(), activities));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent0 = new Intent(MemoryTechniques.this, NumberMajorSystem.class);
                        startActivity(intent0);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MemoryTechniques.this, WordsMemorySystem.class);
                        startActivity(intent1);
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
