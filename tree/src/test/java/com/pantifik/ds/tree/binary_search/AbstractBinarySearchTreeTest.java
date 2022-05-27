package com.pantifik.ds.tree.binary_search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AbstractBinarySearchTreeTest {

  @Spy
  private AbstractBinarySearchTree<Integer> tree;

  @Test
  void isEmpty_whenSizeIs0_shouldReturnTrue() {
    when(tree.size()).thenReturn(0);
    assertTrue(tree.isEmpty());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 10, 13, 20})
  void isEmpty_whenSizeIsGreaterThan0_shouldReturnFalse(int size) {
    when(tree.size()).thenReturn(size);
    assertFalse(tree.isEmpty());
  }

  @Test
  void contains_whenNull_shouldReturnFalse() {
    assertFalse(tree.contains(null));
  }

  @Test
  void traverse_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> tree.traverse(null));
  }

  @Test
  void traverse_whenNotNull_shouldReturnTraversalTypeResult(@Mock TreeTraversal treeTraversal) {
    List<Integer> expected = List.of(1, 2, 3);
    when(treeTraversal.traverse(any())).thenAnswer(invocationOnMock -> expected);
    assertEquals(expected, tree.traverse(treeTraversal));
  }

  private static BinaryNode<Integer> buildRoot() {

    BinaryNode<Integer> n_2 = buildNode(2);
    BinaryNode<Integer> n_8 = buildNode(8);
    BinaryNode<Integer> n_12 = buildNode(12);
    BinaryNode<Integer> n_17 = buildNode(17);

    BinaryNode<Integer> n_24 = buildNode(24);
    BinaryNode<Integer> n_27 = buildNode(27);
    BinaryNode<Integer> n_35 = buildNode(35);
    BinaryNode<Integer> n_50 = buildNode(50);

    BinaryNode<Integer> n_5 = buildNode(n_2, 5, n_8);
    BinaryNode<Integer> n_15 = buildNode(n_12, 15, n_17);

    BinaryNode<Integer> n_25 = buildNode(n_24, 25, n_27);
    BinaryNode<Integer> n_40 = buildNode(n_35, 40, n_50);

    BinaryNode<Integer> n_10 = buildNode(n_5, 10, n_15);

    BinaryNode<Integer> n_30 = buildNode(n_25, 30, n_40);

    return buildNode(n_10, 20, n_30);

  }

  private static BinaryNode<Integer> buildNode(int data) {
    return buildNode(null, data, null);
  }

  private static BinaryNode<Integer> buildNode(BinaryNode<Integer> left, int data,
      BinaryNode<Integer> right) {
    BinaryNode<Integer> node = mock(BinaryNode.class);
    lenient().when(node.getLeft())
        .thenReturn(left);
    lenient().when(node.getData())
        .thenReturn(data);
    lenient().when(node.getRight())
        .thenReturn(right);
    return node;
  }

  @Nested
  @DisplayName("when_getRoot_isRequired")
  class whenGetRootIsRequiredTest {

    @BeforeEach
    void setUp() {
      BinaryNode<Integer> root = buildRoot();
      when(tree.getRoot()).thenReturn(root);
    }

    @ParameterizedTest
    @ValueSource(ints = {20, 10, 8, 25, 35, 50})
    void contains_whenPresent_shouldReturnTrue(int element) {
      assertTrue(tree.contains(element));
    }

    @ParameterizedTest
    @ValueSource(ints = {21, -1, -10, 37, 28, 13, 6, 3, 0})
    void contains_whenNotPresent_shouldReturnFalse(int element) {
      assertFalse(tree.contains(element));
    }
  }

}