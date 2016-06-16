package br.unicamp.ic.timeverde.dino.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.model.Action;
import br.unicamp.ic.timeverde.dino.model.Room;
import br.unicamp.ic.timeverde.dino.presentation.activity.DeviceRoomActivity;

/**
 * Adapter para o RecyclerView do Fragment de Devices
 */
public class RoomFragmentAdapter extends RecyclerView.Adapter<RoomFragmentAdapter.ViewHolder> {
    private final Context mContext;
    private List<Room> mRoomList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mItemRoom;
        public TextView mItemOnNumber;
        public TextView mItemOffNumber;
        public View mRootView;

        public ViewHolder(View v) {
            super(v);
            mItemRoom = (TextView) v.findViewById(R.id.room_fragment_card_text);
            mItemOnNumber = (TextView) v.findViewById(R.id.room_fragment_turn_on_light_text);
            mItemOffNumber = (TextView) v.findViewById(R.id.room_fragment_turn_off_light_text);
            mRootView = v.findViewById(R.id.room_root_view);
        }
    }

    public RoomFragmentAdapter(Context context) {
        this.mContext = context;
        this.mRoomList = new ArrayList<>();
    }

    public void updateRoomList(List<Room> roomList) {
        mRoomList.clear();
        mRoomList.addAll(roomList);
        notifyDataSetChanged();
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Room room = mRoomList.get(position);
        holder.mItemRoom.setText(room.getName());
        holder.mRootView.setBackground(mContext.getResources().getDrawable(room.getType()
                .getRoomImage()));
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(v.getContext(), DeviceRoomActivity.class);
                intent.putExtra("mRoomId", mRoomList.get(position).getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRoomList.size();
    }
}


