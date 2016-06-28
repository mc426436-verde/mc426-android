package br.unicamp.ic.timeverde.dino.client.job;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import br.unicamp.ic.timeverde.dino.client.WSClient;
import br.unicamp.ic.timeverde.dino.model.Macro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MacroSchedulerService extends GcmTaskService {
    private final String TAG = MacroSchedulerService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onRunTask(TaskParams taskParams) {
        final Long macroId = taskParams.getExtras().getLong("macroId");
        Call<Macro> callMacro = WSClient.getInstance().activateMacroById(macroId);
        callMacro.enqueue(new Callback<Macro>() {

            @Override
            public void onResponse(final Call<Macro> call, final Response<Macro> response) {
                if (response != null && response.code() == 200) {
                    Log.d(TAG, "Macro" + response.body().getId() + "activated successfully!");
                } else {
                    Log.d(TAG, "Failure activating macro");
                }
            }

            @Override
            public void onFailure(final Call<Macro> call, final Throwable t) {
            }
        });
        return GcmNetworkManager.RESULT_SUCCESS;
    }


}
