package jimmy.gg.flashingnumbers.highscore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Level;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class HighScore extends Fragment{
    public final String KEY_HIGH_SCORE ="KEY_HIGH_SCORE";
    public ArrayList<Score> scores;
    public ListView highScores;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_high_score, container, false);
        scores = new ArrayList<>();
        highScores = (ListView) rootView.findViewById(R.id.high_scores_tab1);
        for(int i = 0; i<FlashingNumbers.levelList.size(); i++){
            Level lv = FlashingNumbers.levelList.get(i);
            if(FlashingNumbers.sharedPreferences.getString(KEY_HIGH_SCORE+String.valueOf(i),"0")!="0") {
                scores.add(new Score(lv.getLevel() + " (" + lv.getNumbers()+"): ",
                        FlashingNumbers.sharedPreferences.getString(KEY_HIGH_SCORE + String.valueOf(i), "0")));
            }

        }
        if(scores.size()==0){
            TextView view = new TextView(rootView.getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 450, 0, 0);
            view.setLayoutParams(params);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setText("Nothing to display");
            view.setTextSize(20);
            view.setTextColor(getResources().getColor(R.color.black));
            RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.activity_high_score);
            layout.addView(view);
        }
        highScores.setAdapter(new HighScoreAdapter(rootView.getContext(),scores));
        return rootView;
    }

    public void removeList() {
        for (int i = 0; i < FlashingNumbers.levelList.size(); i++) {
            FlashingNumbers.sharedPreferences.edit().remove(KEY_HIGH_SCORE + String.valueOf(i));
        }
        this.scores.removeAll(scores);
    }
}
