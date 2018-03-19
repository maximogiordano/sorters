package com.example.sorters;

import org.junit.Test;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class Sorters {
    private static final int NUMBER_OF_TESTS = 300;
    private static final int NUMBER_OF_ELEMENTS = 1000;

    @Test
    public void testBubbleSorter() {
        testSorterOfInteger(new BubbleSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testImprovedBubbleSorter() {
        testSorterOfInteger(new ImprovedBubbleSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testSelectionSorter() {
        testSorterOfInteger(new SelectionSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testInsertionSorter() {
        testSorterOfInteger(new InsertionSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testImprovedInsertionSorter() {
        testSorterOfInteger(new ImprovedInsertionSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testQuickSorter() {
        testSorterOfInteger(new QuickSorter<>(null), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testImprovedQuickSorter() {
        testSorterOfInteger(new ImprovedQuickSorter<>(null, (ImprovedQuickSorter.MIN_CRITICAL_SIZE + ImprovedQuickSorter.MAX_CRITICAL_SIZE) / 2, new ImprovedInsertionSorter<>()), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    @Test
    public void testMergeSorter() {
        testSorterOfInteger(new MergeSorter<>(), NUMBER_OF_TESTS, NUMBER_OF_ELEMENTS);
    }

    private void testSorterOfInteger(Sorter<Integer> sorter, int numberOfTests, int numberOfElements) {
        Random random = new Random();

        for (int i = 1; i <= numberOfTests; i++) {
            testSorterOfInteger(sorter, Helper.getRandomList(random, numberOfElements));
        }
    }

    private void testSorterOfInteger(Sorter<Integer> sorter, List<Integer> list) {
        sorter.sort(list, Integer::compareTo);
        assertTrue(sorter.getClass().getSimpleName() + " has failed!", Helper.isSorted(list, Integer::compareTo));
    }
}
