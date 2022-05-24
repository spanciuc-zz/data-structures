package com.pantifik.ds.tree.binary_search.simple;

import com.pantifik.ds.tree.binary_search.BinaryNode;
import java.util.Objects;

public class SimpleBinaryNode<T extends Comparable<T>> implements BinaryNode<T> {

  private T data;
  private BinaryNode<T> left;
  private BinaryNode<T> right;

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
  public void setData(T data) {
    this.data = data;
  }

  @Override
  public BinaryNode<T> getLeft() {
    return left;
  }

  @Override
  public void setLeft(BinaryNode<T> left) {
    this.left = left;
  }

  @Override
  public BinaryNode<T> getRight() {
    return right;
  }

  @Override
  public void setRight(BinaryNode<T> right) {
    this.right = right;
  }
}
