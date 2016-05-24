package br.unicamp.ic.timeverde.dino.client.service;


import br.unicamp.ic.timeverde.dino.model.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {

    @POST("/oauth/token")
    Call<Token> authenticate(String username, String password);
}
