package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class ListsUtils {

    public static <T> List<T> joinLists(List<T>... lists) {
        final List<T> joinedList = new ArrayList<>();
        Stream.of(lists).forEach(joinedList::addAll);
        return joinedList;
    }

    public static <T> T getRandomElementFromList(List<T> list) {
        final int listSize = list.size();
        if (listSize > 0) {
            return list.get(new Random().nextInt(list.size()));
        } else {
            throw new IllegalStateException("Unable to get random element from empty list");
        }
    }
}
