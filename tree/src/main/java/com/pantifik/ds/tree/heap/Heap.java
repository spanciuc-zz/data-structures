package com.pantifik.ds.tree.heap;

import java.util.Objects;
import java.util.Optional;

/**
 * Defines a heap data structure.
 *
 * @param <T>
 *     the type of the elements.
 */
public interface Heap<T extends Comparable<T>> {

  /**
   * Gets the number of elements in the heap.
   *
   * @return the number of elements.
   */
  int size();

  /**
   * Gets and removes the min or max element of this heap, depending on type of the heap. see {@link
   * Type}.
   * <p>
   * In case the heap is empty, an empty Optional is returned.
   *
   * @return an Optional of the max or min element if present, or empty Optional if the heap is
   *     empty.
   */
  Optional<T> getElement();

  /**
   * Gets but does not remove min or max element of this heap, depending on type of the heap. see
   * {@link Type}.
   *
   * <p>
   * In case the heap is empty, an empty Optional is returned.
   *
   * @return an Optional of the max or min element if present, or empty Optional if the heap is
   *     empty.
   */
  Optional<T> peek();

  /**
   * Gets the type of the heap. see {@link Type}.
   *
   * @return the type of the heap.
   */
  Type getType();

  /**
   * Converts this heap into given type. see {@link Type}.
   *
   * @param type
   *     the type to convert into.
   * @throws NullPointerException
   *     if the type is null.
   */
  void convertTo(Type type);

  /**
   * Adds an element to the heap.
   *
   * @param element
   *     the element to add.
   * @return true if the heap was modified, otherwise - false.
   *
   * @throws NullPointerException
   *     if the element is null.
   */
  @SuppressWarnings("SameReturnValue")
  boolean add(T element);

  /**
   * Adds the elements from an iterable to the heap.
   *
   * @param elements
   *     the source iterable.
   * @return true if the heap was modified, otherwise - false.
   *
   * @throws NullPointerException
   *     if the iterable is null.
   * @throws NullPointerException
   *     if the iterable contains null elements.
   */
  default boolean addAll(Iterable<T> elements) {
    Objects.requireNonNull(elements);
    boolean modified = false;
    for (var elem : elements) {
      if (add(elem) && !modified) {
        modified = true;
      }
    }
    return modified;
  }

  /**
   * Removes an element from heap.
   *
   * @param element
   *     the element to remove.
   * @return true if the heap was modified, otherwise - false.
   *
   * @throws NullPointerException
   *     if the element is null.
   */
  boolean remove(T element);

  /**
   * Removes from the heap the elements present in the iterable.
   *
   * @param elements
   *     the elements to be removed.
   * @return true if the heap was modified, otherwise - false.
   *
   * @throws NullPointerException
   *     if the iterable is null.
   * @throws NullPointerException
   *     if the iterable contains null elements.
   */
  default boolean removeAll(Iterable<T> elements) {
    Objects.requireNonNull(elements);
    boolean modified = false;
    for (var elem : elements) {
      if (remove(elem) && !modified) {
        modified = true;
      }
    }
    return modified;
  }

  /**
   * Clears the heap, by removing all the elements from it.
   */
  void clear();

  /**
   * Checks if the heap contains an element.
   *
   * @param element
   *     the element to search.
   * @return true - if the element is present in heap, otherwise - false.
   */
  boolean contains(T element);

  /**
   * Checks if the heap contains all the elements.
   *
   * @param elements
   *     the elements to check.
   * @return true if the heap contains all elements, otherwise - false.
   *
   * @throws NullPointerException
   *     if the elements iterable is null.
   */
  default boolean containsAll(Iterable<T> elements) {
    Objects.requireNonNull(elements);
    for (var elem : elements) {
      if (!contains(elem)) {
        return false;
      }
    }
    return true;
  }

}
