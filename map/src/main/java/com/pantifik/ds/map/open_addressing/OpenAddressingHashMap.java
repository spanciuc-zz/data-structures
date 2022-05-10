package com.pantifik.ds.map.open_addressing;

import static com.pantifik.ds.map.utils.MapUtils.calculateMinCapacity;
import static com.pantifik.ds.map.utils.MapUtils.calculateThreshold;
import static com.pantifik.ds.map.utils.MapUtils.validateLoadFactor;
import com.pantifik.ds.map.AbstractMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * An open addressing based hash map implementation.
 *
 * @param <K>
 *     the type of the keys.
 * @param <V>
 *     the type of the values.
 */
public class OpenAddressingHashMap<K, V> extends AbstractMap<K, V> {

  private static final String DELETED_MARK_ENTRY_KEY = "DELETED";
  private static final int RESIZE_FACTOR = 2;
  private static final int DEFAULT_CAPACITY = 13;
  private static final float DEFAULT_LOAD_FACTOR = .75f;
  private final Entry<K, V> deletedMark = new SimpleEntry(DELETED_MARK_ENTRY_KEY, null);
  private final float loadFactor;
  private final Probing probing;
  private Entry<K, V>[] table;
  private int capacity;
  private int threshold;
  private int size;

  public OpenAddressingHashMap(ProbingType probingType) {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, probingType);
  }

  public OpenAddressingHashMap(int capacity, float loadFactor, ProbingType probingType) {
    validateLoadFactor(loadFactor);
    this.capacity = capacity;
    this.loadFactor = loadFactor;
    this.threshold = calculateThreshold(this.capacity, this.loadFactor);
    this.table = new Entry[capacity];
    this.size = 0;
    this.probing = probingType.createProbingInstance(this);
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
    for (int i = 0; i < table.length; i++) {
      var entry = table[i];
      if (entry != null && Objects.equals(entry.getValue(), value)) {
        return true;
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
  public void clear() {
    threshold = 1;
    capacity = probing.nextValidCapacity(calculateMinCapacity(threshold, loadFactor));
    size = 0;
    table = new Entry[capacity];
  }

  @Override
  protected Stream<Entry<K, V>> getEntryStream() {
    return Arrays.stream(table)
        .filter(Objects::nonNull);
  }

  int getCapacity() {
    return capacity;
  }

  private V removeEntryByKey(Object key) {
    int hash = calculateHash((K) key);
    Entry<K, V> entry = null;
    int probeNr = 0;
    int index = calculateIndex(hash, probeNr);
    while ((entry = table[index]) != null) {
      if (Objects.equals(entry.getKey(), key)) {
        table[index] = deletedMark;
        size--;
        return entry.getValue();
      } else {
        index = calculateIndex(hash, ++probeNr);
      }
    }
    return null;
  }

  private V updateEntry(Entry<K, V> entry, V value) {
    return entry.setValue(value);
  }

  private void insertEntry(Entry<K, V> entry) {
    var newSize = size + 1;
    if (newSize > threshold) {
      resizeTable();
    }
    size = newSize;
    int hash = calculateHash(entry.getKey());
    int probeNr = 0;
    int index = calculateIndex(hash, probeNr);
    while (table[index] != null && !Objects.equals(table[index], deletedMark)) {
      index = calculateIndex(hash, ++probeNr);
    }
    table[index] = entry;
  }

  private void resizeTable() {
    capacity = probing.nextValidCapacity(capacity * RESIZE_FACTOR);
    threshold = calculateThreshold(capacity, loadFactor);
    size = 0;
    Entry<K, V>[] oldTable = table;
    table = new Entry[capacity];
    for (var entry : oldTable) {
      if (entry != null) {
        insertEntry(entry);
      }
    }
  }

  private Optional<Entry<K, V>> getEntryByKey(K key) {
    int hash = calculateHash(key);
    Entry<K, V> entry = null;
    int probeNr = 0;
    int index = calculateIndex(hash, probeNr);
    while ((entry = table[index]) != null) {
      if (Objects.equals(entry.getKey(), key)) {
        return Optional.of(entry);
      } else {
        index = calculateIndex(hash, ++probeNr);
      }
    }
    return Optional.empty();
  }

  private int calculateIndex(int hash, int probeNr) {
    return ((hash + probing.probe(probeNr)) & 0x7FFFFFFF) % capacity;
  }

  private int calculateHash(K key) {
    return Objects.hashCode(key);
  }
}
