package utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TaskListGenerator {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    private static SecureRandom RND = new SecureRandom();

    public static List<String> generateListOfRandomStrings(int listSize, int maxStringLength) {
        return IntStream.range(0, listSize)
                .mapToObj(i -> getRandomString(maxStringLength))
                .collect(Collectors.toList());
    }

    public static String getRandomString(int len) {
        return IntStream.range(0, len)
                .mapToObj(i -> String.valueOf(AB.charAt(RND.nextInt(AB.length()))))
                .collect(Collectors.joining());
    }
}
