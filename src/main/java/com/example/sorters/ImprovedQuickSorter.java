package com.example.sorters;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class ImprovedQuickSorter<T> extends QuickSorter<T> {
    public static final int MIN_CRITICAL_SIZE = 2;
    public static final int MAX_CRITICAL_SIZE = 16;

    private int criticalSize;
    private Sorter<T> internalSorter;

    public ImprovedQuickSorter(Random random, int criticalSize, Sorter<T> internalSorter) {
        super(random);

        if (criticalSize < MIN_CRITICAL_SIZE) {
            criticalSize = MIN_CRITICAL_SIZE;
        }

        if (criticalSize > MAX_CRITICAL_SIZE) {
            criticalSize = MAX_CRITICAL_SIZE;
        }

        if (internalSorter == null) {
            internalSorter = new ImprovedInsertionSorter<>();
        }

        this.criticalSize = criticalSize;
        this.internalSorter = internalSorter;
    }

    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        int listSize = endIndex - beginIndex + 1;

        if (listSize < criticalSize) {
            internalSorter.sort(list, comparator, beginIndex, endIndex);
        } else {
            int pivotIndex = distribute(list, comparator, beginIndex, endIndex);

            sort(list, comparator, beginIndex, pivotIndex - 1);
            sort(list, comparator, pivotIndex + 1, endIndex);
        }
    }
}
