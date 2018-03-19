package com.example.sorters.main;

import com.example.sorters.BubbleSorter;
import com.example.sorters.Helper;
import com.example.sorters.ImprovedBubbleSorter;
import com.example.sorters.ImprovedInsertionSorter;
import com.example.sorters.ImprovedQuickSorter;
import com.example.sorters.InsertionSorter;
import com.example.sorters.MergeSorter;
import com.example.sorters.QuickSorter;
import com.example.sorters.SelectionSorter;
import com.example.sorters.Sorter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.Random;

public class TestDrive {
    public static void main(String[] args) {
        new TestDrive().execute();
    }

    private JFrame sortersForm;
    private JComboBox<String> sorterNameComboBox;
    private JTextField numberOfTestsTextField;
    private JTextField numberOfElementsTextField;
    private JTextField criticalSizeTextField;
    private JComboBox<String> internalSorterComboBox;
    private JButton testButton;
    private JButton cancelButton;
    private JButton resetButton;
    private JTextArea outputTextArea;
    private boolean cancelButtonClicked;

    private TestDrive() {
        createComponents();
        setDefaultValues();
        setInitialLayout();
        setActions();
    }

    private void createComponents() {
        sortersForm = new JFrame("Sorters");
        sorterNameComboBox = new JComboBox<>(new String[]{
                "Bubble Sort",
                "Improved Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Improved Insertion Sort",
                "Quick Sort",
                "Improved Quick Sort",
                "Merge Sort"
        });
        numberOfTestsTextField = new JTextField();
        numberOfElementsTextField = new JTextField();
        criticalSizeTextField = new JTextField();
        internalSorterComboBox = new JComboBox<>(new String[]{
                "Bubble Sort",
                "Improved Bubble Sort",
                "Selection Sort",
                "Insertion Sort",
                "Improved Insertion Sort",
                "Quick Sort",
                "Merge Sort"
        });
        testButton = new JButton("Test");
        cancelButton = new JButton("Cancel");
        resetButton = new JButton("Reset");
        outputTextArea = new JTextArea();
    }

    private void setDefaultValues() {
        sorterNameComboBox.setSelectedIndex(0);
        numberOfTestsTextField.setText("300");
        numberOfElementsTextField.setText("1000");
        criticalSizeTextField.setText(String.valueOf((ImprovedQuickSorter.MIN_CRITICAL_SIZE + ImprovedQuickSorter.MAX_CRITICAL_SIZE) / 2));
        internalSorterComboBox.setSelectedIndex(4);
        outputTextArea.setText(null);
    }

    private void setInitialLayout() {
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JPanel outputPanel = new JPanel(new GridLayout(1, 1));
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3));

        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));
        buttonsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        inputPanel.add(new JLabel("Sorter Name"));
        inputPanel.add(sorterNameComboBox);
        inputPanel.add(new JLabel("Number of Tests"));
        inputPanel.add(numberOfTestsTextField);
        inputPanel.add(new JLabel("Number of Elements"));
        inputPanel.add(numberOfElementsTextField);

        outputPanel.add(new JScrollPane(outputTextArea));

        buttonsPanel.add(testButton);
        buttonsPanel.add(cancelButton);
        buttonsPanel.add(resetButton);

        sortersForm.setLayout(new BorderLayout());
        sortersForm.add(inputPanel, BorderLayout.NORTH);
        sortersForm.add(outputPanel, BorderLayout.CENTER);
        sortersForm.add(buttonsPanel, BorderLayout.SOUTH);

        outputTextArea.setRows(10);
    }

    private void setActions() {
        sorterNameComboBox.addItemListener(this::itemStateChanged);
        testButton.addActionListener(this::actionPerformed);
        cancelButton.addActionListener(this::actionPerformed);
        resetButton.addActionListener(this::actionPerformed);
        sortersForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void execute() {
        cancelButton.setEnabled(false);

        sortersForm.pack();
        sortersForm.setLocationRelativeTo(null);
        sortersForm.setVisible(true);
    }

    private void itemStateChanged(ItemEvent itemEvent) {
        if (itemEvent.getSource().equals(sorterNameComboBox) && itemEvent.getItem().equals("Improved Quick Sort")) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                showAdditionalInput();
            } else if (itemEvent.getStateChange() == ItemEvent.DESELECTED) {
                hideAdditionalInput();
            }
        }
    }

    private void showAdditionalInput() {
        JPanel inputPanel = getInputPanel();

        inputPanel.add(new JLabel("Critical Size"));
        inputPanel.add(criticalSizeTextField);
        inputPanel.add(new JLabel("Internal Sorter"));
        inputPanel.add(internalSorterComboBox);

        sortersForm.validate();
    }

    private void hideAdditionalInput() {
        JPanel inputPanel = getInputPanel();

        inputPanel.remove(9);
        inputPanel.remove(8);
        inputPanel.remove(7);
        inputPanel.remove(6);

        sortersForm.validate();
    }

    private JPanel getInputPanel() {
        return (JPanel) sortersForm.getContentPane().getComponent(0);
    }

    private void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(testButton)) {
            testButtonAction();
        } else if (actionEvent.getSource().equals(cancelButton)) {
            cancelButtonAction();
        } else if (actionEvent.getSource().equals(resetButton)) {
            resetButtonAction();
        }
    }

    private void testButtonAction() {
        Sorter<Integer> sorter;
        int numberOfTests;
        int numberOfElements;

        try {
            sorter = getSorter();
            numberOfTests = getNumberOfTests();
            numberOfElements = getNumberOfElements();
        } catch (Exception e) {
            outputTextArea.setText("Exception caught: " + e);
            return;
        }

        setEnabled(true, false);
        outputTextArea.setText(null);
        cancelButtonClicked = false;

        new Thread(() -> test(sorter, numberOfTests, numberOfElements)).start();
    }

    private Sorter<Integer> getSorter() {
        String sorterName = String.valueOf(sorterNameComboBox.getSelectedItem());

        if (sorterName.equals("Improved Quick Sort")) {
            int criticalSize = Integer.parseInt(criticalSizeTextField.getText());
            String internalSorter = String.valueOf(internalSorterComboBox.getSelectedItem());

            return new ImprovedQuickSorter<>(null, criticalSize, getSorter(internalSorter));
        } else {
            return getSorter(sorterName);
        }
    }

    private Sorter<Integer> getSorter(String sorterName) {
        switch (sorterName) {
            case "Bubble Sort":
                return new BubbleSorter<>();
            case "Improved Bubble Sort":
                return new ImprovedBubbleSorter<>();
            case "Selection Sort":
                return new SelectionSorter<>();
            case "Insertion Sort":
                return new InsertionSorter<>();
            case "Improved Insertion Sort":
                return new ImprovedInsertionSorter<>();
            case "Quick Sort":
                return new QuickSorter<>(null);
            case "Merge Sort":
                return new MergeSorter<>();
            default:
                throw new IllegalArgumentException("illegal sorter name: " + sorterName);
        }
    }

    private int getNumberOfElements() {
        return Integer.parseInt(numberOfElementsTextField.getText());
    }

    private int getNumberOfTests() {
        return Integer.parseInt(numberOfTestsTextField.getText());
    }

    private void setEnabled(boolean cancelButtonEnabled, boolean otherComponentsEnabled) {
        sorterNameComboBox.setEnabled(otherComponentsEnabled);
        numberOfTestsTextField.setEnabled(otherComponentsEnabled);
        numberOfElementsTextField.setEnabled(otherComponentsEnabled);
        criticalSizeTextField.setEnabled(otherComponentsEnabled);
        internalSorterComboBox.setEnabled(otherComponentsEnabled);
        testButton.setEnabled(otherComponentsEnabled);
        cancelButton.setEnabled(cancelButtonEnabled);
        resetButton.setEnabled(otherComponentsEnabled);
    }

    private void test(Sorter<Integer> sorter, int numberOfTests, int numberOfElements) {
        try {
            addOutputText(sorter.getClass().getSimpleName());
            addOutputText("");

            Random random = new Random();

            for (int i = 1; i <= numberOfTests && !cancelButtonClicked; i++) {
                long time = test(sorter, Helper.getRandomList(random, numberOfElements));
                addOutputText("Test #" + i + ": " + time + " ms.");
                Thread.sleep(100);
            }

            addOutputText(cancelButtonClicked ? "Cancelled!" : "Completed!");
        } catch (Exception e) {
            addOutputText("Exception caught: " + e);
        } finally {
            SwingUtilities.invokeLater(() -> setEnabled(false, true));
        }
    }

    private void addOutputText(String s) {
        SwingUtilities.invokeLater(() -> outputTextArea.setText(outputTextArea.getText() + s + '\n'));
    }

    private long test(Sorter<Integer> sorter, List<Integer> list) {
        long t1 = System.currentTimeMillis();
        sorter.sort(list, Integer::compareTo);
        long t2 = System.currentTimeMillis();
        return t2 - t1;
    }

    private void cancelButtonAction() {
        cancelButtonClicked = true;
    }

    private void resetButtonAction() {
        setDefaultValues();
    }
}
