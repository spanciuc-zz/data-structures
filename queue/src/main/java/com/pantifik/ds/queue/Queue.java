package com.pantifik.ds.queue;

public interface Queue<E> {

  /**
   * Gets the number of the elements in the queue.
   *
   * @return - the number of elements.
   */
  int size();

  /**
   * Checks if the queue is empty.
   *
   * @return true - if empty, false - otherwise.
   */
  boolean isEmpty();

  /**
   * Adds an element to the queue.
   *
   * @param elem
   *     the element to add.
   */
  void add(E elem);

  /**
   * Retrieves, but does not remove, the head of the queue.
   *
   * @return the head of the queue.
   *
   * @throws java.util.NoSuchElementException
   *     if the queue is empty.
   */
  E element();

  /**
   * Retrieves, but does not remove the head of the queue or null in casa the
   * queue is empty.
   *
   * @return the head of the queue, or null if the queue is empty.
   */
  E peek();

  /**
   * Removes and retrieves the head of the queue, or null if the queue is
   * empty.
   *
   * @return the head of the queue, or null if the queue is empty.
   */
  E poll();

  /**
   * Removes and retrieves the head of the queue, or throws exception if the
   * queue is empty.
   *
   * @return the head of the queue.
   *
   * @throws java.util.NoSuchElementException
   *     if the queue is empty.
   */
  E remove();

  /**
   * Removes all the elements in the queue.
   */
  void clear();

}
