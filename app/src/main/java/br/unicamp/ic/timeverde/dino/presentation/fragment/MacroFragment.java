package br.unicamp.ic.timeverde.dino.presentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.crypto.Mac;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.MacroFragmentAdapter;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Macro;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MacroFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
                                                       MacroFragmentAdapter.Callback{

    private static final String TAG = MacroFragment.class.getSimpleName();

    @BindView(R.id.fragment_macro_recyclerview)
    protected RecyclerView mMacroRecyclerView;
    @BindView(R.id.fragment_macro_refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    private MacroFragmentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_macro, container, false);
        ButterKnife.bind(this, rootView);

        mRefreshLayout.setOnRefreshListener(this);
        mAdapter = new MacroFragmentAdapter(getContext(), this);

        mMacroRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mMacroRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMacroList();
    }

    @Override
    public void onRefresh() {
        requestMacroList();
    }

    @Override
    public void onMacroClick(final Macro macro) {
        activateMacro(macro);
    }

    private void requestMacroList() {
        Call<List<Macro>> callMacro = WSClient.getInstance().getMacroListByUser();
        callMacro.enqueue(new Callback<List<Macro>>() {

            @Override
            public void onResponse(final Call<List<Macro>> call, final Response<List<Macro>> response) {
                mRefreshLayout.setRefreshing(false);
                if (response != null && response.code() == 200) {
                    mAdapter.updateMacroList(response.body());
                }
            }

            @Override
            public void onFailure(final Call<List<Macro>> call, final Throwable t) {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void activateMacro(final Macro macro) {
        Call<Macro> callMacro = WSClient.getInstance().activateMacroById(macro.getId());
        callMacro.enqueue(new Callback<Macro>() {

            @Override
            public void onResponse(final Call<Macro> call, final Response<Macro> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "Macro" + macro.getId() + "activated successfully!");
                } else {
                    Log.d(TAG, "Failure activating macro");
                }
            }

            @Override
            public void onFailure(final Call<Macro> call, final Throwable t) {}
        });
    }
}
