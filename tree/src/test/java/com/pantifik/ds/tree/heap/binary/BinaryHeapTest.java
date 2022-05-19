package com.pantifik.ds.tree.heap.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.tree.heap.Type;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

@SuppressWarnings("unchecked")
class BinaryHeapTest<T extends Comparable<T>> {

  private BinaryHeap<T> heap;

  @Test
  void size_shouldReturnNumberOfElementsInHeap() {
    heap = new BinaryHeap<>();
    assertEquals(0, heap.size());
    heap.add((T) "elem1");
    assertEquals(1, heap.size());
    heap.addAll((Iterable<T>) List.of("elem2", "elem3"));
    assertEquals(3, heap.size());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 3, 10, 100})
  void getCapacity_shouldReturnHeapsCapacity(int expected) {
    heap = new BinaryHeap<>(expected);
    assertEquals(expected, heap.getCapacity());
  }

  @ParameterizedTest
  @EnumSource(Type.class)
  void getCapacity_shouldReturnHeapsCapacity(Type expected) {
    heap = new BinaryHeap<>(10, expected);
    assertEquals(expected, heap.getType());
  }

  @Test
  void add_whenNull_shouldThrowException() {
    heap = new BinaryHeap<>();
    assertThrows(NullPointerException.class, () -> heap.add(null));
  }

  @Test
  void add_whenNotNull_shouldAddElementToTheHeap() {
    heap = new BinaryHeap<>();
    T elem = (T) "element";
    assertFalse(heap.contains(elem));
    heap.add(elem);
    assertTrue(heap.contains(elem));
  }

  @Test
  void add_whenCapacityIsZero_shouldSetCapacityToOne() {
    heap = new BinaryHeap<>(0);
    heap.add((T) (Integer) 4);
    assertEquals(1, heap.getCapacity());
  }

  @Test
  void add_whenCapacityExceeded_shouldResizeHeap() {
    Collection<T> initialElements = (Collection<T>) List.of(1, 2, 3);
    heap = new BinaryHeap<>(initialElements);
    heap.add((T) (Integer) 4);
    assertEquals(initialElements.size() * BinaryHeap.CAPACITY_INCREASE_FACTOR, heap.getCapacity());
  }

  @Test
  void contains_whenNull_shouldReturnFalse() {
    heap = new BinaryHeap<>((Collection<T>) List.of(1));
    assertFalse(heap.contains(null));
  }

  @Test
  void contains_whenNotPresent_shouldReturnFalse() {
    heap = new BinaryHeap<>((Collection<T>) List.of(1, 2, 3));
    assertFalse(heap.contains((T) (Integer) 4));
  }

  @Test
  void contains_whenPresent_shouldReturnTrue() {
    heap = new BinaryHeap<>((Collection<T>) List.of(1, 2, 3, 4));
    assertTrue(heap.contains((T) (Integer) 4));
  }

  @Test
  void remove_whenNull_shouldThrowException() {
    heap = new BinaryHeap<>();
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    assertThrows(NullPointerException.class, () -> heap.remove(null));
  }

  @Test
  void remove_whenNotPresent_shouldReturnFalse() {
    heap = new BinaryHeap<>();
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    assertFalse(heap.remove((T) (Integer) 10));
  }

  @Test
  void remove_whenPresent_shouldRemoveElementAndReturnTrue() {
    heap = new BinaryHeap<>();
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    assertTrue(heap.remove((T) (Integer) 8));
    assertFalse(heap.contains((T) (Integer) 8));
    assertEquals(8, heap.size());
  }

  @Test
  void remove_whenMultiplePresent_shouldRemoveElementAndReturnTrue() {
    heap = new BinaryHeap<>();
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 1, 2, 3, 1, 2, 3));
    assertTrue(heap.remove((T) (Integer) 3));
    assertTrue(heap.contains((T) (Integer) 3));
    assertEquals(8, heap.size());
  }

  @Test
  void getElement_whenEmpty_shouldReturnEmptyOptional() {
    heap = new BinaryHeap<>();
    assertEquals(Optional.empty(), heap.getElement());
  }

  @ParameterizedTest
  @MethodSource("getElementForHeapTypeProvider")
  void getElement_shouldReturnValueForHeapTypeAndRemoveIt(Type type, T expected) {
    heap = new BinaryHeap<>(BinaryHeap.DEFAULT_CAPACITY, type);
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    assertEquals(Optional.of(expected), heap.getElement());
    assertEquals(8, heap.size());
    assertNotEquals(Optional.of(expected), heap.getElement());
  }

  @Test
  void peek_whenEmpty_shouldReturnEmptyOptional() {
    heap = new BinaryHeap<>();
    assertEquals(Optional.empty(), heap.peek());
  }

  @ParameterizedTest
  @MethodSource("getElementForHeapTypeProvider")
  void peek_shouldReturnValueForHeapTypeAndNotRemove(Type type, T expected) {
    heap = new BinaryHeap<>(BinaryHeap.DEFAULT_CAPACITY, type);
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    assertEquals(Optional.of(expected), heap.peek());
    assertEquals(9, heap.size());
    assertEquals(Optional.of(expected), heap.peek());
  }

  @Test
  void clear_shouldResetTheHeap() {
    heap = new BinaryHeap<>((Collection<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    heap.clear();
    assertEquals(BinaryHeap.INITIAL_SIZE, heap.size());
    assertEquals(BinaryHeap.DEFAULT_CAPACITY, heap.getCapacity());
  }

  @ParameterizedTest
  @MethodSource("getElementForHeapTypeProvider")
  void convertTo_whenSameType_shouldDoNothing(Type type, T expected) {
    heap = new BinaryHeap<>(BinaryHeap.DEFAULT_CAPACITY, type);
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    //noinspection OptionalGetWithoutIsPresent
    T elem = heap.getElement()
        .get();
    assertEquals(expected, elem);
    heap.convertTo(type);
    assertEquals(type, heap.getType());
    heap.add(elem);
    elem = heap.getElement()
        .get();
    assertEquals(expected, elem);
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @ParameterizedTest
  @MethodSource("convertToForHeapTypeProvider")
  void convertTo_whenDifferentType_shouldChangeHeapType(Type initType, T initExpected,
      Type afterType, T afterExpected) {
    heap = new BinaryHeap<>(BinaryHeap.DEFAULT_CAPACITY, initType);
    heap.addAll((Iterable<T>) List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
    T elem = heap.getElement()
        .get();
    assertEquals(initExpected, elem);
    heap.convertTo(afterType);
    assertEquals(afterType, heap.getType());
    heap.add(elem);
    elem = heap.getElement()
        .get();
    assertEquals(afterExpected, elem);
  }

  private static Stream<Arguments> getElementForHeapTypeProvider() {
    return Stream.of(Arguments.of(Type.MAX, 9), Arguments.of(Type.MIN, 1));
  }

  private static Stream<Arguments> convertToForHeapTypeProvider() {
    return Stream.of(Arguments.of(Type.MAX, 9, Type.MIN, 1),
        Arguments.of(Type.MIN, 1, Type.MAX, 9));
  }
}