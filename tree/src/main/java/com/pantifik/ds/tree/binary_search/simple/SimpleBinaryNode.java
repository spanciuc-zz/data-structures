package com.pantifik.ds.tree.binary_search.simple;

import com.pantifik.ds.tree.binary_search.BinaryNode;
import java.util.Objects;

class SimpleBinaryNode<T extends Comparable<T>> implements BinaryNode<T> {

  private T data;
  private SimpleBinaryNode<T> left;
  private SimpleBinaryNode<T> right;

  public SimpleBinaryNode(T elem) {
    Objects.requireNonNull(elem);
    data = elem;
    left = right = null;
  }

  @Override
  public T getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param data
   *     the data.
   */
  public void setData(T data) {
    this.data = data;
  }

  @Override
  public SimpleBinaryNode<T> getLeft() {
    return left;
  }

  /**
   * Sets the left node.
   *
   * @param left
   *     the node.
   */
  public void setLeft(SimpleBinaryNode<T> left) {
    this.left = left;
  }

  @Override
  public SimpleBinaryNode<T> getRight() {
    return right;
  }

  /**
   * Sets the right child node.
   *
   * @param right
   *     the node.
   */
  public void setRight(SimpleBinaryNode<T> right) {
    this.right = right;
  }
}
