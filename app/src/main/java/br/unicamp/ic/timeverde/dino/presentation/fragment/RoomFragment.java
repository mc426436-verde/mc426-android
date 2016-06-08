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

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.RoomFragmentAdapter;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Device;
import br.unicamp.ic.timeverde.dino.model.Room;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = RoomFragment.class.getSimpleName();

    @BindView(R.id.room_recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_room_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private RecyclerView.LayoutManager mLayoutManager;

    private RoomFragmentAdapter mRoomFragmentAdapter;

    private List<Room> mRoomList;

    private Handler mUiHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUiHandler = new Handler(Looper.getMainLooper());

        requestByUserRoom();
    }

    public RoomFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        final View view = inflater.inflate(R.layout.fragment_room, container, false);
        ButterKnife.bind(this, view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRoomFragmentAdapter = new RoomFragmentAdapter();
        mRecyclerView.setAdapter(mRoomFragmentAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mUiHandler.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        return view;
    }

    @Override
    public void onRefresh() {
        requestByUserRoom();
    }

    public void requestByUserRoom() {
        Call<List<Room>> call = WSClient.getInstance().deviceRoomByUser();
        call.enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "Device list retrieved successfully!");
                    mRoomList = response.body();
                    // Run list update on UI thread
                    mUiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mRoomFragmentAdapter.updateRoomList(mRoomList);
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {

            }
        });
    }
}
