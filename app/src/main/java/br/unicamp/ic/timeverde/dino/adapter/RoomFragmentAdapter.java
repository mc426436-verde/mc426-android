package br.unicamp.ic.timeverde.dino.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.unicamp.ic.timeverde.dino.R;

/**
 * Adapter para o RecyclerView do Fragment de Devices
 */
public class RoomFragmentAdapter extends RecyclerView.Adapter<RoomFragmentAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemRoom;
        public TextView mItemOnNumber;
        public TextView mItemOffNumber;

        public ViewHolder(View v) {
            super(v);
            mItemRoom = (TextView) v.findViewById(R.id.room_fragment_card_text);
            mItemOnNumber = (TextView) v.findViewById(R.id.room_fragment_turn_on_light_text);
            mItemOffNumber = (TextView) v.findViewById(R.id.room_fragment_turn_off_light_text);
        }
    }

    public RoomFragmentAdapter(ArrayList<String> mDataset) {
        //TODO recebe e seta o data set
        this.mDataset = mDataset;
    }

    @Override
    public RoomFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_room_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TODO Bind do view
       holder.mItemRoom.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


