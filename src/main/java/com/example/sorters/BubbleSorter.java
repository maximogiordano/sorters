package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public class BubbleSorter<T> implements Sorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        for (int i = beginIndex; i < endIndex; i++) {
            for (int j = endIndex; j > i; j--) {
                if (comparator.compare(list.get(j), list.get(j - 1)) < 0) {
                    Sorter.swap(list, j, j - 1);
                }
            }
        }
    }
}
