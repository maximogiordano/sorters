package com.example.sorters;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MergeSorter<T> implements Sorter<T> {
    @Override
    public void sort(List<T> list, Comparator<? super T> comparator, int beginIndex, int endIndex) {
        if (beginIndex < endIndex) {
            int middle = (beginIndex + endIndex) / 2;

            sort(list, comparator, beginIndex, middle);
            sort(list, comparator, middle + 1, endIndex);
            merge(list, comparator, beginIndex, middle, endIndex);
        }
    }

    private void merge(List<T> list, Comparator<? super T> comparator, int beginIndex, int middle, int endIndex) {
        int head1 = beginIndex;
        int head2 = middle + 1;

        List<T> combinedList = new ArrayList<>();

        while (head1 <= middle && head2 <= endIndex) {
            if (comparator.compare(list.get(head1), list.get(head2)) <= 0) {
                combinedList.add(list.get(head1++));
            } else {
                combinedList.add(list.get(head2++));
            }
        }

        while (head1 <= middle) {
            combinedList.add(list.get(head1++));
        }

        for (int i = 0; i < combinedList.size(); i++) {
            list.set(beginIndex + i, combinedList.get(i));
        }
    }
}
