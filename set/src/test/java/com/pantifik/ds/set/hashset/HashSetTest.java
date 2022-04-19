package com.pantifik.ds.set.hashset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.set.SetTest;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class HashSetTest extends SetTest {

  @Override
  protected Set<Object> createSetInstance() {
    return new HashSet<>();
  }

  @Test
  void constructor_whenNoArgs_shouldCreateEmptyHashSet() {
    assertTrue(set.isEmpty());
  }

  @Test
  void constructor_whenInvalidCapacity_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new HashSet<>(-1));
  }

  @Test
  void constructor_whenValidCapacity_shouldCreateEmptyHashSet() {
    set = new HashSet<>(5);
    assertTrue(set.isEmpty());
  }

  @ParameterizedTest
  @ValueSource(floats = {0.05f, 1.1f})
  void constructor_whenInvalidLoadFactor_shouldThrowException(
      float loadFactor) {
    assertThrows(IllegalArgumentException.class,
        () -> new HashSet<>(1, loadFactor));
  }

  @Test
  void constructor_whenValidLoadFactor_shouldCreateEmptyHashSet() {
    set = new HashSet<>(5, 0.5f);
    assertTrue(set.isEmpty());
  }

  @Test
  void constructor_whenNullCollection_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> new HashSet<>(null));
  }

  @Test
  void constructor_whenEmptyCollection_shouldCreateEmptyHashSet() {
    set = new HashSet<>(List.of());
    assertTrue(set.isEmpty());
  }

  @Test
  void constructor_whenNotEmptyCollection_shouldCreateWithElements() {
    Collection<Integer> collection = List.of(1, 2, 3, 4, 5);
    set = new HashSet<>(collection);
    assertEquals(collection.size(), set.size());
    assertTrue(set.containsAll(collection));
  }

  @Test
  void add_whenCapacityExceeded_shouldAddAndResize() {
    set = new HashSet<>(List.of(1, 2));
    assertTrue(set.add(3));
    assertTrue(set.add(10));
    assertTrue(set.add(-4));
    assertTrue(set.add(5));
    assertTrue(set.add(6));
    assertTrue(set.add(7));
    assertTrue(set.containsAll(Arrays.asList(1, 2, 3, -4, 5, 6, 7, 10)));
    assertEquals(8, set.size());
  }

}