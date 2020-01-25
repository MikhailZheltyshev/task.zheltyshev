package services;

import base.service.ServiceProvider;
import helpers.RestHelper;
import interfaces.Authorization;
import models.LoginResponseStatus;
import models.UserCredentials;
import retrofit2.Response;

import java.io.IOException;

public class AuthorizationService {

    private Authorization authorizationInterface;

    public AuthorizationService() {
        this.authorizationInterface = ServiceProvider.createService(Authorization.class);
    }

    public AuthorizationService(String username, String password) {
        this.authorizationInterface = ServiceProvider.createService(Authorization.class, username, password);
    }

    public Response<LoginResponseStatus> loginWithCredentials(UserCredentials credentials) throws IOException {
        return authorizationInterface.login(credentials).execute();
    }

    public String getAuthToken(UserCredentials credentials) throws IOException {
        Response<LoginResponseStatus> loginResponseStatus = authorizationInterface.login(credentials).execute();
        String setCookieString = loginResponseStatus.headers().get("Set-Cookie");
        return RestHelper.convertSetCookiesStringToMap(setCookieString).get("Authorization");
    }
}
