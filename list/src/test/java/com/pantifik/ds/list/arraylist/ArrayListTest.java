package com.pantifik.ds.list.arraylist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.list.AbstractListTest;
import java.util.List;
import org.junit.jupiter.api.Test;

class ArrayListTest extends AbstractListTest {

  @Override
  protected List<Object> createListInstance() {
    return new ArrayList<>();
  }

  @Test
  void defaultConstructor_shouldCreateEmptyArrayList() {
    ArrayList<Integer> actual = new ArrayList<>();
    assertTrue(actual.isEmpty());
  }

  @Test
  void constructorWithInitialCapacity_whenNegativeCapacity_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new ArrayList<>(-1));
  }

  @Test
  void constructorWithInitialCapacity_shouldCreateEmptyArrayList() {
    ArrayList<Integer> actual = new ArrayList<>();
    assertTrue(actual.isEmpty());
  }

  @Test
  void constructorWithIterable_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> new ArrayList<>(null));
  }

  @Test
  void constructorWithIterable_shouldCreateArrayListWithElementFromCollection() {
    List<Object> iterable = List.of("1", 2, '3', 4.4);
    ArrayList<Object> arrayList = new ArrayList<>(iterable);
    assertFalse(arrayList.isEmpty());
    assertEquals(iterable.size(), arrayList.size());
    assertEquals(0, arrayList.indexOf("1"));
    assertEquals(1, arrayList.indexOf(2));
    assertEquals(2, arrayList.indexOf('3'));
    assertEquals(3, arrayList.indexOf(4.4));
  }

  @Test
  void toString_shouldReturnStringRepresentation() {
    String expected = "[1, 3, 6, null, abc]";
    list.add(1);
    list.add(3);
    list.add(6);
    list.add(null);
    list.add("abc");
    assertEquals(expected, list.toString());
  }

  @Test
  void add_whenCapacityZero_shouldResizeAndAddElement() {
    list = new ArrayList<>(0);
    assertTrue(list.add(6));
    assertEquals(1, list.size());
    assertTrue(list.contains(6));
  }

  @Test
  void add_whenCapacityExceeded_shouldResizeAndAddElement() {
    list = new ArrayList<>(5);
    list.addAll(List.of(1, 2, 3, 4));
    list.add(5);
    assertTrue(list.add(6));
    assertEquals(6, list.size());
    assertTrue(list.containsAll(List.of(1, 2, 3, 4, 5, 6)));
  }
}