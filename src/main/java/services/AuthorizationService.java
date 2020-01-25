package services;

import base.service.ServiceProvider;
import interfaces.Authorization;
import models.LoginResponseStatus;
import models.UserCredentials;
import retrofit2.Response;

import java.io.IOException;

public class AuthorizationService {

    private Authorization authorizationService;

    public AuthorizationService() {
        this.authorizationService = ServiceProvider.createService(Authorization.class);
    }

    public AuthorizationService(String username, String password) {
        this.authorizationService = ServiceProvider.createService(Authorization.class, username, password);
    }

    public Response<LoginResponseStatus> loginWithCredentials(UserCredentials credentials) throws IOException {
        return authorizationService.login(credentials).execute();
    }
}
