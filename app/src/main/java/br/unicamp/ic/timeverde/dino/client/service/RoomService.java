package br.unicamp.ic.timeverde.dino.client.service;

import java.util.List;

import br.unicamp.ic.timeverde.dino.model.Room;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface RoomService {

    @GET("/api/rooms")
    Call<List<Room>> roomListByUser(@Header("Authorization") String accessToken);

}
