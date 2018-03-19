package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public class InsertionSorter<T> implements Sorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        for (int i = beginIndex; i < endIndex; i++) {
            insert(list, comparator, beginIndex, i);
        }
    }

    private void insert(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        T t = list.get(endIndex + 1);
        int i = beginIndex;

        while (i <= endIndex && comparator.compare(t, list.get(i)) >= 0) {
            i++;
        }

        if (i <= endIndex) {
            for (int j = endIndex + 1; j > i; j--) {
                list.set(j, list.get(j - 1));
            }

            list.set(i, t);
        }
    }
}
