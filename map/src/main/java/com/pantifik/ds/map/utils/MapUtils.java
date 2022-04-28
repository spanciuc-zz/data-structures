package com.pantifik.ds.map.utils;

public class MapUtils {

  private MapUtils() {}

  public static void validateCapacity(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException("The capacity must not be negative");
    }
  }

  public static void validateLoadFactor(float loadFactor) {
    if (loadFactor < 0.1f || loadFactor > 1f) {
      throw new IllegalArgumentException(
          "The load factor must be in range [0.1, 1]");
    }
  }

  public static int calculateThreshold(int capacity, float loadFactor) {
    return (int) (capacity * loadFactor);
  }

  public static int calculateMinCapacity(int threshold, float loadFactor) {
    return (int) Math.ceil((threshold / loadFactor));
  }


}
