package air.texnodev.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class ActivityEnd extends AppCompatActivity {

    LottieAnimationView laugh, angry;
    Handler handler = new Handler();
    TextView time, tv_right, tv_wrong, tv_not_choose;
    int counter = 0;
    int right, wrong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        initALL();



        right = getIntent().getExtras().getInt("right");
        wrong = getIntent().getExtras().getInt("wrong");
        ShowUI();


    }

    private void initALL(){
        time = findViewById(R.id.allStates);
        tv_right = findViewById(R.id.right_side);
        tv_wrong = findViewById(R.id.wrong_side);
        tv_not_choose = findViewById(R.id.up_side);
        laugh = findViewById(R.id.animationLaugh);
        angry = findViewById(R.id.animationAngry);
    }

    private void ShowUI(){
        tv_right.setText("To'g'ri: " + right);
        tv_wrong.setText("Xato: " + wrong);
        if (right >= 18){
            laugh.setVisibility(View.VISIBLE);
            laugh.playAnimation();

        }else {
            angry.setVisibility(View.VISIBLE);
            angry.playAnimation();
        }

        if ((20 - (right + wrong)) != 0){
            tv_not_choose.setVisibility(View.VISIBLE);
            tv_not_choose.setText("Tanlanmadi: " + (20 - (right + wrong)));
        }


        runTime();
    }


    private void runTime(){
        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long l) {
                time.setText(String.valueOf(l / 1000));
            }

            @Override
            public void onFinish() {
                finish();
            }
        }.start();
    }

}