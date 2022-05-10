package com.pantifik.ds.map.open_addressing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class GCDTest {

  @ParameterizedTest
  @CsvSource({"-1, 2", "5, 0", "0, -7"})
  void findFor_whenDividendOrDivisorIsLessThanOne_shouldThrowException(int dividend, int divisor) {
    assertThrows(IllegalArgumentException.class, () -> GCD.findFor(dividend, divisor));
  }

  @ParameterizedTest
  @MethodSource("findForProvider")
  void findFor_whenValidParams_shouldReturnGCD(int expected, int value1, int value2) {
    assertEquals(expected, GCD.findFor(value1, value2));
  }

  private static Stream<Arguments> findForProvider() {
    return Stream.of(
        Arguments.of(1, 1, 1),
        Arguments.of(1, 2, 1),
        Arguments.of(1, 3, 1),
        Arguments.of(1, 3, 2),
        Arguments.of(5, 5, 5),
        Arguments.of(5, 5, 25),
        Arguments.of(9, 9, 126),
        Arguments.of(3, 6, 9)
    );
  }


}