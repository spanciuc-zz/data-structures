package com.pantifik.ds.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.map.separate_chaining.SeparateChainingHashMap;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractMapTest {

  protected Map<Object, Object> map;

  protected abstract Map<Object, Object> createMapInstance();

  @BeforeEach
  void startUp() {
    map = createMapInstance();
  }

  @Test
  void size_whenEmpty_shouldReturnZero() {
    assertEquals(0, map.size());
  }

  @Test
  void size_whenNotEmpty_shouldReturnNumberOfElements() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    assertEquals(4, map.size());
  }

  @Test
  void isEmpty_whenEmpty_shouldReturnTrue() {
    assertTrue(map.isEmpty());
  }

  @Test
  void isEmpty_whenNotEmpty_shouldReturnFalse() {
    map.put(1, 1);
    assertFalse(map.isEmpty());
  }

  @Test
  void put_whenNullKey_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> map.put(null, 1));
  }

  @Test
  void put_whenKeyNotPresent_shouldAddElementAndReturnNull() {
    assertNull(map.put(1, "first"));
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue("first"));
    assertEquals("first", map.get(1));
  }

  @Test
  void put_whenKeyIsPresent_shouldReplaceValueAndReturnReplaced() {
    map.put(1, "first");
    assertEquals("first", map.put(1, "second"));
    assertEquals("second", map.get(1));
    assertFalse(map.containsValue("first"));
  }

  @Test
  void putAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> map.putAll(null));
  }

  @Test
  void putAll_whenThisMapIsEmpty_shouldAddAllEntries() {
    Map<Object, Object> targetMap = Map.of(1, "first", 2, "second", 3, "third");
    map.putAll(targetMap);
    assertEquals(map, targetMap);
  }

  @Test
  void get_whenNullKey_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> map.get(null));
  }

  @Test
  void get_whenKeyPresent_shouldReturnValueAtKey() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    assertEquals("first", map.get(1));
    assertEquals("second", map.get(2));
    assertEquals("third", map.get(3));
  }

  @Test
  void get_whenKeyNotPresent_shouldReturnNull() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    assertNull(map.get(4));
  }

  @Test
  void containsKey_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> map.containsKey(null));
  }

  @Test
  void containsKey_whenContains_shouldReturnTrue() {
    map.put(1, new Object());
    assertTrue(map.containsKey(1));
  }

  @Test
  void containsKey_whenDoesNotContain_shouldReturnFalse() {
    map.put(1, new Object());
    assertFalse(map.containsKey(2));
  }

  @Test
  void containsValue_whenNullAndPresent_shouldReturnTrue() {
    map.put(1, null);
    assertTrue(map.containsValue(null));
  }

  @Test
  void containsValue_whenNullAndNotPresent_shouldReturnTrue() {
    map.put(1, 1);
    map.put(1, 2);
    assertFalse(map.containsValue(null));
  }

  @Test
  void containsValue_whenContains_shouldReturnTrue() {
    Object value = new Object();
    map.put(1, value);
    assertTrue(map.containsValue(value));
  }

  @Test
  void containsValue_whenDoesNotContain_shouldReturnFalse() {
    map.put(1, new Object());
    assertFalse(map.containsValue(2));
  }

  @Test
  void remove_whenNullKey_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> map.remove(null));
  }

  @Test
  void remove_whenKeyPresent_shouldRemoveEntryAndReturnRemovedValue() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    assertEquals(2, map.remove(2));
    assertEquals(2, map.size());
    assertFalse(map.containsKey(2));
    assertFalse(map.containsValue(2));
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue(1));
    assertTrue(map.containsKey(3));
    assertTrue(map.containsValue(3));
  }

  @Test
  void remove_whenKeyNotPresent_shouldNotChangeMapAndReturnNull() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    assertNull(map.remove(-1));
    assertEquals(3, map.size());
    assertTrue(map.containsKey(2));
    assertTrue(map.containsValue(2));
    assertTrue(map.containsKey(1));
    assertTrue(map.containsValue(1));
    assertTrue(map.containsKey(3));
    assertTrue(map.containsValue(3));
  }

  @Test
  void clear_shouldEmptyTheMap() {
    map = new SeparateChainingHashMap<>(0, 0.85f);
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    map.put(5, 5);
    map.put(6, 6);
    map.clear();
    assertTrue(map.isEmpty());
  }

  @Test
  void entrySet_whenEmpty_shouldReturnEmptySet() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    map.put(5, 5);
    map.put(6, 6);
    map.clear();
    assertTrue(map.entrySet()
        .isEmpty());
  }

  @Test
  void entrySet_whenNotEmpty_shouldReturnSetOfAllEntries() {
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    Set<Map.Entry<Object, Object>> entrySet = map.entrySet();
    assertEquals(map.size(), entrySet.size());
    assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(2, 2)));
    assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(3, 3)));
    assertTrue(entrySet.contains(new AbstractMap.SimpleEntry<>(4, 4)));
  }

  @Test
  void keySet_whenEmpty_shouldReturnKeySet() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    map.put(5, 5);
    map.put(6, 6);
    map.clear();
    assertTrue(map.keySet()
        .isEmpty());
  }

  @Test
  void keySet_whenNotEmpty_shouldReturnSetOfAllKeys() {
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    var keySet = map.keySet();
    assertEquals(map.size(), keySet.size());
    assertTrue(keySet.contains(2));
    assertTrue(keySet.contains(3));
    assertTrue(keySet.contains(4));
  }

  @Test
  void values_whenEmpty_shouldReturnEmpty() {
    map.put(1, 1);
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    map.put(5, 5);
    map.put(6, 6);
    map.clear();
    assertTrue(map.values()
        .isEmpty());
  }

  @Test
  void values_whenNotEmpty_shouldReturnCollectionOfValues() {
    map.put(2, 2);
    map.put(3, 3);
    map.put(4, 4);
    var values = map.values();
    assertEquals(map.size(), values.size());
    assertTrue(values.contains(2));
    assertTrue(values.contains(3));
    assertTrue(values.contains(4));
  }

  @Test
  void toString_shouldReturnStringRepresentation() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    map.put(4, "fourth");
    var expectedSet = new HashSet<>(
        List.of("1:first", "2:second", "3:third", "4:fourth"));
    var actualString = map.toString();
    var actualSet = new HashSet<>(Arrays.asList(
        actualString.substring(1, actualString.length() - 1)
            .split(", ")));
    assertEquals(expectedSet, actualSet);
  }

  @Test
  void equals_whenSameRef_shouldReturnTrue() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = map;
    assertEquals(map, hashMap);
  }

  @Test
  void equals_whenDifferentSizes_shouldReturnFalse() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = new SeparateChainingHashMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    assertNotEquals(map, hashMap);
  }

  @Test
  void equals_whenSameSizeAndDifferentEntries_shouldReturnFalse() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = new SeparateChainingHashMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    hashMap.put(4, "third");
    assertNotEquals(map, hashMap);
  }

  @Test
  void equals_whenSameSizeAndEntries_shouldReturnTrue() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = new SeparateChainingHashMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    hashMap.put(3, "third");
    assertEquals(map, hashMap);
  }

  @Test
  void hashCode_whenSameEntries_shouldReturnSameHashCode() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = new SeparateChainingHashMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    hashMap.put(3, "third");
    assertEquals(map.hashCode(), hashMap.hashCode());
  }

  @Test
  void hashCode_whenDifferentEntries_shouldReturnDifferentHashCode() {
    map.put(1, "first");
    map.put(2, "second");
    map.put(3, "third");
    var hashMap = new SeparateChainingHashMap<>();
    hashMap.put(1, "first");
    hashMap.put(2, "second");
    hashMap.put(4, "third");
    assertNotEquals(map.hashCode(), hashMap.hashCode());
  }
}