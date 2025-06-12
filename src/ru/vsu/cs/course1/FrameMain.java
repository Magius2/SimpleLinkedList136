package ru.vsu.cs.course1;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ru.vsu.cs.course1.util.ArrayUtils;
import ru.vsu.cs.course1.util.JTableUtils;
import ru.vsu.cs.course1.util.SwingUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FrameMain extends JFrame {
    private JPanel panelMain;
    private JTable tableInput;
    private JButton buttonLoadInputFromFile;
    private JButton buttonRandomInput;
    private JButton buttonSaveInputIntoFile;
    private JButton buttonExecute;
    private JButton buttonSaveOutputIntoFile;
    private JTable tableOutput;
    private JScrollPane scrollPaneTableInput;
    private JScrollPane scrollPaneTableOutput;

    private JFileChooser fileChooserOpen;
    private JFileChooser fileChooserSave;
    private JMenuBar menuBarMain;
    private JMenu menuLookAndFeel;

    public FrameMain() {
        // Установка contentPane
        if (panelMain == null) {
            panelMain = new JPanel();
            panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10)); // Убрал лишнюю строку
        }
        this.setContentPane(panelMain);
        this.setTitle("Remove Duplicates from Linked List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        // Резервная инициализация таблиц
        if (tableInput == null || scrollPaneTableInput == null) {
            tableInput = new JTable();
            scrollPaneTableInput = new JScrollPane(tableInput);
            panelMain.add(scrollPaneTableInput, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 90), null, 0, false));
        }
        if (tableOutput == null || scrollPaneTableOutput == null) {
            tableOutput = new JTable();
            scrollPaneTableOutput = new JScrollPane(tableOutput);
            panelMain.add(scrollPaneTableOutput, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 90), null, 0, false));
        }

        // Инициализация таблиц
        JTableUtils.initJTableForArray(tableInput, 40, false, true, false, true);
        JTableUtils.initJTableForArray(tableOutput, 40, false, true, false, true);
        tableInput.setRowHeight(25);
        tableOutput.setRowHeight(25);

        // Инициализация выбора файлов
        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);
        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");

        // Инициализация меню
        menuBarMain = new JMenuBar();
        setJMenuBar(menuBarMain);
        menuLookAndFeel = new JMenu("Вид");
        menuBarMain.add(menuLookAndFeel);
        SwingUtils.initLookAndFeelMenu(menuLookAndFeel);

        // Начальные данные
        JTableUtils.writeArrayToJTable(tableInput, new int[]{0, 1, 2, 3, 4, 5});
        this.pack();

        // Обработчики кнопок
        JPanel panel1 = new JPanel(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        if (buttonLoadInputFromFile == null) buttonLoadInputFromFile = new JButton("Загрузить из файла");
        panel1.add(buttonLoadInputFromFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonLoadInputFromFile.addActionListener(e -> {
            try {
                if (fileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[] arr = ArrayUtils.readIntArrayFromFile(fileChooserOpen.getSelectedFile().getPath());
                    if (arr != null) JTableUtils.writeArrayToJTable(tableInput, arr);
                    else SwingUtils.showInfoMessageBox("Не удалось загрузить данные из файла.");
                }
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        if (buttonRandomInput == null) buttonRandomInput = new JButton("Заполнить случайными числами");
        panel1.add(buttonRandomInput, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRandomInput.addActionListener(e -> {
            try {
                int columnCount = tableInput.getColumnCount();
                if (columnCount == 0) {
                    columnCount = 10;
                    JTableUtils.initJTableForArray(tableInput, 40, false, true, false, true);
                }
                int[] arr = ArrayUtils.createRandomIntArray(columnCount, 100);
                JTableUtils.writeArrayToJTable(tableInput, arr);
                JOptionPane.showMessageDialog(panelMain, "Таблица заполнена случайными числами: " + java.util.Arrays.toString(arr));
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        if (buttonSaveInputIntoFile == null) buttonSaveInputIntoFile = new JButton("Сохранить в файл");
        panel1.add(buttonSaveInputIntoFile, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSaveInputIntoFile.addActionListener(e -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[] arr = JTableUtils.readIntArrayFromJTable(tableInput);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) file += ".txt";
                    ArrayUtils.writeArrayToFile(file, arr);
                }
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        panel1.add(new Spacer(), new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(100, -1), null, 0, false));
        panelMain.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));

        if (buttonExecute == null) buttonExecute = new JButton("Удалить дубликаты");
        JPanel panel2 = new JPanel(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel2.add(buttonExecute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel2.add(new Spacer(), new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelMain.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonExecute.addActionListener(e -> {
            try {
                int[] arr = JTableUtils.readIntArrayFromJTable(tableInput);
                SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
                for (int value : arr) {
                    list.addLast(value);
                }
                list.removeDuplicatedValues();
                int[] result = new int[list.size()];
                int i = 0;
                for (Integer value : list) {
                    result[i++] = value;
                }
                JTableUtils.writeArrayToJTable(tableOutput, result);
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });

        if (buttonSaveOutputIntoFile == null) buttonSaveOutputIntoFile = new JButton("Сохранить в файл");
        JPanel panel3 = new JPanel(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.add(buttonSaveOutputIntoFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        panel3.add(new Spacer(), new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        panelMain.add(panel3, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSaveOutputIntoFile.addActionListener(e -> {
            try {
                if (fileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                    int[] arr = JTableUtils.readIntArrayFromJTable(tableOutput);
                    String file = fileChooserSave.getSelectedFile().getPath();
                    if (!file.toLowerCase().endsWith(".txt")) file += ".txt";
                    ArrayUtils.writeArrayToFile(file, arr);
                }
            } catch (Exception ex) {
                SwingUtils.showErrorMessageBox(ex);
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    private void $$$setupUI$$$() {
        panelMain = new JPanel();
        panelMain.setLayout(new GridLayoutManager(5, 2, new Insets(10, 10, 10, 10), 10, 10));
        scrollPaneTableInput = new JScrollPane();
        scrollPaneTableInput.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPaneTableInput, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 90), null, 0, false));
        tableInput = new JTable();
        scrollPaneTableInput.setViewportView(tableInput);
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel1, new GridConstraints(1, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonLoadInputFromFile = new JButton();
        buttonLoadInputFromFile.setText("Загрузить из файла");
        panel1.add(buttonLoadInputFromFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonRandomInput = new JButton();
        buttonRandomInput.setText("Заполнить случайными числами");
        panel1.add(buttonRandomInput, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonSaveInputIntoFile = new JButton();
        buttonSaveInputIntoFile.setText("Сохранить в файл");
        panel1.add(buttonSaveInputIntoFile, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, new Dimension(100, -1), null, 0, false));
        scrollPaneTableOutput = new JScrollPane();
        scrollPaneTableOutput.setEnabled(true);
        scrollPaneTableOutput.setVerticalScrollBarPolicy(21);
        panelMain.add(scrollPaneTableOutput, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(200, 90), null, 0, false));
        tableOutput = new JTable();
        tableOutput.setEnabled(true);
        scrollPaneTableOutput.setViewportView(tableOutput);
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel2, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonExecute = new JButton();
        buttonExecute.setText("Удалить дубликаты");
        panel2.add(buttonExecute, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel2.add(spacer2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panelMain.add(panel3, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonSaveOutputIntoFile = new JButton();
        buttonSaveOutputIntoFile.setText("Сохранить в файл");
        panel3.add(buttonSaveOutputIntoFile, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    public JComponent $$$getRootComponent$$$() {
        return panelMain;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FrameMain frame = new FrameMain();
                frame.setVisible(true);
            } catch (Exception e) {
                SwingUtils.showErrorMessageBox(e);
            }
        });
    }
}