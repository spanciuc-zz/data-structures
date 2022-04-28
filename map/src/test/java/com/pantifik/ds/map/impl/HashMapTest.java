package com.pantifik.ds.map.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.map.AbstractMapTest;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HashMapTest extends AbstractMapTest {

  @Override
  protected Map<Object, Object> createMapInstance() {
    return new SeparateChainingHashMap<>();
  }

  @Test
  void defaultConstructor_shouldCreateEmptyMap() {
    assertTrue(map.isEmpty());
  }

  @Test
  void constructorWithCapacity_whenInvalidCapacity_shouldThrowException() {
    assertThrows(IllegalArgumentException.class, () -> new SeparateChainingHashMap<>(-1));
  }

  @Test
  void constructorWithCapacity_whenValidCapacity_shouldCreateEmptyMap() {
    map = new SeparateChainingHashMap<>(10);
    assertTrue(map.isEmpty());
  }

  @Test
  void constructorWithCapacityAndLoadFactor_whenValidParams_shouldCreateEmptyMap() {
    map = new SeparateChainingHashMap<>(10, 0.5f);
    assertTrue(map.isEmpty());
  }

  @ParameterizedTest
  @CsvSource({"-1, -0.1f", "-1, 1.1f", "-1, 0.5f", "10, 1.1f"})
  void constructorCapacityLoadFactor_whenInvalidParams_shouldThrowException(
      int capacity, float loadFactor) {
    assertThrows(IllegalArgumentException.class,
        () -> new SeparateChainingHashMap<>(capacity, loadFactor));
  }

  @Test
  void put_whenCapacityExceeded_shouldResizeTable() {
    map = new SeparateChainingHashMap<>(0, 0.5f);
    map.put(1, "1");
    map.put(2, "2");
    map.put(3, "3");
    map.put(4, "4");
    map.put(5, "5");
    map.put(6, "6");
    map.put(7, "7");
    map.put(8, "8");
    map.put(9, "9");
    map.put(1f, "10");
    assertEquals(10, map.size());
  }
}