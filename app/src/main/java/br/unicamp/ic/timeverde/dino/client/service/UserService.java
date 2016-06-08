package br.unicamp.ic.timeverde.dino.client.service;


import java.util.List;

import br.unicamp.ic.timeverde.dino.model.Token;
import br.unicamp.ic.timeverde.dino.model.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserService {

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<Token> authenticate(@Field("client_id")String clientId,
                             @Field("client_secret")String clientSecret,
                             @Field("grant_type")String grantType,
                             @Field("scope")String scope,
                             @Field("username")String username,
                             @Field("password")String password);

    @GET("/api/account")
    Call<User> authorizeUser(@Header("Authorization")String accessToken);

    @GET("/api/users")
    Call<List<User>> getUsers(@Header("Authorization")String accessToken);
}
