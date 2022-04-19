package com.pantifik.ds.set.hashset;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * An implementation of a hash set using separate chaining with a linked list.
 *
 * @param <E>
 *     the type of the elements in the set.
 */
public class HashSet<E> implements Set<E> {

  public static final int RESIZE_FACTOR = 2;
  private static final int DEFAULT_CAPACITY = 13;
  private static final float DEFAULT_LOAD_FACTOR = .75f;
  private final float loadFactor;
  private LinkedList<E>[] table;
  private int capacity;
  private int threshold;
  private int size;

  /**
   * Creates a hash set instance with default capacity and load factor.
   */
  public HashSet() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  /**
   * Created a hash set instance with default load factor and given capacity.
   *
   * @param capacity
   *     the initial capacity.
   * @throws IllegalArgumentException
   *     - if capacity is less than 0.
   */
  public HashSet(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  /**
   * Created a hash table instance with given capacity and load factor.
   *
   * @param capacity
   *     - the initial capacity.
   * @param loadFactor
   *     - the load factor.
   * @throws IllegalArgumentException
   *     if capacity is less than 0 or load factor is not in range [0.1, 1].
   */
  @SuppressWarnings("unchecked")
  public HashSet(int capacity, float loadFactor) {
    validateCapacity(capacity);
    validateLoadFactor(loadFactor);
    this.loadFactor = loadFactor;
    this.capacity = capacity;
    this.threshold = calculateThreshold(capacity, loadFactor);
    this.size = 0;
    this.table = new LinkedList[capacity];
  }

  /**
   * Creates a hash table instance from a given collection.
   * <p>
   * Uses the default load factor but capacity = collection.size() * 2.
   *
   * @param collection
   *     the elements to populate the hash set with.
   * @throws NullPointerException
   *     if the collection is null.
   */
  public HashSet(Collection<? extends E> collection) {
    this(collection.size() * RESIZE_FACTOR, DEFAULT_LOAD_FACTOR);
    addAll(collection);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  @Override
  public boolean contains(Object o) {
    int index = calculateIndex(o);
    LinkedList<E> list = table[index];
    if (list == null) {
      return false;
    }
    return list.contains(o);
  }

  @Override
  public Iterator<E> iterator() {
    return new SetIterator();
  }

  @Override
  public Object[] toArray() {
    if (isEmpty()) {
      return new Object[0];
    }

    Object[] array = new Object[size];
    copyIntoArray(array);

    return array;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(T[] a) {
    Objects.requireNonNull(a);
    if (isEmpty()) {
      return a;
    }
    if (a.length < size()) {
      a = (T[]) Array.newInstance(a.getClass()
          .getComponentType(), size());
    }
    copyIntoTypedArray(a);
    return a;
  }

  @Override
  public boolean add(E e) {
    Objects.requireNonNull(e);
    int index = calculateIndex(e);
    return insert(index, e);
  }

  @Override
  public boolean remove(Object o) {
    int index = calculateIndex(o);
    return retrieve(index, o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    Objects.requireNonNull(c);
    boolean hasChanged = false;
    for (E e : c) {
      if (add(e)) {
        hasChanged = true;
      }
    }
    return hasChanged;
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    Objects.requireNonNull(c);
    Iterator<E> iterator = iterator();
    int oldSize = size();
    while (iterator.hasNext()) {
      E elem = iterator.next();
      if (!c.contains(elem)) {
        iterator.remove();
      }
    }
    return oldSize != size();
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    boolean hasChanged = false;
    for (Object o : c) {
      if (remove(o)) {
        hasChanged = true;
      }
    }
    return hasChanged;
  }

  @Override
  @SuppressWarnings("unchecked")
  public void clear() {
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        table[i].clear();
        table[i] = null;
      }
    }
    size = 0;
    capacity = DEFAULT_CAPACITY;
    threshold = calculateThreshold(capacity, loadFactor);
    table = new LinkedList[capacity];
  }

  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner(",", "[", "]");
    for (int i = 0; i < capacity; i++) {
      if (isPresent(i)) {
        table[i].forEach(e -> joiner.add(Objects.toString(e)));
      }
    }
    return joiner.toString();
  }

  private void copyIntoArray(Object[] array) {
    Iterator<E> iterator = iterator();
    int index = 0;
    while (iterator.hasNext()) {
      array[index++] = iterator.next();
    }
  }

  @SuppressWarnings("unchecked")
  private <T> void copyIntoTypedArray(T[] array) {
    Iterator<E> iterator = iterator();
    int index = 0;
    while (iterator.hasNext()) {
      E elem = iterator.next();
      array[index++] = (T) elem;
    }
  }

  private int calculateThreshold(int capacity, float loadFactor) {
    return (int) (capacity * loadFactor);
  }

  private boolean retrieve(int index, Object o) {
    LinkedList<E> list = table[index];
    if (list == null || !list.remove(o)) {
      return false;
    }

    if (list.size() == 0) {
      table[index] = null;
    }
    size--;
    return true;
  }

  private boolean isPresent(int index) {
    return table[index] != null;
  }

  private boolean insert(int index, E e) {
    LinkedList<E> listAtIndex = table[index];
    if (listAtIndex == null) {
      table[index] = listAtIndex = new LinkedList<>();
    } else {
      if (listAtIndex.contains(e)) {
        return false;
      }
    }
    listAtIndex.add(e);
    if (++size > threshold) {
      resizeTable();
    }
    return true;
  }

  @SuppressWarnings("unchecked")
  private void resizeTable() {
    capacity *= RESIZE_FACTOR;
    size = 0;
    threshold = calculateThreshold(capacity, loadFactor);
    LinkedList<E>[] oldTable = table;
    table = new LinkedList[capacity];
    for (int i = 0; i < oldTable.length; i++) {
      if (oldTable[i] != null) {
        addAll(oldTable[i]);
        oldTable[i].clear();
        oldTable[i] = null;
      }
    }
  }

  private int calculateIndex(Object object) {
    int hash = generateHash(object);
    return (hash & 0x7FFFFFFF) % capacity;
  }

  private int generateHash(Object object) {
    return Objects.hashCode(object);
  }

  private void validateLoadFactor(float loadFactor) {
    if (loadFactor < 0.1f || loadFactor > 1f) {
      throw new IllegalArgumentException(
          "The load factor must be in range [0.1, 1]");
    }
  }

  private void validateCapacity(int capacity) {
    if (capacity < 0) {
      throw new IllegalArgumentException(
          "The capacity must not be less than 0");
    }
  }

  private class SetIterator implements Iterator<E> {

    private int index;

    private E current;

    private E next;

    private Iterator<E> listIterator;

    private int expectedSize;

    private boolean allowRemove = false;

    public SetIterator() {
      expectedSize = size();
      index = 0;
      current = null;
      listIterator = null;
      next = findNext();
    }

    @Override
    public boolean hasNext() {
      return next != null;
    }

    @Override
    public E next() {
      throwIfConcurrentModification();
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      current = next;
      E data = current;
      next = findNext();
      allowRemove = true;
      return data;
    }

    @Override
    public void remove() {
      if (!allowRemove) {
        throw new IllegalStateException();
      }
      allowRemove = false;
      expectedSize--;
      HashSet.this.remove(current);
    }

    private void throwIfConcurrentModification() {
      if (expectedSize != size()) {
        throw new ConcurrentModificationException();
      }
    }

    private E findNext() {
      if (listIterator == null || !listIterator.hasNext()) {
        listIterator = findNextIterator();
      }
      return listIterator == null ? null : listIterator.next();
    }

    private Iterator<E> findNextIterator() {
      LinkedList<E> list;
      do {
        list = table[index++];
      } while (index < table.length && list == null);
      return list == null ? null : list.iterator();
    }
  }

}
