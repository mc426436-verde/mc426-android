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
public class DeviceFragmentAdapter extends RecyclerView.Adapter<DeviceFragmentAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemHeader;
        public TextView mItemName;
        public TextView mItemRoom;
        public ImageView mItemStatusIcon;
        public LinearLayout mItemDivider;


        public ViewHolder(View v) {
            super(v);
            mItemHeader = (TextView) v.findViewById(R.id.header_device_list_item);
            mItemName = (TextView) v.findViewById(R.id.name_device_list_item);
            mItemRoom = (TextView) v.findViewById(R.id.room_device_list_item);
            mItemStatusIcon = (ImageView) v.findViewById(R.id.icon_device_list_item);
            mItemDivider = (LinearLayout) v.findViewById(R.id.divider_device_list_item);
        }
    }

    public DeviceFragmentAdapter(ArrayList<String> mDataset) {
        //TODO recebe e seta o data set
        this.mDataset = mDataset;
    }

    @Override
    public DeviceFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_device_fragment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == 0){
            holder.mItemHeader.setVisibility(View.VISIBLE);
        }
       holder.mItemName.setText(mDataset.get(position));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


