package br.unicamp.ic.timeverde.dino.client;


import br.unicamp.ic.timeverde.dino.client.service.UserService;
import br.unicamp.ic.timeverde.dino.model.Token;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WSClient {

    private static WSClient sInstance;

    private Retrofit mRetrofit;

    private WSClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfiguration.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static WSClient getInstance() {
        if (sInstance == null) {
            sInstance = new WSClient();
        }
        return sInstance;
    }

    public Call<Token> autenthicate(String email, String password) {
        UserService userService = mRetrofit.create(UserService.class);
        return userService.authenticate(email, password);
    }
}
