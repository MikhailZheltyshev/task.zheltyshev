package utils;

import com.google.gson.Gson;
import okhttp3.ResponseBody;

import java.io.IOException;

public class ApiResponseUtils {

    private static final Gson gson = new Gson();

    public static <T> T convertResponseBodyToType(ResponseBody responseBody, Class<T> targetClass) {
        try {
            return gson.fromJson(responseBody.string(), targetClass);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to get response body as string", e);
        }
    }
}
