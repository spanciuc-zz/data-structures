package com.pantifik.ds.queue.test.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.queue.simple.SimpleQueue;
import com.pantifik.ds.queue.test.QueueTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimpleQueueTest extends QueueTest {

  @BeforeEach
  @Override
  protected void setUp() {
    this.queue = new SimpleQueue<>();
  }

  @Test
  void constructor_shouldCreateEmptyQueue() {
    assertTrue(queue.isEmpty());
    assertEquals(0, queue.size());
  }
}