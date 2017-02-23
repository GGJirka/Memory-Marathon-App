package jimmy.gg.flashingnumbers.highscore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;

public class NumbersGraph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers_graph);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.quick_level_graph_title));
        initScore();
    }

    public void initScore() {
        GraphView graph = (GraphView) findViewById(R.id.numbers_graph);
        StaticLabelsFormatter labelsFormatter = new StaticLabelsFormatter(graph);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalableY(true);
        graph.getViewport().setScrollableY(true);
        ArrayList<Integer> scores = new ArrayList<>();
        ArrayList<String> dates = new ArrayList<>();

        for (int i = 1; i <= FlashingNumbers.sharedPreferences.getInt(KEY_COUNT, 0); i++) {
            scores.add(Integer.valueOf(FlashingNumbers.sharedPreferences.getString(KEY + String.valueOf(i), "")
                    .split(" ")[2]));
            dates.add(FlashingNumbers.sharedPreferences.getString(KEY + String.valueOf(i), "")
                    .split(" ")[0]);
        }

        DataPoint[] points = new DataPoint[scores.size()];
        for (int i = 0; i < scores.size(); i++) {
            points[i] = new DataPoint(((double) i / scores.size()), scores.get(i));
        }
        LineGraphSeries<DataPoint> graphSeries = new LineGraphSeries<>(points);
        graphSeries.setColor(getResources().getColor(R.color.numbers_title_bot));
        graphSeries.setDrawDataPoints(true);
        graphSeries.setDataPointsRadius(8);
        graphSeries.setThickness(7);
        graph.setClickable(false);
        graph.addSeries(graphSeries);
        labelsFormatter.setHorizontalLabels(new String[]{dates.get(0), dates.get(dates.size() - 1)});
        graph.getGridLabelRenderer().setLabelFormatter(labelsFormatter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }

    public final String KEY = "HIGHEST_SCORE_QUICK";
    public final String KEY_COUNT = "HIGHEST_SCORE_QUICK_COUNT";
}
