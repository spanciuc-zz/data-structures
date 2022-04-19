package com.pantifik.ds.stack.impl;

import com.pantifik.ds.stack.Stack;
import java.util.EmptyStackException;

/**
 * A linked list based stack representation..
 *
 * @param <E>
 *     the type of the elements in the stack.
 */
public class StackImpl<E> implements Stack<E> {

  private int size;

  private Node<E> top;

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public void clear() {
    while (top != null) {
      removeTop();
    }
  }

  @Override
  public E peek() {
    throwExceptionIfEmpty();
    return top.data;
  }

  @Override
  public E pop() {
    throwExceptionIfEmpty();
    return removeTop();
  }

  @Override
  public void push(E elem) {
    top = new Node<>(elem, top);
    size++;
  }

  private void throwExceptionIfEmpty() {
    if (isEmpty()) {
      throw new EmptyStackException();
    }
  }

  private E removeTop() {
    Node<E> oldTop = top;
    E data = oldTop.data;
    top = top.next;
    oldTop.data = null;
    oldTop.next = null;
    size--;
    return data;
  }

  private static class Node<E> {
    private E data;
    private Node<E> next;

    public Node(E data, Node<E> next) {
      this.data = data;
      this.next = next;
    }
  }
}
