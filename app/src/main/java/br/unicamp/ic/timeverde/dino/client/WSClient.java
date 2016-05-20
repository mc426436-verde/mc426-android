package br.unicamp.ic.timeverde.dino.client;


import br.unicamp.ic.timeverde.dino.client.service.UserService;
import br.unicamp.ic.timeverde.dino.model.Token;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WSClient {

    private static WSClient sInstance;

    private Retrofit mRetrofit;

    private ResponseListener mCallback;

    private WSClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(ApiConfiguration.BASE_URL)
                .build();
    }

    public void setResponseCallback(ResponseListener callback) {
        mCallback = callback;
    }

    public static WSClient getInstance() {
        if (sInstance == null) {
            sInstance = new WSClient();
        }
        return sInstance;
    }

    public String autenthicate(String email, String password) {
        return null;
    }
}
