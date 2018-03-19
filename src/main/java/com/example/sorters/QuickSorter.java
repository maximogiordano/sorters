package com.example.sorters;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class QuickSorter<T> implements Sorter<T> {
    private Random random;

    public QuickSorter(Random random) {
        this.random = random == null ? new Random() : random;
    }

    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        if (beginIndex < endIndex) {
            int pivotIndex = distribute(list, comparator, beginIndex, endIndex);

            sort(list, comparator, beginIndex, pivotIndex - 1);
            sort(list, comparator, pivotIndex + 1, endIndex);
        }
    }

    int distribute(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        int storeIndex = beginIndex;
        int pivotIndex = getPivotIndex(beginIndex, endIndex);

        T pivotValue = list.get(pivotIndex);

        Sorter.swap(list, pivotIndex, endIndex);

        for (int i = beginIndex; i < endIndex; i++) {
            if (comparator.compare(list.get(i), pivotValue) < 0) {
                Sorter.swap(list, i, storeIndex++);
            }
        }

        Sorter.swap(list, storeIndex, endIndex);

        return storeIndex;
    }

    private int getPivotIndex(int beginIndex, int endIndex) {
        return random.nextInt(endIndex - beginIndex + 1) + beginIndex;
    }
}
