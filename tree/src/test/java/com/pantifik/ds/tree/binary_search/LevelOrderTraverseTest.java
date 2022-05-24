package com.pantifik.ds.tree.binary_search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class LevelOrderTraverseTest {


  @Test
  void traverse_whenNull_shouldReturnEmptyList() {
    LevelOrderTraverse lot = new LevelOrderTraverse();
    assertTrue(lot.traverse(null)
        .isEmpty());
  }

  @Test
  void traverse_whenNotNull_shouldReturnTraversedList() {
    BinaryNode<Integer> root =TraversalTypeTest.buildNodes();
    List<Integer> expected = buildExpectedLevelOrderList();
    LevelOrderTraverse lot = new LevelOrderTraverse();
    assertEquals(expected, lot.traverse(root));
  }

  private List<Integer> buildExpectedLevelOrderList() {

    List<Integer> result = new ArrayList<>();

    //root
    result.add(20);

    //level 1
    result.add(10);
    result.add(30);

    //level 2
    result.add(5);
    result.add(15);
    result.add(25);
    result.add(40);

    //level 3
    result.add(2);
    result.add(8);
    result.add(12);
    result.add(17);
    result.add(24);
    result.add(27);
    result.add(35);
    result.add(50);

    //level 4
    result.add(0);
    result.add(6);
    result.add(9);
    result.add(11);
    result.add(13);
    result.add(16);
    result.add(18);
    result.add(22);
    result.add(26);
    result.add(29);
    result.add(32);
    result.add(49);
    result.add(55);

    //level5
    result.add(1);
    result.add(7);
    result.add(14);
    result.add(21);
    result.add(23);
    result.add(28);

    return result;
  }
}