package com.pantifik.ds.tree.heap;

import java.util.Comparator;
import java.util.Objects;

class MinHeapInvariantCheck implements HeapInvariantCheck {

  @Override
  public <T extends Comparable<T>> boolean check(T parent, T child) {
    return Objects.compare(parent, child, Comparator.nullsFirst(Comparator.naturalOrder())) <= 0;
  }
}
