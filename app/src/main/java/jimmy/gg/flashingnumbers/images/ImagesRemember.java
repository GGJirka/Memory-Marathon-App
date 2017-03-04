package jimmy.gg.flashingnumbers.images;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import jimmy.gg.flashingnumbers.R;

public class ImagesRemember extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_remember);
        initBoard();
        initPictures();
    }
    public void initBoard(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.images_linear);
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.HORIZONTAL);

        for(int i=0;i<ImagesMain.imageOnLeft.size();i++){
            if(i%6==0){
                linear = new LinearLayout(this);
                linear.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(linear);
            }
            LinearLayout index = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(130,130);
            params.setMargins(20, 0, 0, 20);
            index.setLayoutParams(params);
            index.setBackgroundResource(R.drawable.rectangle_drop);
            index.setOnDragListener(new MyDragListener());
            linear.addView(index);
        }
    }

    public void initPictures(){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.images_linear);
        LinearLayout linear = new LinearLayout(this);
        linear.setOrientation(LinearLayout.HORIZONTAL);
        linear.setOnDragListener(new MyDragListener());

        for(int i=0;i<ImagesMain.imageOnLeft.size();i++){
            if(i%6==0){
                linear = new LinearLayout(this);
                linear.setOnDragListener(new MyDragListener());
                linear.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.addView(linear);
            }
            ImageView index = new ImageView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(130,130);
            layoutParams.setMargins(20, 0, 0, 20);
            index.setImageResource(R.drawable.image1);
            index.setLayoutParams(layoutParams);
            index.setOnTouchListener(new MyTouchListener());
            linear.addView(index);
        }
    }
        private final class MyTouchListener implements View.OnTouchListener {
            public boolean onTouch(View view, MotionEvent  motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    view.setVisibility(View.INVISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        }

        class MyDragListener implements View.OnDragListener {
            Drawable enterShape = getResources().getDrawable(
                    R.drawable.shape_drop);
            Drawable normalShape = getResources().getDrawable(R.drawable.rectangle_drop);

            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // do nothing
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        v.setBackgroundDrawable(enterShape);
                        break;
                    case DragEvent.ACTION_DRAG_EXITED:
                        v.setBackgroundDrawable(normalShape);
                        break;
                    case DragEvent.ACTION_DROP:
                        // Dropped, reassign View to ViewGroup
                        View view = (View) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);
                        LinearLayout container = (LinearLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);
                        break;
                    case DragEvent.ACTION_DRAG_ENDED:
                        v.setBackgroundDrawable(normalShape);
                    default:
                        break;
                }
                return true;
            }
        }
}
