package com.example.sorters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Helper {
    public static List<Integer> getRandomList(Random random, int numberOfElements) {
        List<Integer> list = new ArrayList<>(numberOfElements);

        for (int i = 1; i <= numberOfElements; i++) {
            list.add(random.nextInt());
        }

        return list;
    }

    public static <T> boolean isSorted(List<T> list, Comparator<? super T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            if (comparator.compare(list.get(i - 1), list.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }
}
