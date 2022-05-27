package com.pantifik.ds.tree.binary_search.avl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AVLTreeTest<T extends Comparable<T>> {

  private AVLTree<T> tree;

  @BeforeEach
  void setUp() {
    tree = new AVLTree<>();
  }

  @Test
  void constructor_shouldCreateEmptyTree() {
    assertTrue(tree.isEmpty());
  }

  @Test
  void add_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> tree.add(null));
  }

  @Test
  void add_whenNotNull_shouldAddElementToTheTree() {
    T element = (T) (Integer) 1;
    tree.add(element);
    assertTrue(tree.contains(element));
    assertEquals(0, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenAlreadyPresent_shouldReturnFalseAndNotModifyTree() {
    T element = (T) (Integer) 1;
    tree.add(element);
    assertTrue(tree.contains(element));
    assertFalse(tree.add(element));
    assertEquals(1, tree.size());
  }

  @Test
  void add_whenLeftRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 1);
    tree.add((T) (Integer) 2);
    tree.add((T) (Integer) 3);
    assertTrue(tree.contains((T) (Integer) 1));
    assertTrue(tree.contains((T) (Integer) 2));
    assertTrue(tree.contains((T) (Integer) 3));
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenSubtreeRequiresLeftRightRotation_shouldRotateNodes() {
    tree.add((T) (Integer) 10);
    tree.add((T) (Integer) 20);
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 7);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 9);
    assertEquals(2, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenSubtreeRequiresRightRotation_shouldRotateNodes() {
    tree.add((T) (Integer) 10);
    tree.add((T) (Integer) 20);
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 7);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 25);
    tree.add((T) (Integer) 15);
    tree.add((T) (Integer) 14);
    tree.add((T) (Integer) 16);
    tree.add((T) (Integer) 13);
    assertEquals(3, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenSubtreeRequiresRightLeftRotation_shouldRotateNodes() {
    tree.add((T) (Integer) 10);
    tree.add((T) (Integer) 20);
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 15);
    tree.add((T) (Integer) 22);
    tree.add((T) (Integer) 14);
    assertEquals(2, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenLeftRightRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 2);
    tree.add((T) (Integer) 4);
    tree.add((T) (Integer) 3);
    assertTrue(tree.contains((T) (Integer) 2));
    assertTrue(tree.contains((T) (Integer) 4));
    assertTrue(tree.contains((T) (Integer) 3));
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenRightRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 2);
    tree.add((T) (Integer) 1);
    assertTrue(tree.contains((T) (Integer) 3));
    assertTrue(tree.contains((T) (Integer) 2));
    assertTrue(tree.contains((T) (Integer) 1));
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void add_whenRightLeftRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 1);
    tree.add((T) (Integer) 2);
    assertTrue(tree.contains((T) (Integer) 3));
    assertTrue(tree.contains((T) (Integer) 1));
    assertTrue(tree.contains((T) (Integer) 2));
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void clear_shouldRemoveAllTheElements() {
    tree.add((T) "1");
    tree.add((T) "2");
    tree.add((T) "3");
    tree.add((T) "4");
    tree.add((T) "5");
    tree.clear();
    assertTrue(tree.isEmpty());
  }

  @Test
  void remove_whenNull_shouldReturnFalseAndNotModifyTree() {
    tree.add((T) (Integer) 0);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 100);
    tree.add((T) (Integer) 101);
    assertFalse(tree.remove(null));
    assertEquals(4, tree.size());
  }

  @Test
  void remove_whenNotPresent_shouldReturnFalseAndNotModifyTree() {
    T element = (T) (Integer) 1;
    tree.add((T) (Integer) 0);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 100);
    tree.add((T) (Integer) 101);
    assertFalse(tree.remove(element));
    assertEquals(4, tree.size());
  }

  @ParameterizedTest
  @MethodSource("removeWhenPresentProvider")
  void remove_whenPresent_shouldReturnTrueAndModifyTree(T element, List<T> treeElements) {
    treeElements.forEach(tree::add);
    assertTrue(tree.remove(element));
    assertFalse(tree.contains(element));
    assertEquals(treeElements.size() - 1, tree.size());
  }

  @Test
  void remove_whenLeftRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 1);
    tree.add((T) (Integer) 2);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 4);
    assertTrue(tree.remove((T) (Integer) 1));
    assertEquals(3, tree.size());
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void remove_whenLeftRightRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 1);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 4);
    tree.add((T) (Integer) 6);
    assertTrue(tree.remove((T) (Integer) 1));
    assertEquals(4, tree.size());
    assertEquals(2, tree.getRoot()
        .getHeight());
  }

  @Test
  void remove_whenRightRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 10);
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 15);
    tree.add((T) (Integer) 4);
    assertTrue(tree.remove((T) (Integer) 15));
    assertEquals(3, tree.size());
    assertEquals(1, tree.getRoot()
        .getHeight());
  }

  @Test
  void remove_whenRightLeftRotationRequired_shouldRotateNodes() {
    tree.add((T) (Integer) 10);
    tree.add((T) (Integer) 5);
    tree.add((T) (Integer) 15);
    tree.add((T) (Integer) 4);
    tree.add((T) (Integer) 6);
    assertTrue(tree.remove((T) (Integer) 15));
    assertEquals(4, tree.size());
    assertEquals(2, tree.getRoot()
        .getHeight());
  }

  private static Stream<Arguments> removeWhenPresentProvider() {
    return Stream.of(Arguments.of(1, List.of(1)), Arguments.of(1, List.of(1, 2)),
        Arguments.of(2, List.of(1, 2)),
        Arguments.of(10, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(1, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(4, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(5, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(20, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(15, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(11, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(23, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)),
        Arguments.of(26, List.of(10, 5, 20, 2, 8, 1, 4, 3, 15, 13, 12, 14, 11, 25, 21, 23, 26)));
  }

}