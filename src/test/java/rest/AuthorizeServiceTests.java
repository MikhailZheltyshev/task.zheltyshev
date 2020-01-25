package rest;

import dataProviders.DataProviders;
import models.LoginResponseStatus;
import models.ResponseStatus;
import models.UserCredentials;
import okhttp3.Headers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import services.AuthorizationService;

import java.util.Map;

import static constants.HttpStatusCodes.OK;
import static constants.HttpStatusCodes.UNAUTHORIZED;
import static constants.LoginResponseBodyStatuses.FAILED_STATUS;
import static constants.LoginResponseBodyStatuses.SUCCESS_STATUS;
import static helpers.RestHelper.convertSetCookiesStringToMap;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthorizeServiceTests {

    private AuthorizationService authorizationService;

    @BeforeMethod(description = "Setting up client for authorize service")
    public void setUp() {
        this.authorizationService = new AuthorizationService();
    }

    @Test(description = "Check that login request with valid credentials to Authorize service returns SUCCESS status in response body",
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginRequestWithValidCredentialsReturnsSuccessfulStatusInBody(String username, String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        ResponseStatus loginResponseStatus = authorizationService.loginWithCredentials(credentials).body();
        assertThat(loginResponseStatus.getStatus())
                .as("Login response status should be SUCCESS for Login request with valid credentials")
                .isEqualTo(SUCCESS_STATUS);
    }

    @Test(description = "Check that login request with valid credentials to Authorize service returns 200 OK Http status in response",
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkOkHttpStatusReturnedForLoginRequestWithValidCredentials(String username, String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        int loginResponseHttpCode = authorizationService.loginWithCredentials(credentials).code();
        assertThat(loginResponseHttpCode)
                .as("200 OK Http status should be returned on attempt to authorize with valid credentials")
                .isEqualTo(OK);
    }

    @Test(description = "Check that session id is returned in response body for request with valid credentials",
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkSessionIdIsReturnedInResponseBodyForLoginRequestWithValidCredentials(String username, String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        LoginResponseStatus loginResponseStatus = authorizationService.loginWithCredentials(credentials).body();
        assertThat(loginResponseStatus.getSessionId())
                .as("Login response session id should be returned for Login request with valid credentials")
                .isNotNull();
    }

    @Test(description = "Check that auth token is returned in response body for request with valid credentials",
            dataProvider = "valid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkAuthorizationTokenReturnedInResponseHeaderForLoginRequestWithValidCredentials(String username,
                                                                                                   String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        Headers loginResponseHeaders = authorizationService.loginWithCredentials(credentials).headers();
        Map<String, String> actualSetCookiesAsMap = convertSetCookiesStringToMap(loginResponseHeaders.get("Set-Cookie"));
        assertThat(actualSetCookiesAsMap.get("Authorization"))
                .as("Authorization token should be returned in Login response header for Login request with valid credentials")
                .isNotBlank();
    }

    @Test(description = "Check that login request with invalid credentials to Authorize service returns FAILED status in response body",
            dataProvider = "invalid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkLoginResponseHasFailedStatusInBodyForLoginRequestWithInvalidCredentials(String username,
                                                                                             String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        ResponseStatus loginResponseStatus = authorizationService.loginWithCredentials(credentials).body();
        assertThat(loginResponseStatus.getStatus())
                .as("Login response body status should be FAILED for Login request with invalid credentials")
                .isEqualTo(FAILED_STATUS);
    }

    @Test(description = "Check that login request with invalid credentials to Authorize service returns 401 Unauthorized Http status in response",
            dataProvider = "invalid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkUnauthorizedHttpStatusCodeIsReturnedForLoginRequestWithInvalidCredentials(String username,
                                                                                               String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        int loginResponseHttpCode = authorizationService.loginWithCredentials(credentials).code();
        assertThat(loginResponseHttpCode)
                .as("401 UNAUTHORIZED Http status should be returned on attempt to authorize with invalid credentials")
                .isEqualTo(UNAUTHORIZED);
    }

    @Test(description = "Check that auth token is not returned in response body for request with invalid credentials",
            dataProvider = "invalid-creds-provider",
            dataProviderClass = DataProviders.class)
    public void checkAuthorizationTokenIsNotReturnedInResponseHeaderForLoginRequestWithInvalidCredentials(String username,
                                                                                                          String password) {
        UserCredentials credentials = new UserCredentials(username, password);
        Headers loginResponseHeaders = authorizationService.loginWithCredentials(credentials).headers();
        Map<String, String> actualSetCookiesAsMap = convertSetCookiesStringToMap(loginResponseHeaders.get("Set-Cookie"));
        assertThat(actualSetCookiesAsMap.get("Authorization"))
                .as("Authorization token should not be returned in Login response header for Login request with invalid credentials")
                .isNull();
    }
}
