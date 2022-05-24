package com.pantifik.ds.tree.binary_search.simple;

import com.pantifik.ds.tree.binary_search.BinaryNode;
import com.pantifik.ds.tree.binary_search.BinarySearchTree;
import com.pantifik.ds.tree.binary_search.TreeTraversal;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * A BST implementation.
 *
 * @param <T>
 *     the type of the element in the tree.
 */
public class SimpleBinarySearchTree<T extends Comparable<T>> implements BinarySearchTree<T> {

  /**
   * Initial tree size.
   */
  static final int INITIAL_SIZE = 0;
  private int size;
  private BinaryNode<T> root;

  /**
   * Creates an instance of this class.
   */
  public SimpleBinarySearchTree() {
    size = INITIAL_SIZE;
    root = null;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean add(T elem) {
    Objects.requireNonNull(elem);
    if (isEmpty()) {
      root = new SimpleBinaryNode<>(elem);
      size++;
      return true;
    } else {
      boolean modified = insert(root, elem);
      if (modified) {
        size++;
      }
      return modified;
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
  public boolean contains(T elem) {
    if (elem == null) {
      return false;
    }
    return search(root, elem).isPresent();
  }

  @Override
  public void clear() {
    clearNode(root);
    root = null;
    size = INITIAL_SIZE;
  }

  @Override
  public List<T> traverse(TreeTraversal treeTraversal) {
    Objects.requireNonNull(treeTraversal);
    return treeTraversal.traverse(root);
  }

  /**
   * Gets the root node of the tree.
   *
   * @return the root node
   */
  public BinaryNode<T> getRoot() {
    return root;
  }

  private BinaryNode<T> removeNode(BinaryNode<T> node, T elem) {
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

  private BinaryNode<T> findMaxInLeftSubtree(BinaryNode<T> node) {
    while (node.getRight() != null) {
      node = node.getRight();
    }
    return node;
  }

  private void clearNode(BinaryNode<T> node) {
    if (node != null) {
      clearNode(node.getLeft());
      clearNode(node.getRight());
      node.setLeft(null);
      node.setRight(null);
    }
  }

  private Optional<BinaryNode<T>> search(BinaryNode<T> current, T elem) {
    if (current == null) {
      return Optional.empty();
    } else {
      int compared = Objects.compare(elem, current.getData(), Comparator.naturalOrder());
      if (compared == 0) {
        return Optional.of(current);
      } else if (compared < 0) {
        return search(current.getLeft(), elem);
      } else {
        return search(current.getRight(), elem);
      }
    }
  }

  private boolean insert(BinaryNode<T> current, T elem) {
    int compared = Objects.compare(elem, current.getData(), Comparator.naturalOrder());
    if (compared == 0) {
      return false;
    } else if (compared < 0) {
      return insertLeft(current, elem);
    } else {
      return insertRight(current, elem);
    }
  }

  private boolean insertRight(BinaryNode<T> current, T elem) {
    if (current.getRight() == null) {
      current.setRight(new SimpleBinaryNode<>(elem));
      return true;
    } else {
      return insert(current.getRight(), elem);
    }
  }

  private boolean insertLeft(BinaryNode<T> current, T elem) {
    if (current.getLeft() == null) {
      current.setLeft(new SimpleBinaryNode<>(elem));
      return true;
    } else {
      return insert(current.getLeft(), elem);
    }
  }
}
