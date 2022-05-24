package com.pantifik.ds.tree.binary_search;

/**
 * Defines a binary search tree data structure.
 * <p>
 * A tree is binary search tree if:
 * <p>
 * - Each node can have at most 2 child nodes.
 * <p>
 * - The left subtree of a node contains only nodes with keys lesser than the node’s key.
 * <p>
 * - The right subtree of a node contains only nodes with keys greater than the node’s key.
 * <p>
 * - The left and right subtree each must also be a binary search tree.
 * <p>
 * - There must be no duplicate nodes.
 *
 * @param <T>
 *     the type of the elements.
 */
public interface BinarySearchTree<T extends Comparable<T>> extends TraversableTree<T> {

  /**
   * Gets the number of elements in the tree.
   *
   * @return the number of elements.
   */
  int size();

  /**
   * Checks if the tree is empty.
   *
   * @return true - if empty, otherwise - false.
   */
  boolean isEmpty();

  /**
   * Adds an elements to the tree.
   *
   * @param elem
   *     the element to add.
   * @return true if the tree was modified, otherwise - false.
   *
   * @throws NullPointerException
   *     if the element is null.
   */
  boolean add(T elem);

  /**
   * Removes an element from a tree.
   *
   * @param elem
   *     the element to remove.
   * @return true if the tree was modified, otherwise - false.
   */
  boolean remove(T elem);

  /**
   * Checks if an element is present in the tree.
   *
   * @param elem
   *     the element to check.
   * @return true - if present, otherwise - false.
   */
  boolean contains(T elem);

  /**
   * Removes all the elements from the tree.
   */
  void clear();

}
