package com.pantifik.ds.tree.binary_search.avl;

import static com.pantifik.ds.tree.binary_search.avl.SimpleAVLNode.SINGLE_NODE_HEIGHT;
import com.pantifik.ds.tree.binary_search.AbstractBinarySearchTree;
import com.pantifik.ds.tree.binary_search.BinaryNode;
import java.util.Comparator;
import java.util.Objects;

public class AVLTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T> {

  static final int INITIAL_SIZE = 0;

  private SimpleAVLNode<T> root;
  private int size;

  @Override
  public AVLNode<T> getRoot() {
    return root;
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
    if (contains(elem)) {
      root = removeNode(root, elem);
      size--;
      return true;
    }
    return false;
  }

  @Override
  public void clear() {
    clearNode(root);
    root = null;
    size = INITIAL_SIZE;
  }

  private SimpleAVLNode<T> removeNode(SimpleAVLNode<T> node, T elem) {
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
        removeBalancedNode(node);
      }
    }
    update(node);
    return balance(node);
  }

  private void removeBalancedNode(SimpleAVLNode<T> node) {
    if (node.getLeftHeight() >= node.getRightHeight()) {
      BinaryNode<T> next = findMaxInLeftSubtree(node.getLeft());
      node.setData(next.getData());
      node.setLeft(removeNode(node.getLeft(), next.getData()));
    } else {
      BinaryNode<T> next = findMinInRightSubtree(node.getRight());
      node.setData(next.getData());
      node.setRight(removeNode(node.getRight(), next.getData()));
    }
  }

  private SimpleAVLNode<T> findMaxInLeftSubtree(SimpleAVLNode<T> node) {
    while (node.getRight() != null) {
      node = node.getRight();
    }
    return node;
  }

  private SimpleAVLNode<T> findMinInRightSubtree(SimpleAVLNode<T> node) {
    while (node.getLeft() != null) {
      node = node.getLeft();
    }
    return node;
  }

  private void clearNode(SimpleAVLNode<T> node) {
    if (node != null) {
      clearNode(node.getLeft());
      clearNode(node.getRight());
      node.setLeft(null);
      node.setRight(null);
    }
  }

  private SimpleAVLNode<T> insert(SimpleAVLNode<T> current, T elem) {
    if (current == null) {
      return createNode(elem);
    } else {
      int compared = Objects.compare(elem, current.getData(), Comparator.naturalOrder());
      if (compared < 0) {
        current.setLeft(insert(current.getLeft(), elem));
      } else {
        current.setRight(insert(current.getRight(), elem));
      }
      update(current);
      return balance(current);
    }
  }

  private SimpleAVLNode<T> balance(SimpleAVLNode<T> node) {
    return switch (node.getBalanceState()) {
      case BALANCED_LEFT, BALANCED, BALANCED_RIGHT -> node;
      case UNBALANCED_LEFT -> balanceLeft(node);
      case UNBALANCED_RIGHT -> balanceRight(node);
    };
  }

  private SimpleAVLNode<T> balanceRight(SimpleAVLNode<T> node) {
    if (node.getRight()
        .getBalanceFactor() == SimpleAVLNode.LEFT_HEAVY) {
      node.setRight(rotateRight(node.getRight()));
    }

    return rotateLeft(node);
  }

  private SimpleAVLNode<T> balanceLeft(SimpleAVLNode<T> node) {

    if (node.getLeft()
        .getBalanceFactor() == SimpleAVLNode.RIGHT_HEAVY) {
      node.setLeft(rotateLeft(node.getLeft()));
    }

    return rotateRight(node);

  }

  private SimpleAVLNode<T> rotateLeft(SimpleAVLNode<T> node) {
    SimpleAVLNode<T> newParent = node.getRight();
    node.setRight(newParent.getLeft());
    newParent.setLeft(node);
    update(node);
    update(newParent);
    return newParent;
  }

  private SimpleAVLNode<T> rotateRight(SimpleAVLNode<T> node) {
    SimpleAVLNode<T> newParent = node.getLeft();
    node.setLeft(newParent.getRight());
    newParent.setRight(node);
    update(node);
    update(newParent);
    return newParent;
  }

  private void update(SimpleAVLNode<T> current) {
    int leftHeight = current.getLeftHeight();
    int rightHeight = current.getRightHeight();
    current.setHeight(SINGLE_NODE_HEIGHT + Math.max(leftHeight, rightHeight));
    current.setBalanceFactor(leftHeight - rightHeight);
  }

  private SimpleAVLNode<T> createNode(T elem) {
    return new SimpleAVLNode<>(elem);
  }
}
