package br.unicamp.ic.timeverde.dino.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.model.Action;
import br.unicamp.ic.timeverde.dino.model.Device;
import br.unicamp.ic.timeverde.dino.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter para o RecyclerView do Fragment de Devices
 */
public class NewMacroFragmentAdapter extends RecyclerView.Adapter<NewMacroFragmentAdapter.ViewHolder> {

    private List<Action> mActionList;
    private Activity mActivity;
    private List<User> allUsers;
    private Callback mCallback;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_device_new_macro_list_item)
        public TextView mItemHeader;
        @BindView(R.id.status_device_new_macro_list_item)
        public TextView mItemStatus;
        public View mRootView;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            mRootView = v;

        }
    }

    public NewMacroFragmentAdapter(Activity activity, Callback callback) {
        mActivity = activity;
        mActionList = new ArrayList<>();
        allUsers = new ArrayList<>();
        mCallback = callback;
    }

    public void updateActionList(List<Action> actionList) {
        mActionList.clear();
        mActionList.addAll(actionList);
        notifyDataSetChanged();
    }



    @Override
    public NewMacroFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_new_macro, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItemHeader.setText(mActionList.get(position).getDevice().getName());
        holder.mItemStatus.setText(mActionList.get(position).getStatus());
        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onActionClick(mActionList.get(position), position);
                mActionList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    public interface Callback {
        void onActionClick(Action action, int position);
    }


    @Override
    public int getItemCount() {
        return mActionList.size();
    }


}


