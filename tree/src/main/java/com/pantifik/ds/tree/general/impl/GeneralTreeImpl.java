package com.pantifik.ds.tree.general.impl;

import com.pantifik.ds.tree.general.GeneralTree;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * An implementation of the GeneralTree.
 *
 * @param <T>
 *     the type of the elements.
 */
public class GeneralTreeImpl<T> implements GeneralTree<T> {

  final T root;
  final List<GeneralTree<T>> childs;
  GeneralTree<T> parent;
  int size;

  /**
   * Creates a general tree instance with the given element as a root node.
   *
   * @param root
   *     the root element.
   * @throws NullPointerException
   *     if the given root element is null.
   */
  public GeneralTreeImpl(T root) {
    this(root, new ArrayList<>());
  }

  GeneralTreeImpl(T root, List<GeneralTree<T>> childs) {
    Objects.requireNonNull(root);
    this.root = root;
    this.childs = childs;
    this.size = 1;
    this.parent = null;
  }

  GeneralTreeImpl(GeneralTree<T> parent, T root) {
    Objects.requireNonNull(root);
    this.root = root;
    this.childs = new ArrayList<>();
    this.size = 1;
    this.parent = parent;
  }

  @Override
  public Optional<GeneralTree<T>> parent() {
    return Optional.ofNullable(parent);
  }

  @Override
  public void setParent(GeneralTree<T> parent) {
    this.parent = parent;
  }

  @Override
  public T root() {
    return root;
  }

  @Override
  public boolean isLeaf() {
    return numberOfSubtrees() == 0;
  }

  @Override
  public int numberOfElements() {
    return size;
  }

  @Override
  public int numberOfSubtrees() {
    return childs.size();
  }

  @Override
  public GeneralTree<T> getSubtree(int index) {
    Objects.checkIndex(index, numberOfSubtrees());
    return childs.get(index);
  }

  @Override
  public void addSubtree(GeneralTree<T> subtree) {
    Objects.requireNonNull(subtree);
    this.childs.add(subtree);
    size += subtree.numberOfElements();
    subtree.setParent(this);
  }

  @Override
  public int height() {
    OptionalInt maxPathLength = childs.stream()
        .mapToInt(GeneralTree::height)
        .max();
    if (maxPathLength.isPresent()) {
      return maxPathLength.getAsInt() + 1;
    } else {
      return 0;
    }
  }

  @Override
  public int depth() {
    return parent == null ? 0 : parent.depth() + 1;
  }

  @Override
  public List<T> preorder() {
    return depthFirstPreOrderTraversal(this);
  }

  @Override
  public List<T> inorder() {
    var result = new ArrayList<T>();
    depthFirstInOrderTraversal(this, result);
    return result;
  }

  @Override
  public List<T> postorder() {
    var result = new ArrayList<T>();
    depthFirstPostOrderTraversal(this, result);
    return result;
  }

  @Override
  public List<T> levelOrder() {
    return breadthFirstTraversal(this);
  }

  private List<T> depthFirstPreOrderTraversal(GeneralTree<T> tree) {
    Deque<GeneralTree<T>> deque = new LinkedList<>();
    deque.push(tree);
    List<T> result = new ArrayList<>();
    while (!deque.isEmpty()) {
      var subtree = deque.pop();
      result.add(subtree.root());
      for (int i = subtree.numberOfSubtrees() - 1; i >= 0; i--) {
        deque.push(subtree.getSubtree(i));
      }
    }
    return result;
  }

  private void depthFirstInOrderTraversal(GeneralTree<T> tree, List<T> result) {
    if (tree.numberOfSubtrees() == 0) {
      result.add(tree.root());
    } else if (tree.numberOfSubtrees() == 1) {
      depthFirstInOrderTraversal(tree.getSubtree(0), result);
      result.add(tree.root());
    } else {
      depthFirstInOrderTraversal(tree.getSubtree(0), result);
      result.add(tree.root());
      for (int i = 1; i < tree.numberOfSubtrees(); i++) {
        depthFirstInOrderTraversal(tree.getSubtree(i), result);
      }
    }
  }

  private void depthFirstPostOrderTraversal(GeneralTree<T> tree, List<T> result) {
    for (int i = 0; i < tree.numberOfSubtrees(); i++) {
      var subtree = tree.getSubtree(i);
      depthFirstPostOrderTraversal(subtree, result);
    }
    result.add(tree.root());
  }

  private List<T> breadthFirstTraversal(GeneralTree<T> tree) {
    var queue = new LinkedList<GeneralTree<T>>();
    queue.add(tree);
    var result = new ArrayList<T>();
    while (!queue.isEmpty()) {
      var subtree = queue.poll();
      result.add(subtree.root());
      for (int i = 0; i < subtree.numberOfSubtrees(); i++) {
        queue.offer(subtree.getSubtree(i));
      }
    }
    return result;
  }
}
