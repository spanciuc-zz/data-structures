package com.pantifik.ds.tree.binary_search.simple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SimpleBinarySearchTreeTest<T extends Comparable<T>> {

  private SimpleBinarySearchTree<T> tree;

  @BeforeEach
  void setUp() {
    tree = new SimpleBinarySearchTree<>();
  }

  @Test
  void constructor_shouldCreateEmptyTree() {
    assertTrue(tree.isEmpty());
  }

  @Test
  void size_shouldReturnNumberOfElements() {
    assertEquals(0, tree.size());
    tree.add((T) "first");
    assertEquals(1, tree.size());
    tree.add((T) "second");
    assertEquals(2, tree.size());
    tree.add((T) "third");
    assertEquals(3, tree.size());
    tree.remove((T) "third");
    assertEquals(2, tree.size());
    tree.remove((T) "second");
    assertEquals(1, tree.size());
    tree.remove((T) "first");
    assertEquals(0, tree.size());
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
  }

  @Test
  void add_whenPresent_shouldNotModifyTreeAndReturnFalse() {
    T element = (T) (Integer) 1;
    tree.add((T) (Integer) 0);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 100);
    tree.add(element);
    assertFalse(tree.add(element));
    assertEquals(4, tree.size());
  }

  @Test
  void add_whenNotPresent_shouldModifyTreeAndReturnTrue() {
    T element = (T) (Integer) 1;
    tree.add((T) (Integer) 0);
    tree.add((T) (Integer) 3);
    tree.add((T) (Integer) 100);
    tree.add((T) (Integer) 101);
    assertTrue(tree.add(element));
    assertEquals(5, tree.size());
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
  void clear_shouldRemoveAllTheElements() {
    tree.add((T) "1");
    tree.add((T) "2");
    tree.add((T) "3");
    tree.add((T) "4");
    tree.add((T) "5");
    tree.clear();
    assertTrue(tree.isEmpty());
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