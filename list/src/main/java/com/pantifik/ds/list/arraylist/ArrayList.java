package com.pantifik.ds.list.arraylist;

import com.pantifik.ds.list.AbstractList;
import com.pantifik.ds.list.utils.ListUtils;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ArrayList<E> extends AbstractList<E> {

  public static final int DEFAULT_CAPACITY = 16;
  public static final int INIT_SIZE = 0;
  public static final int CAPACITY_INCREASE_RATE = 2;
  private int capacity;
  private int size;
  private Object[] array;

  public ArrayList() {
    this(DEFAULT_CAPACITY);
  }

  public ArrayList(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException(
          String.format("Invalid capacity: %s", capacity));
    }
    this.capacity = capacity;
    size = INIT_SIZE;
    array = new Object[this.capacity];
  }

  public ArrayList(Collection<E> iterable) {
    this(DEFAULT_CAPACITY);
    Objects.requireNonNull(iterable);
    addAll(iterable);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  @SuppressWarnings("unchecked")
  public E get(int index) {
    Objects.checkIndex(index, size);
    return (E) array[index];
  }

  @Override
  public E set(int index, E element) {
    E data = get(index);
    array[index] = element;
    return data;
  }

  @Override
  public void add(int index, E element) {
    ListUtils.checkIndexInclusiveLength(index, size);
    if (size + 1 > capacity) {
      resize();
    }
    if (size - index >= 0)
      System.arraycopy(array, index, array, index + 1, size - index);
    size++;
    array[index] = element;
  }

  @Override
  public E remove(int index) {
    E data = get(index);
    int newCapacity = size - 1;
    Object[] newArray = new Object[newCapacity];
    copyArraySkipIndex(array, newArray, newCapacity, index);
    array = newArray;
    capacity = size = newCapacity;
    return data;
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    return new Sublist<>(this, fromIndex, toIndex);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(capacity, size);
    result = 31 * result + Arrays.hashCode(array);
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (!(o instanceof ArrayList<?> arrayList)) {return false;}
    return capacity == arrayList.capacity && size == arrayList.size
        && Arrays.equals(array, arrayList.array);
  }

  @Override
  public void removeRange(int fromIndex, int toIndex) {
    int removeCount = toIndex - fromIndex;
    int newCapacity = size - removeCount;
    Object[] newArray = new Object[newCapacity];
    System.arraycopy(array, 0, newArray, 0, fromIndex);
    System.arraycopy(array, toIndex, newArray, fromIndex, size - toIndex);
    array = newArray;
    capacity = size = newCapacity;
  }

  private void copyArraySkipIndex(Object[] src, Object[] dest, int length,
      int skipIndex) {
    System.arraycopy(src, 0, dest, 0, skipIndex);
    System.arraycopy(src, skipIndex + 1, dest, skipIndex, length - skipIndex);
  }

  private void resize() {
    if (capacity == 0) {
      capacity = 1;
    } else {
      capacity *= CAPACITY_INCREASE_RATE;
    }
    Object[] newArray = new Object[capacity];
    copyArray(array, newArray);
    array = newArray;
  }

  private void copyArray(Object[] array, Object[] newArray) {
    System.arraycopy(array, 0, newArray, 0, array.length);
  }
}
