package com.pantifik.ds.map.open_addressing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PrimeTest {

  @ParameterizedTest
  @ValueSource(ints = {-10, -1, 0, 1})
  void isPrime_whenNumberIsLessOrEqualsToZero_shouldThrowException(int number) {
    assertThrows(IllegalArgumentException.class, () -> Prime.isPrime(number));
  }

  @ParameterizedTest
  @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23})
  void isPrime_whenNumberIsPrime_shouldReturnTrue(int number) {
    assertTrue(Prime.isPrime(number));
  }

  @ParameterizedTest
  @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25})
  void isPrime_whenNumberIsNotPrime_shouldReturnFalse(int number) {
    assertFalse(Prime.isPrime(number));
  }
}