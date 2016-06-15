package br.unicamp.ic.timeverde.dino.presentation.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
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
public class MacroFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fragment_macro_recyclerview)
    protected RecyclerView mMacroRecyclerView;
    @BindView(R.id.fragment_macro_refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_macro, container, false);
        ButterKnife.bind(this, rootView);

        mRefreshLayout.setOnRefreshListener(this);

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

    private void requestMacroList() {
        Call<List<Macro>> callMacro = WSClient.getInstance().getMacroListByUser();
        callMacro.enqueue(new Callback<List<Macro>>() {

            @Override
            public void onResponse(final Call<List<Macro>> call, final Response<List<Macro>> response) {
                mRefreshLayout.setRefreshing(false);
                if (response != null && response.code() == 200) {
                    // TODO populate adapter
                }
            }

            @Override
            public void onFailure(final Call<List<Macro>> call, final Throwable t) {
                mRefreshLayout.setRefreshing(false);
            }
        });
    }
}
