package com.pantifik.ds.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.pantifik.ds.list.arraylist.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public abstract class AbstractListTest {

  protected List<Object> list;

  /**
   * Must be implemented and run before each test case in subclasses.
   */
  @BeforeEach
  protected void setUp() {
    this.list = createListInstance();
  }

  protected abstract List<Object> createListInstance();

  @Test
  protected void sublist_whenValidIndexes_shouldReturnListViewInRange() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    List<Object> sublist = list.subList(1, 4);
    assertEquals(2, sublist.get(0));
    assertEquals(3, sublist.get(1));
    assertEquals(4, sublist.get(2));
  }

  @Test
  void size_whenEmpty_shouldReturnZero() {
    assertEquals(0, list.size());
  }

  @Test
  void size_whenNotEmpty_shouldReturnNumberOfElements() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    assertEquals(5, list.size());
  }

  @Test
  void isEmpty_whenEmpty_shouldReturnTrue() {
    assertTrue(list.isEmpty());
  }

  @Test
  void isEmpty_whenNotEmpty_shouldReturnFalse() {
    list.add(new Object());
    assertFalse(list.isEmpty());
  }

  @Test
  void add_whenElementAdded_shouldReturnTrue() {
    assertTrue(list.add(1));
    assertEquals(1, list.size());
    assertTrue(list.contains(1));
  }

  @Test
  void addAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> list.addAll(null));
  }

  @Test
  void addAll_whenEmpty_shouldNotChangeListAndReturnFalse() {
    list.add(1);
    list.add(2);
    assertFalse(list.addAll(List.of()));
    assertEquals(2, list.size());
    assertTrue(list.containsAll(List.of(1, 2)));
  }

  @Test
  void addAll_whenNotEmpty_shouldAddAllElementsToListAndReturnTrue() {
    list.add(1);
    list.add(2);
    assertTrue(list.addAll(List.of(3, 4, 5)));
    assertEquals(5, list.size());
    assertTrue(list.containsAll(List.of(1, 2, 3, 4, 5)));
  }

  @Test
  void contains_whenNotPresent_shouldReturnFalse() {
    list.addAll(List.of(1, 2, 4));
    assertFalse(list.contains(3));
  }

  @Test
  void contains_whenPresent_shouldReturnTrue() {
    list.addAll(List.of(1, 2, 4));
    assertTrue(list.contains(4));
  }

  @Test
  void containsAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> list.containsAll(null));
  }

  @Test
  void containsAll_whenAtLeastOneNotPresent_shouldReturnFalse() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    assertFalse(list.containsAll(List.of(1, 4, 5, 6)));
  }

  @Test
  void containsAll_whenAllPresent_shouldReturnTrue() {
    list.addAll(List.of(1, 2, 3, 4, 5, 6));
    assertTrue(list.containsAll(List.of(1, 4, 5, 6)));
  }

  @Test
  void indexOf_whenNotPresent_shouldReturnNegativeOne() {
    list.addAll(List.of(1, 2, 3, 5, 4));
    assertEquals(-1, list.indexOf(0.1));
  }

  @Test
  void indexOf_whenAtLeastOnePresent_shouldReturnFirstIndexOfTheElement() {
    list.addAll(List.of(1, 2, 3, 5, 3, 6, 3));
    assertEquals(2, list.indexOf(3));
  }

  @Test
  void lastIndexOf_whenNotPresent_shouldReturnNegativeOne() {
    list.addAll(List.of(1, 2, 3, 5, 4));
    assertEquals(-1, list.lastIndexOf(0.1));
  }

  @Test
  void lastIndexOf_whenAtLeastOnePresent_shouldReturnLastIndexOfTheElement() {
    list.addAll(List.of(1, 2, 3, 5, 3, 6, 3));
    assertEquals(6, list.lastIndexOf(3));
  }

  @ParameterizedTest
  @ValueSource(ints = {-2, 4})
  void get_whenInvalidIndex_shouldThrowException(int index) {
    list.addAll(List.of(1, 2, 3, 4));
    assertThrows(IndexOutOfBoundsException.class, () -> list.get(index));
  }

  @Test
  void get_whenValidIndex_shouldReturnElementAtThisIndex() {
    list.addAll(List.of(1, 2, 3, 4));
    assertEquals(1, list.get(0));
    assertEquals(2, list.get(1));
    assertEquals(3, list.get(2));
    assertEquals(4, list.get(3));
  }

  @Test
  void clear_shouldRemoveAllElements() {
    list.addAll(List.of(1, 2, 3, 4));
    list.clear();

    assertEquals(0, list.size());
    assertTrue(list.isEmpty());
    list.addAll(List.of(1, 2, 3));
    assertEquals(3, list.size());
    list.clear();
    assertEquals(0, list.size());
    assertTrue(list.isEmpty());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 3})
  void set_whenInvalidIndex_shouldThrowException(int index) {
    list.addAll(List.of(1, 2, 3));
    assertThrows(IndexOutOfBoundsException.class, () -> list.set(index, null));
  }

  @Test
  void set_whenValidIndex_shouldReplaceElementAndReturnOldOne() {
    list.addAll(List.of(1, 2, 3, 4, 5, 6));
    assertEquals(4, list.set(3, 100));
    assertEquals(6, list.size());
    assertEquals(3, list.indexOf(100));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 4})
  void remove_whenInvalidIndex_shouldThrowException(int index) {
    list.addAll(List.of(100, 2, 3, 4));
    assertThrows(IndexOutOfBoundsException.class, () -> list.remove(index));
  }

  @Test
  void remove_whenValidIndex_shouldRemoveElementFromListAndReturnOldOne() {
    list.addAll(List.of(100, 2, 3, 4, 56));
    assertEquals(3, list.remove(2));
    assertEquals(4, list.size());
    assertFalse(list.contains(3));
    assertTrue(list.containsAll(List.of(100, 2, 4, 56)));
  }

  @Test
  void remove_whenFirstIndex_shouldRemoveAndReturnFirstElement() {
    list.addAll(List.of(100, 2, 3, 4, 56));
    assertEquals(100, list.remove(0));
    assertEquals(4, list.size());
    assertFalse(list.contains(100));
    assertTrue(list.containsAll(List.of(3, 2, 4, 56)));
  }

  @Test
  void remove_whenLastIndex_shouldRemoveAndReturnLastElement() {
    list.addAll(List.of(100, 2, 3, 4, 56));
    assertEquals(56, list.remove(4));
    assertEquals(4, list.size());
    assertFalse(list.contains(56));
    assertTrue(list.containsAll(List.of(3, 2, 4, 100)));
  }

  @Test
  void remove_whenNotPresent_shouldNotChangeListAndReturnFalse() {
    list.addAll(List.of(100, 2, 3, 4, 56));
    assertFalse(list.remove((Object) 101));
    assertEquals(5, list.size());
    assertTrue(list.containsAll(List.of(100, 2, 3, 4, 56)));
  }

  @Test
  void remove_whenPresent_shouldRemoveFirstMatchAndReturnTrue() {
    list.addAll(List.of(100, 4, 3, 4, 56));
    assertTrue(list.remove((Object) 4));
    assertEquals(4, list.size());
    assertTrue(list.containsAll(List.of(100, 3, 4, 56)));
    assertEquals(2, list.indexOf(4));
  }

  @Test
  void removeAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> list.removeAll(null));
  }

  @Test
  void removeAll_whenEmpty_shouldNotChangeListAndReturnFalse() {
    list.addAll(List.of("1", "2", "3"));
    assertFalse(list.removeAll(List.of()));
    assertEquals(3, list.size());
    assertTrue(list.containsAll(List.of("1", "2", "3")));
  }

  @Test
  void removeAll_whenAllNotPresent_shouldNotChangeListAndReturnFalse() {
    list.addAll(List.of("1", "2", "3"));
    assertFalse(list.removeAll(List.of("5", "a")));
    assertEquals(3, list.size());
    assertTrue(list.containsAll(List.of("1", "2", "3")));
  }

  @Test
  void removeAll_whenAtLeastOnePresent_shouldRemoveFirstMatchAndReturnTrue() {
    list.addAll(List.of("1", "2", "3", "abc", "1"));
    assertTrue(list.removeAll(List.of("1", "3", "abc", "123")));
    assertEquals(1, list.size());
    assertTrue(list.contains("2"));
  }

  @Test
  void retainAll_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class, () -> list.retainAll(null));
  }

  @Test
  void retainAll_whenEmpty_shouldClearListAndReturnTrue() {
    list.addAll(List.of("1", "2", "3", "abc", "1"));
    assertTrue(list.retainAll(List.of()));
    assertTrue(list.isEmpty());
  }

  @Test
  void retainAll_whenAllElements_shouldNotChangeListAndReturnFalse() {
    list.addAll(List.of("1", "2", "3", "abc", "1"));
    assertFalse(list.retainAll(List.of("1", "2", "3", "abc", "1")));
    assertTrue(list.containsAll(List.of("1", "2", "3", "abc", "1")));
    assertEquals(5, list.size());
  }

  @Test
  void retainAll_whenSomeElements_shouldRemoveAllThatNotInCollection() {
    list.addAll(List.of("1", "2", "3", "abc", "1"));
    assertTrue(list.retainAll(List.of("2", "3")));
    assertTrue(list.containsAll(List.of("2", "3")));
    assertEquals(2, list.size());
  }

  @Test
  void iteratorHasNext_whenHasNext_shouldReturnTrue() {
    list.add(1);
    assertTrue(list.iterator()
        .hasNext());
  }

  @Test
  void iteratorHasNext_whenHasNotNext_shouldReturnFalse() {
    list.add(1);
    Iterator<Object> iterator = list.iterator();
    iterator.next();
    assertFalse(iterator.hasNext());
  }

  @Test
  void iteratorNext_whenHasNotNext_shouldThrowException() {
    list.add(1);
    Iterator<Object> iterator = list.iterator();
    iterator.next();
    assertThrows(NoSuchElementException.class, iterator::next);
  }

  @Test
  void iteratorNext_whenHasNext_shouldReturnNextElement() {
    list.add(1);
    assertEquals(1, list.iterator()
        .next());
  }

  @Test
  void iterator_whenNotEmpty_shouldIterateOverAllElements() {
    list.addAll(List.of(-1, 0, 2, 5, 6));

    List<Object> visitedElements = new ArrayList<>();
    visitedElements.addAll(list);
    assertEquals(list.size(), visitedElements.size());
    assertEquals(list.get(0), visitedElements.get(0));
    assertEquals(list.get(1), visitedElements.get(1));
    assertEquals(list.get(2), visitedElements.get(2));
    assertEquals(list.get(3), visitedElements.get(3));
    assertEquals(list.get(4), visitedElements.get(4));
  }

  @Test
  void iteratorRemove_whenCalledWithoutNext_shouldThrowException() {
    list.addAll(List.of(1, 2, 3));
    Iterator<Object> iterator = list.iterator();
    assertThrows(IllegalStateException.class, iterator::remove);
  }

  @Test
  void iteratorRemove_whenCalledMultipleTimesWithoutNext_shouldThrowException() {
    list.addAll(List.of(1, 2, 3));
    Iterator<Object> iterator = list.iterator();
    iterator.next();
    iterator.remove();
    assertThrows(IllegalStateException.class, iterator::remove);
  }

  @Test
  void iteratorRemove_shouldRemoveElementsFormList() {
    list.addAll(List.of(1, 2, 3, 4, 6));
    list.removeIf(elem -> ((Integer) elem) % 2 == 0);
    assertEquals(2, list.size());
    assertTrue(list.containsAll(List.of(1, 3)));
  }

  @Test
  void modifyListSize_whileIteratingWithoutIterator_shouldThrowException() {
    list.addAll(List.of(1, 2, 3, 4, 6));
    assertThrows(ConcurrentModificationException.class, () -> {
      for (Object o : list) {
        list.remove(o);
      }
    });
  }

  @Test
  void hashCode_whenSameRef_shouldBeEquals() {
    List<Object> list2 = list;
    assertEquals(list.hashCode(), list2.hashCode());
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 3})
  void add_whenInvalidIndex_shouldThrowException(int index) {
    list.add(1);
    list.add(1);
    assertThrows(IndexOutOfBoundsException.class, () -> list.add(index, null));
  }

  @Test
  void add_whenValidIndex_shouldInsertElementAtIndexMovingElementsToTheRight() {
    list.addAll(List.of(1, 2, 3, 4, 5, 6));
    list.add(2, 100);
    assertEquals(7, list.size());
    assertEquals(2, list.indexOf(100));
    assertTrue(list.containsAll(List.of(1, 2, 3, 4, 5, 6)));
  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 5})
  void addAll_whenInvalidIndex_shouldThrowException(int index) {
    list.addAll(List.of(1, 2, 3, 4));
    List<Object> emptyList = List.of();
    assertThrows(IndexOutOfBoundsException.class,
        () -> list.addAll(index, emptyList));
  }

  @Test
  void addAll_whenValidIndexAndNull_shouldThrowException() {
    list.addAll(List.of(1, 2, 3, 4));
    assertThrows(NullPointerException.class, () -> list.addAll(1, null));
  }

  @Test
  void addAll_whenValidParams_shouldAddAllStartingAtIndexMovingToTheRight() {
    list.addAll(List.of(1, 2, 3, 4));
    list.addAll(2, List.of(100, 200, 300));
    assertEquals(7, list.size());
    assertTrue(list.containsAll(List.of(1, 2, 3, 4, 100, 200, 300)));
  }

  @Test
  void toArray_shouldReturnNewArrayWithElementsFromList() {
    list.addAll(List.of(1, 2, 3, 4));
    Object[] array = list.toArray();
    assertEquals(4, array.length);
    assertEquals(1, array[0]);
    assertEquals(2, array[1]);
    assertEquals(3, array[2]);
    assertEquals(4, array[3]);

    array[0] = null;
    assertTrue(list.containsAll(List.of(1, 2, 3, 4)));
  }

  @Test
  void toArray_whenNull_shouldThrowException() {
    assertThrows(NullPointerException.class,
        () -> list.toArray((Object[]) null));
  }

  @Test
  void toArray_whenLengthToLow_shouldNewArrayWithSufficientLength() {
    list.addAll(List.of(1, 2, 3, 4));
    Object[] initialArray = new Object[2];
    Object[] actual = list.toArray(initialArray);
    assertNotEquals(initialArray, actual);

    assertEquals(4, actual.length);
    assertEquals(1, actual[0]);
    assertEquals(2, actual[1]);
    assertEquals(3, actual[2]);
    assertEquals(4, actual[3]);

    actual[0] = null;
    assertTrue(list.containsAll(List.of(1, 2, 3, 4)));

  }

  @ParameterizedTest
  @ValueSource(ints = {-1, 4})
  void listIterator_whenInvalidIndex_shouldThrowException(int index) {
    list.addAll(List.of(1, 2, 4));
    assertThrows(IndexOutOfBoundsException.class,
        () -> list.listIterator(index));
  }

  @Test
  void listIterator_whenValidIndex_shouldReturnExpectedNextAndPrev() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(1);
    assertEquals(2, listIterator.next());
    listIterator = list.listIterator(1);
    assertEquals(1, listIterator.previous());
  }

  @Test
  void listIteratorHasPrevious_whenHasNoPrevious_shouldReturnFalse() {
    list.add(1);
    assertFalse(list.listIterator()
        .hasPrevious());
  }

  @Test
  void listIteratorHasPrevious_whenHasPrevious_shouldReturnTrue() {
    list.addAll(List.of(1, 2));
    ListIterator<Object> listIterator = list.listIterator(1);
    assertTrue(listIterator.hasPrevious());
  }

  @Test
  void listIterator_whenNotEmpty_shouldIterateOverAllElements() {
    list.addAll(List.of(-1, 0, 2, 5, 6));

    List<Object> visitedElements = new ArrayList<>();
    ListIterator<Object> listIterator = list.listIterator(list.size() - 1);
    while (listIterator.hasPrevious()) {
      visitedElements.add(listIterator.previous());
    }
    assertTrue(list.containsAll(visitedElements));

  }

  @ParameterizedTest
  @CsvSource({"0, 0", "2, 2", "1, 1"})
  void listIteratorNextIndex_shouldReturnNextIndexIfExists(int index,
      int next) {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(index);
    assertEquals(next, listIterator.nextIndex());
  }

  @ParameterizedTest
  @CsvSource({"0, -1", "2, 1", "1, 0"})
  void listIteratorPreviousIndex_shouldReturnPrevIndexIfExists(int index,
      int prev) {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(index);
    assertEquals(prev, listIterator.previousIndex());
  }

  @Test
  void listIteratorPrevious_whenHasPrevious_shouldReturnPreviousElement() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(2);
    assertEquals(2, listIterator.previous());
  }

  @Test
  void listIteratorPrevious_whenHasNotPrevious_shouldThrowException() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(0);
    assertThrows(NoSuchElementException.class, listIterator::previous);
  }

  @Test
  void listIteratorSet_whenCalledWithoutNextOrPrev_shouldThrowException() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(0);
    assertThrows(IllegalStateException.class, () -> listIterator.set("obj"));
  }

  @Test
  void listIteratorSet_shouldReplaceTheElementAtIndex() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator();
    listIterator.next();
    listIterator.set(null);
    assertNull(list.get(0));
  }

  @Test
  void listIteratorSet_whenCalledMultipleTimesConsecutively_shouldSetValueAtIndex() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(0);
    listIterator.next();
    listIterator.set("first");
    listIterator.set("second");
    assertEquals(0, list.indexOf("second"));
  }

  @Test
  void listIteratorAdd_whenCalledWithoutNextOrPrev_shouldAddAtIndex() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(0);
    listIterator.add("first");
    assertEquals(0, list.indexOf("first"));
  }

  @Test
  void listIteratorAdd_shouldAddElementAtIndex() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator();
    listIterator.next();
    listIterator.next();
    listIterator.add("added");
    assertEquals(2, list.indexOf("added"));
  }

  @Test
  void listIteratorAdd_whenListIsEmpty_shouldAddElementAtFirstIndex() {
    ListIterator<Object> listIterator = list.listIterator();
    listIterator.add("added");
    assertEquals(0, list.indexOf("added"));
  }

  @Test
  void listIteratorAdd_whenCalledMultipleTimesConsecutively_shouldAddElementsAtIndex() {
    list.addAll(List.of(1, 2, 3));
    ListIterator<Object> listIterator = list.listIterator(0);
    listIterator.next();
    listIterator.add("first");
    listIterator.add("second");
    listIterator.add("third");
    assertEquals(1, list.indexOf("first"));
    assertEquals(2, list.indexOf("second"));
    assertEquals(3, list.indexOf("third"));
  }

  @ParameterizedTest
  @CsvSource({"-1, -1", "1, -1", "-1, 1", "2, 6", "6, 2", "6, 6"})
  void sublist_whenInvalidIndexes_shouldThrowException(int fromIndex,
      int toIndex) {
    list.addAll(List.of(1, 2, 3, 4, 5));
    assertThrows(IndexOutOfBoundsException.class,
        () -> list.subList(fromIndex, toIndex));
  }

  @Test
  void equals_whenSameRef_shouldReturnTrue() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    List<Object> list2 = list;
    assertEquals(list, list2);
  }

  @Test
  void equals_whenEquals_shouldReturnTrue() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    List<Object> list1 = createListInstance();
    list1.addAll(List.of(1, 2, 3, 4, 5));
    assertEquals(list, list1);
  }

  @Test
  void equals_whenEquals_shouldReturnFalse() {
    list.addAll(List.of(1, 2, 3, 4, 5));
    List<Object> list1 = createListInstance();
    list1.addAll(List.of(1, 2, 4, 5));
    assertNotEquals(list, list1);
  }

}