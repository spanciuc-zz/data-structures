package com.pantifik.ds.tree.heap;

import java.util.Comparator;
import java.util.Objects;

class MaxHeapInvariantCheck implements HeapInvariantCheck {
  @Override
  public <T extends Comparable<T>> boolean check(T parent, T child) {
    return Objects.compare(parent, child, Comparator.nullsLast(Comparator.naturalOrder())) >= 0;
  }
}
