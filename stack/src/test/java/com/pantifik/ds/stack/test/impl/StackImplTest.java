package com.pantifik.ds.stack.test.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.stack.impl.StackImpl;
import com.pantifik.ds.stack.test.StackTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StackImplTest extends StackTest {

  @BeforeEach
  @Override
  protected void setUp() {
    this.stack = new StackImpl<>();
  }

  @Test
  void constructor_shouldCreateEmptyStack() {
    // the constructor was called in setUp() method.
    assertTrue(stack.isEmpty());
  }
}