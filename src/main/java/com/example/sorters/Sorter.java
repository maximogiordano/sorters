package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public interface Sorter<T> {
    void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex);

    default void sort(List<T> list, Comparator<? super T> comparator) {
        sort(list, comparator, 0, list.size() - 1);
    }

    static <T> void swap(List<T> list, int i, int j) {
        T t = list.get(i);

        list.set(i, list.get(j));
        list.set(j, t);
    }
}
