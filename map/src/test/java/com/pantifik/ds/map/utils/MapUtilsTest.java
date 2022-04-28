package com.pantifik.ds.map.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class MapUtilsTest {

  @ParameterizedTest
  @ValueSource(ints = {-100, -10, -1})
  void validateCapacity_whenNegative_shouldThrowException(int capacity) {
    assertThrows(IllegalArgumentException.class,
        () -> MapUtils.validateCapacity(capacity));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 4, 100, 1000})
  void validateCapacity_whenNotNegative_shouldNotThrowException(int capacity) {
    assertDoesNotThrow(() -> MapUtils.validateCapacity(capacity));
  }

  @ParameterizedTest
  @ValueSource(floats = {0f, 1.1f, -11f, 100f})
  void validateLoadFactor_whenNotInRange_shouldThrowException(
      float loadFactor) {
    assertThrows(IllegalArgumentException.class,
        () -> MapUtils.validateLoadFactor(loadFactor));
  }

  @ParameterizedTest
  @ValueSource(floats = {0.1f, 1f, 0.5f, 0.33f, 0.9f})
  void validateLoadFactor_whenInRange_shouldNotThrowException(
      float loadFactor) {
    assertDoesNotThrow(() -> MapUtils.validateLoadFactor(loadFactor));
  }

  @ParameterizedTest
  @MethodSource("calculateThresholdProvider")
  void calculateThreshold_shouldCalculateThresholdValue(int expected,
      int capacity, float loadFactor) {
    assertEquals(expected, MapUtils.calculateThreshold(capacity, loadFactor));
  }

  @ParameterizedTest
  @MethodSource("calculateMinCapacityProvider")
  void calculateMinCapacity_shouldCalculateMinCapacityBasedOnParams(
      int expected, int threshold, float loadFactor) {
    assertEquals(expected,
        MapUtils.calculateMinCapacity(threshold, loadFactor));
  }

  private static Stream<Arguments> calculateMinCapacityProvider() {
    return Stream.of(Arguments.of(10, 1, 0.1f),
        Arguments.of(1, 1, 1f),
        Arguments.of(2, 1, 0.5f),
        Arguments.of(3, 1, 0.4f),
        Arguments.of(5, 1, 0.23f),
        Arguments.of(2, 1, 0.75f),
        Arguments.of(2, 1, 0.99f));
  }

  private static Stream<Arguments> calculateThresholdProvider() {
    return Stream.of(Arguments.of(0, 0, 0.1f), Arguments.of(0, 0, 1.0f),
        Arguments.of(0, 1, 0.2f), Arguments.of(1, 1, 1.0f),
        Arguments.of(1, 2, 0.5f), Arguments.of(9, 13, 0.75f));
  }
}