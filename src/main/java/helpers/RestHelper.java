package helpers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public class RestHelper {

    public static Map<String, String> convertSetCookiesStringToMap(String cookiesString) {
        if (cookiesString != null) {
            String[] cookies = cookiesString.split("; ");
            return Arrays.stream(cookies)
                    .map(cookie -> cookie.split("="))
                    .collect(toMap(cookieKeyValue -> cookieKeyValue[0],
                            cookieKeyValue -> cookieKeyValue[1],
                            (a, b) -> b));
        } else {
            return new HashMap<>();
        }
    }

    public static String convertCookiesMapToString(Map<String, String> cookiesAsMap) {
        return cookiesAsMap.entrySet().stream()
                .map(cookieEntry -> String.format("%s=%s", cookieEntry.getKey(), cookieEntry.getValue()))
                .collect(joining("; "));
    }
}
