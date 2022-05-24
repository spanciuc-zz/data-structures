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
   * Gets the left child node.
   *
   * @return the left child node.
   */
  BinaryNode<T> getLeft();

  /**
   * Gets the right child node.
   *
   * @return the right child node.
   */
  BinaryNode<T> getRight();
}
