package com.example.sorters;

import java.util.Comparator;
import java.util.List;

public class ImprovedInsertionSorter<T> extends InsertionSorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        for (int i = beginIndex; i < endIndex; i++) {
            T t = list.get(i + 1);

            int j = i;

            while (j >= beginIndex && comparator.compare(list.get(j), t) > 0) {
                list.set(j + 1, list.get(j));

                j--;
            }

            if (j < i) {
                list.set(j + 1, t);
            }
        }
    }
}
