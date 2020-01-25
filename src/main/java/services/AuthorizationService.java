package services;

import base.service.ServiceProvider;
import helpers.RestHelper;
import interfaces.Authorization;
import io.qameta.allure.Step;
import models.LoginResponseStatus;
import models.UserCredentials;
import retrofit2.Response;

import java.io.IOException;

public class AuthorizationService {

    private Authorization authorizationInterface;

    public AuthorizationService() {
        this.authorizationInterface = ServiceProvider.createService(Authorization.class);
    }

    @Step("Perform login request to \"Authorize\" service with \"{0}\" username and \"{1}\" password")
    public Response<LoginResponseStatus> loginWithCredentials(UserCredentials credentials) {
        try {
            return authorizationInterface.login(credentials).execute();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to perform login request", e);
        }
    }

    public String getAuthToken(UserCredentials credentials) {
        try {
            Response<LoginResponseStatus> loginResponseStatus = authorizationInterface.login(credentials).execute();
            String setCookieString = loginResponseStatus.headers().get("Set-Cookie");
            return RestHelper.convertSetCookiesStringToMap(setCookieString).get("Authorization");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get auth token with credentials", e);
        }
    }
}
