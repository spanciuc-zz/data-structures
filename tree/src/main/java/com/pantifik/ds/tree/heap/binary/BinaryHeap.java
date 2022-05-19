package com.pantifik.ds.tree.heap.binary;

import com.pantifik.ds.tree.heap.Heap;
import com.pantifik.ds.tree.heap.Type;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * A complete binary tree implementation of a heap.
 * <p>
 * This class uses an array to store elements.
 *
 * @param <T>
 *     type of the elements in this heap.
 */
public class BinaryHeap<T extends Comparable<T>> implements Heap<T> {

  /**
   * The default capacity of the heap.
   */
  static final int DEFAULT_CAPACITY = 15;

  /**
   * The minimum allowed capacity.
   */
  static final int MIN_CAPACITY = 0;

  /**
   * The increase factor used when a heap resize is required.
   */
  static final int CAPACITY_INCREASE_FACTOR = 2;

  /**
   * The initial size of the heap.
   */
  static final int INITIAL_SIZE = 0;

  /**
   * The default type of the heap.
   */
  static final Type DEFAULT_TYPE = Type.MAX;

  private int capacity;

  private int size;

  private T[] array;

  private Type type;

  /**
   * Creates a heap instance with the given capacity and heap type.
   *
   * @param capacity
   *     the initial capacity.
   * @param type
   *     the type of the heap.
   * @throws IllegalArgumentException
   *     if the capacity is less than {@value #MIN_CAPACITY}.
   * @throws NullPointerException
   *     if the type is null.
   */
  @SuppressWarnings("unchecked")
  public BinaryHeap(int capacity, Type type) {
    if (capacity < MIN_CAPACITY) {
      throw new IllegalArgumentException(String.format("Invalid capacity: %d", capacity));
    }
    Objects.requireNonNull(type);
    this.capacity = capacity;
    this.size = INITIAL_SIZE;
    this.type = type;
    this.array = (T[]) new Comparable[capacity];
  }

  /**
   * Creates a heap instance with default capacity and type.
   * <p>
   * The default capacity is {@value #DEFAULT_CAPACITY}.
   * <p>
   * The default type is max heap.
   */
  public BinaryHeap() {
    this(DEFAULT_CAPACITY, DEFAULT_TYPE);
  }

  /**
   * Creates a heap instance with given initial capacity and default type.
   *
   * @param capacity
   *     the capacity.
   * @throws IllegalArgumentException
   *     if the capacity is less than {@value #MIN_CAPACITY}.
   */
  public BinaryHeap(int capacity) {
    this(capacity, DEFAULT_TYPE);
  }

  /**
   * Creates and populates a heap instance with given elements.
   *
   * @param elements
   *     the elements to insert in heap.
   * @throws NullPointerException
   *     if the collection is null.
   * @throws NullPointerException
   *     if the collection contains null elements.
   */
  public BinaryHeap(Collection<T> elements) {
    this(elements.size(), DEFAULT_TYPE);
    elements.forEach(Objects::requireNonNull);
    elements.toArray(array);
    size = elements.size();
    heapify();
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public Optional<T> getElement() {
    if (size() == 0) {
      return Optional.empty();
    }
    return removeAt(0);
  }

  @Override
  public Optional<T> peek() {
    if (size() == 0) {
      return Optional.empty();
    } else {
      return Optional.of(array[0]);
    }
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public void convertTo(Type type) {
    if (this.type != type) {
      this.type = type;
      heapify();
    }
  }

  @Override
  public boolean add(T element) {
    Objects.requireNonNull(element);
    if (size + 1 > capacity) {
      resize();
    }
    array[size] = element;
    moveUp(size);
    size++;
    return true;
  }

  @Override
  public boolean remove(T element) {
    Objects.requireNonNull(element);
    int index = indexOf(element);
    if (index != -1) {
      removeAt(index);
      return true;
    } else {
      return false;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public void clear() {
    capacity = DEFAULT_CAPACITY;
    size = INITIAL_SIZE;
    array = (T[]) new Comparable[capacity];
  }

  @Override
  public boolean contains(T element) {
    if (element == null) {
      return false;
    }
    return indexOf(element) != -1;
  }

  /**
   * Gets the capacity of the heap.
   *
   * @return the capacity.
   */
  int getCapacity() {
    return capacity;
  }

  private void heapify() {
    int midIndex = size() / 2;
    for (int i = midIndex; i >= 0; i--) {
      moveDown(i);
    }
  }

  private int indexOf(T element) {
    for (int i = 0; i < size(); i++) {
      if (array[i].equals(element)) {
        return i;
      }
    }
    return -1;
  }

  private Optional<T> removeAt(int index) {
    int lastIndex = size - 1;
    swap(array, index, lastIndex);
    Optional<T> result = Optional.of(array[lastIndex]);
    array[lastIndex] = null;
    size--;
    moveDown(index);
    return result;
  }

  private void moveDown(int index) {
    int leftChildIndex = calculateLeftChildIndex(index);
    int rightChildIndex = leftChildIndex + 1;
    getNextChildIndex(leftChildIndex, rightChildIndex).ifPresent(i -> {
      if (!type.invariantCheck(array[index], array[i])) {
        swap(array, index, i);
        moveDown(i);
      }
    });
  }

  private OptionalInt getNextChildIndex(int leftChildIndex, int rightChildIndex) {
    if (leftChildIndex >= size()) {
      return OptionalInt.empty();
    } else if (rightChildIndex >= size()) {
      return OptionalInt.of(leftChildIndex);
    } else {
      int index = type.invariantCheck(array[leftChildIndex], array[rightChildIndex]) ?
          leftChildIndex : rightChildIndex;
      return OptionalInt.of(index);
    }
  }

  private int calculateLeftChildIndex(int index) {
    return index * 2 + 1;
  }

  private void moveUp(int index) {
    if (index == 0) {
      return;
    }
    int parentIndex = calculateParentIndex(index);
    if (!type.invariantCheck(array[parentIndex], array[index])) {
      swap(array, parentIndex, index);
      moveUp(parentIndex);
    }
  }

  private void swap(T[] array, int from, int to) {
    T temp = array[to];
    array[to] = array[from];
    array[from] = temp;
  }

  private int calculateParentIndex(int index) {
    return (index - 1) / 2;
  }


  @SuppressWarnings("unchecked")
  private void resize() {
    if (capacity == 0) {
      capacity = 1;
    } else {
      capacity *= CAPACITY_INCREASE_FACTOR;
    }
    T[] newArray = (T[]) new Comparable[capacity];
    System.arraycopy(array, 0, newArray, 0, size());
    array = newArray;
  }
}
