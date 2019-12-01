package akashsarkar188.expandabletextview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewMain;
    Adapter adapter;
    ArrayList<Doc> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapter = new Adapter(MainActivity.this, list);

        // INITIALIZE RETROFIT
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<Model> getData = api.getData();

        // START API CALL
        getData.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {

                Model a = response.body();
                akashsarkar188.expandabletextview.Response  b = a.getResponse();
                // ADDED ALL DATA IN LIST FOR RECYCLER VIEW
                list.addAll(b.getDocs());
                recyclerViewMain.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e("Error", "onFailure: " + t );
            }
        });

    }
}
