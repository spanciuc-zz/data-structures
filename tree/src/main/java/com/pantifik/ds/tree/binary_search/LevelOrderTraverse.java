package com.pantifik.ds.tree.binary_search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LevelOrderTraverse implements TreeTraversal {
  @Override
  public <T extends Comparable<T>> List<T> traverse(BinaryNode<T> node) {
    return doTraverse(node);
  }

  private <T extends Comparable<T>> List<T> doTraverse(BinaryNode<T> root) {
    List<T> result = new ArrayList<>();
    if (root == null) {
      return result;
    }
    Queue<BinaryNode<T>> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      var node = queue.remove();
      result.add(node.getData());
      if (node.getLeft() != null) {
        queue.offer(node.getLeft());
      }
      if (node.getRight() != null) {
        queue.offer(node.getRight());
      }
    }
    return result;
  }
}
