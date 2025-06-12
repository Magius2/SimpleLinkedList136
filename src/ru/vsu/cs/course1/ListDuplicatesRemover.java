package ru.vsu.cs.course1;

/**
 * Интерфейс для удаления дубликатов из списка
 * @param <T> тип элементов списка
 */
public interface ListDuplicatesRemover<T> {
    void removeDuplicatedValues() throws SimpleLinkedListException;
}