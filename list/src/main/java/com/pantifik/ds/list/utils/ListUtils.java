package com.pantifik.ds.list.utils;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Provides useful methods working with lists.
 */
public class ListUtils {
  private ListUtils() {}

  /**
   * Returns appropriate equals check predicate considering null parameter.
   * <p>
   * If the object is null, then returns null check predicate.
   *
   * @param obj
   *     - the object for which to check equality.
   * @return - the appropriate predicate.
   */
  public static Predicate<Object> resolveEqualsPredicate(Object obj) {
    if (Objects.isNull(obj)) {
      return Objects::isNull;
    } else {
      return obj::equals;
    }
  }

  /**
   * Throws exception if given index is not in range.
   *
   * @param index
   *     - the index to check.
   * @param length
   *     - the max valid length.
   * @throws IndexOutOfBoundsException
   *     if index is not in range [0, length].
   */
  public static void checkIndexInclusiveLength(int index, int length) {
    if (index < 0 || index > length) {
      throw new IndexOutOfBoundsException(index);
    }
  }
}
