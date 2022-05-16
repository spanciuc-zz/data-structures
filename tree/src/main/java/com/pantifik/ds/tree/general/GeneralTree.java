package com.pantifik.ds.tree.general;

import java.util.List;
import java.util.Optional;

/**
 * Defines a general tree data structure.
 *
 * @param <T>
 *     the type of the objects.
 */
public interface GeneralTree<T> {

  /**
   * Gets the parent of this tree.
   *
   * @return an optional instance containing the parent of the tree, or empty optional if this is
   *     the root of the tree.
   */
  Optional<GeneralTree<T>> parent();

  /**
   * Sets the parent of the tree.
   *
   * @param parent
   *     the parent to set.
   */
  void setParent(GeneralTree<T> parent);

  /**
   * Gets the root element of the tree.
   *
   * @return the root element.
   */
  T root();

  /**
   * Check if the tree is a leaf.
   * <p>
   * A tree is a leaf if it has no childs.
   *
   * @return true if it is a leaf, otherwise- false.
   */
  boolean isLeaf();

  /**
   * Gets the number of total elements in the tree.
   *
   * @return the total number of elements.
   */
  int numberOfElements();

  /**
   * Gets the number of the subtrees for this tree.
   *
   * @return the number of subtrees.
   */
  int numberOfSubtrees();

  /**
   * Gets the subtree by index.
   *
   * @param index
   *     the index of subtree.
   * @return the subtree at the index.
   *
   * @throws IndexOutOfBoundsException
   *     if the index is not in valid range [0, ].
   */
  GeneralTree<T> getSubtree(int index);

  /**
   * Adds given tree as a subtree.
   *
   * @param subtree
   *     the subtree to be added.
   * @throws NullPointerException
   *     if the given subtree is null.
   */
  void addSubtree(GeneralTree<T> subtree);

  /**
   * Gets the number of edges on the longest path from that node to a leaf node.
   *
   * @return the number of edges.
   */
  int height();

  /**
   * Gets the number of edges from that node to the tree’s root node.
   *
   * @return the number of edges.
   */
  int depth();

  /**
   * Performs a preorder tree traversal.
   *
   * @return a list of elements.
   */
  List<T> preorder();

  /**
   * Performs an inorder tree traversal.
   *
   * @return a list of elements.
   */
  List<T> inorder();

  /**
   * Performs a postorder tree traversal.
   *
   * @return a list of elements.
   */
  List<T> postorder();

  /**
   * Performs a level order tree traversal.
   *
   * @return a list of elements.
   */
  List<T> levelOrder();

}
