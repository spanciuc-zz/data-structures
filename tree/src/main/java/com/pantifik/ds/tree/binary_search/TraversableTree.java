package com.pantifik.ds.tree.binary_search;

import java.util.List;

/**
 * Defines methods for an iterable tree.
 *
 * @param <T>
 *     the type of the elements in the iterable.
 */
@FunctionalInterface
public interface TraversableTree<T extends Comparable<T>> {

  /**
   * Traverses the tree using the given traversal type.
   *
   * @param treeTraversal
   *     the desired traversal type.
   * @return the list of elements traversed in the order of the traversal type.
   *
   * @throws NullPointerException
   *     if the traversal type is null.
   */
  List<T> traverse(TreeTraversal treeTraversal);

}
