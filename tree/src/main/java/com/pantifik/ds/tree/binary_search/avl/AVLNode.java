package com.pantifik.ds.tree.binary_search.avl;

import com.pantifik.ds.tree.binary_search.BinaryNode;

public interface AVLNode<T extends Comparable<T>> extends BinaryNode<T> {

  int getBalanceFactor();

  void setBalanceFactor(int balanceFactor);

  int getHeight();

  void setHeight(int height);

}
