package air.texnodev.lesson6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import air.texnodev.lesson6.Adapter.Adapter_Answers;
import air.texnodev.lesson6.Models.Root;

public class MainActivity extends AppCompatActivity implements OnItemsClick {
    ArrayList<String> strings = new ArrayList<>();
    RecyclerView recyclerView;
    TextView tv_title, start, end, now, setnum, raqami1, raqami2;
    ImageView imageView;
    LinearLayout yakunlamoq;
    HashMap<Integer, Boolean> hashMap = new HashMap<>();
    NestedScrollView nestedScrollView;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initALL();

        int pos = getIntent().getExtras().getInt("page");

        showQuestion((pos * 20));
        end.setText(String.valueOf((pos + 20)));

    }


    private void initALL(){
        start = findViewById(R.id.start_num);
        end = findViewById(R.id.end_num);
        now = findViewById(R.id.at_the_moment);
        raqami1 = findViewById(R.id.raqami);
        raqami2 = findViewById(R.id.raqami2);
        nestedScrollView = findViewById(R.id.Question_place);
    }

    private Root getData(int row){
        Type type = new TypeToken<ArrayList<Root>>(){}.getType();
        Gson gson = new Gson();
        List<Root> root = gson.fromJson(getRaw(), type);
        return root.get(row);
    }
    private void showQuestion(int pozission){



        now.setText(String.valueOf(pozission));

        Root root = getData(pozission);
        int plus_question = (pozission + 1);
        String text = plus_question + ". Savol: " + root.question;
        tv_title = findViewById(R.id.savol);
        tv_title.setText(text);
        nestedScrollView.smoothScrollTo(0,0);
        imageView = findViewById(R.id.action_image);
        if (!root.image_q.isEmpty()){

            int image_id = getResources().getIdentifier(root.image_q, "drawable", getPackageName());
            imageView.setImageResource(image_id);
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }
        strings.clear();
        for (String str : root.answers) {
            strings.add(str);
        }

        recyclerView = findViewById(R.id.example_answerview);
        Adapter_Answers adapter_answers = new Adapter_Answers(this, strings,this);
        recyclerView.setAdapter(adapter_answers);



    }

    private String getRaw(){

        InputStream raw = getResources().openRawResource(R.raw.question);
        Scanner sc = new Scanner(raw);
        StringBuilder stringBuilder = new StringBuilder();

        while (sc.hasNextLine()){
            stringBuilder.append(sc.nextLine());
        }
        return stringBuilder.toString();
    }


    @Override
    public void onClick(View view, int pozitsion){
        ProgressBar progressBar = findViewById(R.id.progress_circular);
        TextView textView = view.findViewById(R.id.example_answer);
        LinearLayout layout = view.findViewById(R.id.labeled);
        int num = Integer.parseInt(now.getText().toString());
        int open = Integer.parseInt(end.getText().toString());
        Root root = getData(num);
        if (root.correct_answer == (pozitsion + 1)){
            hashMap.put(num, true);
            showRight();
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.GREEN));
            progressBar.setVisibility(View.VISIBLE);
            layout.setBackground(getResources().getDrawable(R.drawable.button_green));
            LottieAnimationView lottie = findViewById(R.id.animationView);
            lottie.cancelAnimation();
            lottie.setSpeed(1.50f);
            lottie.playAnimation();
            lottie.setVisibility(View.VISIBLE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    lottie.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);

                    int newposs = num + 1;
                    if (newposs == open){
                        setEnd();

                    }else {
                        showQuestion((num + 1));
                    }
                }
            }, 3000);

        }else{
            hashMap.put(num, false);
            showRight();
            progressBar.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
            progressBar.setVisibility(View.VISIBLE);
            layout.setBackground(getResources().getDrawable(R.drawable.button_red));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);


                    int newposs = num + 1;
                    if (newposs == open){
                        setEnd();
                    }else {
                        showQuestion((num + 1));
                    }
                }
            }, 3000);

        }

    }

    private void showRight(){
        AtomicInteger wrong = new AtomicInteger();
        AtomicInteger right = new AtomicInteger();

        hashMap.forEach((k, v) -> {
            if (v){
                right.getAndIncrement();
            }else {
                wrong.getAndIncrement();
            }
        });
        raqami1.setText(String.valueOf(right.get()));
        raqami2.setText(String.valueOf(wrong.get()));
    }

    public void getYakunlash(View view) {
        setEnd();

    }

    private void setEnd(){
        AtomicInteger wrong = new AtomicInteger();
        AtomicInteger right = new AtomicInteger();

        hashMap.forEach((k, v) -> {
            if (v){
                right.getAndIncrement();
            }else {
                wrong.getAndIncrement();
            }
        });

        Intent intent = new Intent(this, ActivityEnd.class);
        intent.putExtra("right", right.get());
        intent.putExtra("wrong", wrong.get());
        startActivity(intent);
        this.finish();
        Toast.makeText(this, "bosilmoq", Toast.LENGTH_SHORT).show();
    }
}