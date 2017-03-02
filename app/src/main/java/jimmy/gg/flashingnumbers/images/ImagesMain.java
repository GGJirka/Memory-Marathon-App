package jimmy.gg.flashingnumbers.images;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import jimmy.gg.flashingnumbers.R;

public class ImagesMain extends AppCompatActivity {
    public ArrayList<ImageView> imageOnLeft;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        imageOnLeft = new ArrayList<>();
        initImages();
    }

    public void initImages(){
        GridLayout gridLayout = new GridLayout(this);
        gridLayout.setColumnCount(1);
        gridLayout.setRowCount(10);
        for(int i=0;i<12;i++){
            ImageView image = new ImageView(this);
            image.setImageResource(R.drawable.image2);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
            image.setLayoutParams(layoutParams);
            gridLayout.addView(image);
        }
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.images_layout);
        layout.addView(gridLayout);
    }
}
