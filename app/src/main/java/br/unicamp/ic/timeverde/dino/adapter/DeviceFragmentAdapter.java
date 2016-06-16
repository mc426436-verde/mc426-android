package br.unicamp.ic.timeverde.dino.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import br.unicamp.ic.timeverde.dino.DinoApplication;
import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Device;
import br.unicamp.ic.timeverde.dino.model.User;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Adapter para o RecyclerView do Fragment de Devices
 */
public class DeviceFragmentAdapter extends RecyclerView.Adapter<DeviceFragmentAdapter.ViewHolder> {

    private List<Device> mDeviceList;
    private Callback mCallback;
    private Activity mActivity;
    private List<User> allUsers;

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

    public DeviceFragmentAdapter(Callback callback, Activity activity) {
        mActivity = activity;
        mDeviceList = new ArrayList<>();
        mCallback = callback;
        allUsers = new ArrayList<>();
    }

    public void updateDeviceList(List<Device> deviceList) {
        mDeviceList.clear();
        mDeviceList.addAll(deviceList);
        notifyDataSetChanged();
    }

    public void setAllUsers(List<User> allUsers) {
        this.allUsers = allUsers;
    }


    public void updateSingleDevice(Device device) {
        int position = -1;
        do {
            if (device.getId() == mDeviceList.get(++position).getId()) {
                mDeviceList.set(position, device);
                notifyItemChanged(position);
                notifyDataSetChanged();
                position = -1;
            }
        } while (position != -1);
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
        if (device.getUsers() == null) device.setUsers(new HashSet<User>());
        if (position == 0) {
            holder.mItemHeader.setText("LÂMPADAS");
            holder.mItemHeader.setVisibility(View.VISIBLE);
        }
        holder.mItemName.setText(device.getName());
        holder.mItemStatusIcon.setSelected("ON".equals(device.getStatus()));
        holder.mItemRoom.setText(device.getRoom().getName());
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (device.getUsers().contains(DinoApplication.getApplication().getAccount())) {
                    mCallback.onDeviceClickToggle(device, holder.getAdapterPosition());
                } else {
                    Toast.makeText(mActivity, "Você não tem permissão para ativar o device.", Toast.LENGTH_SHORT).show();
                }

            }
        });
        if (DinoApplication.getApplication().getAccount().getAdmin()) {
            holder.rootView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    buildShareDeviceDialog(device);
                    return true;
                }
            });
        }
    }

    private void buildShareDeviceDialog(final Device device) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.MultiChoiceShare);
        final CharSequence[] users = new CharSequence[allUsers.size()];
        boolean[] checked = new boolean[allUsers.size()];
        int counter = 0;
        for (User user : allUsers) {
            users[counter] = user.getFirstName() + " " + user.getLastName();
            checked[counter++] = device.getUsers().contains(user);
        }
        builder.setMultiChoiceItems(users, checked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (device.getUsers().contains(allUsers.get(which))) {
                    device.getUsers().remove(allUsers.get(which));
                } else {
                    device.getUsers().add(allUsers.get(which));
                }

            }
        });
        builder.setPositiveButton("COMPARTILHAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateDevice(device);
            }
        });
        builder.setTitle("Lista de Usuários");
        builder.show();

    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    private void updateDevice(final Device device) {
        Call<Device> call = WSClient.getInstance().updateDevice(device);
        call.enqueue(new retrofit2.Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response != null && response.code() == 200) {
                    device.setUsers(response.body().getUsers());
                } else {
                    Toast.makeText(mActivity, "Falha ao compartilhar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {
                Toast.makeText(mActivity, "Falha ao compartilhar", Toast.LENGTH_SHORT).show();
            }
        });

    }


}


