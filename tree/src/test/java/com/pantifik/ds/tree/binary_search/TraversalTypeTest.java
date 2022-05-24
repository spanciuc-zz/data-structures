package com.pantifik.ds.tree.binary_search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.pantifik.ds.tree.binary_search.simple.SimpleBinarySearchTree;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TraversalTypeTest {

  @ParameterizedTest
  @MethodSource("traverseProvider")
  void traverse_shouldReturnTreeTraversalResult(TreeTraversal treeTraversal, TraversalType type) {
    BinaryNode<Integer> node = TraversalTypeTest.buildNodes();
    assertEquals(treeTraversal.traverse(node), type.traverse(node));
  }

  private static Stream<Arguments> traverseProvider() {
    LevelOrderTraverse levelOrderTraverse = new LevelOrderTraverse();
    PreOrderTraverse preOrderTraverse = new PreOrderTraverse();
    InOrderTraverse inOrderTraverse = new InOrderTraverse();
    PostOrderTraverse postOrderTraverse = new PostOrderTraverse();

    return Stream.of(Arguments.of(levelOrderTraverse, TraversalType.LEVEL_ORDER),
        Arguments.of(preOrderTraverse, TraversalType.PRE_ORDER),
        Arguments.of(inOrderTraverse, TraversalType.IN_ORDER),
        Arguments.of(postOrderTraverse, TraversalType.POST_ORDER));
  }

  public static BinaryNode<Integer> buildNodes(){
    SimpleBinarySearchTree<Integer> tree = new SimpleBinarySearchTree<>();
    //root
    tree.add(20);

    //level 1
    tree.add(10);
    tree.add(30);

    //level 2
    tree.add(5);
    tree.add(15);
    tree.add(25);
    tree.add(40);

    //level 3
    tree.add(2);
    tree.add(8);
    tree.add(12);
    tree.add(17);
    tree.add(24);
    tree.add(27);
    tree.add(35);
    tree.add(50);

    //level 4
    tree.add(0);
    tree.add(6);
    tree.add(9);
    tree.add(11);
    tree.add(13);
    tree.add(16);
    tree.add(18);
    tree.add(22);
    tree.add(26);
    tree.add(29);
    tree.add(32);
    tree.add(49);
    tree.add(55);

    //level5
    tree.add(1);
    tree.add(7);
    tree.add(14);
    tree.add(21);
    tree.add(23);
    tree.add(28);

    return tree.getRoot();

  }
}