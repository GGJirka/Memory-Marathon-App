package jimmy.gg.flashingnumbers.highscore;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;

public class HighScore extends Fragment{
    public final static String KEY_HIGH_SCORE ="KEY_HIGH_SCORE";
    public ArrayList<Score> scores;
    public ArrayList<Level> levelList;
    public ListView highScores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_high_score, container, false);
        scores = new ArrayList<>();
        levelList = new ArrayList<>();
        highScores = (ListView) rootView.findViewById(R.id.high_scores);

        for(int i=0;i<Levels.levelList.size();i++){
            Level lv = Levels.levelList.get(i);
            if(Levels.highScore.getString(KEY_HIGH_SCORE+String.valueOf(i),"0")!="0") {
                scores.add(new Score(lv.getLevel() + " (" + lv.getNumbers()+"): ",
                        Levels.highScore.getString(KEY_HIGH_SCORE + String.valueOf(i), "0")));
            }
        }
        highScores.setAdapter(new HighScoreAdapter(rootView.getContext(),scores));
        return rootView;
    }
}
