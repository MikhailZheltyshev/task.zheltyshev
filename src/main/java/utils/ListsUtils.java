package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListsUtils {

    public static <T> List<T> joinLists(List<T> ...lists) {
        List<T> joinedList = new ArrayList<>();
        Stream.of(lists).forEach(joinedList::addAll);
        return joinedList;
    }
}
