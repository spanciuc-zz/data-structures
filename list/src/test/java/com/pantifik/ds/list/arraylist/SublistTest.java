package com.pantifik.ds.list.arraylist;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.pantifik.ds.list.AbstractList;
import com.pantifik.ds.list.AbstractListTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SublistTest extends AbstractListTest {

  private AbstractList<Object> sourceList;

  @Override
  protected List<Object> createListInstance() {
    sourceList = new ArrayList<>();
    return sourceList.subList(0, 0);
  }

  @Test
  void constructor_whenNullList_shouldThrowException() {
    assertThrows(NullPointerException.class,
        () -> list = new Sublist<>(null, 0, 0));
  }

  @ParameterizedTest
  @CsvSource({"-1, -1", "1, -1", "-1, 1", "2, 6", "6, 2", "6, 6"})
  void constructor_whenInvalidIndexes_shouldThrowException(int fromIndex,
      int toIndex) {
    sourceList.addAll(List.of(1, 2, 3, 4, 5));
    assertThrows(IndexOutOfBoundsException.class,
        () -> new Sublist<>(sourceList, fromIndex, toIndex));
  }
}