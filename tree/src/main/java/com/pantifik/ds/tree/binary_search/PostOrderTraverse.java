package com.pantifik.ds.tree.binary_search;

import java.util.ArrayList;
import java.util.List;

public class PostOrderTraverse implements TreeTraversal {

  @Override
  public <T extends Comparable<T>> List<T> traverse(BinaryNode<T> node) {
    List<T> result = new ArrayList<>();
    doTraverse(node, result);
    return result;
  }

  private <T extends Comparable<T>> void doTraverse(BinaryNode<T> node, List<T> result) {
    if (null != node) {
      doTraverse(node.getLeft(), result);
      doTraverse(node.getRight(), result);
      result.add((node.getData()));
    }

  }
}
