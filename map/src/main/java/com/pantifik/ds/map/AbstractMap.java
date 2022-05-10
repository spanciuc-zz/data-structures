package com.pantifik.ds.map;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractMap<K, V> implements Map<K, V> {

  private static final String NULL_STR = "null";
  private static final String KEY_VALUE_DELIMITER = ":";

  /**
   * Unmodifiable set.
   */
  @Override
  public Set<K> keySet() {
    return getEntryStream().map(Entry::getKey)
        .collect(Collectors.toUnmodifiableSet());
  }

  /**
   * Unmodifiable list.
   */
  @Override
  public Collection<V> values() {
    return getEntryStream().map(Entry::getValue)
        .toList();
  }

  /**
   * Unmodifiable set.
   */
  @Override
  public Set<Entry<K, V>> entrySet() {
    return getEntryStream().collect(Collectors.toUnmodifiableSet());
  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    Objects.requireNonNull(m);
    m.forEach(this::put);
  }

  @Override
  public int hashCode() {
    int result = 0;
    for (var entry : entrySet()) {
      result = 31 * result + Objects.hashCode(entry);
    }
    return result;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {return true;}
    if (o instanceof Map<?, ?> map) {
      if (size() != map.size()) {
        return false;
      }
      for (var entry : entrySet()) {
        try {
          if (!Objects.equals(entry.getValue(), map.get(entry.getKey()))) {
            return false;
          }
        } catch (NullPointerException ignored) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringJoiner sj = new StringJoiner(", ", "{", "}");
    entrySet().forEach(entry -> sj.add(entry.toString()));
    return sj.toString();
  }

  protected abstract Stream<Entry<K, V>> getEntryStream();

  public static class SimpleEntry<K, V> implements Map.Entry<K, V> {

    private final K key;

    private V value;

    public SimpleEntry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public K getKey() {
      return key;
    }

    @Override
    public V getValue() {
      return value;
    }

    @Override
    public V setValue(V value) {
      var oldValue = this.value;
      this.value = value;
      return oldValue;
    }

    @Override
    public int hashCode() {
      return Objects.hash(key, value);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {return true;}
      if (o instanceof SimpleEntry<?, ?> entry) {
        return Objects.equals(key, entry.getKey()) && Objects.equals(value,
            entry.getValue());
      } else {
        return false;
      }
    }

    @Override
    public String toString() {
      return key + KEY_VALUE_DELIMITER + Objects.toString(value, NULL_STR);
    }
  }
}
