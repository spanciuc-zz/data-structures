package com.pantifik.ds.tree.binary_search;

import java.util.List;

/**
 * Defines methods for tree traversal.
 */
public interface TreeTraversal {

  /**
   * Traverses a tree.
   *
   * @param node
   *     the root node of the tree.
   * @param <T>
   *     type of the elements in the tree.
   * @return a list of traversed elements.
   */
  <T extends Comparable<T>> List<T> traverse(BinaryNode<T> node);

}
