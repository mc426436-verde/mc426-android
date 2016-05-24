package br.unicamp.ic.timeverde.dino.presentation.fragment;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.DeviceFragmentAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment {

    @BindView(R.id.device_recycler_view)
    RecyclerView mRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private DeviceFragmentAdapter mDeviceFragmentAdapter;

    public DeviceFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDeviceFragmentAdapter = new DeviceFragmentAdapter(new ArrayList<>(Arrays.asList("Lâmpada","Outra Lâmpada")));
        mRecyclerView.setAdapter(mDeviceFragmentAdapter);


        return view;
    }

}
