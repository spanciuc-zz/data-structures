package com.pantifik.ds.tree.heap;

/**
 * Defines heap types.
 */
public enum Type {
  /**
   * Defines the type of heap used to access the max element.
   */
  MAX(new MaxHeapInvariantCheck()),
  /**
   * Defines the type of the heap used to access the min element.
   */
  MIN(new MinHeapInvariantCheck());

  private final HeapInvariantCheck heapInvariantCheck;

  Type(HeapInvariantCheck heapInvariantCheck) {this.heapInvariantCheck = heapInvariantCheck;}

  public <T extends Comparable<T>> boolean invariantCheck(T a, T b) {
    return heapInvariantCheck.check(a, b);
  }
}
