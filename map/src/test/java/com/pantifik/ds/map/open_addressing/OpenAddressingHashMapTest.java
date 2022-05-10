package com.pantifik.ds.map.open_addressing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.pantifik.ds.map.AbstractMapTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

abstract class OpenAddressingHashMapTest extends AbstractMapTest {

  @Override
  protected Map<Object, Object> createMapInstance() {
    return new OpenAddressingHashMap<>(createProbingInstance());
  }

  protected abstract ProbingType createProbingInstance();

  @Test
  void put_whenCapacityExceeded_shouldResizeTable() {
    map.put(1, "1");
    map.put(-2, "-2");
    map.put(2, "2");
    map.put(-3, "-3");
    map.put(3, "3");
    map.put(-4, "-4");
    map.put(4, "4");
    map.put(-5, "-5");
    map.put(5, "5");
    map.put(6, "6");
    map.put(7, "7");
    map.put(8, "8");
    map.put(9, "9");
    map.put(1f, "10");
    assertEquals(14, map.size());
  }
}