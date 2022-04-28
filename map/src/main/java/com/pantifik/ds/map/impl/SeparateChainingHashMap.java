package com.pantifik.ds.map.impl;

import static com.pantifik.ds.map.utils.MapUtils.calculateMinCapacity;
import static com.pantifik.ds.map.utils.MapUtils.calculateThreshold;
import static com.pantifik.ds.map.utils.MapUtils.validateCapacity;
import static com.pantifik.ds.map.utils.MapUtils.validateLoadFactor;
import com.pantifik.ds.map.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SeparateChainingHashMap<K, V> extends AbstractMap<K, V> {


  private static final int RESIZE_FACTOR = 2;
  private static final int DEFAULT_CAPACITY = 13;
  private static final float DEFAULT_LOAD_FACTOR = .75f;
  private final float loadFactor;
  private LinkedList<Entry<K, V>>[] table;
  private int capacity;
  private int threshold;
  private int size;

  private final IntFunction<Supplier<LinkedList<Entry<K, V>>>> createNewListAtIndex
      = index -> () -> {
    var newList = new LinkedList<Entry<K, V>>();
    table[index] = newList;
    return newList;
  };

  public SeparateChainingHashMap() {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public SeparateChainingHashMap(int capacity) {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  public SeparateChainingHashMap(int capacity, float loadFactor) {
    validateCapacity(capacity);
    validateLoadFactor(loadFactor);
    this.capacity = capacity;
    this.loadFactor = loadFactor;
    this.threshold = calculateThreshold(this.capacity, this.loadFactor);
    table = new LinkedList[capacity];
    size = 0;
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
  public boolean containsKey(Object key) {
    Objects.requireNonNull(key);
    return getEntryByKey((K) key).isPresent();
  }

  @Override
  public boolean containsValue(Object value) {
    for (var list : table) {
      if (list != null) {
        for (var entry : list) {
          if (Objects.equals(entry.getValue(), value)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public V get(Object key) {
    Objects.requireNonNull(key);
    var k = (K) key;
    return getEntryByKey(k).orElseGet(() -> new SimpleEntry<>(k, null))
        .getValue();
  }

  @Override
  public V put(K key, V value) {
    Objects.requireNonNull(key);
    var entry = getEntryByKey(key);
    if (entry.isEmpty()) {
      insertEntry(new SimpleEntry<>(key, value));
      return null;
    } else {
      return updateEntry(entry.get(), value);
    }
  }

  @Override
  public V remove(Object key) {
    Objects.requireNonNull(key);
    return removeEntryByKey(key);
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    Objects.requireNonNull(m);
    m.forEach(this::put);
  }

  @Override
  public void clear() {
    threshold = 1;
    capacity = calculateMinCapacity(threshold, loadFactor);
    size = 0;
    table = new LinkedList[capacity];
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  protected Stream<Entry<K, V>> getEntryStream() {
    return Arrays.stream(table)
        .filter(Objects::nonNull)
        .flatMap(Collection::stream);
  }

  private V removeEntryByKey(Object key) {
    var index = calculateIndex((K) key);
    var list = getListAtIndex(index);

    if (list.isPresent()) {
      var l = list.get();
      V removedValue = null;
      var iterator = l.iterator();
      while (iterator.hasNext()) {
        var entry = iterator.next();
        if (Objects.equals(entry.getKey(), key)) {
          iterator.remove();
          removedValue = entry.getValue();
          size--;
          break;
        }
      }
      if (l.isEmpty()) {
        table[index] = null;
      }
      return removedValue;
    }

    return null;
  }

  private V updateEntry(Entry<K, V> entry, V value) {
    return entry.setValue(value);
  }

  private Optional<Entry<K, V>> getEntryByKey(K key) {
    if (capacity == 0) {
      return Optional.empty();
    }
    int index = calculateIndex(key);
    var list = table[index];
    if (list == null) {
      return Optional.empty();
    } else {
      return list.stream()
          .filter(e -> Objects.equals(e.getKey(), key))
          .findFirst();
    }
  }

  private void insertEntry(Entry<K, V> entry) {
    var newSize = size + 1;
    if (newSize > threshold) {
      resizeTable();
    }
    size = newSize;
    int index = calculateIndex(entry.getKey());
    var list = getListAtIndex(index).orElseGet(
        createNewListAtIndex.apply(index));
    list.add(entry);
  }

  private Optional<LinkedList<Entry<K, V>>> getListAtIndex(int index) {
    return Optional.ofNullable(table[index]);
  }

  private void resizeTable() {
    if (capacity == 0) {
      threshold = 1;
      capacity = calculateMinCapacity(threshold, loadFactor);
    } else {
      capacity *= RESIZE_FACTOR;
      threshold = calculateThreshold(capacity, loadFactor);
    }
    size = 0;
    LinkedList<Entry<K, V>>[] oldTable = table;
    table = new LinkedList[capacity];
    for (var list : oldTable) {
      if (list != null) {
        for (var entry : list) {
          insertEntry(entry);
        }
      }
    }
  }

  private int calculateIndex(K key) {
    int hash = calculateHash(key);
    return (hash & 0x7FFFFFFF) % capacity;
  }

  private int calculateHash(K key) {
    return Objects.hashCode(key);
  }


}
