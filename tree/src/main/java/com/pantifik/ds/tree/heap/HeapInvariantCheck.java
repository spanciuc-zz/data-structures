package com.pantifik.ds.tree.heap;

@FunctionalInterface
public interface HeapInvariantCheck {

  <T extends Comparable<T>> boolean check(T first, T second);

}
