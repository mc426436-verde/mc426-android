package br.unicamp.ic.timeverde.dino.client.service;


import java.util.List;

import br.unicamp.ic.timeverde.dino.model.Device;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface DeviceService {

    @GET("/api/devices")
    Call<List<Device>> deviceListByUser(@Header("Authorization") String accessToken);

    @GET("/api/device/toggle/{id}")
    Call<Device> toggleDevice(@Path("id") Long id);

}
