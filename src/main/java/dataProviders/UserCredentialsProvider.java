package dataProviders;

import org.testng.annotations.DataProvider;

import static utils.StringsGenerator.EMPTY;

public class UserCredentialsProvider {

    @DataProvider(name = "valid-creds-provider")
    public Object[][] provideValidLoginCredentials() {
        return new Object[][]{
                {"john_dow@some.domaine.com", "123456789"},
                {"simon_dow@some.domaine.com", "123456789"}
        };
    }

    @DataProvider(name = "invalid-creds-provider")
    public Object[][] provideInvalidLoginCredentials() {
        return new Object[][]{
                {EMPTY, EMPTY},
                {EMPTY, "123456789"},
                {"john_dow@some.domaine.com", EMPTY},
                {"bla", "bla"}
        };
    }
}
