package air.texnodev.lesson6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import air.texnodev.lesson6.Adapter.Adapter;
import air.texnodev.lesson6.Models.Root;

public class ListActivity extends AppCompatActivity implements OnItemsClick{

    ArrayList<String> aModels = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Type type = new TypeToken<ArrayList<Root>>(){}.getType();
        Gson gson = new Gson();
        List<Root> root = gson.fromJson(getRaw(), type);
        int form = Math.round((root.size() / 20));
        for (int i = 0; i < form; i++) {
            aModels.add(new String("Bilet "+ (i+1)));
        }

        recyclerView = findViewById(R.id.biletlar);
        Adapter adapter = new Adapter(this, aModels, this);
        recyclerView.setAdapter(adapter);

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
    public void onClick(View view, int pozitsion) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("page", pozitsion);
        startActivity(intent);
    }
}