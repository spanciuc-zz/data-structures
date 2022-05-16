package com.pantifik.ds.tree.general.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import com.pantifik.ds.tree.general.GeneralTree;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GeneralTreeImplTest {

  public static final String TEST_ROOT = "test root";
  private GeneralTreeImpl<Object> tree;

  @Test
  void constructor_whenNullRoot_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> new GeneralTreeImpl<>(null));
  }

  @Test
  void constructor_whenNonNullRoot_shouldCreateTreeWithGivenRootAndNoChilds() {
    Object root = "some root";
    var tree = new GeneralTreeImpl<>(root);
    assertEquals(root, tree.root);
    assertEquals(0, tree.childs.size());
    assertEquals(1, tree.size);
  }

  @Test
  void root_shouldReturnTheRootElement() {
    tree = new GeneralTreeImpl<>(TEST_ROOT);
    assertEquals(TEST_ROOT, tree.root());
  }

  @Test
  void parent_whenHasParent_shouldReturnOptionalOfParentTree() {
    GeneralTree<Object> parent = new GeneralTreeImpl<>("parent");
    tree = new GeneralTreeImpl<>(parent, TEST_ROOT);
    assertEquals(parent, tree.parent()
        .get());
  }

  @Test
  void parent_whenHasNotParent_shouldReturnEmptyOptional() {
    tree = new GeneralTreeImpl<>(null, TEST_ROOT);
    assertTrue(tree.parent()
        .isEmpty());
  }

  @Test
  void isLeaf_whenHasNotChilds_shouldReturnTrue(@Mock List<GeneralTree<Object>> childs) {
    when(childs.size()).thenReturn(0);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    assertTrue(tree.isLeaf());
  }

  @Test
  void isLeaf_whenHasChilds_shouldReturnFalse(@Mock List<GeneralTree<Object>> childs) {
    when(childs.size()).thenReturn(1);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    assertFalse(tree.isLeaf());
  }

  @Test
  void numberOfSubtrees_whenIsLeaf_shouldReturnZero(@Mock List<GeneralTree<Object>> childs) {
    when(childs.size()).thenReturn(0);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    assertEquals(0, tree.numberOfSubtrees());
  }

  @ParameterizedTest
  @ValueSource(ints = {1, 3, 5, 100})
  void numberOfSubtrees_whenIsNotLeaf_shouldReturnNumberOfChilds(int nrOfChilds) {
    List<GeneralTree<Object>> childs = mock(List.class);
    when(childs.size()).thenReturn(nrOfChilds);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    assertEquals(nrOfChilds, tree.numberOfSubtrees());
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 30, 40, 1, 3})
  void numberOfElements_shouldReturnTheSizeField(int size) {
    tree = new GeneralTreeImpl<>(TEST_ROOT);
    tree.size = size;
    assertEquals(size, tree.numberOfElements());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 5, 10, 100})
  void getSubtree_whenInvalidIndex_shouldThrowException(int subtreeIndex) {
    tree = spy(new GeneralTreeImpl<>(TEST_ROOT));
    when(tree.numberOfSubtrees()).thenReturn(5);
    assertThrows(IndexOutOfBoundsException.class, () -> tree.getSubtree(subtreeIndex));
  }

  @ParameterizedTest
  @ValueSource(ints = {0, 1, 2})
  void getSubtree_whenValidIndex_shouldReturnTheSubtreeAtIndex(int subtreeIndex) {
    List<GeneralTree<Object>> childs = mock(List.class);
    GeneralTree<Object> subtree = new GeneralTreeImpl<>(subtreeIndex);
    when(childs.size()).thenReturn(3);
    when(childs.get(subtreeIndex)).thenReturn(subtree);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    assertEquals(childs.get(subtreeIndex), tree.getSubtree(subtreeIndex));
  }

  @Test
  void addSubtree_whenNull_shouldThrowException() {
    tree = new GeneralTreeImpl<>(TEST_ROOT);
    assertThrows(NullPointerException.class, () -> tree.addSubtree(null));
  }

  @Test
  void addSubtree_whenNotNull_shouldAddSubtreeAsChild() {
    List<GeneralTree<Object>> childs = new ArrayList<>();
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    GeneralTree<Object> subtree1 = new GeneralTreeImpl<>("1");
    GeneralTree<Object> subtree2 = new GeneralTreeImpl<>("1");
    tree.addSubtree(subtree1);
    tree.addSubtree(subtree2);
    assertEquals(childs.get(0), subtree1);
    assertEquals(childs.get(1), subtree2);
    assertEquals(2, childs.size());
  }

  @Test
  void addSubtree_whenNotNull_shouldIncreaseNumberOfElements() {
    List<GeneralTree<Object>> childs = new ArrayList<>();
    GeneralTree<Object> subtree = mock(GeneralTree.class);
    when(subtree.numberOfElements()).thenReturn(5);
    tree = new GeneralTreeImpl<>(TEST_ROOT, childs);
    tree.addSubtree(subtree);
    assertEquals(6, tree.numberOfElements());
  }

  @Test
  void addSubtree_whenNotNull_shouldSetTheParentForAddedSubtree() {
    GeneralTree<Object> subtree = new GeneralTreeImpl<>(null, TEST_ROOT);
    tree = new GeneralTreeImpl<>(TEST_ROOT);
    tree.addSubtree(subtree);
    assertEquals(tree, subtree.parent()
        .get());
  }

  @Test
  void height_whenIsLeaf_shouldReturnZero() {
    tree = spy(new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>()));
    assertEquals(0, tree.height());
  }

  @Test
  void height_whenNotLeaf_shouldReturnNumberOfEdgesOnLongestPath() {
    int maxSubtreeHeight = 10;
    List<GeneralTree<Object>> childs = buildSubtreesOfMaxHeight(maxSubtreeHeight);
    tree = spy(new GeneralTreeImpl<>(TEST_ROOT, childs));
    assertEquals(maxSubtreeHeight + 1, tree.height());
  }

  @Test
  void depth_whenIsTreeRoot_shouldReturnZero() {
    tree = spy(new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>()));
    assertEquals(0, tree.depth());
  }

  @Test
  void depth_whenIsSubtree_shouldReturnDepth() {
    int parentDepth = 10;
    GeneralTree<Object> parent = buildParentOfDepth(parentDepth);
    tree = new GeneralTreeImpl<>(TEST_ROOT);
    tree.parent = parent;
    assertEquals(parentDepth + 1, tree.depth());
  }

  @Test
  void levelOrder_whenIsLeaf_shouldReturnListWithRootElement() {
    List<Object> expected = List.of(TEST_ROOT);
    tree = new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>());
    assertEquals(expected, tree.levelOrder());
  }

  @Test
  void levelOrder_whenIsNotLeaf_shouldReturnLevelOrderTraversal() {
    List<Object> expected = List.of(9, 8, 6, 7, 2, 1, 5, 4, 3, 0);
    GeneralTreeImpl<Object> tree = buildTreeForTraversalTests();
    assertEquals(expected, tree.levelOrder());
  }

  @Test
  void preorder_whenIsLeaf_shouldReturnListWithRootElement() {
    List<Object> expected = List.of(TEST_ROOT);
    tree = new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>());
    assertEquals(expected, tree.preorder());
  }

  @Test
  void preorder_whenIsNotLeaf_shouldReturnPreorderTraversal() {
    List<Object> expected = List.of(9, 8, 6, 2, 0, 1, 7, 5, 4, 3);
    GeneralTreeImpl<Object> tree = buildTreeForTraversalTests();
    assertEquals(expected, tree.preorder());
  }

  @Test
  void inorder_whenIsLeaf_shouldReturnListWithRootElement() {
    List<Object> expected = List.of(TEST_ROOT);
    tree = new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>());
    assertEquals(expected, tree.inorder());
  }

  @Test
  void inorder_whenIsNotLeaf_shouldReturnPreorderTraversal() {
    List<Object> expected = List.of(0, 2, 6, 1, 8, 5, 7, 4, 3, 9);
    GeneralTreeImpl<Object> tree = buildTreeForTraversalTests();
    assertEquals(expected, tree.inorder());
  }

  @Test
  void postorder_whenIsLeaf_shouldReturnListWithRootElement() {
    List<Object> expected = List.of(TEST_ROOT);
    tree = new GeneralTreeImpl<>(TEST_ROOT, new ArrayList<>());
    assertEquals(expected, tree.postorder());
  }

  @Test
  void postorder_whenIsNotLeaf_shouldReturnPreorderTraversal() {
    List<Object> expected = List.of(0, 2, 1, 6, 5, 4, 3, 7, 8, 9);
    GeneralTreeImpl<Object> tree = buildTreeForTraversalTests();
    assertEquals(expected, tree.postorder());
  }

  private GeneralTreeImpl<Object> buildTreeForTraversalTests() {
    GeneralTreeImpl<Object> subtree0 = new GeneralTreeImpl<>(0);
    GeneralTreeImpl<Object> subtree1 = new GeneralTreeImpl<>(1);
    GeneralTreeImpl<Object> subtree2 = new GeneralTreeImpl<>(2, List.of(subtree0));
    GeneralTreeImpl<Object> subtree3 = new GeneralTreeImpl<>(3);
    GeneralTreeImpl<Object> subtree4 = new GeneralTreeImpl<>(4);
    GeneralTreeImpl<Object> subtree5 = new GeneralTreeImpl<>(5);
    List<GeneralTree<Object>> subtree6Childs = new ArrayList<>();
    subtree6Childs.add(subtree2);
    subtree6Childs.add(subtree1);
    GeneralTreeImpl<Object> subtree6 = new GeneralTreeImpl<>(6, subtree6Childs);
    List<GeneralTree<Object>> subtree7Childs = new ArrayList<>();
    subtree7Childs.add(subtree5);
    subtree7Childs.add(subtree4);
    subtree7Childs.add(subtree3);
    GeneralTreeImpl<Object> subtree7 = new GeneralTreeImpl<>(7, subtree7Childs);
    List<GeneralTree<Object>> subtree8Childs = new ArrayList<>();
    subtree8Childs.add(subtree6);
    subtree8Childs.add(subtree7);
    GeneralTreeImpl<Object> subtree8 = new GeneralTreeImpl<>(8, subtree8Childs);
    return new GeneralTreeImpl<>(9, List.of(subtree8));
  }

  private GeneralTree<Object> buildParentOfDepth(int depth) {
    GeneralTree<Object> tree = mock(GeneralTree.class);
    when(tree.depth()).thenReturn(depth);
    return tree;
  }

  private List<GeneralTree<Object>> buildSubtreesOfMaxHeight(int maxHeight) {
    List<GeneralTree<Object>> childs = new ArrayList<>();
    for (int i = 0; i <= maxHeight; i++) {
      GeneralTreeImpl<Object> gtMock = mock(GeneralTreeImpl.class);
      when(gtMock.height()).thenReturn(i);
      childs.add(gtMock);
    }
    return childs;
  }

}