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
  private SimpleBinaryNode<T> root;

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
    clear(root);
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

  private SimpleBinaryNode<T> removeNode(SimpleBinaryNode<T> node, T elem) {
    int compared = Objects.compare(elem, node.data, Comparator.naturalOrder());
    if (compared < 0) {
      node.left = removeNode(node.left, elem);
    } else if (compared > 0) {
      node.right = removeNode(node.right, elem);
    } else {
      if (node.left == null) {
        return node.right;
      } else if (node.right == null) {
        return node.left;
      } else {
        SimpleBinaryNode<T> next = findMaxInLeftSubtree(node.left);
        node.data = next.data;
        node.left = removeNode(node.left, next.data);
      }
    }
    return node;
  }

  private SimpleBinaryNode<T> findMaxInLeftSubtree(SimpleBinaryNode<T> node) {
    while (node.right != null) {
      node = node.right;
    }
    return node;
  }

  private void clear(SimpleBinaryNode<T> node) {
    if (node != null) {
      clear(node.left);
      clear(node.right);
      node.left = null;
      node.right = null;
    }
  }

  private Optional<SimpleBinaryNode<T>> search(SimpleBinaryNode<T> current, T elem) {
    if (current == null) {
      return Optional.empty();
    } else {
      int compared = Objects.compare(elem, current.data, Comparator.naturalOrder());
      if (compared == 0) {
        return Optional.of(current);
      } else if (compared < 0) {
        return search(current.left, elem);
      } else {
        return search(current.right, elem);
      }
    }
  }

  private boolean insert(SimpleBinaryNode<T> current, T elem) {
    int compared = Objects.compare(elem, current.data, Comparator.naturalOrder());
    if (compared == 0) {
      return false;
    } else if (compared < 0) {
      return insertLeft(current, elem);
    } else {
      return insertRight(current, elem);
    }
  }

  private boolean insertRight(SimpleBinaryNode<T> current, T elem) {
    if (current.right == null) {
      current.right = new SimpleBinaryNode<>(elem);
      return true;
    } else {
      return insert(current.right, elem);
    }
  }

  private boolean insertLeft(SimpleBinaryNode<T> current, T elem) {
    if (current.left == null) {
      current.left = new SimpleBinaryNode<>(elem);
      return true;
    } else {
      return insert(current.left, elem);
    }
  }

  protected static class SimpleBinaryNode<T extends Comparable<T>> implements BinaryNode<T> {

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

    @Override
    public BinaryNode<T> getLeft() {
      return left;
    }

    @Override
    public BinaryNode<T> getRight() {
      return right;
    }
  }
}
