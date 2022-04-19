package com.pantifik.ds.stack;

/**
 * Represents a last-in-first-out (LIFO) stack of objects.
 *
 * @param <E>
 *     the type of the objects.
 */
public interface Stack<E> {

  /**
   * Gets the size of the stack.
   *
   * @return the number of element in the stack.
   */
  int size();

  /**
   * Check if the stack is empty.
   *
   * @return true if it is empty, otherwise - false.
   */
  boolean isEmpty();

  /**
   * Removes all the elements in the stack.
   */
  void clear();

  /**
   * Gets the top element without removing from the stack.
   *
   * @return the top element of the stack.
   *
   * @throws java.util.EmptyStackException
   *     if stack is empty.
   */
  E peek();

  /**
   * Removes the top element of the stack and returns it.
   *
   * @return the removed element.
   *
   * @throws java.util.EmptyStackException
   *     if stack is empty.
   */
  E pop();

  /**
   * Adds the element to the top of the stack.
   *
   * @param elem
   *     - the element to add.
   */
  void push(E elem);

}
