package com.pantifik.ds.map.open_addressing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class LinearProbingTest {

  @ParameterizedTest
  @CsvSource({"-1, 2", "3, -2", "-3, -5"})
  void constructor_whenNegativeParam_shouldThrowException(int constant, int capacity) {
    assertThrows(IllegalArgumentException.class, () -> new LinearProbing(constant, capacity));
  }

  @ParameterizedTest
  @CsvSource({"2, 4", "3, 9", "10, 100"})
  void constructor_whenGCDIsNotOne_shouldThrowException(int constant, int capacity) {
    IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
        () -> new LinearProbing(constant, capacity));
    assertEquals("The GCD must be 1", e.getMessage());
  }

  @Test
  void constructor_whenValidParams_shouldCreateLinearProbingInstance() {
    LinearProbing lp = new LinearProbing(5, 9);
    assertEquals(5, lp.getConstant());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, -2, -3})
  void probe_whenNegativeParam_shouldThrowException(int index) {
    LinearProbing lp = new LinearProbing(7, 13);
    assertThrows(IllegalArgumentException.class, () -> lp.probe(index));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2, 3, 4, 5, 100, 123})
  void probe_whenValidParams_shouldReturnCalculatedValue(int index) {
    int constant = 9;
    LinearProbing lp = new LinearProbing(constant, 17);
    int expected = index * constant;
    assertEquals(expected, lp.probe(index));
  }

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 0, 1})
  void nextValidCapacity_whenCurrentCapacityLessThan2_shouldThrowException(int capacity) {
    LinearProbing lp = new LinearProbing(7, 13);
    assertThrows(IllegalArgumentException.class, () -> lp.nextValidCapacity(capacity));
  }

  @ParameterizedTest
  @CsvSource({"13, 13", "5, 5", "24, 29", "4, 5"})
  void nextValidCapacity_whenValidParam_shouldReturnNextPrimeNumber(int capacity, int expected) {
    LinearProbing lp = new LinearProbing(7, 13);
    assertEquals(expected, lp.nextValidCapacity(capacity));
  }
}