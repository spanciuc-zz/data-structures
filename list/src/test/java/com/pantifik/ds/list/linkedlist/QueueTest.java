package com.pantifik.ds.list.linkedlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.junit.jupiter.api.Test;

class QueueTest {

  @Test
  void offer_shouldAddElementToTheEnd() {
    Queue<Object> queue = createQueueInstance();
    queue.offer("first");
    assertTrue(queue.contains("first"));
  }

  @Test
  void remove_whenEmpty_shouldThrowException() {
    Queue<Object> queue = createQueueInstance();
    assertThrows(NoSuchElementException.class, queue::remove);
  }

  @Test
  void remove_whenNotEmpty_shouldRemoveAndReturnFirstElement() {
    Queue<Object> queue = createQueueInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.remove());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(2, 3)));
  }

  @Test
  void poll_whenNotEmpty_shouldRemoveAndReturnFirstElement() {
    Queue<Object> queue = createQueueInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.poll());
    assertEquals(2, queue.size());
    assertTrue(queue.containsAll(List.of(2, 3)));
  }

  @Test
  void poll_whenEmpty_shouldReturnNull() {
    Queue<Object> queue = createQueueInstance();
    assertNull(queue.poll());
  }

  @Test
  void element_whenEmpty_shouldThrowException() {
    Queue<Object> queue = createQueueInstance();
    assertThrows(NoSuchElementException.class, queue::element);
  }

  @Test
  void element_whenNotEmpty_shouldReturnFirstElement() {
    Queue<Object> queue = createQueueInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.element());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void peek_whenEmpty_shouldReturnNull() {
    Queue<Object> queue = createQueueInstance();
    assertNull(queue.peek());
  }

  @Test
  void peek_whenNotEmpty_shouldReturnFirstElement() {
    Queue<Object> queue = createQueueInstance();
    queue.addAll(List.of(1, 2, 3));
    assertEquals(1, queue.peek());
    assertEquals(3, queue.size());
    assertTrue(queue.containsAll(List.of(1, 2, 3)));
  }

  private Queue<Object> createQueueInstance() {
    return new DoublyLinkedList<>();
  }
}
