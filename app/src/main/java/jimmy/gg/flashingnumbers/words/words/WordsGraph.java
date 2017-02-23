package jimmy.gg.flashingnumbers.words.words;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.highscore.Score;
import jimmy.gg.flashingnumbers.main.MemoryMarathon;

public class WordsGraph extends AppCompatActivity {
    public ArrayList<Score> score;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = MemoryMarathon.sharedPreferences;
        setTitle("High Score Graph");
        initScore();
    }

    public void initScore() {
        ArrayList<Integer> points = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(sharedPreferences.getString("WORDS_COUNT_SCORE", "0")); i++) {
            points.add(Integer.valueOf(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), "none").split(" ")[3]));
            dates.add(sharedPreferences.getString("WORDS_SCORE" + String.valueOf(i), "none").split(" ")[0]);
        }
        initPoints(points, dates);
    }

    public void initPoints(ArrayList<Integer> points, ArrayList<String> dates) {
        GraphView graph = (GraphView) findViewById(R.id.words_graph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);

        DataPoint[] dataPoints = new DataPoint[points.size()];
        String[] date = new String[points.size()];

        for (int i = 0; i < points.size(); i++) {
            dataPoints[i] = new DataPoint(((double) i / points.size()), points.get(i));
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setColor(getResources().getColor(R.color.words_title_bot));
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(5);
        series.setThickness(8);
        graph.addSeries(series);
        staticLabelsFormatter.setHorizontalLabels(new String[]{dates.get(0), dates.get(dates.size() / 2)
                , dates.get(dates.size() - 1)});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
