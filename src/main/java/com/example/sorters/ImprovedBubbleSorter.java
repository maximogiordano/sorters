package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public class ImprovedBubbleSorter<T> extends BubbleSorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        boolean changed = true;

        for (int i = beginIndex; i < endIndex && changed; i++) {
            changed = false;

            for (int j = endIndex; j > i; j--) {
                if (comparator.compare(list.get(j), list.get(j - 1)) < 0) {
                    Sorter.swap(list, j, j - 1);
                    changed = true;
                }
            }
        }
    }
}
