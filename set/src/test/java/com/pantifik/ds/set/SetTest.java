package com.pantifik.ds.set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.set.hashset.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class SetTest {

  protected Set<Object> set;

  /**
   * Must be implemented and run before each test case in subclasses.
   */
  @BeforeEach
  protected void setUp() {
    this.set = createSetInstance();
  }

  protected abstract Set<Object> createSetInstance();

  @Test
  void size_shouldReturnNumberOfElements() {
    assertEquals(0, set.size());
    set.add(new Object());
    assertEquals(1, set.size());
    set.add(1);
    assertEquals(2, set.size());
    set.add(3f);
    assertEquals(3, set.size());
  }

  @Test
  void isEmpty_whenHasNoElements_shouldReturnTrue() {
    assertTrue(set.isEmpty());
  }

  @Test
  void isEmpty_whenHasElements_shouldReturnFalse() {
    set.addAll(List.of(1, 2, 3));
    assertFalse(set.isEmpty());
  }

  @Test
  void add_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> set.add(null));
  }

  @Test
  void add_whenNotPresent_shouldAddElementAndReturnTrue() {
    assertFalse(set.contains(1));
    assertTrue(set.add(1));
    assertTrue(set.contains(1));
  }

  @Test
  void add_whenPresent_shouldReturnFalse() {
    set.add(1);
    assertFalse(set.add(1));
    assertEquals(1, set.size());
  }

  @Test
  void addAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> set.addAll(null));
  }

  @Test
  void addAll_whenEmpty_shouldNotChangeSetAndReturnFalse() {
    set.addAll(List.of(1, 2, 3));
    assertFalse(set.addAll(List.of()));
    assertEquals(3, set.size());
    assertTrue(set.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void addAll_whenAtLeastOneNotPresent_shouldAddNotPresentAndReturnTrue() {
    set.add(1);
    set.add(2);
    set.add(3);
    assertTrue(set.addAll(List.of(1, 2, 4)));
    assertEquals(4, set.size());
    assertTrue(set.containsAll(List.of(1, 2, 3, 4)));
  }

  @Test
  void addAll_whenAllPresent_shouldAddAllAndReturnTrue() {
    set.add(1);
    set.add(2);
    set.add(3);
    assertTrue(set.addAll(List.of(4, 5, 6, 7)));
    assertEquals(7, set.size());
    assertTrue(set.containsAll(List.of(1, 3, 2, 4, 5, 6, 7)));
  }

  @Test
  void contains_whenPresent_shouldReturnTrue() {
    set.add(1);
    assertTrue(set.contains(1));
  }

  @Test
  void contains_whenNotPresent_shouldReturnFalse() {
    set.add(1);
    set.add(2);
    set.add(3);
    assertFalse(set.contains(4));
  }

  @Test
  void containsAll_whenAllPresent_shouldReturnTrue() {
    set.addAll(List.of(1, 2, 3, 4, 5, 6));
    assertTrue(set.containsAll(List.of(2, 4, 6)));
  }

  @Test
  void containsAll_whenAtLeastOneNotPresent_shouldReturnFalse() {
    set.addAll(List.of(1, 2, 3, 4, 5, 6));
    assertFalse(set.containsAll(List.of(2, 4, 6, 7)));
  }

  @Test
  void remove_whenNotPresent_shouldNotChangeSetAndReturnFalse() {
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    assertFalse(set.remove(new Object()));
    assertTrue(set.containsAll(List.of(1, 2, 3, 4)));
    assertEquals(4, set.size());
  }

  @Test
  void remove_whenPresent_shouldRemoveElementAndReturnTrue() {
    set.add(1);
    set.add(2);
    set.add(3);
    set.add(4);
    assertTrue(set.contains(3));
    assertTrue(set.remove(3));
    assertFalse(set.contains(3));
    assertTrue(set.containsAll(List.of(1, 2, 4)));
    assertEquals(3, set.size());
  }

  @Test
  void removeAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> set.removeAll(null));
  }

  @Test
  void removeAll_whenEmpty_shouldNotChangeSetAndReturnFalse() {
    set.add(1);
    set.add(2);
    set.add(3);
    assertFalse(set.removeAll(List.of()));
    assertEquals(3, set.size());
    assertTrue(set.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void removeAll_whenAllNotPresent_shouldNotChangeSetAndReturnFalse() {
    set.addAll(List.of(1, 2, 3));
    assertFalse(set.removeAll(List.of(6, 5, 8)));
    assertEquals(3, set.size());
    assertTrue(set.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void removeAll_whenAtLeastOnePresent_shouldRemovePresentAndReturnTrue() {
    set.addAll(List.of(1, 2, 3, 4));
    assertTrue(set.removeAll(List.of(6, 2, 8, 4)));
    assertEquals(2, set.size());
    assertTrue(set.containsAll(List.of(1, 3)));
    assertFalse(set.contains(2));
    assertFalse(set.contains(4));
  }

  @Test
  void removeAll_whenAllPresent_shouldRemoveAllAndReturnTrue() {
    set.addAll(List.of(1, 2, 3, 4));
    assertTrue(set.removeAll(List.of(1, 3, 4)));
    assertEquals(1, set.size());
    assertTrue(set.contains(2));
  }

  @Test
  void clear_shouldRemoveAllElements() {
    set.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
    set.clear();
    assertTrue(set.isEmpty());
  }

  @Test
  void iterator_whenEmptySetAndNextCalled_shouldThrowException() {
    Iterator<Object> iterator = set.iterator();
    assertFalse(iterator.hasNext());
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void iterator_whenHasNoNextAndNextCalled_shouldThrowException() {
    set.addAll(List.of(1, 2, 3, 4, 5, 7));
    Iterator<Object> iterator = set.iterator();
    while (iterator.hasNext()) {
      iterator.next();
    }
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void iterator_shouldAllowIterationOverAllElements() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    Iterator<Object> iterator = set.iterator();
    List<Object> list = new ArrayList<>();
    while (iterator.hasNext()) {
      list.add(iterator.next());
    }
    assertEquals(list.size(), set.size());
    assertTrue(set.containsAll(list));
  }

  @Test
  void iterator_whenRemoveWhileIterating_shouldThrowException() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    assertThrows(ConcurrentModificationException.class, () -> {
      for (Object o : set) {
        set.remove(o);
      }
    });
  }

  @Test
  void iterator_whenCallsRemoveWithoutNext_shouldThrowException() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    Iterator<Object> iterator = set.iterator();
    assertThrows(IllegalStateException.class, iterator::remove);
  }

  @Test
  void iterator_whenTwoTimesCallsRemoveWithoutNext_shouldThrowException() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    Iterator<Object> iterator = set.iterator();
    iterator.next();
    iterator.remove();
    assertThrows(IllegalStateException.class, iterator::remove);
  }

  @Test
  void iterator_whenCallsRemove_shouldRemoveElement() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    Iterator<Object> iterator = set.iterator();
    iterator.next();
    iterator.remove();
    assertEquals(7, set.size());
    assertFalse(set.contains(0));
    assertTrue(set.containsAll(List.of(1, 2, 3, 4, 5, 7, 1f)));
  }

  @Test
  void iterator_whenMultipleCallsRemove_shouldRemoveElements() {
    set.addAll(Arrays.asList(0, 1, 2, 3, 4, 5, 7, 1f));
    Iterator<Object> iterator = set.iterator();
    List<Object> toRemove = List.of(1, 3, 5, 1f);
    while (iterator.hasNext()) {
      Object elem = iterator.next();
      if (toRemove.contains(elem)) {
        iterator.remove();
      }
    }
    assertEquals(4, set.size());
    assertFalse(set.contains(1));
    assertFalse(set.contains(3));
    assertFalse(set.contains(5));
    assertFalse(set.contains(1f));
    assertTrue(set.containsAll(List.of(0, 2, 4, 7)));
  }

  @Test
  void retainAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> set.retainAll(null));
  }

  @Test
  void retainAll_whenEmpty_shouldRemoveAllElementsAndReturnTrue() {
    set.addAll(List.of(1, 2, 3));
    assertTrue(set.retainAll(List.of()));
    assertEquals(0, set.size());
    assertTrue(set.isEmpty());
  }

  @Test
  void retainAll_whenSameAsSet_shouldNotChangeSetAndReturnFalse() {
    set.addAll(List.of(1, 2, 3));
    assertFalse(set.retainAll(List.of(1, 2, 3)));
    assertEquals(3, set.size());
    assertTrue(set.containsAll(List.of(1, 2, 3)));
  }

  @Test
  void retainAll_whenAtLeastOneIntersection_shouldRemoveFromSetAndReturnTrue() {
    set.addAll(List.of(1, 2, 3, 4, 5, 100));
    assertTrue(set.retainAll(List.of(2, 4, 100)));
    assertEquals(3, set.size());
    assertTrue(set.containsAll(List.of(100, 4, 2)));
    assertFalse(set.contains(1));
    assertFalse(set.contains(3));
    assertFalse(set.contains(5));
  }

  @Test
  void toArray_whenEmpty_shouldReturnEmptyArray() {
    assertEquals(0, set.toArray().length);
  }

  @Test
  void toArray_whenNotEmpty_shouldReturnArrayWithElementsFromSet() {
    set.addAll(List.of(1, 2, 3, 4, 5, 0));
    Object[] array = set.toArray();
    assertEquals(6, array.length);
    for (Object o : array) {
      assertTrue(set.contains(o));
    }
  }

  @Test
  void toTypedArray_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class,
        () -> set.toArray((Integer[]) null));
  }

  @Test
  void toTypedArray_whenEmpty_shouldGivenArray() {
    Integer[] array = new Integer[4];
    assertEquals(array, set.toArray(array));
  }

  @Test
  void toTypedArray_whenSizeIsLessThanSetsSize_shouldReturnNewArrayWithElements() {
    Set<Integer> integers = new HashSet<>(List.of(1, 2, 3, 4, 5, 0));
    Integer[] expected = new Integer[3];
    Integer[] actual = integers.toArray(expected);
    assertEquals(6, actual.length);
    assertNotEquals(expected, actual);
    for (Integer integer : actual) {
      assertTrue(integers.contains(integer));
    }
  }

  @Test
  void toTypedArray_whenSizeIsEnough_shouldReturnSameArrayWithElements() {
    Set<Integer> integers = new HashSet<>(List.of(1, 2, 3, 4, 5, 0));
    Integer[] expected = new Integer[6];
    Integer[] actual = integers.toArray(expected);
    assertEquals(6, actual.length);
    assertEquals(expected, actual);
    for (Integer integer : actual) {
      assertTrue(integers.contains(integer));
    }
  }

}
