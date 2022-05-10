package com.pantifik.ds.map.open_addressing;

/**
 * Defines the probing function for the hash map.
 */
public interface Probing {

  /**
   * Applies probing function for current number of probe.
   *
   * @param x
   *     the current probe.
   * @return the probe result;
   */
  int probe(int x);

  /**
   * Calculates the next valid capacity required for probing function to work correctly.
   *
   * @param currentCapacity
   *     the current capacity.
   * @return the valid capacity.
   */
  int nextValidCapacity(int currentCapacity);
}
