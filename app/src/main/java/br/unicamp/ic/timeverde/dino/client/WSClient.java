package br.unicamp.ic.timeverde.dino.client;


import java.util.List;

import br.unicamp.ic.timeverde.dino.DinoApplication;
import br.unicamp.ic.timeverde.dino.client.service.DeviceService;
import br.unicamp.ic.timeverde.dino.client.service.MacroService;
import br.unicamp.ic.timeverde.dino.client.service.RoomService;
import br.unicamp.ic.timeverde.dino.client.service.UserService;
import br.unicamp.ic.timeverde.dino.model.Device;
import br.unicamp.ic.timeverde.dino.model.Macro;
import br.unicamp.ic.timeverde.dino.model.Room;
import br.unicamp.ic.timeverde.dino.model.Token;
import br.unicamp.ic.timeverde.dino.model.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;

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

    private String getAuthorizatonToken() {
        User userSession = DinoApplication.getApplication().getAccount();
        StringBuilder authorization = new StringBuilder();
        authorization.append(userSession.getToken().getTokenType());
        authorization.append(" ");
        authorization.append(userSession.getToken().getAccessToken());
        return authorization.toString();
    }

    public Call<Token> autenthicate(String email, String password) {
        UserService userService = mRetrofit.create(UserService.class);
        return userService.authenticate(ApiConfiguration.Credential.CLIENT_ID,
                ApiConfiguration.Credential.CLIENT_SECRET,
                ApiConfiguration.Credential.GRANT_TYPE,
                ApiConfiguration.Credential.SCOPE,
                email, password);
    }

    public Call<User> authorizeUser(String accessToken) {
        UserService userService = mRetrofit.create(UserService.class);
        return userService.authorizeUser(accessToken);
    }

    public Call<List<Device>> deviceListByUser() {
        DeviceService deviceService = mRetrofit.create(DeviceService.class);
        return deviceService.deviceListByUser(getAuthorizatonToken());
    }

    public Call<List<Room>> deviceRoomByUser() {
        User userSession = DinoApplication.getApplication().getAccount();
        StringBuilder authorization = new StringBuilder();
        authorization.append(userSession.getToken().getTokenType());
        authorization.append(" ");
        authorization.append(userSession.getToken().getAccessToken());
        RoomService roomService = mRetrofit.create(RoomService.class);
        return roomService.roomListByUser(authorization.toString());
    }

    public Call<Device> toggleDevice(Long id) {
        DeviceService deviceService = mRetrofit.create(DeviceService.class);
        return deviceService.toggleDevice(id);
    }

    public Call<List<User>> getAllUsers() {
        UserService userService = mRetrofit.create(UserService.class);
        return userService.getUsers(getAuthorizatonToken());
    }

    public Call<List<Macro>> getMacroListByUser() {
        MacroService macroService = mRetrofit.create(MacroService.class);
        return macroService.macroListByUser(getAuthorizatonToken());
    }
    
    public Call<Macro> activateMacroById(Long id) {
        MacroService macroService = mRetrofit.create(MacroService.class);
        return macroService.activateMacroById(getAuthorizatonToken(), id);
    }

    public Call<Device> updateDevice(Device device) {
        DeviceService deviceService = mRetrofit.create(DeviceService.class);
        return deviceService.updateDevice(getAuthorizatonToken(), device);
    }
}
