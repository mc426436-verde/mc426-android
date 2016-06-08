package br.unicamp.ic.timeverde.dino.presentation.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.unicamp.ic.timeverde.dino.DinoApplication;
import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.DeviceFragmentAdapter;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Device;
import br.unicamp.ic.timeverde.dino.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeviceFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = DeviceFragment.class.getSimpleName();

    public static final String ROOM_ID = "room-id";

    private String mRoomId;

    @BindView(R.id.device_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_device_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.LayoutManager mLayoutManager;

    private DeviceFragmentAdapter mDeviceFragmentAdapter;

    private List<Device> mDeviceList;

    private Handler mUiHandler;

    public DeviceFragment newInstance(Long roomId) {
        DeviceFragment fragment = new DeviceFragment();
        Bundle bundle = new Bundle();
        if (roomId != null) {
            bundle.putLong(ROOM_ID, roomId);
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mDeviceFragmentAdapter = new DeviceFragmentAdapter(new DeviceFragmentAdapter.Callback() {

            @Override
            public void onDeviceClickToggle(Device device, int position) {
                toggleDevice(device);
            }
        }, getActivity());
        mRecyclerView.setAdapter(mDeviceFragmentAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setRefreshing(true);

        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUiHandler = new Handler(Looper.getMainLooper());

        requestDevicesByUser();

        if (DinoApplication.getApplication().getAccount().getAdmin()) requestAllUsers();
    }

    @Override
    public void onRefresh() {
        requestDevicesByUser();
    }

    public void requestAllUsers() {
        Call<List<User>> call = WSClient.getInstance().getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "User list retrieved successfully!");
                    mDeviceFragmentAdapter.setAllUsers(response.body());
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }

    public void requestDevicesByUser() {
        Call<List<Device>> call = WSClient.getInstance().deviceListByUser();
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "Device list retrieved successfully!");
                    mDeviceList = response.body();
                    // Run list update on UI thread
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDeviceFragmentAdapter.updateDeviceList(mDeviceList);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {

            }
        });
    }

    public void toggleDevice(final Device device) {
        Call<Device> call = WSClient.getInstance().toggleDevice(device.getId());

        call.enqueue(new Callback<Device>() {
            @Override
            public void onResponse(Call<Device> call, Response<Device> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "Device toggle success!");
                    final Device updatedDevice = response.body();
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDeviceFragmentAdapter.updateSingleDevice(updatedDevice);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Device> call, Throwable t) {

            }
        });
    }
}
