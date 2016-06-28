package br.unicamp.ic.timeverde.dino.presentation.fragment;


import android.content.DialogInterface;
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

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.OneoffTask;

import java.util.Calendar;
import java.util.List;

import br.unicamp.ic.timeverde.dino.R;
import br.unicamp.ic.timeverde.dino.adapter.MacroFragmentAdapter;
import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.client.job.MacroSchedulerService;
import br.unicamp.ic.timeverde.dino.model.Macro;
import br.unicamp.ic.timeverde.dino.presentation.activity.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MacroFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        MacroFragmentAdapter.Callback {

    private static final String TAG = MacroFragment.class.getSimpleName();

    @BindView(R.id.fragment_macro_recyclerview)
    protected RecyclerView mMacroRecyclerView;
    @BindView(R.id.fragment_macro_refresh_layout)
    protected SwipeRefreshLayout mRefreshLayout;
    private GcmNetworkManager mGcmNetworkManager;


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
        mGcmNetworkManager = GcmNetworkManager.getInstance(getActivity());
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

    @Override
    public boolean onMacroLongClick(final Macro macro) {
        // Show date picker dialog.
        CalendarDatePickerDialogFragment dialog = new CalendarDatePickerDialogFragment();
        final Calendar scheduledTime = Calendar.getInstance();
        dialog.setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {


            @Override
            public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
                scheduledTime.set(year, monthOfYear, dayOfMonth);

            }


        });
        dialog.setOnDismissListener(new CalendarDatePickerDialogFragment.OnDialogDismissListener() {
            @Override
            public void onDialogDismiss(DialogInterface dialoginterface) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                scheduledTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                scheduledTime.set(Calendar.MINUTE, minute);

                                Bundle bundle = new Bundle();
                                bundle.putLong("macroId", macro.getId());
                                final long scheduledTimeLong = (scheduledTime.getTimeInMillis() -
                                        Calendar.getInstance().getTimeInMillis
                                                ()) / 1000;

                                OneoffTask oneoffTask = new OneoffTask.Builder()
                                        .setService(MacroSchedulerService.class)
                                        .setTag(macro.getId().toString())
                                        .setRequiredNetwork(OneoffTask.NETWORK_STATE_ANY)
                                        .setExecutionWindow(scheduledTimeLong, 30 + scheduledTimeLong
                                        )
                                        .setExtras(bundle)
                                        .build();
                                mGcmNetworkManager.schedule(oneoffTask);
                            }
                        })
                        .setStartTime(10, 10)
                        .setDoneText("CRIAR")
                        .setCancelText("CANCELAR");
                rtpd.show(getFragmentManager(), "FRAG_TAG_TIME_PICKER");
            }
        });
        dialog.setDoneText("CONTINUAR");
        dialog.show(getFragmentManager(), "DATE_PICKER_TAG");
        return true;
    }

    private void requestMacroList() {
        Call<List<Macro>> callMacro = WSClient.getInstance().getMacroListByUser();
        callMacro.enqueue(new Callback<List<Macro>>() {

            @Override
            public void onResponse(final Call<List<Macro>> call, final Response<List<Macro>> response) {
                mRefreshLayout.setRefreshing(false);
                if (response != null && response.code() == 200) {
                    mAdapter.updateMacroList(response.body());
                    if (getActivity().getIntent().getLongExtra(MainActivity.EXTRA_TOGGLE_MACRO_ID, 0L) != 0L) {
                        Macro macro = new Macro();
                        macro.setId(getActivity().getIntent().getLongExtra(MainActivity.EXTRA_TOGGLE_MACRO_ID, 0L));
                        activateMacro(macro);
                    }
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
            public void onFailure(final Call<Macro> call, final Throwable t) {
            }
        });
    }
}
