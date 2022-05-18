package com.pantifik.ds.tree.heap.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.tree.heap.Type;
import java.util.Arrays;
import java.util.Collection;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
class ConstructorsBinaryHeapTest<T extends Comparable<T>> {

  private BinaryHeap<T> heap;

  @Test
  void default_shouldCreateWithDefaultCapacityAndType() {
    heap = new BinaryHeap<>();
    assertEquals(BinaryHeap.DEFAULT_CAPACITY, heap.getCapacity());
    assertEquals(BinaryHeap.DEFAULT_TYPE, heap.getType());
  }

  @Test
  void withCapacity_whenIsLessThanZero_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new BinaryHeap<>(-1));
  }

  @Test
  void withCapacity_whenValidCapacity_shouldCreateWithGivenCapacityAndDefaultType() {
    int capacity = 10;
    heap = new BinaryHeap<>(capacity);
    assertEquals(capacity, heap.getCapacity());
    assertEquals(BinaryHeap.DEFAULT_TYPE, heap.getType());
  }

  @Test
  void withCapacityAndType_whenCapacityIsLessThanZero_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new BinaryHeap<>(-1, Type.MIN));
  }

  @Test
  void withCapacityAndType_whenNullType_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> new BinaryHeap<>(20, null));
  }

  @Test
  void withCapacityAndType_whenBothValid_shouldCreateHeapOfGivenCapacityAndType() {
    int capacity = 5;
    Type type = Type.MAX;
    heap = new BinaryHeap<>(capacity, type);
    assertEquals(capacity, heap.getCapacity());
    assertEquals(type, heap.getType());
  }

  @Test
  void withCollection_whenNull_shouldThrowException() {
    //noinspection ConstantConditions
    assertThrows(NullPointerException.class, () -> new BinaryHeap<>(null));
  }

  @Test
  void withCollection_whenContainsNulls_shouldThrowException() {
    Collection<T> collection = (Collection<T>) Arrays.asList(1, 2, null, 3, null);
    assertThrows(NullPointerException.class, () -> new BinaryHeap<>(collection));
  }

  @Test
  void withCollection_whenValid_shouldCreateHeapWithCollectionsCapacityAndElements() {
    Collection<T> collection = (Collection<T>) Arrays.asList(1, 20, 2, 9, 5, 3, 7, -1);
    heap = new BinaryHeap<>(collection);
    assertEquals(collection.size(), heap.getCapacity());
    assertTrue(heap.containsAll(collection));
  }
}