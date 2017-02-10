package jimmy.gg.flashingnumbers.LevelManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.helpers.IternalMemory;
import jimmy.gg.flashingnumbers.highscore.TabbedHighScore;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class Levels extends AppCompatActivity {
    public  ListView levels;
    public ArrayList<Level> levelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);
        setTitle("Levels");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        levelList = new ArrayList<>();
        levels = (ListView) findViewById(R.id.levels);
        levels.setItemsCanFocus(false);
        levelList = ((IternalMemory)this.getApplication()).getLevelList();
        levels.setAdapter(new LevelAdapter(getApplicationContext(), levelList));
        levels.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(levelList.get(position).getDifficult()!="locked") {
                    String level = levelList.get(position).getLevel();
                    String numbers = levelList.get(position).getNumbers();
                    String time = levelList.get(position).getTime();
                    gameStarted(level, numbers, time);
                }else{
                    Toast.makeText(getApplicationContext(),"Level locked, complete "+levelList.get(position-1).getLevel()
                            +" to unlock.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.popup_menu,menu);
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        int index = FlashingNumbers.sharedPreferences.getInt(String.valueOf(getText(R.string.LEVEL_KEY)),1);
        for(int i=index;i<levelList.size();i++){
            levelList.get(i).setDifficult("locked");
        }
        levels.setAdapter(new LevelAdapter(getApplicationContext(), levelList));
    }
    public void gameStarted(String level, String numbers, String time){
        Intent intent = new Intent(this, Numbers.class);
        intent.putExtra(EXTRA_LEVEL,level);
        intent.putExtra(EXTRA_NUMBERS,numbers);
        intent.putExtra(EXTRA_TIME,time);
        startActivity(intent);
    }

    public void onPause(){
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.high_score:
                Intent intent = new Intent(this, TabbedHighScore.class);
                intent.putExtra(String.valueOf(getText(R.string.EXTRA_PAGE)),"0");
                startActivity(intent);
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Level> getLevelList(){
        return levelList;
    }
    public final String EXTRA_NUMBERS = "jimmy.gg.flashingnumbers.NUMBERS";
    public final String EXTRA_NUMBERS_COUNT = "jimmy.gg.flashingnumbers.NUMBERS_COUNT";
    public final String EXTRA_LEVEL = "jimmy.gg.flashingnumbers.LEVEL";
    public final String EXTRA_TIME = "jimmy.gg.flashingnumbers.TIME";
}
