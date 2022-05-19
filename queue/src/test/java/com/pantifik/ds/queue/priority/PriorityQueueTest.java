package com.pantifik.ds.queue.priority;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PriorityQueueTest<E extends Comparable<E>> {

  private PriorityQueue<E> queue;

  @BeforeEach
  void setUp() {
    queue = new PriorityQueue<>();
  }

  @Test
  void constructor_shouldCreateEmptyQueue() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }

  @Test
  void add_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> queue.add(null));
  }

  @Test
  void add_whenNotNull_shouldAddElementToTheQueue() {
    E element = (E) "first";
    queue.add(element);
    assertTrue(queue.contains(element));
  }

  @Test
  void contains_whenPresent_shouldReturnTrue() {
    E element = (E) "element";
    queue.add(element);
    assertTrue(queue.contains(element));
  }

  @Test
  void contains_whenNotPresent_shouldReturnFalse() {
    E element = (E) "element";
    queue.add(element);
    assertFalse(queue.contains(null));
    assertFalse(queue.contains((E) "asd"));
  }

  @Test
  void size_shouldReturnNumberOfElementInTheQueue() {
    assertEquals(0, queue.size());
    queue.add((E) "first");
    assertEquals(1, queue.size());
    queue.add((E) "first");
    assertEquals(2, queue.size());
    queue.add((E) "first");
    assertEquals(3, queue.size());
  }

  @Test
  void isEmpty_whenHasNoElements_shouldReturnTrue() {
    assertTrue(queue.isEmpty());
  }

  @Test
  void isEmpty_whenHasElements_shouldReturnFalse() {
    queue.add((E) (Integer) 1);
    queue.add((E) (Integer) 1);
    queue.add((E) (Integer) 1);
    assertFalse(queue.isEmpty());
  }

  @Test
  void remove_whenIsEmpty_shouldThrowException() {
    assertThrows(NoSuchElementException.class, () -> queue.remove());
  }

  @Test
  void remove_whenNotEmpty_shouldReturnBiggestElement() {
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 3);
    queue.add((E) (Integer) 5);
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 6);
    queue.add((E) (Integer) 7);
    queue.add((E) (Integer) 4);
    assertEquals(7, queue.remove());
    assertEquals(6, queue.remove());
    assertEquals(5, queue.remove());
    assertEquals(4, queue.remove());
    assertEquals(4, queue.remove());
    assertEquals(4, queue.remove());
    assertEquals(3, queue.remove());
  }

  @Test
  void poll_whenIsEmpty_shouldReturnNull() {
    assertNull(queue.poll());
    queue.add((E) (Integer) 4);
    queue.remove();
    assertNull(queue.poll());
  }

  @Test
  void poll_whenNotEmpty_shouldReturnBiggestElement() {
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 3);
    queue.add((E) (Integer) 5);
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 6);
    queue.add((E) (Integer) 7);
    queue.add((E) (Integer) 4);
    assertEquals(7, queue.poll());
    assertEquals(6, queue.poll());
    assertEquals(5, queue.poll());
    assertEquals(4, queue.poll());
    assertEquals(4, queue.poll());
    assertEquals(4, queue.poll());
    assertEquals(3, queue.poll());
    assertNull(queue.poll());
  }

  @Test
  void element_whenEmpty_shouldGetFirstElementAndNotRemove() {
    assertThrows(NoSuchElementException.class, () -> queue.element());
  }

  @Test
  void element_whenNotEmpty_shouldThrowException() {
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 3);
    queue.add((E) (Integer) 5);
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 6);
    queue.add((E) (Integer) 7);
    queue.add((E) (Integer) 4);
    assertEquals(7, queue.element());
    assertEquals(7, queue.size());
    assertEquals(7, queue.element());
  }

  @Test
  void peek_whenEmpty_shouldGetFirstElementAndNotRemove() {
    assertNull(queue.peek());
  }

  @Test
  void peek_whenNotEmpty_shouldThrowException() {
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 3);
    queue.add((E) (Integer) 5);
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 6);
    queue.add((E) (Integer) 7);
    queue.add((E) (Integer) 4);
    assertEquals(7, queue.peek());
    assertEquals(7, queue.size());
    assertEquals(7, queue.peek());
  }

  @Test
  void clear_whenNotEmpty_shouldThrowException() {
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 3);
    queue.add((E) (Integer) 5);
    queue.add((E) (Integer) 4);
    queue.add((E) (Integer) 6);
    queue.add((E) (Integer) 7);
    queue.add((E) (Integer) 4);
    queue.clear();
    assertTrue(queue.isEmpty());
  }
}