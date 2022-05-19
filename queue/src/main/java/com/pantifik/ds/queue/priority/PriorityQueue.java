package com.pantifik.ds.queue.priority;

import com.pantifik.ds.queue.Queue;
import com.pantifik.ds.tree.heap.Heap;
import com.pantifik.ds.tree.heap.binary.BinaryHeap;
import java.util.Objects;

public class PriorityQueue<E extends Comparable<E>> implements Queue<E> {

  private final Heap<E> heap;

  public PriorityQueue() {
    heap = new BinaryHeap<>();
  }

  @Override
  public int size() {
    return heap.size();
  }

  @Override
  public boolean isEmpty() {
    return heap.size() == 0;
  }

  /**
   * {@inheritDoc}
   *
   * @throws NullPointerException
   *     if the element is null.
   */
  @Override
  public void add(E elem) {
    Objects.requireNonNull(elem);
    heap.add(elem);
  }

  @Override
  public E element() {
    return heap.peek()
        .orElseThrow();
  }

  @Override
  public E peek() {
    return heap.peek()
        .orElse(null);
  }

  @Override
  public E poll() {
    return heap.getElement()
        .orElse(null);
  }

  @Override
  public E remove() {
    return heap.getElement()
        .orElseThrow();
  }

  @Override
  public void clear() {
    heap.clear();
  }

  /**
   * Check if the queue contains an element.
   *
   * @param elem
   *     the element to check
   * @return true if the element is present, otherwise - false.
   */
  boolean contains(E elem) {
    return heap.contains(elem);
  }
}
