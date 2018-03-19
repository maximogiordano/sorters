package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public class SelectionSorter<T> implements Sorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        for (int i = beginIndex; i < endIndex; i++) {
            int min = i;

            for (int j = i + 1; j <= endIndex; j++) {
                if (comparator.compare(list.get(j), list.get(min)) < 0) {
                    min = j;
                }
            }

            if (min != i) {
                Sorter.swap(list, min, i);
            }
        }
    }
}
