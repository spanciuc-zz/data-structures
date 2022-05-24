package com.pantifik.ds.tree.binary_search;

import java.util.List;

/**
 * Defines the types of the tree traversal.
 */
public enum TraversalType implements TreeTraversal {
  /**
   * Traverses the tree's nodes level by level, visiting all the nodes present at the same level
   * one-by-one from left to right and then move to the next level to visit all the nodes of that
   * level.
   */
  LEVEL_ORDER(new LevelOrderTraverse()),
  /**
   * Traverses the tree's nodes by visiting the current node first and then goes to the left
   * subtree. After covering every node of the left subtree, moves towards the right subtree and
   * visits in a similar fashion.
   */
  PRE_ORDER(new PreOrderTraverse()),
  /**
   * Traverses the tree's nodes by visiting the left subtree first, then the current node, and
   * lastly the right subtree.
   */
  IN_ORDER(new InOrderTraverse()),
  /**
   * Traverses the tree's nodes by visiting the left subtree and the right subtree before visiting
   * the current node in recursion.
   */
  POST_ORDER(new PostOrderTraverse());

  private final TreeTraversal treeTraversal;

  TraversalType(TreeTraversal treeTraversal) {this.treeTraversal = treeTraversal;}

  /**
   * Performs a defined traversal type of the node.
   *
   * @param root
   *     the root node of the tree.
   * @param <T>
   *     the type of the elements in the tree.
   * @return list of traversed elements.
   */
  public <T extends Comparable<T>> List<T> traverse(BinaryNode<T> root) {
    return this.treeTraversal.traverse(root);
  }
}
