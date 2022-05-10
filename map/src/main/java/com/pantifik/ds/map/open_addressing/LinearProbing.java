package com.pantifik.ds.map.open_addressing;

import static com.pantifik.ds.map.utils.MapUtils.requireGreaterThan;
import com.pantifik.ds.map.utils.MapUtils;

/**
 * A class that represent a linear probing method.
 */
public class LinearProbing implements Probing {

  private final int constant;

  /**
   * Creates the probing instance doing some validation.
   *
   * @param constant
   *     the constant factor of the linear function.
   * @param capacity
   *     the capacity of the hash map.
   * @throws IllegalArgumentException
   *     if the GCD of the constant and capacity are not prime between themselves.
   */
  public LinearProbing(int constant, int capacity) {
    if (GCD.findFor(constant, capacity) != 1) {
      throw new IllegalArgumentException("The GCD must be 1");
    }
    this.constant = constant;
  }

  /**
   * Probes linear function based on the input.
   *
   * @param x
   *     the number of probing.
   * @return the linear function result.
   */
  @Override
  public int probe(int x) {
    requireGreaterThan(-1, x);
    return constant * x;
  }

  /**
   * Finds the next valid capacity for the current probing function.
   *
   * @param currentCapacity
   *     current capacity.
   * @return the next valid capacity.
   *
   * @throws IllegalArgumentException
   *     if the current capacity is less than 2.
   */
  @Override
  public int nextValidCapacity(int currentCapacity) {
    MapUtils.requireGreaterThan(1, currentCapacity);
    int capacity = currentCapacity;
    while (!Prime.isPrime(capacity)) {
      capacity++;
    }
    return capacity;
  }

  int getConstant() {
    return constant;
  }
}
