package jimmy.gg.flashingnumbers.images;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jimmy.gg.flashingnumbers.LevelManager.Numbers;
import jimmy.gg.flashingnumbers.R;
import jimmy.gg.flashingnumbers.menu.FlashingNumbers;


public class ImagesMain extends AppCompatActivity {
    public static ArrayList<ImageView> imageOnLeft;
    public ArrayList<Integer> images;
    private int index=0;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageOnLeft = new ArrayList<>();
        images = new ArrayList<>();
        initList();
        initImages();
    }

    public void initList(){
        images.add(R.drawable.image1);
        images.add(R.drawable.image2);
        images.add(R.drawable.image3);
        images.add(R.drawable.image4);
        images.add(R.drawable.image1);
        images.add(R.drawable.image2);
        images.add(R.drawable.image3);
        images.add(R.drawable.image4);
        images.add(R.drawable.image1);
        images.add(R.drawable.image2);
        images.add(R.drawable.image3);
        images.add(R.drawable.image4);
    }
    //INIT of top images.
    public void initImages(){
        long seed = System.nanoTime();
        Collections.shuffle(images, new Random(seed));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout gridLayout = new LinearLayout(this);
        gridLayout.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.images_layout);
        layout.addView(linearLayout);

        /*gridLayout.setColumnCount(10);
        gridLayout.setRowCount(1);*/

       /* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        (this).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);*/

        for(int i=0;i<images.size();i++){
            if(i%8==0){
                gridLayout = new LinearLayout(this);
                gridLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(gridLayout);
            }
            ImageView image = new ImageView(this);
            image.setImageResource(images.get(i));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
            layoutParams.setMargins(20, 0, 0, 20);
            image.setLayoutParams(layoutParams);
            imageOnLeft.add(image);
            gridLayout.addView(image);
        }
        imageOnLeft.get(index).setColorFilter(Color.argb(70, 0, 0, 0));
        ImageView image = (ImageView) findViewById(R.id.image);
        image.setImageResource(images.get(index));
    }

    public void nextImage(View view){
        if(index!=images.size()-1) {
            ImageView image = (ImageView) findViewById(R.id.image);
            index++;
            image.setImageResource(images.get(index));
            imageOnLeft.get(index).setColorFilter(Color.argb(70, 0, 0, 0));
            imageOnLeft.get(index-1).setColorFilter(Color.argb(0, 0, 0, 0));
        }
    }

    public void previousImage(View view){
        if(index!=0) {
            ImageView image = (ImageView) findViewById(R.id.image);
            index--;
            image.setImageResource(images.get(index));
            imageOnLeft.get(index).setColorFilter(Color.argb(70, 0, 0, 0));
            imageOnLeft.get(index+1).setColorFilter(Color.argb(0, 0, 0, 0));
        }
    }

    public void done(View view){
        Intent intent = new Intent(this, ImagesRemember.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}
