package com.pantifik.ds.tree.binary_search.simple;

import com.pantifik.ds.tree.binary_search.AbstractBinarySearchTree;
import com.pantifik.ds.tree.binary_search.BinaryNode;
import java.util.Comparator;
import java.util.Objects;

/**
 * A BST implementation.
 *
 * @param <T>
 *     the type of the element in the tree.
 */
public class SimpleBinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T> {

  /**
   * Initial tree size.
   */
  static final int INITIAL_SIZE = 0;
  protected int size;
  protected SimpleBinaryNode<T> root;

  /**
   * Creates an instance of this class.
   */
  public SimpleBinarySearchTree() {
    super();
    size = INITIAL_SIZE;
    root = null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean add(T elem) {
    Objects.requireNonNull(elem);
    if (contains(elem)) {
      return false;
    } else {
      root = insert(root, elem);
      size++;
      return true;
    }
  }

  @Override
  public boolean remove(T elem) {
    if (elem == null) {
      return false;
    }
    if (!contains(elem)) {
      return false;
    } else {
      root = removeNode(root, elem);
      size--;
      return true;
    }
  }

  @Override
  public void clear() {
    clearNode(root);
    root = null;
    size = INITIAL_SIZE;
  }

  @Override
  public BinaryNode<T> getRoot() {
    return root;
  }

  private SimpleBinaryNode<T> createNode(T elem) {
    return new SimpleBinaryNode<>(elem);
  }

  private SimpleBinaryNode<T> removeNode(SimpleBinaryNode<T> node, T elem) {
    int compared = Objects.compare(elem, node.getData(), Comparator.naturalOrder());
    if (compared < 0) {
      node.setLeft(removeNode(node.getLeft(), elem));
    } else if (compared > 0) {
      node.setRight(removeNode(node.getRight(), elem));
    } else {
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else {
        BinaryNode<T> next = findMaxInLeftSubtree(node.getLeft());
        node.setData(next.getData());
        node.setLeft(removeNode(node.getLeft(), next.getData()));
      }
    }
    return node;
  }

  private SimpleBinaryNode<T> findMaxInLeftSubtree(SimpleBinaryNode<T> node) {
    while (node.getRight() != null) {
      node = node.getRight();
    }
    return node;
  }

  private void clearNode(SimpleBinaryNode<T> node) {
    if (node != null) {
      clearNode(node.getLeft());
      clearNode(node.getRight());
      node.setLeft(null);
      node.setRight(null);
    }
  }


  private SimpleBinaryNode<T> insert(SimpleBinaryNode<T> current, T elem) {

    if (current == null) {
      current = createNode(elem);
    } else {
      int compared = Objects.compare(elem, current.getData(), Comparator.naturalOrder());
      if (compared < 0) {
        current.setLeft(insert(current.getLeft(), elem));
      } else {
        current.setRight(insert(current.getRight(), elem));
      }
    }
    return current;
  }
}
