package br.unicamp.ic.timeverde.dino.presentation.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.NewMacroFragmentAdapter;
import br.unicamp.ic.timeverde.dino.adapter.RoomFragmentAdapter;
import br.unicamp.ic.timeverde.dino.model.Action;
import br.unicamp.ic.timeverde.dino.model.Device;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMacroFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewMacroFragment.class.getSimpleName();

    @BindView(R.id.fragment_macro_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.create_macro_button)
    Button mNewActionButton;


    @OnClick(R.id.fragment_new_macro_button)
    public void addAction(){
        Device device = new Device();
        device.setName("Device");
        mActionList.add(new Action("ON", device));
        mNewMacroFragmentAdapter.updateActionList(mActionList);
    }

    @OnClick(R.id.create_macro_button)
    public void onCreateMacro(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    }


    private RecyclerView.LayoutManager mLayoutManager;

    private NewMacroFragmentAdapter mNewMacroFragmentAdapter;

    private List<Action> mActionList;

    private Handler mUiHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUiHandler = new Handler(Looper.getMainLooper());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_new_macro, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mNewMacroFragmentAdapter = new NewMacroFragmentAdapter(getActivity(), new NewMacroFragmentAdapter.Callback() {
            @Override
            public void onActionClick(Action action, int position) {
                mActionList.remove(position);
            }
        });
        mRecyclerView.setAdapter(mNewMacroFragmentAdapter);


        mActionList = new ArrayList<>();

        return view;
    }

    @Override
    public void onRefresh() {

    }



}
