package ru.vsu.cs.course1;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Test removeDuplicatedValues: ");
        SimpleLinkedList<Integer> testList = new SimpleLinkedList<>();
        testList.addLast(10);
        testList.addLast(20);
        testList.addLast(10);  // дубликат
        testList.addLast(30);
        testList.addLast(20);  // дубликат

        System.out.print("До удаления: ");
        for (Integer v : testList) {
            System.out.print(v + " ");
        }
        System.out.println();

        testList.removeDuplicatedValues();

        System.out.print("После удаления: ");
        for (Integer v : testList) {
            System.out.print(v + " ");
        }
        System.out.println();
    }
}