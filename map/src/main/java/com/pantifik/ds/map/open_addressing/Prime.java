package com.pantifik.ds.map.open_addressing;

import com.pantifik.ds.map.utils.MapUtils;

/**
 * A class that provides methods to manipulate prime numbers.
 */
public class Prime {
  private Prime() {
  }

  /**
   * Checks if the given number is prime.
   *
   * @param number
   *     the number to check.
   * @return true if number is prime, otherwise - false.
   *
   * @throws IllegalArgumentException
   *     if the number is less than 2.
   */
  public static boolean isPrime(int number) {
    MapUtils.requireGreaterThan(1, number);
    for (int i = 2; i * i <= number; i++) {
      if (number % i == 0) {
        return false;
      }
    }
    return true;
  }
}
