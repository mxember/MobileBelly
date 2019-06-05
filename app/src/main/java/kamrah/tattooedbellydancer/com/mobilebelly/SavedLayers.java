package kamrah.tattooedbellydancer.com.mobilebelly;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

public class SavedLayers extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private LayerListAdapter mAdapter;

    private final LinkedList<String> mLayerList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_layers);

        for (int i = 0; i < 20; i++) {
            mLayerList.addLast("Layer" + i);
        }


        mRecyclerView = findViewById(R.id.recyclerview);

        mAdapter = new LayerListAdapter(this, mLayerList);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
