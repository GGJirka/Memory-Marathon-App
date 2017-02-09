package jimmy.gg.flashingnumbers.highscore;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

/**
 * Created by ggjimmy on 1/18/17.
 */

public class HighScoreAdapter extends BaseAdapter{
    public final static String LEVEL_KEY = "HIGHEST_LEVEL1";
    private Context context;
    private ArrayList<Score> scores;
    private View v;

    public HighScoreAdapter(Context context, ArrayList<Score> scores) {
        this.context = context;
        this.scores= scores;
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        v = View.inflate(context, R.layout.activity_score,null);
        TextView time = (TextView) v.findViewById(R.id.high_score_score);
        TextView level = (TextView) v.findViewById(R.id.high_score_level);
        level.setText(scores.get(position).getLevel());
        time.setText(scores.get(position).getHighScore()+"s");
        v.setTag(position);
        return v;
    }
}
