package jimmy.gg.flashingnumbers.highscore;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.LevelManager.Levels;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class QuickLevelHighScore extends Fragment {
    /*
    * TODO: BUGFIX ON START - SETTINGS,SHARED PREFERENCE IN FLASHINGNUMBERS,DATA STORAGE,LEVELS .
    * */
    private ArrayList<Score> scores;
    public SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.activity_quick_level_high_score, container, false);
        sharedPreferences = FlashingNumbers.sharedPreferences;
        scores = new ArrayList<>();
        init(rootView);
        return rootView;
    }

    public void init(View rootView){
        ListView list = (ListView) rootView.findViewById(R.id.quick_hs_list);
        for(int i=0;i<sharedPreferences.getInt(KEY_COUNT,0);i++){
            scores.add(new Score("datum",sharedPreferences.getString(KEY+String.valueOf(i),"0")));
        }
        if(scores.size()>0) {
            list.setAdapter(new HighScoreAdapter(rootView.getContext(), scores));
        }else {
            TextView view = new TextView(rootView.getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 450, 0, 0);
            view.setLayoutParams(params);
            view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            view.setText("Nothing to display");
            view.setTextSize(20);
            view.setTextColor(getResources().getColor(R.color.black));
            RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.activity_quick_level_high_score);
            layout.addView(view);
        }
    }

    public static final String KEY = "HIGHEST_SCORE_QUICK";
    public static final String KEY_COUNT = "HIGHEST_SCORE_QUICK_COUNT";

}
