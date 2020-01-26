package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class CookiesUtils {

    public static Map<String, String> convertSetCookiesStringToMap(String cookiesString) {
        if (cookiesString != null) {
            final String[] cookies = cookiesString.split("; ");
            return Arrays.stream(cookies)
                    .map(cookie -> cookie.split("="))
                    .collect(toMap(cookieKeyValue -> cookieKeyValue[0],
                            cookieKeyValue -> cookieKeyValue[1],
                            (a, b) -> b));
        } else {
            return new HashMap<>();
        }
    }
}
