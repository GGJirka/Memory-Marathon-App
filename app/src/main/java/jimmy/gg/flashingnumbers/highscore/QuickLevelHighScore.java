package jimmy.gg.flashingnumbers.highscore;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class QuickLevelHighScore extends Fragment {
    private ArrayList<Score> scores;
    public SharedPreferences sharedPreferences;
    public View rootView;
    public BaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        rootView = inflater.inflate(R.layout.activity_quick_level_high_score, container, false);
        sharedPreferences = FlashingNumbers.sharedPreferences;
        scores = new ArrayList<>();
        init(rootView);
        return rootView;
    }

    public void init(View rootView){
        ListView list = (ListView) rootView.findViewById(R.id.quick_hs_list);
        for (int i = 1; i <= sharedPreferences.getInt(KEY_COUNT, 0); i++) {
            String s = sharedPreferences.getString(KEY + String.valueOf(i), "0");
            int length = s.split(" ").length;
            scores.add(new Score(s.split(" ")[0]+" "+"cs "+s.split(" ")[length-1]));
        }
        adapter = new HighScoreAdapter(rootView.getContext(), scores);
        if(scores.size()>0) {
            list.setAdapter(adapter);
        }else {
            addView();
        }
        if (FlashingNumbers.sharedPreferences.getString("HIGH_SCORE_SORT", "0").equals("0")) {
            sortByDate();
        } else {
            sortByScore();
        }
    }

    public void setListener(ListView list) {
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(parent.getContext())
                        .setItems(new CharSequence[]{getString(R.string.delete_score)}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                scores.remove(position);
                                adapter.notifyDataSetChanged();
                                sharedPreferences.edit().remove(KEY + String.valueOf(position)).apply();
                                sharedPreferences.edit().putInt(KEY_COUNT, sharedPreferences.getInt(KEY_COUNT, 0) - 1).apply();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void addView() {
        TextView view = new TextView(rootView.getContext());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 450, 0, 0);
        view.setLayoutParams(params);
        view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        view.setText(R.string.nothing_to_display);
        view.setTextSize(20);
        view.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout layout = (RelativeLayout) rootView.findViewById(R.id.activity_quick_level_high_score);
        layout.addView(view);
    }

    public void removeList(){
        for (int i = 0; i < this.scores.size(); i++) {
            this.sharedPreferences.edit().remove(KEY + String.valueOf(i)).apply();
        }
        this.sharedPreferences.edit().remove(KEY_COUNT).apply();
        addView();
        this.scores.removeAll(scores);
        adapter.notifyDataSetChanged();
    }

    public void sortByScore() {
        scores.removeAll(scores);
        ArrayList<Integer> addScore = new ArrayList<>();
        for (int i = 1; i <= FlashingNumbers.sharedPreferences.getInt(KEY_COUNT, 0); i++) {
            Score score = new Score(FlashingNumbers.sharedPreferences.getString(KEY + String.valueOf(i), ""));
            int length = score.getText().split(" ").length;
            addScore.add(Integer.parseInt(score.getText().split(" ")[length-1]));
        }
        Collections.sort(addScore, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int i = 0; i < addScore.size(); i++) {
            for (int j = 1; j <= FlashingNumbers.sharedPreferences.getInt(KEY_COUNT, 0); j++) {
                Score score = new Score(FlashingNumbers.sharedPreferences.getString(KEY + String.valueOf(j), ""));
                int length = score.getText().split(" ").length;
                int number = Integer.parseInt(score.getText().split(" ")[length-1]);
                if (number == addScore.get(i)) {
                    String s = sharedPreferences.getString(KEY + String.valueOf(j), "0");
                    scores.add(new Score(s.split(" ")[0]+" | "+getString(R.string.score)+" "+s.split(" ")[length-1]));
                   // scores.add(score);
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void sortByDate(){
        scores.removeAll(scores);
        for (int i = 1; i <= FlashingNumbers.sharedPreferences.getInt(KEY_COUNT, 0); i++) {
            //scores.add(new Score(sharedPreferences.getString(KEY + String.valueOf(i), "0")));
            String s = sharedPreferences.getString(KEY + String.valueOf(i), "0");
            int length = s.split(" ").length;
            scores.add(new Score(s.split(" ")[0]+" | "+getString(R.string.score)+" "+s.split(" ")[length-1]));
        }
        Collections.reverse(scores);
        adapter.notifyDataSetChanged();
    }

    public static final String KEY = "HIGHEST_SCORE_QUICK";
    public static final String KEY_COUNT = "HIGHEST_SCORE_QUICK_COUNT";

}
