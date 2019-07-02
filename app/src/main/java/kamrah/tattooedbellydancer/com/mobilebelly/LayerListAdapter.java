package kamrah.tattooedbellydancer.com.mobilebelly;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * LayerList Adapter class creates the Recycler View for saving layers
 */
public class LayerListAdapter extends RecyclerView.Adapter<LayerListAdapter.LayerViewHolder> {

    //Member variables
    private List<Layer> mLayerList;
    private final LayoutInflater mInflater;

    //Inner class to hold layer view, the view of each individual saved item
    class LayerViewHolder extends RecyclerView.ViewHolder {

        //Member variables
        public final TextView layerItemView;
        final LayerListAdapter mAdapter;

        //Constructor
        public LayerViewHolder(View itemView, LayerListAdapter adapter) {
            super(itemView);
            layerItemView = itemView.findViewById(R.id.layer);
            this.mAdapter = adapter;

        }
    }

    //Constructor for LayerListAdapter
    public LayerListAdapter(Context context, List<Layer> layerList) {
        mInflater = LayoutInflater.from(context);
        this.mLayerList = layerList;
    }

    //OnCreateViewHolder to inflate item layout
    @Override
    public LayerListAdapter.LayerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.layerlist_item, parent, false);
        return new LayerViewHolder(mItemView, this);
    }

    //OnBindViewHolder to bind view holder to RecyclerView
    @Override
    public void onBindViewHolder(LayerListAdapter.LayerViewHolder holder, int position) {
        if (mLayerList != null) {
            Layer current = mLayerList.get(position);
            holder.layerItemView.setText(current.getLayer());
        } else {
            holder.layerItemView.setText("No Layer");
        }
    }

    //Get the number of items in the view
    @Override
    public int getItemCount() {
        if (mLayerList != null)
            return mLayerList.size();
        else return 0;
    }

    //Populate database list of layers
    void setLayers(List<Layer> layers) {
        mLayerList = layers;
        notifyDataSetChanged();
    }

    //Get position of layer selected
    public Layer getLayerAtPosition (int position) {
        return mLayerList.get(position);
    }
}
