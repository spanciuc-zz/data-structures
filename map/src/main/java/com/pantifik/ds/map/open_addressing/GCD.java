package com.pantifik.ds.map.open_addressing;

import static com.pantifik.ds.map.utils.MapUtils.requireGreaterThan;

/**
 * A class that provides methods to find the greatest common denominator.
 */
public class GCD {

  private GCD() {}

  /**
   * Finds the GCD for 2 given numbers.
   *
   * @param value1
   *     the first number.
   * @param value2
   *     the second number.
   * @return the greatest common denominator.
   *
   * @throws IllegalArgumentException
   *     if at least one of the numbers is less than 1.
   */
  public static int findFor(int value1, int value2) {
    requireGreaterThan(0, value1);
    requireGreaterThan(0, value2);
    for (var i = Math.min(value1, value2); i > 1; i--) {
      if (isDividedExactly(value1, i) && isDividedExactly(value2, i)) {
        return i;
      }
    }
    return 1;
  }

  private static boolean isDividedExactly(int dividend, int division) {
    return dividend % division == 0;
  }

}
