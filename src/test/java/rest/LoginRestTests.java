package rest;

import models.SessionInfo;
import models.UserCredentials;
import org.testng.annotations.Test;
import retrofit2.Response;
import services.AuthorizationService;

import java.io.IOException;

public class LoginRestTests {

    @Test
    public void checkLoginWithValidCredentials() throws IOException {
        AuthorizationService authorizationService = new AuthorizationService();
        UserCredentials credentials = new UserCredentials("john_dow@some.domaine.com", "123456789");
        Response<SessionInfo> sessionInfoResponse = authorizationService.loginWithCredentials(credentials);
        System.out.println(sessionInfoResponse.headers());
    }
}
