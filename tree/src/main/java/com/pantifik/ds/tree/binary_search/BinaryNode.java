package com.pantifik.ds.tree.binary_search;

/**
 * Defines a binary tree node.
 *
 * @param <T>
 *     the type of the element stored in node.
 */
public interface BinaryNode<T extends Comparable<T>> {
  /**
   * Gets the data of the node.
   *
   * @return the data.
   */
  T getData();

  /**
   * Sets the data.
   *
   * @param data
   *     the data.
   */
  void setData(T data);

  /**
   * Gets the left child node.
   *
   * @return the left child node.
   */
  BinaryNode<T> getLeft();

  /**
   * Sets the left node.
   *
   * @param left
   *     the node.
   */
  void setLeft(BinaryNode<T> left);

  /**
   * Gets the right child node.
   *
   * @return the right child node.
   */
  BinaryNode<T> getRight();

  /**
   * Sets the right child node.
   *
   * @param right
   *     the node.
   */
  void setRight(BinaryNode<T> right);
}
