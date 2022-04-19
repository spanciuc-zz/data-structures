package com.pantifik.ds.list.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class DequeueTest {

  @Test
  void offerFirst_shouldAddElementToTheStart() {
    Deque<Object> queue = createDequeInstance();
    queue.add(1);
    queue.offerFirst("first");
    assertTrue(queue.contains("first"));
    assertEquals("first", queue.peekFirst());
  }

  @Test
  void offerLast_shouldAddElementToTheEnd() {
    Deque<Object> queue = createDequeInstance();
    queue.add(1);
    queue.offerLast("first");
    assertTrue(queue.contains("first"));
    assertEquals("first", queue.peekLast());
  }

  @Test
  void removeFirst_whenEmpty_shouldThrowException() {
    Deque<Object> queue = createDequeInstance();
    assertThrows(NoSuchElementException.class, queue::removeFirst);
  }

  @Test
  void removeLast_whenEmpty_shouldThrowException() {
    Deque<Object> queue = createDequeInstance();
    assertThrows(NoSuchElementException.class, queue::removeLast);
  }


  @Test
  void removeFirst_whenNotEmpty_shouldRemoveAndReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.removeFirst());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(2, 3)));
  }

  @Test
  void removeLast_whenNotEmpty_shouldRemoveAndReturnLastElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(3, queue.removeLast());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(1, 1)));
  }

  @Test
  void pollFirst_whenNotEmpty_shouldRemoveAndReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.pollFirst());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(2, 3)));
  }

  @Test
  void pollLast_whenNotEmpty_shouldRemoveAndReturnLastElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(3, queue.pollLast());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2)));
  }

  @Test
  void pollFirst_whenEmpty_shouldReturnNull() {
    Deque<Object> queue = createDequeInstance();
    assertNull(queue.pollFirst());
  }

  @Test
  void pollLast_whenEmpty_shouldReturnNull() {
    Deque<Object> queue = createDequeInstance();
    assertNull(queue.pollLast());
  }

  @Test
  void getFirst_whenEmpty_shouldThrowException() {
    Deque<Object> queue = createDequeInstance();
    assertThrows(NoSuchElementException.class, queue::getFirst);
  }

  @Test
  void getLast_whenEmpty_shouldThrowException() {
    Deque<Object> queue = createDequeInstance();
    assertThrows(NoSuchElementException.class, queue::getLast);
  }

  @Test
  void getFirst_whenNotEmpty_shouldReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.getFirst());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void getLast_whenNotEmpty_shouldReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(3, queue.getLast());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void peekFirst_whenEmpty_shouldReturnNull() {
    Deque<Object> queue = createDequeInstance();
    assertNull(queue.peekFirst());
  }

  @Test
  void peekLast_whenEmpty_shouldReturnNull() {
    Deque<Object> queue = createDequeInstance();
    assertNull(queue.peekLast());
  }

  @Test
  void peekFirst_whenNotEmpty_shouldReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.peekFirst());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void peekLast_whenNotEmpty_shouldReturnFirstElement() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(3, queue.peekLast());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void iterator_whenNotEmpty_shouldIterateOverAllElements() {
    Deque<Object> queue = createDequeInstance();
    queue.addAll(List.of(-1, 0, 2, 5, 6));

    Deque<Object> visitedElements = new DoublyLinkedList<>();
    Iterator<Object> iterator = queue.descendingIterator();
    while (iterator.hasNext()) {
      visitedElements.addFirst(iterator.next());
    }

    assertEquals(queue, visitedElements);
  }

  private Deque<Object> createDequeInstance() {
    return new DoublyLinkedList<>();
  }
}
