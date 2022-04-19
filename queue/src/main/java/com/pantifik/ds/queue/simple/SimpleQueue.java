package com.pantifik.ds.queue.simple;

import com.pantifik.ds.queue.Queue;
import java.util.NoSuchElementException;

/**
 * An Linked List based queue implementation.
 *
 * @param <E>
 *     the type of the elements in the queue.
 */
public class SimpleQueue<E> implements Queue<E> {

  private int size;

  private Node<E> head;

  private Node<E> tail;

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void add(E elem) {
    if (isEmpty()) {
      addToEmptyQueue(elem);
    } else {
      addLast(elem);
    }
  }

  @Override
  public E element() {
    throwsIfEmpty();
    return getDataAtHead();
  }

  @Override
  public E peek() {
    return isEmpty() ? null : getDataAtHead();
  }

  @Override
  public E poll() {
    return isEmpty() ? null : removeFirst();
  }

  @Override
  public E remove() {
    throwsIfEmpty();
    return removeFirst();
  }

  @Override
  public void clear() {
    while (head != null) {
      removeFirst();
    }
  }

  private E removeFirst() {
    Node<E> oldHead = head;
    E data = head.data;
    head = head.next;
    oldHead.data = null;
    oldHead.next = null;
    size--;
    return data;
  }

  private E getDataAtHead() {
    return head.data;
  }

  private void throwsIfEmpty() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
  }

  private void addLast(E elem) {
    tail.next = new Node<>(elem);
    tail = tail.next;
    size++;
  }

  private void addToEmptyQueue(E elem) {
    head = tail = new Node<>(elem);
    size++;
  }

  private static class Node<E> {
    private E data;
    private Node<E> next;

    public Node(E data, Node<E> next) {
      this.data = data;
      this.next = next;
    }

    public Node(E elem) {
      this(elem, null);
    }
  }
}
