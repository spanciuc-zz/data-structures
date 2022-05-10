package com.pantifik.ds.map.open_addressing;

import java.util.function.Function;

/**
 * Defines different probing types for the hash map.
 */
public enum ProbingType {
  /**
   * Uses linear function.
   */
  LINEAR(hashMap -> new LinearProbing(5, hashMap.getCapacity()));

  private final Function<OpenAddressingHashMap<?, ?>, Probing> probingCreator;

  ProbingType(Function<OpenAddressingHashMap<?, ?>, Probing> createProbingInstance) {
    this.probingCreator = createProbingInstance;
  }

  Probing createProbingInstance(OpenAddressingHashMap<?, ?> hashMap) {
    return this.probingCreator.apply(hashMap);
  }

}
