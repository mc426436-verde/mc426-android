package br.unicamp.ic.timeverde.dino.client.service;

import java.util.List;

import br.unicamp.ic.timeverde.dino.model.Macro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MacroService {

    @GET("/api/macros")
    Call<List<Macro>> macroListByUser(@Header("Authorization") String accessToken);

    @GET("/api/macros/run/{id}")
    Call<Macro> activateMacroById(@Header("Authorization") String accessToken,
                                  @Path("id") Long id);
}
