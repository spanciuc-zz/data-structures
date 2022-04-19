package com.pantifik.ds.list.utils;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * Provides methods for list to array conversion.
 */
public class ListToArrayConverter {

  private ListToArrayConverter() {}

  /**
   * Copies the elements of a list to the given array.
   * <p>
   * If the given array's size is less than list size, a new array with the
   * necessary size is created
   *
   * @param list
   *     - the list to copy elements from.
   * @param array
   *     - the array to copy elements into.
   * @param <T>
   *     - type of the elements.
   * @return - the array with copied elements.
   *
   * @throws NullPointerException
   *     - if list or array is null.
   * @throws ArrayStoreException
   *     - if the runtime type of the specified array is not a supertype of the
   *     runtime type of every element in this list
   */
  @SuppressWarnings("unchecked")
  public static <T> T[] convert(List<?> list, T[] array) {
    Objects.requireNonNull(list);
    Objects.requireNonNull(array);
    array = ensureCapacity(list, array);
    listToArrayWithMapping(list, array, obj -> (T) obj);
    return array;
  }

  /**
   * Copies the elements of a list into an array.
   *
   * @param list
   *     the list to copy elements from.
   * @return the newly created array with copied elements.
   *
   * @throws NullPointerException
   *     - if the list is null.
   */
  public static Object[] convert(List<?> list) {
    Objects.requireNonNull(list);
    Object[] array = new Object[list.size()];
    listToArrayWithMapping(list, array, Function.identity());
    return array;
  }

  @SuppressWarnings("unchecked")
  private static <T> T[] ensureCapacity(List<?> list, T[] array) {
    if (array.length < list.size()) {
      array = (T[]) Array.newInstance(array.getClass()
          .getComponentType(), list.size());
    }
    return array;
  }

  private static <T> void listToArrayWithMapping(List<?> list, T[] array,
      Function<Object, T> mapper) {
    Iterator<?> iterator = list.iterator();
    int index = 0;
    while (iterator.hasNext()) {
      array[index++] = mapper.apply(iterator.next());
    }
  }
}
