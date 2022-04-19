package com.pantifik.ds.list.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.pantifik.ds.list.AbstractListTest;
import java.util.List;
import org.junit.jupiter.api.Test;

class DoublyLinkedListTest extends AbstractListTest {

  @Override
  protected List<Object> createListInstance() {
    return new DoublyLinkedList<>();
  }

  @Test
  @Override
  protected void sublist_whenValidIndexes_shouldReturnListViewInRange() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    assertThrows(UnsupportedOperationException.class, () -> list.subList(1, 4));
  }

  @Test
  void constructorWithIterable_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class,
        () -> new DoublyLinkedList<>(null));
  }

  @Test
  void constructorWithIterable_shouldCreateArrayListWithElementFromCollection() {
    List<Object> iterable = List.of("1", 2, '3', 4.4);
    DoublyLinkedList<Object> arrayList = new DoublyLinkedList<>(iterable);
    assertFalse(arrayList.isEmpty());
    assertEquals(iterable.size(), arrayList.size());
    assertEquals(0, arrayList.indexOf("1"));
    assertEquals(1, arrayList.indexOf(2));
    assertEquals(2, arrayList.indexOf('3'));
    assertEquals(3, arrayList.indexOf(4.4));
  }
}