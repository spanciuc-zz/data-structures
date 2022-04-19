package com.pantifik.ds.stack.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.stack.Stack;
import java.util.EmptyStackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class StackTest {

  protected Stack<Object> stack;

  /**
   * Sets up the test suite's stack implementation.
   */
  @BeforeEach
  protected abstract void setUp();

  @Test
  void push_shouldAddElementAtTheTopOfTheStack() {
    stack.push(1);
    assertEquals(1, stack.size());
    assertEquals(1, stack.peek());
  }

  @Test
  void size_whenStackHasNoElements_shouldReturnStackSize() {
    assertEquals(0, stack.size());
  }

  @Test
  void size_whenStackHasElements_shouldReturnNumberOfElements() {
    stack.push(1);
    stack.push(1);
    stack.push(1);
    stack.clear();
    stack.push(2);
    stack.push(2);
    assertEquals(2, stack.size());
  }

  @Test
  void isEmpty_whenStackHasNoElements_shouldReturnTrue() {
    assertTrue(stack.isEmpty());
  }

  @Test
  void isEmpty_whenStackHasElements_shouldReturnFalse() {
    stack.push(new Object());
    assertFalse(stack.isEmpty());
  }

  @Test
  void peek_whenEmpty_shouldThrowException() {
    assertThrows(EmptyStackException.class, stack::peek);
  }

  @Test
  void peek_whenNotEmpty_shouldReturnTopElement() {
    stack.push(4);
    stack.push(3);
    stack.push(2);
    stack.push(4);
    assertEquals(4, stack.peek());
  }

  @Test
  void pop_whenEmpty_shouldThrowException() {
    assertThrows(EmptyStackException.class, stack::pop);
  }

  @Test
  void pop_whenNotEmpty_shouldRemoveTopElementAndReturnIt() {
    stack.push(4);
    stack.push(3);
    stack.push(2);
    stack.push(4);
    assertEquals(4, stack.pop());
    assertEquals(3, stack.size());
  }

  @Test
  void clear_shouldRemoveAllElementsFromStack() {
    stack.push(new Object());
    stack.push(1);
    stack.push(100L);
    stack.clear();
    assertTrue(stack.isEmpty());
  }


}