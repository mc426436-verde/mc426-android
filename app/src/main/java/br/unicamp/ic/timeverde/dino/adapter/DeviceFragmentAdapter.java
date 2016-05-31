package br.unicamp.ic.timeverde.dino.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.model.Device;

/**
 * Adapter para o RecyclerView do Fragment de Devices
 */
public class DeviceFragmentAdapter extends RecyclerView.Adapter<DeviceFragmentAdapter.ViewHolder> {

    private List<Device> mDeviceList;
    private Callback mCallback;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View rootView;
        public TextView mItemHeader;
        public TextView mItemName;
        public TextView mItemRoom;
        public ImageView mItemStatusIcon;
        public LinearLayout mItemDivider;


        public ViewHolder(View v) {
            super(v);
            rootView = v;
            mItemHeader = (TextView) v.findViewById(R.id.header_device_list_item);
            mItemName = (TextView) v.findViewById(R.id.name_device_list_item);
            mItemRoom = (TextView) v.findViewById(R.id.room_device_list_item);
            mItemStatusIcon = (ImageView) v.findViewById(R.id.icon_device_list_item);
            mItemDivider = (LinearLayout) v.findViewById(R.id.divider_device_list_item);
        }
    }

    public interface Callback {
        void onDeviceClickToggle(Device device, int position);
    }

    public DeviceFragmentAdapter(Callback callback) {
        mDeviceList = new ArrayList<>();
        mCallback = callback;
    }

    public void updateDeviceList(List<Device> deviceList) {
        mDeviceList.clear();
        mDeviceList.addAll(deviceList);
        notifyDataSetChanged();
    }

    public void updateSingleDevice(Device device) {
        int position = 0;
        while (position != -1) {
            if (device.getId() == mDeviceList.get(position).getId()) mDeviceList.set(position, device);
            notifyItemChanged(position);
            position = -1;
        }
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Device device = mDeviceList.get(position);
        if (position == 0){
            holder.mItemHeader.setVisibility(View.VISIBLE);
        }
        holder.mItemName.setText(device.getDeviceName());
        holder.mItemStatusIcon.setSelected("ON".equals(device.getStatus()));
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onDeviceClickToggle(device, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }
}


