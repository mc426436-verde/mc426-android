package br.unicamp.ic.timeverde.dino.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.model.Action;
import br.unicamp.ic.timeverde.dino.model.Macro;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MacroFragmentAdapter extends RecyclerView.Adapter<MacroFragmentAdapter.MacroViewHolder> {

    private ArrayList<Macro> mMacroList;
    private Context mContext;
    private Callback mCallback;

    public MacroFragmentAdapter(Context context, Callback callback) {
        mMacroList = new ArrayList<>();
        mContext = context;
        mCallback = callback;
    }

    @Override
    public MacroViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View rootView = LayoutInflater
                        .from(mContext)
                        .inflate(R.layout.list_item_macro_fragment, parent, false);
        return new MacroViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final MacroViewHolder holder, final int position) {
        final Macro macro = mMacroList.get(position);
        holder.macroName.setText(macro.getName());
        if (macro.getAction() != null && !macro.getAction().isEmpty()) {
            int lightOnQty = 0, lightOffQty = 0;
            for(Action action : macro.getAction()) {
                switch (action.getStatus()) {
                    case Action.STATUS_ON : {
                        lightOnQty++;
                        break;
                    }
                    case Action.STATUS_OFF : {
                        lightOffQty++;
                        break;
                    }
                }
            }
            holder.lightOn.setVisibility(View.VISIBLE);
            holder.lightOn.setText(String.valueOf(lightOnQty));
            holder.lightOff.setVisibility(View.VISIBLE);
            holder.lightOff.setText(String.valueOf(lightOffQty));
        } else {
            holder.lightOff.setVisibility(View.INVISIBLE);
            holder.lightOn.setVisibility(View.INVISIBLE);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                mCallback.onMacroClick(mMacroList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMacroList.size();
    }

    public void updateMacroList(List<Macro> macroList) {
        mMacroList.clear();
        mMacroList.addAll(macroList);
        notifyDataSetChanged();
    }

    public interface Callback {
        void onMacroClick(Macro macro);
    }

    public class MacroViewHolder extends RecyclerView.ViewHolder {

        protected View rootView;
        @BindView(R.id.macro_name)
        protected TextView macroName;
        @BindView(R.id.macro_light_off_qty)
        protected TextView lightOff;
        @BindView(R.id.macro_light_on_qty)
        protected TextView lightOn;

        MacroViewHolder(View v) {
            super(v);
            rootView = v;
            ButterKnife.bind(this, v);
        }
    }
}