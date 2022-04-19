package com.pantifik.ds.queue.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.queue.Queue;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class QueueTest {

  protected Queue<Object> queue;

  /**
   * Must be implemented and run before each test case in subclasses.
   */
  @BeforeEach
  protected abstract void setUp();

  @Test
  void isEmpty_whenIsEmpty_shouldReturnTrue() {
    assertTrue(queue.isEmpty());
  }

  @Test
  void isEmpty_whenIsNotEmpty_shouldReturnFalse() {
    queue.add(new Object());
    assertFalse(queue.isEmpty());
  }

  @Test
  void size_whenIsEmpty_shouldReturnZero() {
    assertEquals(0, queue.size());
  }

  @Test
  void isEmpty_whenIsNotEmpty_shouldReturnTheNumberOfTheElements() {
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    queue.add(new Object());
    assertEquals(10, queue.size());
  }

  @Test
  void add_whenEmpty_shouldAddElementAsHeadAndTail() {
    queue.add(1);
    assertFalse(queue.isEmpty());
    assertEquals(1, queue.size());
    assertEquals(1, queue.element());
  }

  @Test
  void add_whenNotEmpty_shouldAddElementAtTheEndOfTheQueue() {
    queue.add(1);
    queue.add(2);
    queue.add(3);
    assertFalse(queue.isEmpty());
    assertEquals(3, queue.size());
    assertEquals(1, queue.element());
  }

  @Test
  void element_whenEmpty_shouldThrowException() {
    assertThrows(NoSuchElementException.class, queue::element);
  }

  @Test
  void element_whenNotEmpty_shouldReturnFirstElementOfTheQueue() {
    queue.add(5);
    queue.add(4);
    queue.add(3);
    queue.add(2);
    queue.add(1);
    assertEquals(5, queue.element());
  }

  @Test
  void peek_whenEmpty_shouldReturnNull() {
    assertNull(queue.peek());
  }

  @Test
  void peek_whenNotEmpty_shouldReturnFirstElementOfTheQueue() {
    queue.add(4);
    queue.add(3);
    queue.add(5);
    queue.add(2);
    assertEquals(4, queue.peek());
  }

  @Test
  void poll_whenEmpty_shouldReturnNull() {
    assertNull(queue.poll());
  }

  @Test
  void poll_whenNotEmpty_shouldRemoveAndReturnFirstElementOfTheQueue() {
    queue.add(4);
    queue.add(3);
    queue.add(5);
    queue.add(2);
    assertEquals(4, queue.poll());
    assertEquals(3, queue.size());
    assertEquals(3, queue.element());
  }

  @Test
  void remove_whenEmpty_shouldThrowException() {
    assertThrows(NoSuchElementException.class, queue::remove);
  }

  @Test
  void remove_whenNotEmpty_shouldRemoveAndReturnFirstElementOfTheQueue() {
    queue.add(4);
    queue.add(5);
    queue.add(2);
    assertEquals(4, queue.remove());
    assertEquals(2, queue.size());
    assertEquals(5, queue.element());
  }

  @Test
  void clear_shouldRemoveAllElementsFromTheQueue() {
    queue.add(4);
    queue.add(5);
    queue.add(2);
    queue.add(2);
    queue.add(null);
    queue.add(19);
    queue.clear();
    assertEquals(0, queue.size());
    assertTrue(queue.isEmpty());
  }


}