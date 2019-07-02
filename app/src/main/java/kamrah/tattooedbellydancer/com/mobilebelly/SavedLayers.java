package kamrah.tattooedbellydancer.com.mobilebelly;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

//SavedLayers class to handle the Recycler View of saved layers
public class SavedLayers extends AppCompatActivity {

    //Member variables
    private RecyclerView mRecyclerView;
    private LayerListAdapter mAdapter;
    private LayerViewModel mLayerViewModel;

    public static final String EXTRA_REPLY = "kamrah.tattooedbellydancer.com.mobilebelly.REPLY";

    //List of layers
    private List<Layer> mLayerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_layers);

        //Create Recycler view and add adapter to handle data
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new LayerListAdapter(this, mLayerList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Add View Model to Recycler View to populate from database
        mLayerViewModel = ViewModelProviders.of(this).get(LayerViewModel.class);

        //Add layer and observer for when data list changes
        mLayerViewModel.getAllLayers().observe(this, new Observer<List<Layer>>() {
            @Override
            public void onChanged(@Nullable List<Layer> layers) {
                mAdapter.setLayers(layers);
            }
        });


        //Get bundle of saved layer from MainActivity
        Bundle bundle = getIntent().getExtras();

        //Make sure the bundle isn't empty, if it isn't, add layer to ViewModel
        if (bundle != null) {

            //Retrieve new layer from bundle
            Layer newLayer = new Layer(bundle.getString("Layer"));

            //Add layer to ViewModel
            mLayerViewModel.insert(newLayer);

        }


        //ItemTouchHelper used for handling deletions and movement of layer item
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            //Swipe to delete layer
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Layer myLayer = mAdapter.getLayerAtPosition(position);
                Toast.makeText(SavedLayers.this, "Deleting " +
                        myLayer.getLayer(), Toast.LENGTH_SHORT).show();

                mLayerViewModel.deleteLayer(myLayer);

            }
        });

        helper.attachToRecyclerView(mRecyclerView);
    }
}
