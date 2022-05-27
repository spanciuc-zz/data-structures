package com.pantifik.ds.tree.binary_search.avl;

import com.pantifik.ds.tree.binary_search.BinaryNode;
import java.util.Optional;

class SimpleAVLNode<T extends Comparable<T>> implements BinaryNode<T>, AVLNode<T> {

  public static final int SINGLE_NODE_HEIGHT = 1;
  public static final int NULL_NODE_HEIGHT = -SINGLE_NODE_HEIGHT;
  public static final int LEFT_HEAVY = 1;
  public static final int RIGHT_HEAVY = -LEFT_HEAVY;
  private T data;
  private SimpleAVLNode<T> left;
  private SimpleAVLNode<T> right;
  private int balanceFactor;
  private int height;

  public SimpleAVLNode(T data) {
    this.data = data;
    this.left = this.right = null;
    this.balanceFactor = 0;
    this.height = 0;
  }

  @Override
  public int getBalanceFactor() {
    return this.balanceFactor;
  }

  @Override
  public void setBalanceFactor(int balanceFactor) {
    this.balanceFactor = balanceFactor;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public T getData() {
    return data;
  }

  /**
   * Sets the data.
   *
   * @param data
   *     the data.
   */
  public void setData(T data) {
    this.data = data;
  }

  @Override
  public SimpleAVLNode<T> getLeft() {
    return left;
  }

  /**
   * Sets the left node.
   *
   * @param left
   *     the node.
   */
  public void setLeft(SimpleAVLNode<T> left) {
    this.left = left;
  }

  @Override
  public SimpleAVLNode<T> getRight() {
    return this.right;
  }

  /**
   * Sets the right child node.
   *
   * @param right
   *     the node.
   */
  public void setRight(SimpleAVLNode<T> right) {
    this.right = right;
  }

  public Integer getLeftHeight() {
    return getChildHeight(this.left);
  }

  public Integer getRightHeight() {
    return getChildHeight(this.right);
  }

  /**
   * Gets the balance state of this node.
   *
   * @return the balance state.
   *
   * @throws IllegalStateException
   *     if the node is in unknown state.
   */
  public BalanceState getBalanceState() {
    return BalanceState.getByBalanceFactor(this.balanceFactor)
        .orElseThrow(IllegalStateException::new);
  }

  private Integer getChildHeight(SimpleAVLNode<T> right) {
    return Optional.ofNullable(right)
        .map(SimpleAVLNode::getHeight)
        .orElse(NULL_NODE_HEIGHT);
  }

  public enum BalanceState {
    BALANCED_LEFT(1),
    BALANCED(0),
    BALANCED_RIGHT(-1),
    UNBALANCED_LEFT(2),
    UNBALANCED_RIGHT(-2);

    private final int balanceFactor;

    BalanceState(int balanceFactor) {
      this.balanceFactor = balanceFactor;
    }

    static Optional<BalanceState> getByBalanceFactor(int balanceFactor) {
      for (BalanceState bs : BalanceState.values()) {
        if (balanceFactor == bs.balanceFactor) {
          return Optional.of(bs);
        }
      }
      return Optional.empty();
    }
  }

}
