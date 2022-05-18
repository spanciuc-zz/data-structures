package com.pantifik.ds.tree.heap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class HeapTest<T extends Comparable<T>> {

  @Spy
  private Heap<T> heap;

  @Test
  void addAll_whenNullIterable_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> heap.addAll(null));
  }

  @Test
  void addAll_whenIterableContainsNulls_shouldThrowException() {
    when(heap.add(isNotNull())).thenReturn(false);
    when(heap.add(isNull())).thenThrow(NullPointerException.class);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("1", null, "2");
    assertThrows(NullPointerException.class, () -> heap.addAll(iterable));
  }

  @Test
  void addAll_whenIterableDoesNotContainsNulls_shouldCallAddForEachElement() {
    ArgumentCaptor<T> captor = (ArgumentCaptor<T>) ArgumentCaptor.forClass(String.class);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3");
    heap.addAll(iterable);
    verify(heap, times(3)).add(captor.capture());
    assertEquals(List.of("11", "2", "3"), captor.getAllValues());
  }

  @Test
  void addAll_whenIterableDoesNotContainsNulls_shouldReturnFalseIfForAllElementReturnIsFalse() {
    when(heap.add(isNotNull())).thenReturn(false);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3", "4");
    assertFalse(heap.addAll(iterable));
  }

  @Test
  void addAll_whenIterableDoesNotContainsNulls_shouldReturnTrueIfAtLeastForOneReturnIsTrue() {
    when(heap.add(isNotNull())).thenReturn(false);
    when(heap.add((T) "4")).thenReturn(true);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3", "4");
    assertTrue(heap.addAll(iterable));
  }

  @Test
  void removeAll_whenNullIterable_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> heap.removeAll(null));
  }

  @Test
  void removeAll_whenIterableContainsNulls_shouldThrowException() {
    when(heap.remove(isNotNull())).thenReturn(false);
    when(heap.remove(isNull())).thenThrow(NullPointerException.class);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("1", null, "2");
    assertThrows(NullPointerException.class, () -> heap.removeAll(iterable));
  }

  @Test
  void removeAll_whenIterableDoesNotContainsNulls_shouldCallRemoveForEachElement() {
    ArgumentCaptor<T> captor = (ArgumentCaptor<T>) ArgumentCaptor.forClass(String.class);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3");
    heap.removeAll(iterable);
    verify(heap, times(3)).remove(captor.capture());
    assertEquals(List.of("11", "2", "3"), captor.getAllValues());
  }

  @Test
  void removeAll_whenIterableDoesNotContainsNulls_shouldReturnFalseIfForAllElementReturnIsFalse() {
    when(heap.remove(isNotNull())).thenReturn(false);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3", "4");
    assertFalse(heap.removeAll(iterable));
  }

  @Test
  void removeAll_whenIterableDoesNotContainsNulls_shouldReturnTrueIfAtLeastForOneReturnIsTrue() {
    when(heap.remove(isNotNull())).thenReturn(false);
    when(heap.remove((T) "4")).thenReturn(true);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList("11", "2", "3", "4");
    assertTrue(heap.removeAll(iterable));
  }

  @Test
  void containsAll_whenNullIterable_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> heap.containsAll(null));
  }

  @Test
  void containsAll_whenNotNull_shouldCallContainsForEachElementUntilFalseIsReturned() {
    ArgumentCaptor<T> captor = (ArgumentCaptor<T>) ArgumentCaptor.forClass(Integer.class);
    when(heap.contains(any())).thenReturn(true);
    when(heap.contains((T) (Integer) 5)).thenReturn(false);
    Iterable<T> iterable = (Iterable<T>) Arrays.asList(1, 2, null, 4, 5);
    heap.containsAll(iterable);
    verify(heap, times(5)).contains(captor.capture());
    assertEquals(Arrays.asList(1, 2, null, 4, 5), captor.getAllValues());
  }

  @Test
  void containsAll_whenContainsReturnsTrueForEach_shouldReturnTrue() {
    when(heap.contains(any())).thenReturn(true);
    assertTrue(heap.containsAll((Iterable<T>) Arrays.asList(1, 2, 3, 4, 5, 6, null)));
  }

  @Test
  void containsAll_whenContainsReturnsAtLeastOneFalse_shouldReturnFalse() {
    when(heap.contains(any())).thenReturn(true);
    when(heap.contains((T) (Integer) 6)).thenReturn(false);
    assertFalse(heap.containsAll((Iterable<T>) Arrays.asList(1, 2, 3, 4, 5, 6, null)));
  }
}