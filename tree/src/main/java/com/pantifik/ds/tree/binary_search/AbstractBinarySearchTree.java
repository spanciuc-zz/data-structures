package com.pantifik.ds.tree.binary_search;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public abstract class AbstractBinarySearchTree<T extends Comparable<T>>
    implements BinarySearchTree<T> {

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(T elem) {
    if (elem == null) {
      return false;
    }
    return search(getRoot(), elem).isPresent();
  }

  @Override
  public List<T> traverse(TreeTraversal treeTraversal) {
    Objects.requireNonNull(treeTraversal);
    return treeTraversal.traverse(getRoot());
  }

  /**
   * Gets the root node of the tree.
   *
   * @return the root node
   */
  public abstract BinaryNode<T> getRoot();

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
}
